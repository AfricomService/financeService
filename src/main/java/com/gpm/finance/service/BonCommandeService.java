package com.gpm.finance.service;

import com.gpm.finance.client.UserContactRestClient;
import com.gpm.finance.domain.AclSid;
import com.gpm.finance.domain.BonCommande;
import com.gpm.finance.domain.BonCommandeAutreResponsable;
import com.gpm.finance.domain.enumeration.AclPermission;
import com.gpm.finance.domain.enumeration.StatutBC;
import com.gpm.finance.repository.BonCommandeAutreResponsableRepository;
import com.gpm.finance.repository.BonCommandeRepository;
import com.gpm.finance.security.AuthoritiesConstants;
import com.gpm.finance.security.SecurityUtils;
import com.gpm.finance.service.dto.BonCommandeDTO;
import com.gpm.finance.service.mapper.BonCommandeMapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Service Implementation for managing {@link BonCommande}.
 */
@Service
@Transactional
public class BonCommandeService {

    private static final String OBJECT_TYPE = "BONCOMMANDE"; // matches acl_entry.object_type

    private final Logger log = LoggerFactory.getLogger(BonCommandeService.class);

    private final BonCommandeRepository bonCommandeRepository;

    private final BonCommandeMapper bonCommandeMapper;

    private final AclUtilService aclUtilService;

    private final UserContactRestClient userContactRestClient;

    private final BonCommandeAutreResponsableRepository bonCommandeAutreResponsableRepository;

    public BonCommandeService(
        BonCommandeRepository bonCommandeRepository,
        BonCommandeMapper bonCommandeMapper,
        AclUtilService aclUtilService,
        UserContactRestClient userContactRestClient,
        BonCommandeAutreResponsableRepository bonCommandeAutreResponsableRepository
    ) {
        this.bonCommandeRepository = bonCommandeRepository;
        this.bonCommandeMapper = bonCommandeMapper;
        this.aclUtilService = aclUtilService;
        this.userContactRestClient = userContactRestClient;
        this.bonCommandeAutreResponsableRepository = bonCommandeAutreResponsableRepository;
    }

    /**
     * Save a bonCommande.
     *
     * @param bonCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeDTO save(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to save BonCommande : {}", bonCommandeDTO);
        String currentLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new RuntimeException("Current user login not found"));

        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande.setStatus(StatutBC.Brouillon.name());
        bonCommande = bonCommandeRepository.save(bonCommande);

        Long societeId = userContactRestClient.getSocieteId(bonCommande.getAffaireId());

        // 1. Creator: READ + WRITE
        aclUtilService.grantOwner(OBJECT_TYPE, bonCommande.getId(), currentLogin.toLowerCase());

        // 2. Company logistics contacts: READ
        List<String> matriculesToGrantPermissionLogistics = userContactRestClient.getMatriculesToGrantPermission("LOGISTIQUE", societeId);

        BonCommande finalBonCommande = bonCommande;
        matriculesToGrantPermissionLogistics.forEach(matricule ->
            aclUtilService.grantToUser(OBJECT_TYPE, finalBonCommande.getId(), matricule.toLowerCase(), AclPermission.READ)
        );

        // 3. Company MANAGERS contacts: READ

        List<String> matriculesToGrantPermissionManagers = userContactRestClient.getMatriculesToGrantPermission("LOGISTIQUE", societeId);

        matriculesToGrantPermissionManagers.forEach(matricule ->
            aclUtilService.grantToUser(OBJECT_TYPE, finalBonCommande.getId(), matricule.toLowerCase(), AclPermission.READ)
        );

        // 4. BON COMMANDE RESP : READ
        String responsaleMatricule = userContactRestClient.getMatriculeByContactSocieteId(
            Long.valueOf(finalBonCommande.getResponsableId())
        );
        aclUtilService.grantToUser(OBJECT_TYPE, finalBonCommande.getId(), responsaleMatricule, AclPermission.READ);

        // 5. BON COMMANDE AUTRE RESP : READ
        List<Long> autresRespIds = bonCommandeAutreResponsableRepository
            .findByBonCommandeId(finalBonCommande.getId())
            .stream()
            .map(bonCommandeAutreResp -> bonCommandeAutreResp.getContactSocieteId())
            .collect(Collectors.toList());
        List<String> autresRespMatricules = userContactRestClient.getMatriculesByContactSocieteIds(autresRespIds);
        autresRespMatricules.forEach(matricule ->
            aclUtilService.grantToUser(OBJECT_TYPE, finalBonCommande.getId(), matricule.toLowerCase(), AclPermission.READ)
        );

        return withPermissions(bonCommandeMapper.toDto(bonCommande));
    }

    /**
     * Update a bonCommande.
     *
     * @param bonCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeDTO update(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to update BonCommande : {}", bonCommandeDTO);
        assertWrite(bonCommandeDTO.getId()); // << ACL
        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande = bonCommandeRepository.save(bonCommande);
        return withPermissions(bonCommandeMapper.toDto(bonCommande));
    }

    /**
     * Partially update a bonCommande.
     *
     * @param bonCommandeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BonCommandeDTO> partialUpdate(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to partially update BonCommande : {}", bonCommandeDTO);
        assertWrite(bonCommandeDTO.getId()); // << ACL

        return bonCommandeRepository
            .findById(bonCommandeDTO.getId())
            .map(existingBonCommande -> {
                bonCommandeMapper.partialUpdate(existingBonCommande, bonCommandeDTO);

                return existingBonCommande;
            })
            .map(bonCommandeRepository::save)
            .map(bonCommandeMapper::toDto)
            .map(this::withPermissions);
    }

    /**
     * Get all the bonCommandes for a given affaire, optionnellement filtrées par statut.
     *
     * @param affaireId the id of the affaire.
     * @param status le statut à filtrer (nullable / blank = pas de filtre).
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BonCommandeDTO> findByAffaireId(Long affaireId, String status) {
        log.debug("Request to get BonCommandes by affaireId : {} and status : {}", affaireId, status);

        List<BonCommande> bonCommandes = StringUtils.hasText(status)
            ? bonCommandeRepository.findByAffaireIdAndStatus(affaireId, status)
            : bonCommandeRepository.findByAffaireId(affaireId);

        return bonCommandes.stream().map(bonCommandeMapper::toDto).collect(java.util.stream.Collectors.toList());
    }

    /**
     * Get all the bonCommandes matching a free-text search (referenceClient, lieu, identifiantUnique).
     *
     * @param pageable the pagination information.
     * @param search the search term (nullable / blank = no filter).
     * @return the list of entities.
     */
    //    @Transactional(readOnly = true)
    //    public Page<BonCommandeDTO> findAll(Pageable pageable, String search) {
    //        log.debug("Request to get all BonCommandes matching search : {}", search);
    //        return bonCommandeRepository.findAll(buildSearchSpecification(search), pageable).map(bonCommandeMapper::toDto);
    //    }

    @Transactional(readOnly = true)
    public Page<BonCommandeDTO> findAll(Pageable pageable, String search) {
        log.debug("Request to get all Affaires");

        // << ACL : bypass admin
        if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            return bonCommandeRepository.findAll(pageable).map(bonCommandeMapper::toDto);
        }

        // << ACL : seulement les affaires accessibles (filtrage en base, pagination préservée)
        List<Long> sidIds = aclUtilService.getCurrentUserSids().stream().map(AclSid::getId).collect(Collectors.toList());
        if (sidIds.isEmpty()) {
            return Page.empty(pageable);
        }
        return bonCommandeRepository.findAllAccessible(sidIds, pageable).map(bonCommandeMapper::toDto);
    }

    private Specification<BonCommande> buildSearchSpecification(String search) {
        if (!StringUtils.hasText(search)) {
            return null;
        }
        String likePattern = "%" + search.trim().toLowerCase() + "%";

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.like(cb.lower(root.get("referenceClient")), likePattern));
            predicates.add(cb.like(cb.lower(root.get("lieu")), likePattern));
            predicates.add(cb.like(cb.lower(root.get("identifiantUnique")), likePattern));
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Get all the bonCommandes for a given affaire.
     *
     * @param affaireId the id of the affaire.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BonCommandeDTO> findByAffaireId(Long affaireId) {
        log.debug("Request to get BonCommandes by affaireId : {}", affaireId);
        return bonCommandeRepository
            .findByAffaireId(affaireId)
            .stream()
            .map(bonCommandeMapper::toDto)
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Get one bonCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BonCommandeDTO> findOne(Long id) {
        log.debug("Request to get BonCommande : {}", id);
        return bonCommandeRepository.findById(id).map(bonCommandeMapper::toDto).map(this::withPermissions);
    }

    private BonCommandeDTO withPermissions(BonCommandeDTO dto) {
        dto.setCanRead(aclUtilService.canRead(OBJECT_TYPE, dto.getId()));
        dto.setCanWrite(aclUtilService.canWrite(OBJECT_TYPE, dto.getId()));
        return dto;
    }

    /**
     * Delete the bonCommande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BonCommande : {}", id);
        assertWrite(id); // << ACL
        bonCommandeRepository.deleteById(id);
    }

    private void assertWrite(Long id) {
        if (!aclUtilService.canWrite(OBJECT_TYPE, id)) {
            throw new AccessDeniedException("Pas d'accès en écriture à l'affaire " + id);
        }
    }

    private void assertCanChangeStatut(Long id) {
        boolean privileged = SecurityUtils.hasCurrentUserAnyOfAuthorities(
            AuthoritiesConstants.ADMIN,
            AuthoritiesConstants.CAN_ACTIVATE_BON_COMMANDE
        );
        if (!privileged && !aclUtilService.canWrite(OBJECT_TYPE, id)) {
            throw new AccessDeniedException("Pas d'accès en écriture à l'affaire " + id);
        }
    }

    public void changeStatut(String newStatut, Long bonCommandeId) {
        assertCanChangeStatut(bonCommandeId);
        BonCommande bonCommande = bonCommandeRepository
            .findById(bonCommandeId)
            .orElseThrow(() -> new RuntimeException("bonCommande not found"));

        bonCommande.setStatus(newStatut);
        bonCommande = bonCommandeRepository.save(bonCommande);

        switch (newStatut) {
            case "ExecutionDesTravaux":
            case "ConfirmationCommande":
                aclUtilService.makeReadOnlyForAll(OBJECT_TYPE, bonCommande.getId());
                applyAclOnConfirmation(bonCommande);
                break;
            case "Fin":
                // everyone becomes READ only
                aclUtilService.makeReadOnlyForAll(OBJECT_TYPE, bonCommande.getId());
                break;
            default:
                throw new RuntimeException("Statut invalide : " + newStatut);
        }
    }

    /**
     * ConfirmationCommande:
     *  - responsable + autres responsables get WRITE
     *  - creator is downgraded to READ only
     */
    private void applyAclOnConfirmation(BonCommande bonCommande) {
        Long bcId = bonCommande.getId();

        // Responsable: WRITE
        String responsableMatricule = userContactRestClient.getMatriculeByContactSocieteId(Long.valueOf(bonCommande.getResponsableId()));
        aclUtilService.grantToUser(OBJECT_TYPE, bcId, responsableMatricule.toLowerCase(), AclPermission.WRITE);

        // Autres responsables: WRITE
        List<Long> autresRespIds = bonCommandeAutreResponsableRepository
            .findByBonCommandeId(bcId)
            .stream()
            .map(BonCommandeAutreResponsable::getContactSocieteId)
            .collect(Collectors.toList());
        List<String> autresRespMatricules = userContactRestClient.getMatriculesByContactSocieteIds(autresRespIds);
        autresRespMatricules.forEach(matricule ->
            aclUtilService.grantToUser(OBJECT_TYPE, bcId, matricule.toLowerCase(), AclPermission.WRITE)
        );

        //CLIENT READ
        String clientCode = userContactRestClient.getClientCode(bonCommande.getClientId());
        aclUtilService.grantToUser(OBJECT_TYPE, bonCommande.getId(), clientCode.toLowerCase(), AclPermission.READ);
    }
}

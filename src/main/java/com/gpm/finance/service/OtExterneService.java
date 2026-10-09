package com.gpm.finance.service;

import com.gpm.finance.client.UserContactRestClient;
import com.gpm.finance.domain.AclSid;
import com.gpm.finance.domain.OtExterne;
import com.gpm.finance.domain.OtExterneAutreResponsable;
import com.gpm.finance.domain.enumeration.AclPermission;
import com.gpm.finance.domain.enumeration.StatutOtExterne;
import com.gpm.finance.repository.OtExterneAutreResponsableRepository;
import com.gpm.finance.repository.OtExterneRepository;
import com.gpm.finance.security.AuthoritiesConstants;
import com.gpm.finance.security.SecurityUtils;
import com.gpm.finance.service.dto.OtExterneDTO;
import com.gpm.finance.service.mapper.OtExterneMapper;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OtExterne}.
 */
@Service
@Transactional
public class OtExterneService {

    private final Logger log = LoggerFactory.getLogger(OtExterneService.class);

    private final OtExterneRepository otExterneRepository;

    private final OtExterneMapper otExterneMapper;

    private static final String OBJECT_TYPE = "OTEXTERNE";

    private final AclUtilService aclUtilService;

    private final UserContactRestClient userContactRestClient;

    private final OtExterneAutreResponsableRepository otExterneAutreResponsableRepository;

    public OtExterneService(
        OtExterneRepository otExterneRepository,
        OtExterneMapper otExterneMapper,
        AclUtilService aclUtilService,
        UserContactRestClient userContactRestClient,
        OtExterneAutreResponsableRepository otExterneAutreResponsableRepository
    ) {
        this.otExterneRepository = otExterneRepository;
        this.otExterneMapper = otExterneMapper;
        this.aclUtilService = aclUtilService;
        this.userContactRestClient = userContactRestClient;
        this.otExterneAutreResponsableRepository = otExterneAutreResponsableRepository;
    }

    /**
     * Save a otExterne.
     *
     * @param otExterneDTO the entity to save.
     * @return the persisted entity.
     */
    public OtExterneDTO save(OtExterneDTO otExterneDTO) {
        log.debug("Request to save OtExterne : {}", otExterneDTO);
        OtExterne otExterne = otExterneMapper.toEntity(otExterneDTO);
        // Un nouvel OT externe est toujours créé avec le statut ACTIF,
        // quelle que soit la valeur (ou l'absence de valeur) envoyée par le frontend.
        otExterne.setStatut(StatutOtExterne.Brouillon);

        String currentLogin = SecurityUtils.getCurrentUserLogin().orElse("system");
        Instant now = Instant.now();
        otExterne.setCreatedAt(now.atZone(java.time.ZoneId.systemDefault()));
        otExterne.setCreatedBy(currentLogin);
        otExterne.setCreatedByUserLogin(currentLogin);
        otExterne.setUpdatedAt(now.atZone(java.time.ZoneId.systemDefault()));
        otExterne.setUpdatedBy(currentLogin);
        otExterne.setUpdatedByUserLogin(currentLogin);

        otExterne = otExterneRepository.save(otExterne);

        Long societeId = userContactRestClient.getSocieteId(otExterne.getAffaireId());

        // 1. Creator: READ + WRITE
        aclUtilService.grantOwner(OBJECT_TYPE, otExterne.getId(), currentLogin.toLowerCase());

        // 2. Company logistics contacts: READ
        List<String> matriculesToGrantPermissionLogistics = userContactRestClient.getMatriculesToGrantPermission("LOGISTIQUE", societeId);

        OtExterne finalotExterne = otExterne;
        matriculesToGrantPermissionLogistics.forEach(matricule ->
            aclUtilService.grantToUser(OBJECT_TYPE, finalotExterne.getId(), matricule.toLowerCase(), AclPermission.READ)
        );

        // 3. Company MANAGERS contacts: READ

        List<String> matriculesToGrantPermissionManagers = userContactRestClient.getMatriculesToGrantPermission("LOGISTIQUE", societeId);

        matriculesToGrantPermissionManagers.forEach(matricule ->
            aclUtilService.grantToUser(OBJECT_TYPE, finalotExterne.getId(), matricule.toLowerCase(), AclPermission.READ)
        );

        // 4. BON COMMANDE RESP : READ
        String responsaleMatricule = userContactRestClient.getMatriculeByContactSocieteId(finalotExterne.getResponsableId());
        aclUtilService.grantToUser(OBJECT_TYPE, finalotExterne.getId(), responsaleMatricule, AclPermission.READ);

        // 5. BON COMMANDE AUTRE RESP : READ
        List<Long> autresRespIds = otExterneAutreResponsableRepository
            .findByOtExterneId(finalotExterne.getId())
            .stream()
            .map(OtExterneAutreResponsable::getContactSocieteId)
            .collect(Collectors.toList());
        List<String> autresRespMatricules = userContactRestClient.getMatriculesByContactSocieteIds(autresRespIds);
        autresRespMatricules.forEach(matricule ->
            aclUtilService.grantToUser(OBJECT_TYPE, finalotExterne.getId(), matricule.toLowerCase(), AclPermission.READ)
        );

        return withPermissions(otExterneMapper.toDto(otExterne));
    }

    /**
     * Update a otExterne.
     *
     * @param otExterneDTO the entity to save.
     * @return the persisted entity.
     */
    public OtExterneDTO update(OtExterneDTO otExterneDTO) {
        log.debug("Request to update OtExterne : {}", otExterneDTO);
        assertWrite(otExterneDTO.getId()); // << ACL
        OtExterne otExterne = otExterneMapper.toEntity(otExterneDTO);

        String currentLogin = SecurityUtils.getCurrentUserLogin().orElse("system");
        otExterne.setUpdatedAt(Instant.now().atZone(java.time.ZoneId.systemDefault()));
        otExterne.setUpdatedBy(currentLogin);
        otExterne.setUpdatedByUserLogin(currentLogin);

        otExterne = otExterneRepository.save(otExterne);
        return withPermissions(otExterneMapper.toDto(otExterne));
    }

    /**
     * Partially update a otExterne.
     *
     * @param otExterneDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OtExterneDTO> partialUpdate(OtExterneDTO otExterneDTO) {
        log.debug("Request to partially update OtExterne : {}", otExterneDTO);

        assertWrite(otExterneDTO.getId()); // << ACL

        return otExterneRepository
            .findById(otExterneDTO.getId())
            .map(existingOtExterne -> {
                otExterneMapper.partialUpdate(existingOtExterne, otExterneDTO);

                String currentLogin = SecurityUtils.getCurrentUserLogin().orElse("system");
                existingOtExterne.setUpdatedAt(Instant.now().atZone(java.time.ZoneId.systemDefault()));
                existingOtExterne.setUpdatedBy(currentLogin);
                existingOtExterne.setUpdatedByUserLogin(currentLogin);

                return existingOtExterne;
            })
            .map(otExterneRepository::save)
            .map(otExterneMapper::toDto)
            .map(this::withPermissions);
    }

    /**
     * Get all the otExternes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OtExterneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Affaires");

        // << ACL : bypass admin
        if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            return otExterneRepository.findAll(pageable).map(otExterneMapper::toDto);
        }

        // << ACL : seulement les affaires accessibles (filtrage en base, pagination préservée)
        List<Long> sidIds = aclUtilService.getCurrentUserSids().stream().map(AclSid::getId).collect(Collectors.toList());
        if (sidIds.isEmpty()) {
            return Page.empty(pageable);
        }
        return otExterneRepository.findAllAccessible(sidIds, pageable).map(otExterneMapper::toDto);
    }

    /**
     * Get one otExterne by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OtExterneDTO> findOne(Long id) {
        log.debug("Request to get OtExterne : {}", id);
        return otExterneRepository.findById(id).map(otExterneMapper::toDto).map(this::withPermissions);
    }

    private OtExterneDTO withPermissions(OtExterneDTO dto) {
        dto.setCanRead(aclUtilService.canRead(OBJECT_TYPE, dto.getId()));
        dto.setCanWrite(aclUtilService.canWrite(OBJECT_TYPE, dto.getId()));
        return dto;
    }

    /**
     * Delete the otExterne by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OtExterne : {}", id);
        assertWrite(id); // << ACL
        otExterneRepository.deleteById(id);
    }

    private void assertWrite(Long id) {
        if (!aclUtilService.canWrite(OBJECT_TYPE, id)) {
            throw new AccessDeniedException("Pas d'accès en écriture à l'affaire " + id);
        }
    }

    private void assertCanChangeStatut(Long id) {
        boolean privileged = SecurityUtils.hasCurrentUserAnyOfAuthorities(
            AuthoritiesConstants.ADMIN,
            AuthoritiesConstants.CAN_ACTIVATE_OT_EXTERNE
        );
        if (!privileged && !aclUtilService.canWrite(OBJECT_TYPE, id)) {
            throw new AccessDeniedException("Pas d'accès en écriture à l'affaire " + id);
        }
    }

    public void changeStatut(String newStatut, Long otExterneId) {
        assertCanChangeStatut(otExterneId);
        OtExterne otExterne = otExterneRepository.findById(otExterneId).orElseThrow(() -> new RuntimeException("bonCommande not found"));

        otExterne.setStatut(StatutOtExterne.valueOf(newStatut));
        otExterne = otExterneRepository.save(otExterne);

        switch (newStatut) {
            case "ExecutionDesTravaux":
                aclUtilService.makeReadOnlyForAll(OBJECT_TYPE, otExterne.getId());
                applyAclOnConfirmation(otExterne);
                break;
            case "Fin":
                // everyone becomes READ only
                aclUtilService.makeReadOnlyForAll(OBJECT_TYPE, otExterne.getId());
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
    private void applyAclOnConfirmation(OtExterne otExterne) {
        Long otExterneId = otExterne.getId();

        // Responsable: WRITE
        String responsableMatricule = userContactRestClient.getMatriculeByContactSocieteId(Long.valueOf(otExterne.getResponsableId()));
        aclUtilService.grantToUser(OBJECT_TYPE, otExterneId, responsableMatricule.toLowerCase(), AclPermission.WRITE);

        // Autres responsables: WRITE
        List<Long> autresRespIds = otExterneAutreResponsableRepository
            .findByOtExterneId(otExterneId)
            .stream()
            .map(OtExterneAutreResponsable::getContactSocieteId)
            .collect(Collectors.toList());
        List<String> autresRespMatricules = userContactRestClient.getMatriculesByContactSocieteIds(autresRespIds);
        autresRespMatricules.forEach(matricule ->
            aclUtilService.grantToUser(OBJECT_TYPE, otExterneId, matricule.toLowerCase(), AclPermission.WRITE)
        );

        //CLIENT READ
        String clientCode = userContactRestClient.getClientCode(otExterne.getClientId());
        aclUtilService.grantToUser(OBJECT_TYPE, otExterne.getId(), clientCode.toLowerCase(), AclPermission.READ);
    }
}

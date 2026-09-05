package com.gpm.finance.service;

import com.gpm.finance.domain.BonCommandeAutreResponsable;
import com.gpm.finance.repository.BonCommandeAutreResponsableRepository;
import com.gpm.finance.service.dto.BonCommandeAutreResponsableDTO;
import com.gpm.finance.service.mapper.BonCommandeAutreResponsableMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BonCommandeAutreResponsable}.
 */
@Service
@Transactional
public class BonCommandeAutreResponsableService {

    private final Logger log = LoggerFactory.getLogger(BonCommandeAutreResponsableService.class);

    private final BonCommandeAutreResponsableRepository bonCommandeAutreResponsableRepository;

    private final BonCommandeAutreResponsableMapper bonCommandeAutreResponsableMapper;

    public BonCommandeAutreResponsableService(
        BonCommandeAutreResponsableRepository bonCommandeAutreResponsableRepository,
        BonCommandeAutreResponsableMapper bonCommandeAutreResponsableMapper
    ) {
        this.bonCommandeAutreResponsableRepository = bonCommandeAutreResponsableRepository;
        this.bonCommandeAutreResponsableMapper = bonCommandeAutreResponsableMapper;
    }

    /**
     * Save a bonCommandeAutreResponsable.
     *
     * @param bonCommandeAutreResponsableDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeAutreResponsableDTO save(BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO) {
        log.debug("Request to save BonCommandeAutreResponsable : {}", bonCommandeAutreResponsableDTO);
        BonCommandeAutreResponsable bonCommandeAutreResponsable = bonCommandeAutreResponsableMapper.toEntity(
            bonCommandeAutreResponsableDTO
        );
        bonCommandeAutreResponsable = bonCommandeAutreResponsableRepository.save(bonCommandeAutreResponsable);
        return bonCommandeAutreResponsableMapper.toDto(bonCommandeAutreResponsable);
    }

    /**
     * Partially update a bonCommandeAutreResponsable.
     *
     * @param bonCommandeAutreResponsableDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BonCommandeAutreResponsableDTO> partialUpdate(BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO) {
        log.debug("Request to partially update BonCommandeAutreResponsable : {}", bonCommandeAutreResponsableDTO);

        return bonCommandeAutreResponsableRepository
            .findById(bonCommandeAutreResponsableDTO.getId())
            .map(existingBonCommandeAutreResponsable -> {
                bonCommandeAutreResponsableMapper.partialUpdate(existingBonCommandeAutreResponsable, bonCommandeAutreResponsableDTO);

                return existingBonCommandeAutreResponsable;
            })
            .map(bonCommandeAutreResponsableRepository::save)
            .map(bonCommandeAutreResponsableMapper::toDto);
    }

    /**
     * Get all the bonCommandeAutreResponsables.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BonCommandeAutreResponsableDTO> findAll() {
        log.debug("Request to get all BonCommandeAutreResponsables");
        return bonCommandeAutreResponsableRepository
            .findAll()
            .stream()
            .map(bonCommandeAutreResponsableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bonCommandeAutreResponsable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BonCommandeAutreResponsableDTO> findOne(Long id) {
        log.debug("Request to get BonCommandeAutreResponsable : {}", id);
        return bonCommandeAutreResponsableRepository.findById(id).map(bonCommandeAutreResponsableMapper::toDto);
    }

    /**
     * Delete the bonCommandeAutreResponsable by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BonCommandeAutreResponsable : {}", id);
        bonCommandeAutreResponsableRepository.deleteById(id);
    }

    /**
     * Récupère les autres responsables liés à un bon de commande.
     *
     * @param bonCommandeId l'id du bon de commande.
     * @return la liste des liaisons.
     */
    @Transactional(readOnly = true)
    public List<BonCommandeAutreResponsableDTO> findByBonCommandeId(Long bonCommandeId) {
        log.debug("Request to get BonCommandeAutreResponsables by bonCommandeId : {}", bonCommandeId);
        return bonCommandeAutreResponsableRepository
            .findByBonCommandeId(bonCommandeId)
            .stream()
            .map(bonCommandeAutreResponsableMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Remplace intégralement les autres responsables d'un bon de commande
     * (supprime les anciennes liaisons puis recrée celles reçues).
     *
     * @param bonCommandeId    l'id du bon de commande.
     * @param contactSocieteIds les ids des contacts à lier.
     * @return la nouvelle liste des liaisons.
     */
    public List<BonCommandeAutreResponsableDTO> replaceForBonCommande(Long bonCommandeId, List<Long> contactSocieteIds) {
        log.debug("Request to replace autres responsables for bonCommandeId : {}", bonCommandeId);

        bonCommandeAutreResponsableRepository.deleteByBonCommandeId(bonCommandeId);

        List<BonCommandeAutreResponsable> entities = contactSocieteIds
            .stream()
            .map(contactId -> new BonCommandeAutreResponsable().bonCommandeId(bonCommandeId).contactSocieteId(contactId))
            .collect(Collectors.toList());

        return bonCommandeAutreResponsableRepository
            .saveAll(entities)
            .stream()
            .map(bonCommandeAutreResponsableMapper::toDto)
            .collect(Collectors.toList());
    }
}

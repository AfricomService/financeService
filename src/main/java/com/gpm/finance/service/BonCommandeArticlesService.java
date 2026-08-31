package com.gpm.finance.service;

import com.gpm.finance.domain.BonCommandeArticles;
import com.gpm.finance.repository.BonCommandeArticlesRepository;
import com.gpm.finance.service.dto.BonCommandeArticlesDTO;
import com.gpm.finance.service.mapper.BonCommandeArticlesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BonCommandeArticles}.
 */
@Service
@Transactional
public class BonCommandeArticlesService {

    private final Logger log = LoggerFactory.getLogger(BonCommandeArticlesService.class);

    private final BonCommandeArticlesRepository bonCommandeArticlesRepository;

    private final BonCommandeArticlesMapper bonCommandeArticlesMapper;

    public BonCommandeArticlesService(
        BonCommandeArticlesRepository bonCommandeArticlesRepository,
        BonCommandeArticlesMapper bonCommandeArticlesMapper
    ) {
        this.bonCommandeArticlesRepository = bonCommandeArticlesRepository;
        this.bonCommandeArticlesMapper = bonCommandeArticlesMapper;
    }

    /**
     * Save a bonCommandeArticles.
     *
     * @param bonCommandeArticlesDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeArticlesDTO save(BonCommandeArticlesDTO bonCommandeArticlesDTO) {
        log.debug("Request to save BonCommandeArticles : {}", bonCommandeArticlesDTO);
        BonCommandeArticles bonCommandeArticles = bonCommandeArticlesMapper.toEntity(bonCommandeArticlesDTO);
        bonCommandeArticles = bonCommandeArticlesRepository.save(bonCommandeArticles);
        return bonCommandeArticlesMapper.toDto(bonCommandeArticles);
    }

    /**
     * Update a bonCommandeArticles.
     *
     * @param bonCommandeArticlesDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeArticlesDTO update(BonCommandeArticlesDTO bonCommandeArticlesDTO) {
        log.debug("Request to update BonCommandeArticles : {}", bonCommandeArticlesDTO);
        BonCommandeArticles bonCommandeArticles = bonCommandeArticlesMapper.toEntity(bonCommandeArticlesDTO);
        bonCommandeArticles = bonCommandeArticlesRepository.save(bonCommandeArticles);
        return bonCommandeArticlesMapper.toDto(bonCommandeArticles);
    }

    /**
     * Partially update a bonCommandeArticles.
     *
     * @param bonCommandeArticlesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BonCommandeArticlesDTO> partialUpdate(BonCommandeArticlesDTO bonCommandeArticlesDTO) {
        log.debug("Request to partially update BonCommandeArticles : {}", bonCommandeArticlesDTO);

        return bonCommandeArticlesRepository
            .findById(bonCommandeArticlesDTO.getId())
            .map(existingBonCommandeArticles -> {
                bonCommandeArticlesMapper.partialUpdate(existingBonCommandeArticles, bonCommandeArticlesDTO);

                return existingBonCommandeArticles;
            })
            .map(bonCommandeArticlesRepository::save)
            .map(bonCommandeArticlesMapper::toDto);
    }

    /**
     * Get all the bonCommandeArticles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BonCommandeArticlesDTO> findAll() {
        log.debug("Request to get all BonCommandeArticles");
        return bonCommandeArticlesRepository
            .findAll()
            .stream()
            .map(bonCommandeArticlesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bonCommandeArticles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BonCommandeArticlesDTO> findOne(Long id) {
        log.debug("Request to get BonCommandeArticles : {}", id);
        return bonCommandeArticlesRepository.findById(id).map(bonCommandeArticlesMapper::toDto);
    }

    /**
     * Delete the bonCommandeArticles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BonCommandeArticles : {}", id);
        bonCommandeArticlesRepository.deleteById(id);
    }

    /**
     * Get all the articles affectés à un bon de commande donné.
     *
     * @param bonCommandeId l'id du bon de commande.
     * @return la liste des affectations.
     */
    @Transactional(readOnly = true)
    public List<BonCommandeArticlesDTO> findByBonCommandeId(Long bonCommandeId) {
        log.debug("Request to get BonCommandeArticles by BonCommande : {}", bonCommandeId);
        return bonCommandeArticlesRepository
            .findAllByBonCommandeId(bonCommandeId)
            .stream()
            .map(bonCommandeArticlesMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Remplace intégralement les articles affectés à un bon de commande
     * (supprime les anciennes affectations puis recrée celles fournies).
     *
     * @param bonCommandeId l'id du bon de commande.
     * @param bonCommandeArticlesDTOs la nouvelle liste d'affectations (articleId, qteCommande, qteEffectuee, dateRealisation).
     * @return la liste des affectations persistées.
     */
    public List<BonCommandeArticlesDTO> replaceForBonCommande(Long bonCommandeId, List<BonCommandeArticlesDTO> bonCommandeArticlesDTOs) {
        log.debug("Request to replace BonCommandeArticles for BonCommande : {}, {}", bonCommandeId, bonCommandeArticlesDTOs);

        bonCommandeArticlesRepository.deleteAllByBonCommandeId(bonCommandeId);

        List<BonCommandeArticles> toSave = bonCommandeArticlesDTOs
            .stream()
            .map(dto -> {
                BonCommandeArticles entity = bonCommandeArticlesMapper.toEntity(dto);
                // Sécurité : on ignore tout id/bonCommandeId envoyé par le client,
                // on force la valeur issue de l'URL pour éviter toute manipulation.
                entity.setId(null);
                entity.setBonCommandeId(bonCommandeId);
                return entity;
            })
            .collect(Collectors.toList());

        return bonCommandeArticlesRepository
            .saveAll(toSave)
            .stream()
            .map(bonCommandeArticlesMapper::toDto)
            .collect(Collectors.toList());
    }
}

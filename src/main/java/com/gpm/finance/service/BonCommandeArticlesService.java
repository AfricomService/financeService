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
}

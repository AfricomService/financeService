package com.gpm.finance.service;

import com.gpm.finance.domain.OtArticles;
import com.gpm.finance.repository.OtArticlesRepository;
import com.gpm.finance.service.dto.OtArticlesDTO;
import com.gpm.finance.service.mapper.OtArticlesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OtArticles}.
 */
@Service
@Transactional
public class OtArticlesService {

    private final Logger log = LoggerFactory.getLogger(OtArticlesService.class);

    private final OtArticlesRepository otArticlesRepository;

    private final OtArticlesMapper otArticlesMapper;

    public OtArticlesService(OtArticlesRepository otArticlesRepository, OtArticlesMapper otArticlesMapper) {
        this.otArticlesRepository = otArticlesRepository;
        this.otArticlesMapper = otArticlesMapper;
    }

    /**
     * Save a otArticles.
     *
     * @param otArticlesDTO the entity to save.
     * @return the persisted entity.
     */
    public OtArticlesDTO save(OtArticlesDTO otArticlesDTO) {
        log.debug("Request to save OtArticles : {}", otArticlesDTO);
        OtArticles otArticles = otArticlesMapper.toEntity(otArticlesDTO);
        otArticles = otArticlesRepository.save(otArticles);
        return otArticlesMapper.toDto(otArticles);
    }

    /**
     * Update a otArticles.
     *
     * @param otArticlesDTO the entity to save.
     * @return the persisted entity.
     */
    public OtArticlesDTO update(OtArticlesDTO otArticlesDTO) {
        log.debug("Request to update OtArticles : {}", otArticlesDTO);
        OtArticles otArticles = otArticlesMapper.toEntity(otArticlesDTO);
        otArticles = otArticlesRepository.save(otArticles);
        return otArticlesMapper.toDto(otArticles);
    }

    /**
     * Partially update a otArticles.
     *
     * @param otArticlesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OtArticlesDTO> partialUpdate(OtArticlesDTO otArticlesDTO) {
        log.debug("Request to partially update OtArticles : {}", otArticlesDTO);

        return otArticlesRepository
            .findById(otArticlesDTO.getId())
            .map(existingOtArticles -> {
                otArticlesMapper.partialUpdate(existingOtArticles, otArticlesDTO);

                return existingOtArticles;
            })
            .map(otArticlesRepository::save)
            .map(otArticlesMapper::toDto);
    }

    /**
     * Get all the otArticles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OtArticlesDTO> findAll() {
        log.debug("Request to get all OtArticles");
        return otArticlesRepository.findAll().stream().map(otArticlesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one otArticles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OtArticlesDTO> findOne(Long id) {
        log.debug("Request to get OtArticles : {}", id);
        return otArticlesRepository.findById(id).map(otArticlesMapper::toDto);
    }

    /**
     * Delete the otArticles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OtArticles : {}", id);
        otArticlesRepository.deleteById(id);
    }
}

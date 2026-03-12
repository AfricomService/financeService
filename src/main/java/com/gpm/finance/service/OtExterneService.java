package com.gpm.finance.service;

import com.gpm.finance.domain.OtExterne;
import com.gpm.finance.repository.OtExterneRepository;
import com.gpm.finance.service.dto.OtExterneDTO;
import com.gpm.finance.service.mapper.OtExterneMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public OtExterneService(OtExterneRepository otExterneRepository, OtExterneMapper otExterneMapper) {
        this.otExterneRepository = otExterneRepository;
        this.otExterneMapper = otExterneMapper;
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
        otExterne = otExterneRepository.save(otExterne);
        return otExterneMapper.toDto(otExterne);
    }

    /**
     * Update a otExterne.
     *
     * @param otExterneDTO the entity to save.
     * @return the persisted entity.
     */
    public OtExterneDTO update(OtExterneDTO otExterneDTO) {
        log.debug("Request to update OtExterne : {}", otExterneDTO);
        OtExterne otExterne = otExterneMapper.toEntity(otExterneDTO);
        otExterne = otExterneRepository.save(otExterne);
        return otExterneMapper.toDto(otExterne);
    }

    /**
     * Partially update a otExterne.
     *
     * @param otExterneDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OtExterneDTO> partialUpdate(OtExterneDTO otExterneDTO) {
        log.debug("Request to partially update OtExterne : {}", otExterneDTO);

        return otExterneRepository
            .findById(otExterneDTO.getId())
            .map(existingOtExterne -> {
                otExterneMapper.partialUpdate(existingOtExterne, otExterneDTO);

                return existingOtExterne;
            })
            .map(otExterneRepository::save)
            .map(otExterneMapper::toDto);
    }

    /**
     * Get all the otExternes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OtExterneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OtExternes");
        return otExterneRepository.findAll(pageable).map(otExterneMapper::toDto);
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
        return otExterneRepository.findById(id).map(otExterneMapper::toDto);
    }

    /**
     * Delete the otExterne by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OtExterne : {}", id);
        otExterneRepository.deleteById(id);
    }
}

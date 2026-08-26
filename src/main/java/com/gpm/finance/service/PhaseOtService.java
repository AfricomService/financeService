package com.gpm.finance.service;

import com.gpm.finance.domain.PhaseOt;
import com.gpm.finance.repository.PhaseOtRepository;
import com.gpm.finance.service.dto.PhaseOtDTO;
import com.gpm.finance.service.mapper.PhaseOtMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PhaseOt}.
 */
@Service
@Transactional
public class PhaseOtService {

    private final Logger log = LoggerFactory.getLogger(PhaseOtService.class);

    private final PhaseOtRepository phaseOtRepository;

    private final PhaseOtMapper phaseOtMapper;

    public PhaseOtService(PhaseOtRepository phaseOtRepository, PhaseOtMapper phaseOtMapper) {
        this.phaseOtRepository = phaseOtRepository;
        this.phaseOtMapper = phaseOtMapper;
    }

    /**
     * Save a phaseOt.
     *
     * @param phaseOtDTO the entity to save.
     * @return the persisted entity.
     */
    public PhaseOtDTO save(PhaseOtDTO phaseOtDTO) {
        log.debug("Request to save PhaseOt : {}", phaseOtDTO);
        PhaseOt phaseOt = phaseOtMapper.toEntity(phaseOtDTO);
        phaseOt = phaseOtRepository.save(phaseOt);
        return phaseOtMapper.toDto(phaseOt);
    }

    /**
     * Update a phaseOt.
     *
     * @param phaseOtDTO the entity to save.
     * @return the persisted entity.
     */
    public PhaseOtDTO update(PhaseOtDTO phaseOtDTO) {
        log.debug("Request to update PhaseOt : {}", phaseOtDTO);
        PhaseOt phaseOt = phaseOtMapper.toEntity(phaseOtDTO);
        phaseOt = phaseOtRepository.save(phaseOt);
        return phaseOtMapper.toDto(phaseOt);
    }

    /**
     * Partially update a phaseOt.
     *
     * @param phaseOtDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PhaseOtDTO> partialUpdate(PhaseOtDTO phaseOtDTO) {
        log.debug("Request to partially update PhaseOt : {}", phaseOtDTO);

        return phaseOtRepository
            .findById(phaseOtDTO.getId())
            .map(existingPhaseOt -> {
                phaseOtMapper.partialUpdate(existingPhaseOt, phaseOtDTO);

                return existingPhaseOt;
            })
            .map(phaseOtRepository::save)
            .map(phaseOtMapper::toDto);
    }

    /**
     * Get all the phaseOts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PhaseOtDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PhaseOts");
        return phaseOtRepository.findAll(pageable).map(phaseOtMapper::toDto);
    }

    /**
     * Get one phaseOt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PhaseOtDTO> findOne(Long id) {
        log.debug("Request to get PhaseOt : {}", id);
        return phaseOtRepository.findById(id).map(phaseOtMapper::toDto);
    }

    /**
     * Delete the phaseOt by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PhaseOt : {}", id);
        phaseOtRepository.deleteById(id);
    }
}

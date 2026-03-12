package com.gpm.finance.service;

import com.gpm.finance.domain.FraisDeMission;
import com.gpm.finance.repository.FraisDeMissionRepository;
import com.gpm.finance.service.dto.FraisDeMissionDTO;
import com.gpm.finance.service.mapper.FraisDeMissionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FraisDeMission}.
 */
@Service
@Transactional
public class FraisDeMissionService {

    private final Logger log = LoggerFactory.getLogger(FraisDeMissionService.class);

    private final FraisDeMissionRepository fraisDeMissionRepository;

    private final FraisDeMissionMapper fraisDeMissionMapper;

    public FraisDeMissionService(FraisDeMissionRepository fraisDeMissionRepository, FraisDeMissionMapper fraisDeMissionMapper) {
        this.fraisDeMissionRepository = fraisDeMissionRepository;
        this.fraisDeMissionMapper = fraisDeMissionMapper;
    }

    /**
     * Save a fraisDeMission.
     *
     * @param fraisDeMissionDTO the entity to save.
     * @return the persisted entity.
     */
    public FraisDeMissionDTO save(FraisDeMissionDTO fraisDeMissionDTO) {
        log.debug("Request to save FraisDeMission : {}", fraisDeMissionDTO);
        FraisDeMission fraisDeMission = fraisDeMissionMapper.toEntity(fraisDeMissionDTO);
        fraisDeMission = fraisDeMissionRepository.save(fraisDeMission);
        return fraisDeMissionMapper.toDto(fraisDeMission);
    }

    /**
     * Update a fraisDeMission.
     *
     * @param fraisDeMissionDTO the entity to save.
     * @return the persisted entity.
     */
    public FraisDeMissionDTO update(FraisDeMissionDTO fraisDeMissionDTO) {
        log.debug("Request to update FraisDeMission : {}", fraisDeMissionDTO);
        FraisDeMission fraisDeMission = fraisDeMissionMapper.toEntity(fraisDeMissionDTO);
        fraisDeMission = fraisDeMissionRepository.save(fraisDeMission);
        return fraisDeMissionMapper.toDto(fraisDeMission);
    }

    /**
     * Partially update a fraisDeMission.
     *
     * @param fraisDeMissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FraisDeMissionDTO> partialUpdate(FraisDeMissionDTO fraisDeMissionDTO) {
        log.debug("Request to partially update FraisDeMission : {}", fraisDeMissionDTO);

        return fraisDeMissionRepository
            .findById(fraisDeMissionDTO.getId())
            .map(existingFraisDeMission -> {
                fraisDeMissionMapper.partialUpdate(existingFraisDeMission, fraisDeMissionDTO);

                return existingFraisDeMission;
            })
            .map(fraisDeMissionRepository::save)
            .map(fraisDeMissionMapper::toDto);
    }

    /**
     * Get all the fraisDeMissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FraisDeMissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FraisDeMissions");
        return fraisDeMissionRepository.findAll(pageable).map(fraisDeMissionMapper::toDto);
    }

    /**
     * Get one fraisDeMission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FraisDeMissionDTO> findOne(Long id) {
        log.debug("Request to get FraisDeMission : {}", id);
        return fraisDeMissionRepository.findById(id).map(fraisDeMissionMapper::toDto);
    }

    /**
     * Delete the fraisDeMission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FraisDeMission : {}", id);
        fraisDeMissionRepository.deleteById(id);
    }
}

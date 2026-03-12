package com.gpm.finance.service;

import com.gpm.finance.domain.DepenseDiverse;
import com.gpm.finance.repository.DepenseDiverseRepository;
import com.gpm.finance.service.dto.DepenseDiverseDTO;
import com.gpm.finance.service.mapper.DepenseDiverseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DepenseDiverse}.
 */
@Service
@Transactional
public class DepenseDiverseService {

    private final Logger log = LoggerFactory.getLogger(DepenseDiverseService.class);

    private final DepenseDiverseRepository depenseDiverseRepository;

    private final DepenseDiverseMapper depenseDiverseMapper;

    public DepenseDiverseService(DepenseDiverseRepository depenseDiverseRepository, DepenseDiverseMapper depenseDiverseMapper) {
        this.depenseDiverseRepository = depenseDiverseRepository;
        this.depenseDiverseMapper = depenseDiverseMapper;
    }

    /**
     * Save a depenseDiverse.
     *
     * @param depenseDiverseDTO the entity to save.
     * @return the persisted entity.
     */
    public DepenseDiverseDTO save(DepenseDiverseDTO depenseDiverseDTO) {
        log.debug("Request to save DepenseDiverse : {}", depenseDiverseDTO);
        DepenseDiverse depenseDiverse = depenseDiverseMapper.toEntity(depenseDiverseDTO);
        depenseDiverse = depenseDiverseRepository.save(depenseDiverse);
        return depenseDiverseMapper.toDto(depenseDiverse);
    }

    /**
     * Update a depenseDiverse.
     *
     * @param depenseDiverseDTO the entity to save.
     * @return the persisted entity.
     */
    public DepenseDiverseDTO update(DepenseDiverseDTO depenseDiverseDTO) {
        log.debug("Request to update DepenseDiverse : {}", depenseDiverseDTO);
        DepenseDiverse depenseDiverse = depenseDiverseMapper.toEntity(depenseDiverseDTO);
        depenseDiverse = depenseDiverseRepository.save(depenseDiverse);
        return depenseDiverseMapper.toDto(depenseDiverse);
    }

    /**
     * Partially update a depenseDiverse.
     *
     * @param depenseDiverseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DepenseDiverseDTO> partialUpdate(DepenseDiverseDTO depenseDiverseDTO) {
        log.debug("Request to partially update DepenseDiverse : {}", depenseDiverseDTO);

        return depenseDiverseRepository
            .findById(depenseDiverseDTO.getId())
            .map(existingDepenseDiverse -> {
                depenseDiverseMapper.partialUpdate(existingDepenseDiverse, depenseDiverseDTO);

                return existingDepenseDiverse;
            })
            .map(depenseDiverseRepository::save)
            .map(depenseDiverseMapper::toDto);
    }

    /**
     * Get all the depenseDiverses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DepenseDiverseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepenseDiverses");
        return depenseDiverseRepository.findAll(pageable).map(depenseDiverseMapper::toDto);
    }

    /**
     * Get one depenseDiverse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepenseDiverseDTO> findOne(Long id) {
        log.debug("Request to get DepenseDiverse : {}", id);
        return depenseDiverseRepository.findById(id).map(depenseDiverseMapper::toDto);
    }

    /**
     * Delete the depenseDiverse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DepenseDiverse : {}", id);
        depenseDiverseRepository.deleteById(id);
    }
}

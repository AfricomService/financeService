package com.gpm.finance.service;

import com.gpm.finance.domain.FactureWO;
import com.gpm.finance.repository.FactureWORepository;
import com.gpm.finance.service.dto.FactureWODTO;
import com.gpm.finance.service.mapper.FactureWOMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FactureWO}.
 */
@Service
@Transactional
public class FactureWOService {

    private final Logger log = LoggerFactory.getLogger(FactureWOService.class);

    private final FactureWORepository factureWORepository;

    private final FactureWOMapper factureWOMapper;

    public FactureWOService(FactureWORepository factureWORepository, FactureWOMapper factureWOMapper) {
        this.factureWORepository = factureWORepository;
        this.factureWOMapper = factureWOMapper;
    }

    /**
     * Save a factureWO.
     *
     * @param factureWODTO the entity to save.
     * @return the persisted entity.
     */
    public FactureWODTO save(FactureWODTO factureWODTO) {
        log.debug("Request to save FactureWO : {}", factureWODTO);
        FactureWO factureWO = factureWOMapper.toEntity(factureWODTO);
        factureWO = factureWORepository.save(factureWO);
        return factureWOMapper.toDto(factureWO);
    }

    /**
     * Update a factureWO.
     *
     * @param factureWODTO the entity to save.
     * @return the persisted entity.
     */
    public FactureWODTO update(FactureWODTO factureWODTO) {
        log.debug("Request to update FactureWO : {}", factureWODTO);
        FactureWO factureWO = factureWOMapper.toEntity(factureWODTO);
        factureWO = factureWORepository.save(factureWO);
        return factureWOMapper.toDto(factureWO);
    }

    /**
     * Partially update a factureWO.
     *
     * @param factureWODTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FactureWODTO> partialUpdate(FactureWODTO factureWODTO) {
        log.debug("Request to partially update FactureWO : {}", factureWODTO);

        return factureWORepository
            .findById(factureWODTO.getId())
            .map(existingFactureWO -> {
                factureWOMapper.partialUpdate(existingFactureWO, factureWODTO);

                return existingFactureWO;
            })
            .map(factureWORepository::save)
            .map(factureWOMapper::toDto);
    }

    /**
     * Get all the factureWOS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FactureWODTO> findAll(Pageable pageable) {
        log.debug("Request to get all FactureWOS");
        return factureWORepository.findAll(pageable).map(factureWOMapper::toDto);
    }

    /**
     * Get all the factureWOS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FactureWODTO> findAllWithEagerRelationships(Pageable pageable) {
        return factureWORepository.findAllWithEagerRelationships(pageable).map(factureWOMapper::toDto);
    }

    /**
     * Get one factureWO by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FactureWODTO> findOne(Long id) {
        log.debug("Request to get FactureWO : {}", id);
        return factureWORepository.findOneWithEagerRelationships(id).map(factureWOMapper::toDto);
    }

    /**
     * Delete the factureWO by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FactureWO : {}", id);
        factureWORepository.deleteById(id);
    }
}

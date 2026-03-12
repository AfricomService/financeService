package com.gpm.finance.service;

import com.gpm.finance.domain.Devis;
import com.gpm.finance.repository.DevisRepository;
import com.gpm.finance.service.dto.DevisDTO;
import com.gpm.finance.service.mapper.DevisMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Devis}.
 */
@Service
@Transactional
public class DevisService {

    private final Logger log = LoggerFactory.getLogger(DevisService.class);

    private final DevisRepository devisRepository;

    private final DevisMapper devisMapper;

    public DevisService(DevisRepository devisRepository, DevisMapper devisMapper) {
        this.devisRepository = devisRepository;
        this.devisMapper = devisMapper;
    }

    /**
     * Save a devis.
     *
     * @param devisDTO the entity to save.
     * @return the persisted entity.
     */
    public DevisDTO save(DevisDTO devisDTO) {
        log.debug("Request to save Devis : {}", devisDTO);
        Devis devis = devisMapper.toEntity(devisDTO);
        devis = devisRepository.save(devis);
        return devisMapper.toDto(devis);
    }

    /**
     * Update a devis.
     *
     * @param devisDTO the entity to save.
     * @return the persisted entity.
     */
    public DevisDTO update(DevisDTO devisDTO) {
        log.debug("Request to update Devis : {}", devisDTO);
        Devis devis = devisMapper.toEntity(devisDTO);
        devis = devisRepository.save(devis);
        return devisMapper.toDto(devis);
    }

    /**
     * Partially update a devis.
     *
     * @param devisDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DevisDTO> partialUpdate(DevisDTO devisDTO) {
        log.debug("Request to partially update Devis : {}", devisDTO);

        return devisRepository
            .findById(devisDTO.getId())
            .map(existingDevis -> {
                devisMapper.partialUpdate(existingDevis, devisDTO);

                return existingDevis;
            })
            .map(devisRepository::save)
            .map(devisMapper::toDto);
    }

    /**
     * Get all the devis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DevisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Devis");
        return devisRepository.findAll(pageable).map(devisMapper::toDto);
    }

    /**
     * Get all the devis with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DevisDTO> findAllWithEagerRelationships(Pageable pageable) {
        return devisRepository.findAllWithEagerRelationships(pageable).map(devisMapper::toDto);
    }

    /**
     * Get one devis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DevisDTO> findOne(Long id) {
        log.debug("Request to get Devis : {}", id);
        return devisRepository.findOneWithEagerRelationships(id).map(devisMapper::toDto);
    }

    /**
     * Delete the devis by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Devis : {}", id);
        devisRepository.deleteById(id);
    }
}

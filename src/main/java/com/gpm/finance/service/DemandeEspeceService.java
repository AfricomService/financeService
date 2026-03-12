package com.gpm.finance.service;

import com.gpm.finance.domain.DemandeEspece;
import com.gpm.finance.repository.DemandeEspeceRepository;
import com.gpm.finance.service.dto.DemandeEspeceDTO;
import com.gpm.finance.service.mapper.DemandeEspeceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandeEspece}.
 */
@Service
@Transactional
public class DemandeEspeceService {

    private final Logger log = LoggerFactory.getLogger(DemandeEspeceService.class);

    private final DemandeEspeceRepository demandeEspeceRepository;

    private final DemandeEspeceMapper demandeEspeceMapper;

    public DemandeEspeceService(DemandeEspeceRepository demandeEspeceRepository, DemandeEspeceMapper demandeEspeceMapper) {
        this.demandeEspeceRepository = demandeEspeceRepository;
        this.demandeEspeceMapper = demandeEspeceMapper;
    }

    /**
     * Save a demandeEspece.
     *
     * @param demandeEspeceDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeEspeceDTO save(DemandeEspeceDTO demandeEspeceDTO) {
        log.debug("Request to save DemandeEspece : {}", demandeEspeceDTO);
        DemandeEspece demandeEspece = demandeEspeceMapper.toEntity(demandeEspeceDTO);
        demandeEspece = demandeEspeceRepository.save(demandeEspece);
        return demandeEspeceMapper.toDto(demandeEspece);
    }

    /**
     * Update a demandeEspece.
     *
     * @param demandeEspeceDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeEspeceDTO update(DemandeEspeceDTO demandeEspeceDTO) {
        log.debug("Request to update DemandeEspece : {}", demandeEspeceDTO);
        DemandeEspece demandeEspece = demandeEspeceMapper.toEntity(demandeEspeceDTO);
        demandeEspece = demandeEspeceRepository.save(demandeEspece);
        return demandeEspeceMapper.toDto(demandeEspece);
    }

    /**
     * Partially update a demandeEspece.
     *
     * @param demandeEspeceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DemandeEspeceDTO> partialUpdate(DemandeEspeceDTO demandeEspeceDTO) {
        log.debug("Request to partially update DemandeEspece : {}", demandeEspeceDTO);

        return demandeEspeceRepository
            .findById(demandeEspeceDTO.getId())
            .map(existingDemandeEspece -> {
                demandeEspeceMapper.partialUpdate(existingDemandeEspece, demandeEspeceDTO);

                return existingDemandeEspece;
            })
            .map(demandeEspeceRepository::save)
            .map(demandeEspeceMapper::toDto);
    }

    /**
     * Get all the demandeEspeces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeEspeceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeEspeces");
        return demandeEspeceRepository.findAll(pageable).map(demandeEspeceMapper::toDto);
    }

    /**
     * Get one demandeEspece by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DemandeEspeceDTO> findOne(Long id) {
        log.debug("Request to get DemandeEspece : {}", id);
        return demandeEspeceRepository.findById(id).map(demandeEspeceMapper::toDto);
    }

    /**
     * Delete the demandeEspece by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DemandeEspece : {}", id);
        demandeEspeceRepository.deleteById(id);
    }
}

package com.gpm.finance.service;

import com.gpm.finance.domain.DemandeAchat;
import com.gpm.finance.repository.DemandeAchatRepository;
import com.gpm.finance.service.dto.DemandeAchatDTO;
import com.gpm.finance.service.mapper.DemandeAchatMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandeAchat}.
 */
@Service
@Transactional
public class DemandeAchatService {

    private final Logger log = LoggerFactory.getLogger(DemandeAchatService.class);

    private final DemandeAchatRepository demandeAchatRepository;

    private final DemandeAchatMapper demandeAchatMapper;

    public DemandeAchatService(DemandeAchatRepository demandeAchatRepository, DemandeAchatMapper demandeAchatMapper) {
        this.demandeAchatRepository = demandeAchatRepository;
        this.demandeAchatMapper = demandeAchatMapper;
    }

    /**
     * Save a demandeAchat.
     *
     * @param demandeAchatDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeAchatDTO save(DemandeAchatDTO demandeAchatDTO) {
        log.debug("Request to save DemandeAchat : {}", demandeAchatDTO);
        DemandeAchat demandeAchat = demandeAchatMapper.toEntity(demandeAchatDTO);
        demandeAchat = demandeAchatRepository.save(demandeAchat);
        return demandeAchatMapper.toDto(demandeAchat);
    }

    /**
     * Update a demandeAchat.
     *
     * @param demandeAchatDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeAchatDTO update(DemandeAchatDTO demandeAchatDTO) {
        log.debug("Request to update DemandeAchat : {}", demandeAchatDTO);
        DemandeAchat demandeAchat = demandeAchatMapper.toEntity(demandeAchatDTO);
        demandeAchat = demandeAchatRepository.save(demandeAchat);
        return demandeAchatMapper.toDto(demandeAchat);
    }

    /**
     * Partially update a demandeAchat.
     *
     * @param demandeAchatDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DemandeAchatDTO> partialUpdate(DemandeAchatDTO demandeAchatDTO) {
        log.debug("Request to partially update DemandeAchat : {}", demandeAchatDTO);

        return demandeAchatRepository
            .findById(demandeAchatDTO.getId())
            .map(existingDemandeAchat -> {
                demandeAchatMapper.partialUpdate(existingDemandeAchat, demandeAchatDTO);

                return existingDemandeAchat;
            })
            .map(demandeAchatRepository::save)
            .map(demandeAchatMapper::toDto);
    }

    /**
     * Get all the demandeAchats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeAchatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeAchats");
        return demandeAchatRepository.findAll(pageable).map(demandeAchatMapper::toDto);
    }

    /**
     * Get one demandeAchat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DemandeAchatDTO> findOne(Long id) {
        log.debug("Request to get DemandeAchat : {}", id);
        return demandeAchatRepository.findById(id).map(demandeAchatMapper::toDto);
    }

    /**
     * Delete the demandeAchat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DemandeAchat : {}", id);
        demandeAchatRepository.deleteById(id);
    }
}

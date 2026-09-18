package com.gpm.finance.service;

import com.gpm.finance.domain.OtExterneAutreResponsable;
import com.gpm.finance.repository.OtExterneAutreResponsableRepository;
import com.gpm.finance.service.dto.OtExterneAutreResponsableDTO;
import com.gpm.finance.service.mapper.OtExterneAutreResponsableMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OtExterneAutreResponsable}.
 */
@Service
@Transactional
public class OtExterneAutreResponsableService {

    private final Logger log = LoggerFactory.getLogger(OtExterneAutreResponsableService.class);

    private final OtExterneAutreResponsableRepository otExterneAutreResponsableRepository;

    private final OtExterneAutreResponsableMapper otExterneAutreResponsableMapper;

    public OtExterneAutreResponsableService(
        OtExterneAutreResponsableRepository otExterneAutreResponsableRepository,
        OtExterneAutreResponsableMapper otExterneAutreResponsableMapper
    ) {
        this.otExterneAutreResponsableRepository = otExterneAutreResponsableRepository;
        this.otExterneAutreResponsableMapper = otExterneAutreResponsableMapper;
    }

    /**
     * Save a otExterneAutreResponsable.
     *
     * @param otExterneAutreResponsableDTO the entity to save.
     * @return the persisted entity.
     */
    public OtExterneAutreResponsableDTO save(OtExterneAutreResponsableDTO otExterneAutreResponsableDTO) {
        log.debug("Request to save OtExterneAutreResponsable : {}", otExterneAutreResponsableDTO);
        OtExterneAutreResponsable otExterneAutreResponsable = otExterneAutreResponsableMapper.toEntity(otExterneAutreResponsableDTO);
        otExterneAutreResponsable = otExterneAutreResponsableRepository.save(otExterneAutreResponsable);
        return otExterneAutreResponsableMapper.toDto(otExterneAutreResponsable);
    }

    /**
     * Partially update a otExterneAutreResponsable.
     *
     * @param otExterneAutreResponsableDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OtExterneAutreResponsableDTO> partialUpdate(OtExterneAutreResponsableDTO otExterneAutreResponsableDTO) {
        log.debug("Request to partially update OtExterneAutreResponsable : {}", otExterneAutreResponsableDTO);

        return otExterneAutreResponsableRepository
            .findById(otExterneAutreResponsableDTO.getId())
            .map(existingOtExterneAutreResponsable -> {
                otExterneAutreResponsableMapper.partialUpdate(existingOtExterneAutreResponsable, otExterneAutreResponsableDTO);

                return existingOtExterneAutreResponsable;
            })
            .map(otExterneAutreResponsableRepository::save)
            .map(otExterneAutreResponsableMapper::toDto);
    }

    /**
     * Get all the otExterneAutreResponsables.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OtExterneAutreResponsableDTO> findAll() {
        log.debug("Request to get all OtExterneAutreResponsables");
        return otExterneAutreResponsableRepository
            .findAll()
            .stream()
            .map(otExterneAutreResponsableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one otExterneAutreResponsable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OtExterneAutreResponsableDTO> findOne(Long id) {
        log.debug("Request to get OtExterneAutreResponsable : {}", id);
        return otExterneAutreResponsableRepository.findById(id).map(otExterneAutreResponsableMapper::toDto);
    }

    /**
     * Delete the otExterneAutreResponsable by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OtExterneAutreResponsable : {}", id);
        otExterneAutreResponsableRepository.deleteById(id);
    }

    /**
     * Get all otExterneAutreResponsables for a given otExterne.
     */
    @Transactional(readOnly = true)
    public List<OtExterneAutreResponsableDTO> findByOtExterne(Long otExterneId) {
        log.debug("Request to get all OtExterneAutreResponsables for otExterne : {}", otExterneId);
        return otExterneAutreResponsableRepository
            .findByOtExterneId(otExterneId)
            .stream()
            .map(otExterneAutreResponsableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Replace all otExterneAutreResponsables for a given otExterne
     * with the provided list of contactSociete ids.
     */
    public List<OtExterneAutreResponsableDTO> replaceForOtExterne(Long otExterneId, List<Long> contactSocieteIds) {
        log.debug("Request to replace OtExterneAutreResponsables for otExterne {} with {}", otExterneId, contactSocieteIds);

        otExterneAutreResponsableRepository.deleteByOtExterneId(otExterneId);

        List<OtExterneAutreResponsable> newLinks = contactSocieteIds
            .stream()
            .map(contactId -> {
                OtExterneAutreResponsable link = new OtExterneAutreResponsable();
                link.setOtExterneId(otExterneId);
                link.setContactSocieteId(contactId);
                return link;
            })
            .collect(Collectors.toList());

        return otExterneAutreResponsableRepository
            .saveAll(newLinks)
            .stream()
            .map(otExterneAutreResponsableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}

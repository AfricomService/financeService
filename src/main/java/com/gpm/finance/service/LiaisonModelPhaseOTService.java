package com.gpm.finance.service;

import com.gpm.finance.domain.LiaisonModelPhaseOT;
import com.gpm.finance.repository.LiaisonModelPhaseOTRepository;
import com.gpm.finance.service.dto.LiaisonModelPhaseOTDTO;
import com.gpm.finance.service.mapper.LiaisonModelPhaseOTMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LiaisonModelPhaseOT}.
 */
@Service
@Transactional
public class LiaisonModelPhaseOTService {

    private final Logger log = LoggerFactory.getLogger(LiaisonModelPhaseOTService.class);

    private final LiaisonModelPhaseOTRepository liaisonModelPhaseOTRepository;

    private final LiaisonModelPhaseOTMapper liaisonModelPhaseOTMapper;

    public LiaisonModelPhaseOTService(
        LiaisonModelPhaseOTRepository liaisonModelPhaseOTRepository,
        LiaisonModelPhaseOTMapper liaisonModelPhaseOTMapper
    ) {
        this.liaisonModelPhaseOTRepository = liaisonModelPhaseOTRepository;
        this.liaisonModelPhaseOTMapper = liaisonModelPhaseOTMapper;
    }

    /**
     * Save a liaisonModelPhaseOT.
     *
     * @param liaisonModelPhaseOTDTO the entity to save.
     * @return the persisted entity.
     */
    public LiaisonModelPhaseOTDTO save(LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO) {
        log.debug("Request to save LiaisonModelPhaseOT : {}", liaisonModelPhaseOTDTO);
        LiaisonModelPhaseOT liaisonModelPhaseOT = liaisonModelPhaseOTMapper.toEntity(liaisonModelPhaseOTDTO);
        liaisonModelPhaseOT = liaisonModelPhaseOTRepository.save(liaisonModelPhaseOT);
        return liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);
    }

    /**
     * Update a liaisonModelPhaseOT.
     *
     * @param liaisonModelPhaseOTDTO the entity to save.
     * @return the persisted entity.
     */
    public LiaisonModelPhaseOTDTO update(LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO) {
        log.debug("Request to update LiaisonModelPhaseOT : {}", liaisonModelPhaseOTDTO);
        LiaisonModelPhaseOT liaisonModelPhaseOT = liaisonModelPhaseOTMapper.toEntity(liaisonModelPhaseOTDTO);
        liaisonModelPhaseOT = liaisonModelPhaseOTRepository.save(liaisonModelPhaseOT);
        return liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);
    }

    /**
     * Partially update a liaisonModelPhaseOT.
     *
     * @param liaisonModelPhaseOTDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LiaisonModelPhaseOTDTO> partialUpdate(LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO) {
        log.debug("Request to partially update LiaisonModelPhaseOT : {}", liaisonModelPhaseOTDTO);

        return liaisonModelPhaseOTRepository
            .findById(liaisonModelPhaseOTDTO.getId())
            .map(existingLiaisonModelPhaseOT -> {
                liaisonModelPhaseOTMapper.partialUpdate(existingLiaisonModelPhaseOT, liaisonModelPhaseOTDTO);

                return existingLiaisonModelPhaseOT;
            })
            .map(liaisonModelPhaseOTRepository::save)
            .map(liaisonModelPhaseOTMapper::toDto);
    }

    /**
     * Get all the liaisonModelPhaseOTS.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LiaisonModelPhaseOTDTO> findAll() {
        log.debug("Request to get all LiaisonModelPhaseOTS");
        return liaisonModelPhaseOTRepository
            .findAll()
            .stream()
            .map(liaisonModelPhaseOTMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one liaisonModelPhaseOT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LiaisonModelPhaseOTDTO> findOne(Long id) {
        log.debug("Request to get LiaisonModelPhaseOT : {}", id);
        return liaisonModelPhaseOTRepository.findById(id).map(liaisonModelPhaseOTMapper::toDto);
    }

    /**
     * Delete the liaisonModelPhaseOT by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LiaisonModelPhaseOT : {}", id);
        liaisonModelPhaseOTRepository.deleteById(id);
    }
}

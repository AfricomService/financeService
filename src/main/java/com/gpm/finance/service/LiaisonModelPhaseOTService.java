package com.gpm.finance.service;

import com.gpm.finance.domain.LiaisonModelPhaseOT;
import com.gpm.finance.repository.LiaisonModelPhaseOTRepository;
import com.gpm.finance.service.dto.LiaisonModelPhaseOTDTO;
import com.gpm.finance.service.mapper.LiaisonModelPhaseOTMapper;
import com.gpm.finance.web.rest.errors.BadRequestAlertException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LiaisonModelPhaseOT}.
 */
@Service
@Transactional
public class LiaisonModelPhaseOTService {

    private final Logger log = LoggerFactory.getLogger(LiaisonModelPhaseOTService.class);

    private static final String ENTITY_NAME = "financeServiceLiaisonModelPhaseOt";

    private final LiaisonModelPhaseOTRepository liaisonModelPhaseOTRepository;

    private final LiaisonModelPhaseOTMapper liaisonModelPhaseOTMapper;

    public LiaisonModelPhaseOTService(
        LiaisonModelPhaseOTRepository liaisonModelPhaseOTRepository,
        LiaisonModelPhaseOTMapper liaisonModelPhaseOTMapper
    ) {
        this.liaisonModelPhaseOTRepository = liaisonModelPhaseOTRepository;
        this.liaisonModelPhaseOTMapper = liaisonModelPhaseOTMapper;
    }

    // ─────────────────────────────────────────────────────────────────
    // Standard CRUD (unchanged JHipster-generated behaviour)
    // ─────────────────────────────────────────────────────────────────

    public LiaisonModelPhaseOTDTO save(LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO) {
        log.debug("Request to save LiaisonModelPhaseOT : {}", liaisonModelPhaseOTDTO);
        LiaisonModelPhaseOT liaisonModelPhaseOT = liaisonModelPhaseOTMapper.toEntity(liaisonModelPhaseOTDTO);
        liaisonModelPhaseOT = liaisonModelPhaseOTRepository.save(liaisonModelPhaseOT);
        return liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);
    }

    public LiaisonModelPhaseOTDTO update(LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO) {
        log.debug("Request to update LiaisonModelPhaseOT : {}", liaisonModelPhaseOTDTO);
        LiaisonModelPhaseOT liaisonModelPhaseOT = liaisonModelPhaseOTMapper.toEntity(liaisonModelPhaseOTDTO);
        liaisonModelPhaseOT = liaisonModelPhaseOTRepository.save(liaisonModelPhaseOT);
        return liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);
    }

    public Optional<LiaisonModelPhaseOTDTO> partialUpdate(LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO) {
        log.debug("Request to partially update LiaisonModelPhaseOT : {}", liaisonModelPhaseOTDTO);

        return liaisonModelPhaseOTRepository
            .findById(liaisonModelPhaseOTDTO.getId())
            .map(existing -> {
                liaisonModelPhaseOTMapper.partialUpdate(existing, liaisonModelPhaseOTDTO);
                return existing;
            })
            .map(liaisonModelPhaseOTRepository::save)
            .map(liaisonModelPhaseOTMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LiaisonModelPhaseOTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LiaisonModelPhaseOTS");
        return liaisonModelPhaseOTRepository.findAll(pageable).map(liaisonModelPhaseOTMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<LiaisonModelPhaseOTDTO> findOne(Long id) {
        log.debug("Request to get LiaisonModelPhaseOT : {}", id);
        return liaisonModelPhaseOTRepository.findById(id).map(liaisonModelPhaseOTMapper::toDto);
    }

    /**
     * Plain delete, no renumbering. Used by the generic CRUD screen only.
     * The ModelPhaseOT "Phases associées" UI must go through {@link #unassign(Long)} instead,
     * so the remaining phases stay contiguously numbered (1, 2, 3, ...).
     */
    public void delete(Long id) {
        log.debug("Request to delete LiaisonModelPhaseOT : {}", id);
        liaisonModelPhaseOTRepository.deleteById(id);
    }

    // ─────────────────────────────────────────────────────────────────
    // Custom: ordered many-to-many management for ModelPhaseOT <-> PhaseOt
    // ─────────────────────────────────────────────────────────────────

    /**
     * All phases linked to a model, ordered by classement (1st, 2nd, ...).
     */
    @Transactional(readOnly = true)
    public List<LiaisonModelPhaseOTDTO> findByModelPhaseOtId(Long modelPhaseOtId) {
        log.debug("Request to get LiaisonModelPhaseOTS for ModelPhaseOT : {}", modelPhaseOtId);
        return liaisonModelPhaseOTRepository
            .findByModelPhaseOtIdOrderByClassementPhaseAsc(modelPhaseOtId)
            .stream()
            .map(liaisonModelPhaseOTMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Link a PhaseOt to a ModelPhaseOT. Rejects duplicates and auto-assigns the
     * next free classement (i.e. it's appended at the end of the sequence).
     */
    public LiaisonModelPhaseOTDTO assign(Long modelPhaseOtId, Long phaseId) {
        log.debug("Request to assign PhaseOt {} to ModelPhaseOT {}", phaseId, modelPhaseOtId);

        if (liaisonModelPhaseOTRepository.existsByModelPhaseOtIdAndPhaseId(modelPhaseOtId, phaseId)) {
            throw new BadRequestAlertException("Cette phase est déjà associée à ce modèle", ENTITY_NAME, "phasealreadylinked");
        }

        int nextClassement = liaisonModelPhaseOTRepository
            .findFirstByModelPhaseOtIdOrderByClassementPhaseDesc(modelPhaseOtId)
            .map(l -> (l.getClassementPhase() != null ? l.getClassementPhase() : 0) + 1)
            .orElse(1);

        LiaisonModelPhaseOT liaison = new LiaisonModelPhaseOT();
        liaison.setModelPhaseOtId(modelPhaseOtId);
        liaison.setPhaseId(phaseId);
        liaison.setClassementPhase(nextClassement);

        liaison = liaisonModelPhaseOTRepository.save(liaison);
        return liaisonModelPhaseOTMapper.toDto(liaison);
    }

    /**
     * Unlink a PhaseOt from its model, then renumber the remaining phases so the
     * classement stays contiguous (1, 2, 3, ...) with no gaps.
     */
    public void unassign(Long liaisonId) {
        log.debug("Request to unassign LiaisonModelPhaseOT : {}", liaisonId);

        LiaisonModelPhaseOT liaison = liaisonModelPhaseOTRepository
            .findById(liaisonId)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));

        Long modelPhaseOtId = liaison.getModelPhaseOtId();
        liaisonModelPhaseOTRepository.delete(liaison);

        renumber(modelPhaseOtId);
    }

    /**
     * Move a phase one position up (swap classement with its predecessor).
     * No-op if the phase is already first.
     */
    public List<LiaisonModelPhaseOTDTO> moveUp(Long liaisonId) {
        return swapWithNeighbour(liaisonId, -1);
    }

    /**
     * Move a phase one position down (swap classement with its successor).
     * No-op if the phase is already last.
     */
    public List<LiaisonModelPhaseOTDTO> moveDown(Long liaisonId) {
        return swapWithNeighbour(liaisonId, 1);
    }

    private List<LiaisonModelPhaseOTDTO> swapWithNeighbour(Long liaisonId, int direction) {
        LiaisonModelPhaseOT current = liaisonModelPhaseOTRepository
            .findById(liaisonId)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));

        Integer currentClassement = current.getClassementPhase();
        if (currentClassement == null) {
            return findByModelPhaseOtId(current.getModelPhaseOtId());
        }

        int targetClassement = currentClassement + direction;

        Optional<LiaisonModelPhaseOT> neighbourOpt = liaisonModelPhaseOTRepository.findByModelPhaseOtIdAndClassementPhase(
            current.getModelPhaseOtId(),
            targetClassement
        );

        neighbourOpt.ifPresent(neighbour -> {
            current.setClassementPhase(targetClassement);
            neighbour.setClassementPhase(currentClassement);
            liaisonModelPhaseOTRepository.save(neighbour);
            liaisonModelPhaseOTRepository.save(current);
        });

        return findByModelPhaseOtId(current.getModelPhaseOtId());
    }

    private void renumber(Long modelPhaseOtId) {
        List<LiaisonModelPhaseOT> remaining = liaisonModelPhaseOTRepository.findByModelPhaseOtIdOrderByClassementPhaseAsc(modelPhaseOtId);
        int position = 1;
        for (LiaisonModelPhaseOT liaison : remaining) {
            if (!Objects.equals(liaison.getClassementPhase(), position)) {
                liaison.setClassementPhase(position);
                liaisonModelPhaseOTRepository.save(liaison);
            }
            position++;
        }
    }
}

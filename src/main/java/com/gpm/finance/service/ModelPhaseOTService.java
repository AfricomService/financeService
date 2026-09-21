package com.gpm.finance.service;

import com.gpm.finance.domain.LiaisonModelPhaseOT;
import com.gpm.finance.domain.ModelPhaseOT;
import com.gpm.finance.domain.PhaseOt;
import com.gpm.finance.repository.LiaisonModelPhaseOTRepository;
import com.gpm.finance.repository.ModelPhaseOTRepository;
import com.gpm.finance.repository.PhaseOtRepository;
import com.gpm.finance.service.dto.ModelPhaseOTDTO;
import com.gpm.finance.service.dto.PhaseOtDTO;
import com.gpm.finance.service.mapper.ModelPhaseOTMapper;
import com.gpm.finance.service.mapper.PhaseOtMapper;
import java.util.List;
import java.util.Map;
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
 * Service Implementation for managing {@link ModelPhaseOT}.
 */
@Service
@Transactional
public class ModelPhaseOTService {

    private final Logger log = LoggerFactory.getLogger(ModelPhaseOTService.class);

    private final ModelPhaseOTRepository modelPhaseOTRepository;
    private final ModelPhaseOTMapper modelPhaseOTMapper;
    private final LiaisonModelPhaseOTRepository liaisonModelPhaseOTRepository;
    private final PhaseOtRepository phaseOtRepository;
    private final PhaseOtMapper phaseOtMapper;

    public ModelPhaseOTService(
        ModelPhaseOTRepository modelPhaseOTRepository,
        ModelPhaseOTMapper modelPhaseOTMapper,
        LiaisonModelPhaseOTRepository liaisonModelPhaseOTRepository,
        PhaseOtRepository phaseOtRepository,
        PhaseOtMapper phaseOtMapper
    ) {
        this.modelPhaseOTRepository = modelPhaseOTRepository;
        this.modelPhaseOTMapper = modelPhaseOTMapper;
        this.liaisonModelPhaseOTRepository = liaisonModelPhaseOTRepository;
        this.phaseOtRepository = phaseOtRepository;
        this.phaseOtMapper = phaseOtMapper;
    }

    /**
     * Save a modelPhaseOT.
     *
     * @param modelPhaseOTDTO the entity to save.
     * @return the persisted entity.
     */
    public ModelPhaseOTDTO save(ModelPhaseOTDTO modelPhaseOTDTO) {
        log.debug("Request to save ModelPhaseOT : {}", modelPhaseOTDTO);
        ModelPhaseOT modelPhaseOT = modelPhaseOTMapper.toEntity(modelPhaseOTDTO);
        modelPhaseOT = modelPhaseOTRepository.save(modelPhaseOT);
        return modelPhaseOTMapper.toDto(modelPhaseOT);
    }

    /**
     * Update a modelPhaseOT.
     *
     * @param modelPhaseOTDTO the entity to save.
     * @return the persisted entity.
     */
    public ModelPhaseOTDTO update(ModelPhaseOTDTO modelPhaseOTDTO) {
        log.debug("Request to update ModelPhaseOT : {}", modelPhaseOTDTO);
        ModelPhaseOT modelPhaseOT = modelPhaseOTMapper.toEntity(modelPhaseOTDTO);
        modelPhaseOT = modelPhaseOTRepository.save(modelPhaseOT);
        return modelPhaseOTMapper.toDto(modelPhaseOT);
    }

    /**
     * Partially update a modelPhaseOT.
     *
     * @param modelPhaseOTDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ModelPhaseOTDTO> partialUpdate(ModelPhaseOTDTO modelPhaseOTDTO) {
        log.debug("Request to partially update ModelPhaseOT : {}", modelPhaseOTDTO);

        return modelPhaseOTRepository
            .findById(modelPhaseOTDTO.getId())
            .map(existingModelPhaseOT -> {
                modelPhaseOTMapper.partialUpdate(existingModelPhaseOT, modelPhaseOTDTO);

                return existingModelPhaseOT;
            })
            .map(modelPhaseOTRepository::save)
            .map(modelPhaseOTMapper::toDto);
    }

    /**
     * Get all the modelPhaseOTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ModelPhaseOTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModelPhaseOTS");
        return modelPhaseOTRepository.findAll(pageable).map(modelPhaseOTMapper::toDto);
    }

    /**
     * Get one modelPhaseOT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModelPhaseOTDTO> findOne(Long id) {
        log.debug("Request to get ModelPhaseOT : {}", id);
        return modelPhaseOTRepository.findById(id).map(modelPhaseOTMapper::toDto);
    }

    /**
     * Delete the modelPhaseOT by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ModelPhaseOT : {}", id);
        modelPhaseOTRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PhaseOtDTO> findPhasesByModelId(Long modelPhaseOtId) {
        log.debug("Request to get ordered PhaseOts for ModelPhaseOT : {}", modelPhaseOtId);

        List<LiaisonModelPhaseOT> liaisons = liaisonModelPhaseOTRepository.findAllByModelPhaseOtIdOrderByClassementPhaseAsc(modelPhaseOtId);

        if (liaisons.isEmpty()) {
            return List.of();
        }

        List<Long> orderedPhaseIds = liaisons.stream().map(LiaisonModelPhaseOT::getPhaseId).collect(Collectors.toList());

        Map<Long, PhaseOt> phaseOtById = phaseOtRepository
            .findAllById(orderedPhaseIds)
            .stream()
            .collect(Collectors.toMap(PhaseOt::getId, p -> p));

        // Batch fetch does not preserve order — re-apply the classementPhase order here.
        return orderedPhaseIds
            .stream()
            .map(phaseOtById::get)
            .filter(Objects::nonNull)
            .map(phaseOtMapper::toDto)
            .collect(Collectors.toList());
    }
}

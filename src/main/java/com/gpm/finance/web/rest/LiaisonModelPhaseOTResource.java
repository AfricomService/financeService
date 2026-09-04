package com.gpm.finance.web.rest;

import com.gpm.finance.repository.LiaisonModelPhaseOTRepository;
import com.gpm.finance.service.LiaisonModelPhaseOTService;
import com.gpm.finance.service.dto.LiaisonModelPhaseOTDTO;
import com.gpm.finance.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gpm.finance.domain.LiaisonModelPhaseOT}.
 */
@RestController
@RequestMapping("/api")
public class LiaisonModelPhaseOTResource {

    private final Logger log = LoggerFactory.getLogger(LiaisonModelPhaseOTResource.class);

    private static final String ENTITY_NAME = "financeServiceLiaisonModelPhaseOt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LiaisonModelPhaseOTService liaisonModelPhaseOTService;

    private final LiaisonModelPhaseOTRepository liaisonModelPhaseOTRepository;

    public LiaisonModelPhaseOTResource(
        LiaisonModelPhaseOTService liaisonModelPhaseOTService,
        LiaisonModelPhaseOTRepository liaisonModelPhaseOTRepository
    ) {
        this.liaisonModelPhaseOTService = liaisonModelPhaseOTService;
        this.liaisonModelPhaseOTRepository = liaisonModelPhaseOTRepository;
    }

    // ─────────────────────────────────────────────────────────────────
    // Standard CRUD (unchanged JHipster-generated behaviour)
    // ─────────────────────────────────────────────────────────────────

    @PostMapping("/liaison-model-phase-ots")
    public ResponseEntity<LiaisonModelPhaseOTDTO> createLiaisonModelPhaseOT(@RequestBody LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO)
        throws URISyntaxException {
        log.debug("REST request to save LiaisonModelPhaseOT : {}", liaisonModelPhaseOTDTO);
        if (liaisonModelPhaseOTDTO.getId() != null) {
            throw new BadRequestAlertException("A new liaisonModelPhaseOT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LiaisonModelPhaseOTDTO result = liaisonModelPhaseOTService.save(liaisonModelPhaseOTDTO);
        return ResponseEntity
            .created(new URI("/api/liaison-model-phase-ots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/liaison-model-phase-ots/{id}")
    public ResponseEntity<LiaisonModelPhaseOTDTO> updateLiaisonModelPhaseOT(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LiaisonModelPhaseOT : {}, {}", id, liaisonModelPhaseOTDTO);
        if (liaisonModelPhaseOTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, liaisonModelPhaseOTDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!liaisonModelPhaseOTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LiaisonModelPhaseOTDTO result = liaisonModelPhaseOTService.update(liaisonModelPhaseOTDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, liaisonModelPhaseOTDTO.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/liaison-model-phase-ots/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LiaisonModelPhaseOTDTO> partialUpdateLiaisonModelPhaseOT(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LiaisonModelPhaseOT partially : {}, {}", id, liaisonModelPhaseOTDTO);
        if (liaisonModelPhaseOTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, liaisonModelPhaseOTDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!liaisonModelPhaseOTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LiaisonModelPhaseOTDTO> result = liaisonModelPhaseOTService.partialUpdate(liaisonModelPhaseOTDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, liaisonModelPhaseOTDTO.getId().toString())
        );
    }

    @GetMapping("/liaison-model-phase-ots")
    public ResponseEntity<List<LiaisonModelPhaseOTDTO>> getAllLiaisonModelPhaseOTS(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LiaisonModelPhaseOTS");
        Page<LiaisonModelPhaseOTDTO> page = liaisonModelPhaseOTService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/liaison-model-phase-ots/{id}")
    public ResponseEntity<LiaisonModelPhaseOTDTO> getLiaisonModelPhaseOT(@PathVariable Long id) {
        log.debug("REST request to get LiaisonModelPhaseOT : {}", id);
        Optional<LiaisonModelPhaseOTDTO> liaisonModelPhaseOTDTO = liaisonModelPhaseOTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(liaisonModelPhaseOTDTO);
    }

    @DeleteMapping("/liaison-model-phase-ots/{id}")
    public ResponseEntity<Void> deleteLiaisonModelPhaseOT(@PathVariable Long id) {
        log.debug("REST request to delete LiaisonModelPhaseOT : {}", id);
        liaisonModelPhaseOTService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    // ─────────────────────────────────────────────────────────────────
    // Custom: ordered many-to-many assignment workflow used by the
    // ModelPhaseOT update page ("Phases associées" section)
    // ─────────────────────────────────────────────────────────────────

    /**
     * {@code GET  /liaison-model-phase-ots/by-model/:modelPhaseOtId} : get every PhaseOt
     * linked to a given ModelPhaseOT, ordered by classement (1st, 2nd, ...).
     */
    @GetMapping("/liaison-model-phase-ots/by-model/{modelPhaseOtId}")
    public List<LiaisonModelPhaseOTDTO> getPhasesForModel(@PathVariable Long modelPhaseOtId) {
        log.debug("REST request to get LiaisonModelPhaseOTS for model : {}", modelPhaseOtId);
        return liaisonModelPhaseOTService.findByModelPhaseOtId(modelPhaseOtId);
    }

    /**
     * {@code POST  /liaison-model-phase-ots/assign} : link a PhaseOt to a ModelPhaseOT.
     * The classement is assigned automatically (appended at the end). Rejects duplicates
     * with a {@code 400 Bad Request}.
     *
     * @param liaisonModelPhaseOTDTO only {@code modelPhaseOtId} and {@code phaseId} are read.
     */
    @PostMapping("/liaison-model-phase-ots/assign")
    public ResponseEntity<LiaisonModelPhaseOTDTO> assignPhaseToModel(@RequestBody LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO)
        throws URISyntaxException {
        log.debug(
            "REST request to assign PhaseOt {} to ModelPhaseOT {}",
            liaisonModelPhaseOTDTO.getPhaseId(),
            liaisonModelPhaseOTDTO.getModelPhaseOtId()
        );
        if (liaisonModelPhaseOTDTO.getModelPhaseOtId() == null || liaisonModelPhaseOTDTO.getPhaseId() == null) {
            throw new BadRequestAlertException("modelPhaseOtId and phaseId are required", ENTITY_NAME, "missingfield");
        }

        LiaisonModelPhaseOTDTO result = liaisonModelPhaseOTService.assign(
            liaisonModelPhaseOTDTO.getModelPhaseOtId(),
            liaisonModelPhaseOTDTO.getPhaseId()
        );
        return ResponseEntity
            .created(new URI("/api/liaison-model-phase-ots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code DELETE  /liaison-model-phase-ots/:id/unassign} : unlink a PhaseOt from its
     * model and renumber the remaining phases so the classement stays contiguous.
     */
    @DeleteMapping("/liaison-model-phase-ots/{id}/unassign")
    public ResponseEntity<Void> unassignPhase(@PathVariable Long id) {
        log.debug("REST request to unassign LiaisonModelPhaseOT : {}", id);
        liaisonModelPhaseOTService.unassign(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code PUT  /liaison-model-phase-ots/:id/move-up} : swap this phase's classement
     * with its predecessor. Returns the full, freshly-ordered list for its model.
     */
    @PutMapping("/liaison-model-phase-ots/{id}/move-up")
    public List<LiaisonModelPhaseOTDTO> movePhaseUp(@PathVariable Long id) {
        log.debug("REST request to move LiaisonModelPhaseOT {} up", id);
        return liaisonModelPhaseOTService.moveUp(id);
    }

    /**
     * {@code PUT  /liaison-model-phase-ots/:id/move-down} : swap this phase's classement
     * with its successor. Returns the full, freshly-ordered list for its model.
     */
    @PutMapping("/liaison-model-phase-ots/{id}/move-down")
    public List<LiaisonModelPhaseOTDTO> movePhaseDown(@PathVariable Long id) {
        log.debug("REST request to move LiaisonModelPhaseOT {} down", id);
        return liaisonModelPhaseOTService.moveDown(id);
    }
}

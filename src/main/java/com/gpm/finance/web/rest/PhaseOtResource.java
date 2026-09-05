package com.gpm.finance.web.rest;

import com.gpm.finance.repository.PhaseOtRepository;
import com.gpm.finance.service.PhaseOtService;
import com.gpm.finance.service.dto.PhaseOtDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gpm.finance.domain.PhaseOt}.
 */
@RestController
@RequestMapping("/api")
public class PhaseOtResource {

    private final Logger log = LoggerFactory.getLogger(PhaseOtResource.class);

    private static final String ENTITY_NAME = "financeServicePhaseOt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhaseOtService phaseOtService;

    private final PhaseOtRepository phaseOtRepository;

    public PhaseOtResource(PhaseOtService phaseOtService, PhaseOtRepository phaseOtRepository) {
        this.phaseOtService = phaseOtService;
        this.phaseOtRepository = phaseOtRepository;
    }

    /**
     * {@code POST  /phase-ots} : Create a new phaseOt.
     *
     * @param phaseOtDTO the phaseOtDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phaseOtDTO, or with status {@code 400 (Bad Request)} if the phaseOt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phase-ots")
    public ResponseEntity<PhaseOtDTO> createPhaseOt(@RequestBody PhaseOtDTO phaseOtDTO) throws URISyntaxException {
        log.debug("REST request to save PhaseOt : {}", phaseOtDTO);
        if (phaseOtDTO.getId() != null) {
            throw new BadRequestAlertException("A new phaseOt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhaseOtDTO result = phaseOtService.save(phaseOtDTO);
        return ResponseEntity
            .created(new URI("/api/phase-ots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping("/phase-ots/{phaseOtId}/statut")
    public ResponseEntity<PhaseOtDTO> updateStatut(@PathVariable Long phaseOtId, @RequestParam String statut) {
        PhaseOtDTO result = phaseOtService.updateStatut(phaseOtId, statut);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/phase-ots/{phaseOtId}/is-parent")
    public ResponseEntity<Boolean> isParent(@PathVariable Long phaseOtId) {
        return ResponseEntity.ok(phaseOtService.isParent(phaseOtId));
    }

    /**
     * {@code PUT  /phase-ots/:id} : Updates an existing phaseOt.
     *
     * @param id the id of the phaseOtDTO to save.
     * @param phaseOtDTO the phaseOtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phaseOtDTO,
     * or with status {@code 400 (Bad Request)} if the phaseOtDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phaseOtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phase-ots/{id}")
    public ResponseEntity<PhaseOtDTO> updatePhaseOt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PhaseOtDTO phaseOtDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PhaseOt : {}, {}", id, phaseOtDTO);
        if (phaseOtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phaseOtDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phaseOtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhaseOtDTO result = phaseOtService.update(phaseOtDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phaseOtDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phase-ots/:id} : Partial updates given fields of an existing phaseOt, field will ignore if it is null
     *
     * @param id the id of the phaseOtDTO to save.
     * @param phaseOtDTO the phaseOtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phaseOtDTO,
     * or with status {@code 400 (Bad Request)} if the phaseOtDTO is not valid,
     * or with status {@code 404 (Not Found)} if the phaseOtDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the phaseOtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phase-ots/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhaseOtDTO> partialUpdatePhaseOt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PhaseOtDTO phaseOtDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhaseOt partially : {}, {}", id, phaseOtDTO);
        if (phaseOtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phaseOtDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phaseOtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhaseOtDTO> result = phaseOtService.partialUpdate(phaseOtDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phaseOtDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /phase-ots} : get all the phaseOts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phaseOts in body.
     */
    @GetMapping("/phase-ots")
    public ResponseEntity<List<PhaseOtDTO>> getAllPhaseOts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PhaseOts");
        Page<PhaseOtDTO> page = phaseOtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/phase-ots-parent")
    public ResponseEntity<List<PhaseOtDTO>> getAllPhaseOts(
        @RequestParam(required = false) Boolean parent,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PhaseOts, parent: {}", parent);

        Page<PhaseOtDTO> page = phaseOtService.findAll(pageable, parent);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/phase-ots/{id}/children")
    public ResponseEntity<List<PhaseOtDTO>> getPhaseOtChildren(
        @PathVariable Long id,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get children of PhaseOt : {}", id);

        Page<PhaseOtDTO> page = phaseOtService.findChildren(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phase-ots/:id} : get the "id" phaseOt.
     *
     * @param id the id of the phaseOtDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phaseOtDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phase-ots/{id}")
    public ResponseEntity<PhaseOtDTO> getPhaseOt(@PathVariable Long id) {
        log.debug("REST request to get PhaseOt : {}", id);
        Optional<PhaseOtDTO> phaseOtDTO = phaseOtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phaseOtDTO);
    }

    /**
     * {@code DELETE  /phase-ots/:id} : delete the "id" phaseOt.
     *
     * @param id the id of the phaseOtDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phase-ots/{id}")
    public ResponseEntity<Void> deletePhaseOt(@PathVariable Long id) {
        log.debug("REST request to delete PhaseOt : {}", id);
        phaseOtService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

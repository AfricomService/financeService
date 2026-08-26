package com.gpm.finance.web.rest;

import com.gpm.finance.repository.ModelPhaseOTRepository;
import com.gpm.finance.service.ModelPhaseOTService;
import com.gpm.finance.service.dto.ModelPhaseOTDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.ModelPhaseOT}.
 */
@RestController
@RequestMapping("/api")
public class ModelPhaseOTResource {

    private final Logger log = LoggerFactory.getLogger(ModelPhaseOTResource.class);

    private static final String ENTITY_NAME = "financeServiceModelPhaseOt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModelPhaseOTService modelPhaseOTService;

    private final ModelPhaseOTRepository modelPhaseOTRepository;

    public ModelPhaseOTResource(ModelPhaseOTService modelPhaseOTService, ModelPhaseOTRepository modelPhaseOTRepository) {
        this.modelPhaseOTService = modelPhaseOTService;
        this.modelPhaseOTRepository = modelPhaseOTRepository;
    }

    /**
     * {@code POST  /model-phase-ots} : Create a new modelPhaseOT.
     *
     * @param modelPhaseOTDTO the modelPhaseOTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelPhaseOTDTO, or with status {@code 400 (Bad Request)} if the modelPhaseOT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/model-phase-ots")
    public ResponseEntity<ModelPhaseOTDTO> createModelPhaseOT(@RequestBody ModelPhaseOTDTO modelPhaseOTDTO) throws URISyntaxException {
        log.debug("REST request to save ModelPhaseOT : {}", modelPhaseOTDTO);
        if (modelPhaseOTDTO.getId() != null) {
            throw new BadRequestAlertException("A new modelPhaseOT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModelPhaseOTDTO result = modelPhaseOTService.save(modelPhaseOTDTO);
        return ResponseEntity
            .created(new URI("/api/model-phase-ots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /model-phase-ots/:id} : Updates an existing modelPhaseOT.
     *
     * @param id the id of the modelPhaseOTDTO to save.
     * @param modelPhaseOTDTO the modelPhaseOTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelPhaseOTDTO,
     * or with status {@code 400 (Bad Request)} if the modelPhaseOTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelPhaseOTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/model-phase-ots/{id}")
    public ResponseEntity<ModelPhaseOTDTO> updateModelPhaseOT(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModelPhaseOTDTO modelPhaseOTDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ModelPhaseOT : {}, {}", id, modelPhaseOTDTO);
        if (modelPhaseOTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelPhaseOTDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modelPhaseOTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ModelPhaseOTDTO result = modelPhaseOTService.update(modelPhaseOTDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelPhaseOTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /model-phase-ots/:id} : Partial updates given fields of an existing modelPhaseOT, field will ignore if it is null
     *
     * @param id the id of the modelPhaseOTDTO to save.
     * @param modelPhaseOTDTO the modelPhaseOTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelPhaseOTDTO,
     * or with status {@code 400 (Bad Request)} if the modelPhaseOTDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modelPhaseOTDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modelPhaseOTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/model-phase-ots/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModelPhaseOTDTO> partialUpdateModelPhaseOT(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModelPhaseOTDTO modelPhaseOTDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModelPhaseOT partially : {}, {}", id, modelPhaseOTDTO);
        if (modelPhaseOTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelPhaseOTDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modelPhaseOTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModelPhaseOTDTO> result = modelPhaseOTService.partialUpdate(modelPhaseOTDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelPhaseOTDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /model-phase-ots} : get all the modelPhaseOTS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelPhaseOTS in body.
     */
    @GetMapping("/model-phase-ots")
    public ResponseEntity<List<ModelPhaseOTDTO>> getAllModelPhaseOTS(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ModelPhaseOTS");
        Page<ModelPhaseOTDTO> page = modelPhaseOTService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /model-phase-ots/:id} : get the "id" modelPhaseOT.
     *
     * @param id the id of the modelPhaseOTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelPhaseOTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/model-phase-ots/{id}")
    public ResponseEntity<ModelPhaseOTDTO> getModelPhaseOT(@PathVariable Long id) {
        log.debug("REST request to get ModelPhaseOT : {}", id);
        Optional<ModelPhaseOTDTO> modelPhaseOTDTO = modelPhaseOTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modelPhaseOTDTO);
    }

    /**
     * {@code DELETE  /model-phase-ots/:id} : delete the "id" modelPhaseOT.
     *
     * @param id the id of the modelPhaseOTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/model-phase-ots/{id}")
    public ResponseEntity<Void> deleteModelPhaseOT(@PathVariable Long id) {
        log.debug("REST request to delete ModelPhaseOT : {}", id);
        modelPhaseOTService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

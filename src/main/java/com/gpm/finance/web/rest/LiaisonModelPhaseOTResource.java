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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
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

    /**
     * {@code POST  /liaison-model-phase-ots} : Create a new liaisonModelPhaseOT.
     *
     * @param liaisonModelPhaseOTDTO the liaisonModelPhaseOTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new liaisonModelPhaseOTDTO, or with status {@code 400 (Bad Request)} if the liaisonModelPhaseOT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
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

    /**
     * {@code PUT  /liaison-model-phase-ots/:id} : Updates an existing liaisonModelPhaseOT.
     *
     * @param id the id of the liaisonModelPhaseOTDTO to save.
     * @param liaisonModelPhaseOTDTO the liaisonModelPhaseOTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated liaisonModelPhaseOTDTO,
     * or with status {@code 400 (Bad Request)} if the liaisonModelPhaseOTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the liaisonModelPhaseOTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
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

    /**
     * {@code PATCH  /liaison-model-phase-ots/:id} : Partial updates given fields of an existing liaisonModelPhaseOT, field will ignore if it is null
     *
     * @param id the id of the liaisonModelPhaseOTDTO to save.
     * @param liaisonModelPhaseOTDTO the liaisonModelPhaseOTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated liaisonModelPhaseOTDTO,
     * or with status {@code 400 (Bad Request)} if the liaisonModelPhaseOTDTO is not valid,
     * or with status {@code 404 (Not Found)} if the liaisonModelPhaseOTDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the liaisonModelPhaseOTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
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

    /**
     * {@code GET  /liaison-model-phase-ots} : get all the liaisonModelPhaseOTS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of liaisonModelPhaseOTS in body.
     */
    @GetMapping("/liaison-model-phase-ots")
    public List<LiaisonModelPhaseOTDTO> getAllLiaisonModelPhaseOTS() {
        log.debug("REST request to get all LiaisonModelPhaseOTS");
        return liaisonModelPhaseOTService.findAll();
    }

    /**
     * {@code GET  /liaison-model-phase-ots/:id} : get the "id" liaisonModelPhaseOT.
     *
     * @param id the id of the liaisonModelPhaseOTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the liaisonModelPhaseOTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/liaison-model-phase-ots/{id}")
    public ResponseEntity<LiaisonModelPhaseOTDTO> getLiaisonModelPhaseOT(@PathVariable Long id) {
        log.debug("REST request to get LiaisonModelPhaseOT : {}", id);
        Optional<LiaisonModelPhaseOTDTO> liaisonModelPhaseOTDTO = liaisonModelPhaseOTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(liaisonModelPhaseOTDTO);
    }

    /**
     * {@code DELETE  /liaison-model-phase-ots/:id} : delete the "id" liaisonModelPhaseOT.
     *
     * @param id the id of the liaisonModelPhaseOTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/liaison-model-phase-ots/{id}")
    public ResponseEntity<Void> deleteLiaisonModelPhaseOT(@PathVariable Long id) {
        log.debug("REST request to delete LiaisonModelPhaseOT : {}", id);
        liaisonModelPhaseOTService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

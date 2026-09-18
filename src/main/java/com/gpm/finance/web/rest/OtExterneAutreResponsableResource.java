package com.gpm.finance.web.rest;

import com.gpm.finance.repository.OtExterneAutreResponsableRepository;
import com.gpm.finance.service.OtExterneAutreResponsableService;
import com.gpm.finance.service.dto.OtExterneAutreResponsableDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.OtExterneAutreResponsable}.
 */
@RestController
@RequestMapping("/api")
public class OtExterneAutreResponsableResource {

    private final Logger log = LoggerFactory.getLogger(OtExterneAutreResponsableResource.class);

    private static final String ENTITY_NAME = "financeServiceOtExterneAutreResponsable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OtExterneAutreResponsableService otExterneAutreResponsableService;

    private final OtExterneAutreResponsableRepository otExterneAutreResponsableRepository;

    public OtExterneAutreResponsableResource(
        OtExterneAutreResponsableService otExterneAutreResponsableService,
        OtExterneAutreResponsableRepository otExterneAutreResponsableRepository
    ) {
        this.otExterneAutreResponsableService = otExterneAutreResponsableService;
        this.otExterneAutreResponsableRepository = otExterneAutreResponsableRepository;
    }

    /**
     * {@code POST  /ot-externe-autre-responsables} : Create a new otExterneAutreResponsable.
     *
     * @param otExterneAutreResponsableDTO the otExterneAutreResponsableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new otExterneAutreResponsableDTO, or with status {@code 400 (Bad Request)} if the otExterneAutreResponsable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ot-externe-autre-responsables")
    public ResponseEntity<OtExterneAutreResponsableDTO> createOtExterneAutreResponsable(
        @RequestBody OtExterneAutreResponsableDTO otExterneAutreResponsableDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OtExterneAutreResponsable : {}", otExterneAutreResponsableDTO);
        if (otExterneAutreResponsableDTO.getId() != null) {
            throw new BadRequestAlertException("A new otExterneAutreResponsable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OtExterneAutreResponsableDTO result = otExterneAutreResponsableService.save(otExterneAutreResponsableDTO);
        return ResponseEntity
            .created(new URI("/api/ot-externe-autre-responsables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ot-externe-autre-responsables/:id} : Updates an existing otExterneAutreResponsable.
     *
     * @param id the id of the otExterneAutreResponsableDTO to save.
     * @param otExterneAutreResponsableDTO the otExterneAutreResponsableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otExterneAutreResponsableDTO,
     * or with status {@code 400 (Bad Request)} if the otExterneAutreResponsableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the otExterneAutreResponsableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ot-externe-autre-responsables/{id}")
    public ResponseEntity<OtExterneAutreResponsableDTO> updateOtExterneAutreResponsable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OtExterneAutreResponsableDTO otExterneAutreResponsableDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OtExterneAutreResponsable : {}, {}", id, otExterneAutreResponsableDTO);
        if (otExterneAutreResponsableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otExterneAutreResponsableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otExterneAutreResponsableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OtExterneAutreResponsableDTO result = otExterneAutreResponsableService.save(otExterneAutreResponsableDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otExterneAutreResponsableDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /ot-externe-autre-responsables/:id} : Partial updates given fields of an existing otExterneAutreResponsable, field will ignore if it is null
     *
     * @param id the id of the otExterneAutreResponsableDTO to save.
     * @param otExterneAutreResponsableDTO the otExterneAutreResponsableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otExterneAutreResponsableDTO,
     * or with status {@code 400 (Bad Request)} if the otExterneAutreResponsableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the otExterneAutreResponsableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the otExterneAutreResponsableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ot-externe-autre-responsables/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OtExterneAutreResponsableDTO> partialUpdateOtExterneAutreResponsable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OtExterneAutreResponsableDTO otExterneAutreResponsableDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OtExterneAutreResponsable partially : {}, {}", id, otExterneAutreResponsableDTO);
        if (otExterneAutreResponsableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otExterneAutreResponsableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otExterneAutreResponsableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OtExterneAutreResponsableDTO> result = otExterneAutreResponsableService.partialUpdate(otExterneAutreResponsableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otExterneAutreResponsableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ot-externe-autre-responsables} : get all the otExterneAutreResponsables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of otExterneAutreResponsables in body.
     */
    @GetMapping("/ot-externe-autre-responsables")
    public List<OtExterneAutreResponsableDTO> getAllOtExterneAutreResponsables() {
        log.debug("REST request to get all OtExterneAutreResponsables");
        return otExterneAutreResponsableService.findAll();
    }

    /**
     * {@code GET  /ot-externe-autre-responsables/:id} : get the "id" otExterneAutreResponsable.
     *
     * @param id the id of the otExterneAutreResponsableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the otExterneAutreResponsableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ot-externe-autre-responsables/{id}")
    public ResponseEntity<OtExterneAutreResponsableDTO> getOtExterneAutreResponsable(@PathVariable Long id) {
        log.debug("REST request to get OtExterneAutreResponsable : {}", id);
        Optional<OtExterneAutreResponsableDTO> otExterneAutreResponsableDTO = otExterneAutreResponsableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(otExterneAutreResponsableDTO);
    }

    /**
     * {@code DELETE  /ot-externe-autre-responsables/:id} : delete the "id" otExterneAutreResponsable.
     *
     * @param id the id of the otExterneAutreResponsableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ot-externe-autre-responsables/{id}")
    public ResponseEntity<Void> deleteOtExterneAutreResponsable(@PathVariable Long id) {
        log.debug("REST request to delete OtExterneAutreResponsable : {}", id);
        otExterneAutreResponsableService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /ot-externe-autre-responsables/by-ot-externe/:otExterneId} :
     * get all otExterneAutreResponsables for a given otExterne.
     */
    @GetMapping("/ot-externe-autre-responsables/by-ot-externe/{otExterneId}")
    public List<OtExterneAutreResponsableDTO> getAllOtExterneAutreResponsablesByOtExterne(@PathVariable Long otExterneId) {
        log.debug("REST request to get all OtExterneAutreResponsables for otExterne : {}", otExterneId);
        return otExterneAutreResponsableService.findByOtExterne(otExterneId);
    }

    /**
     * {@code PUT  /ot-externe-autre-responsables/by-ot-externe/:otExterneId} :
     * replace all otExterneAutreResponsables for a given otExterne.
     */
    @PutMapping("/ot-externe-autre-responsables/by-ot-externe/{otExterneId}")
    public List<OtExterneAutreResponsableDTO> replaceOtExterneAutreResponsablesForOtExterne(
        @PathVariable Long otExterneId,
        @RequestBody List<Long> contactSocieteIds
    ) {
        log.debug("REST request to replace OtExterneAutreResponsables for otExterne {} with {}", otExterneId, contactSocieteIds);
        return otExterneAutreResponsableService.replaceForOtExterne(otExterneId, contactSocieteIds);
    }
}

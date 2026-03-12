package com.gpm.finance.web.rest;

import com.gpm.finance.repository.FactureWORepository;
import com.gpm.finance.service.FactureWOService;
import com.gpm.finance.service.dto.FactureWODTO;
import com.gpm.finance.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.gpm.finance.domain.FactureWO}.
 */
@RestController
@RequestMapping("/api")
public class FactureWOResource {

    private final Logger log = LoggerFactory.getLogger(FactureWOResource.class);

    private static final String ENTITY_NAME = "financeServiceFactureWo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactureWOService factureWOService;

    private final FactureWORepository factureWORepository;

    public FactureWOResource(FactureWOService factureWOService, FactureWORepository factureWORepository) {
        this.factureWOService = factureWOService;
        this.factureWORepository = factureWORepository;
    }

    /**
     * {@code POST  /facture-wos} : Create a new factureWO.
     *
     * @param factureWODTO the factureWODTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factureWODTO, or with status {@code 400 (Bad Request)} if the factureWO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facture-wos")
    public ResponseEntity<FactureWODTO> createFactureWO(@Valid @RequestBody FactureWODTO factureWODTO) throws URISyntaxException {
        log.debug("REST request to save FactureWO : {}", factureWODTO);
        if (factureWODTO.getId() != null) {
            throw new BadRequestAlertException("A new factureWO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactureWODTO result = factureWOService.save(factureWODTO);
        return ResponseEntity
            .created(new URI("/api/facture-wos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facture-wos/:id} : Updates an existing factureWO.
     *
     * @param id the id of the factureWODTO to save.
     * @param factureWODTO the factureWODTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureWODTO,
     * or with status {@code 400 (Bad Request)} if the factureWODTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factureWODTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facture-wos/{id}")
    public ResponseEntity<FactureWODTO> updateFactureWO(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FactureWODTO factureWODTO
    ) throws URISyntaxException {
        log.debug("REST request to update FactureWO : {}, {}", id, factureWODTO);
        if (factureWODTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureWODTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureWORepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FactureWODTO result = factureWOService.update(factureWODTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, factureWODTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /facture-wos/:id} : Partial updates given fields of an existing factureWO, field will ignore if it is null
     *
     * @param id the id of the factureWODTO to save.
     * @param factureWODTO the factureWODTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureWODTO,
     * or with status {@code 400 (Bad Request)} if the factureWODTO is not valid,
     * or with status {@code 404 (Not Found)} if the factureWODTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the factureWODTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/facture-wos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FactureWODTO> partialUpdateFactureWO(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FactureWODTO factureWODTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FactureWO partially : {}, {}", id, factureWODTO);
        if (factureWODTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureWODTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureWORepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FactureWODTO> result = factureWOService.partialUpdate(factureWODTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, factureWODTO.getId().toString())
        );
    }

    /**
     * {@code GET  /facture-wos} : get all the factureWOS.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factureWOS in body.
     */
    @GetMapping("/facture-wos")
    public ResponseEntity<List<FactureWODTO>> getAllFactureWOS(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of FactureWOS");
        Page<FactureWODTO> page;
        if (eagerload) {
            page = factureWOService.findAllWithEagerRelationships(pageable);
        } else {
            page = factureWOService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facture-wos/:id} : get the "id" factureWO.
     *
     * @param id the id of the factureWODTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factureWODTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facture-wos/{id}")
    public ResponseEntity<FactureWODTO> getFactureWO(@PathVariable Long id) {
        log.debug("REST request to get FactureWO : {}", id);
        Optional<FactureWODTO> factureWODTO = factureWOService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factureWODTO);
    }

    /**
     * {@code DELETE  /facture-wos/:id} : delete the "id" factureWO.
     *
     * @param id the id of the factureWODTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facture-wos/{id}")
    public ResponseEntity<Void> deleteFactureWO(@PathVariable Long id) {
        log.debug("REST request to delete FactureWO : {}", id);
        factureWOService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

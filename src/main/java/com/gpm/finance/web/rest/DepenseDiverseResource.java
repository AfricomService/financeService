package com.gpm.finance.web.rest;

import com.gpm.finance.repository.DepenseDiverseRepository;
import com.gpm.finance.service.DepenseDiverseService;
import com.gpm.finance.service.dto.DepenseDiverseDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.DepenseDiverse}.
 */
@RestController
@RequestMapping("/api")
public class DepenseDiverseResource {

    private final Logger log = LoggerFactory.getLogger(DepenseDiverseResource.class);

    private static final String ENTITY_NAME = "financeServiceDepenseDiverse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepenseDiverseService depenseDiverseService;

    private final DepenseDiverseRepository depenseDiverseRepository;

    public DepenseDiverseResource(DepenseDiverseService depenseDiverseService, DepenseDiverseRepository depenseDiverseRepository) {
        this.depenseDiverseService = depenseDiverseService;
        this.depenseDiverseRepository = depenseDiverseRepository;
    }

    /**
     * {@code POST  /depense-diverses} : Create a new depenseDiverse.
     *
     * @param depenseDiverseDTO the depenseDiverseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depenseDiverseDTO, or with status {@code 400 (Bad Request)} if the depenseDiverse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/depense-diverses")
    public ResponseEntity<DepenseDiverseDTO> createDepenseDiverse(@Valid @RequestBody DepenseDiverseDTO depenseDiverseDTO)
        throws URISyntaxException {
        log.debug("REST request to save DepenseDiverse : {}", depenseDiverseDTO);
        if (depenseDiverseDTO.getId() != null) {
            throw new BadRequestAlertException("A new depenseDiverse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepenseDiverseDTO result = depenseDiverseService.save(depenseDiverseDTO);
        return ResponseEntity
            .created(new URI("/api/depense-diverses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /depense-diverses/:id} : Updates an existing depenseDiverse.
     *
     * @param id the id of the depenseDiverseDTO to save.
     * @param depenseDiverseDTO the depenseDiverseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depenseDiverseDTO,
     * or with status {@code 400 (Bad Request)} if the depenseDiverseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depenseDiverseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/depense-diverses/{id}")
    public ResponseEntity<DepenseDiverseDTO> updateDepenseDiverse(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepenseDiverseDTO depenseDiverseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DepenseDiverse : {}, {}", id, depenseDiverseDTO);
        if (depenseDiverseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depenseDiverseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depenseDiverseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DepenseDiverseDTO result = depenseDiverseService.update(depenseDiverseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depenseDiverseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /depense-diverses/:id} : Partial updates given fields of an existing depenseDiverse, field will ignore if it is null
     *
     * @param id the id of the depenseDiverseDTO to save.
     * @param depenseDiverseDTO the depenseDiverseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depenseDiverseDTO,
     * or with status {@code 400 (Bad Request)} if the depenseDiverseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the depenseDiverseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the depenseDiverseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/depense-diverses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepenseDiverseDTO> partialUpdateDepenseDiverse(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DepenseDiverseDTO depenseDiverseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DepenseDiverse partially : {}, {}", id, depenseDiverseDTO);
        if (depenseDiverseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depenseDiverseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depenseDiverseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DepenseDiverseDTO> result = depenseDiverseService.partialUpdate(depenseDiverseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depenseDiverseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /depense-diverses} : get all the depenseDiverses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depenseDiverses in body.
     */
    @GetMapping("/depense-diverses")
    public ResponseEntity<List<DepenseDiverseDTO>> getAllDepenseDiverses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DepenseDiverses");
        Page<DepenseDiverseDTO> page = depenseDiverseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /depense-diverses/:id} : get the "id" depenseDiverse.
     *
     * @param id the id of the depenseDiverseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depenseDiverseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/depense-diverses/{id}")
    public ResponseEntity<DepenseDiverseDTO> getDepenseDiverse(@PathVariable Long id) {
        log.debug("REST request to get DepenseDiverse : {}", id);
        Optional<DepenseDiverseDTO> depenseDiverseDTO = depenseDiverseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depenseDiverseDTO);
    }

    /**
     * {@code DELETE  /depense-diverses/:id} : delete the "id" depenseDiverse.
     *
     * @param id the id of the depenseDiverseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/depense-diverses/{id}")
    public ResponseEntity<Void> deleteDepenseDiverse(@PathVariable Long id) {
        log.debug("REST request to delete DepenseDiverse : {}", id);
        depenseDiverseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

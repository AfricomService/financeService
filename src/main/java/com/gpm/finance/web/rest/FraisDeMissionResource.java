package com.gpm.finance.web.rest;

import com.gpm.finance.repository.FraisDeMissionRepository;
import com.gpm.finance.service.FraisDeMissionService;
import com.gpm.finance.service.dto.FraisDeMissionDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.FraisDeMission}.
 */
@RestController
@RequestMapping("/api")
public class FraisDeMissionResource {

    private final Logger log = LoggerFactory.getLogger(FraisDeMissionResource.class);

    private static final String ENTITY_NAME = "financeServiceFraisDeMission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraisDeMissionService fraisDeMissionService;

    private final FraisDeMissionRepository fraisDeMissionRepository;

    public FraisDeMissionResource(FraisDeMissionService fraisDeMissionService, FraisDeMissionRepository fraisDeMissionRepository) {
        this.fraisDeMissionService = fraisDeMissionService;
        this.fraisDeMissionRepository = fraisDeMissionRepository;
    }

    /**
     * {@code POST  /frais-de-missions} : Create a new fraisDeMission.
     *
     * @param fraisDeMissionDTO the fraisDeMissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fraisDeMissionDTO, or with status {@code 400 (Bad Request)} if the fraisDeMission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/frais-de-missions")
    public ResponseEntity<FraisDeMissionDTO> createFraisDeMission(@Valid @RequestBody FraisDeMissionDTO fraisDeMissionDTO)
        throws URISyntaxException {
        log.debug("REST request to save FraisDeMission : {}", fraisDeMissionDTO);
        if (fraisDeMissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new fraisDeMission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FraisDeMissionDTO result = fraisDeMissionService.save(fraisDeMissionDTO);
        return ResponseEntity
            .created(new URI("/api/frais-de-missions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /frais-de-missions/:id} : Updates an existing fraisDeMission.
     *
     * @param id the id of the fraisDeMissionDTO to save.
     * @param fraisDeMissionDTO the fraisDeMissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraisDeMissionDTO,
     * or with status {@code 400 (Bad Request)} if the fraisDeMissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fraisDeMissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/frais-de-missions/{id}")
    public ResponseEntity<FraisDeMissionDTO> updateFraisDeMission(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FraisDeMissionDTO fraisDeMissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FraisDeMission : {}, {}", id, fraisDeMissionDTO);
        if (fraisDeMissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraisDeMissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraisDeMissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FraisDeMissionDTO result = fraisDeMissionService.update(fraisDeMissionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fraisDeMissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /frais-de-missions/:id} : Partial updates given fields of an existing fraisDeMission, field will ignore if it is null
     *
     * @param id the id of the fraisDeMissionDTO to save.
     * @param fraisDeMissionDTO the fraisDeMissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraisDeMissionDTO,
     * or with status {@code 400 (Bad Request)} if the fraisDeMissionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fraisDeMissionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fraisDeMissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/frais-de-missions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FraisDeMissionDTO> partialUpdateFraisDeMission(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FraisDeMissionDTO fraisDeMissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FraisDeMission partially : {}, {}", id, fraisDeMissionDTO);
        if (fraisDeMissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraisDeMissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraisDeMissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FraisDeMissionDTO> result = fraisDeMissionService.partialUpdate(fraisDeMissionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fraisDeMissionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /frais-de-missions} : get all the fraisDeMissions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fraisDeMissions in body.
     */
    @GetMapping("/frais-de-missions")
    public ResponseEntity<List<FraisDeMissionDTO>> getAllFraisDeMissions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FraisDeMissions");
        Page<FraisDeMissionDTO> page = fraisDeMissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /frais-de-missions/:id} : get the "id" fraisDeMission.
     *
     * @param id the id of the fraisDeMissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fraisDeMissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/frais-de-missions/{id}")
    public ResponseEntity<FraisDeMissionDTO> getFraisDeMission(@PathVariable Long id) {
        log.debug("REST request to get FraisDeMission : {}", id);
        Optional<FraisDeMissionDTO> fraisDeMissionDTO = fraisDeMissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fraisDeMissionDTO);
    }

    /**
     * {@code DELETE  /frais-de-missions/:id} : delete the "id" fraisDeMission.
     *
     * @param id the id of the fraisDeMissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/frais-de-missions/{id}")
    public ResponseEntity<Void> deleteFraisDeMission(@PathVariable Long id) {
        log.debug("REST request to delete FraisDeMission : {}", id);
        fraisDeMissionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

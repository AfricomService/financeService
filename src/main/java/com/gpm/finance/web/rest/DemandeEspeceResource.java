package com.gpm.finance.web.rest;

import com.gpm.finance.repository.DemandeEspeceRepository;
import com.gpm.finance.service.DemandeEspeceService;
import com.gpm.finance.service.dto.DemandeEspeceDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.DemandeEspece}.
 */
@RestController
@RequestMapping("/api")
public class DemandeEspeceResource {

    private final Logger log = LoggerFactory.getLogger(DemandeEspeceResource.class);

    private static final String ENTITY_NAME = "financeServiceDemandeEspece";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeEspeceService demandeEspeceService;

    private final DemandeEspeceRepository demandeEspeceRepository;

    public DemandeEspeceResource(DemandeEspeceService demandeEspeceService, DemandeEspeceRepository demandeEspeceRepository) {
        this.demandeEspeceService = demandeEspeceService;
        this.demandeEspeceRepository = demandeEspeceRepository;
    }

    /**
     * {@code POST  /demande-especes} : Create a new demandeEspece.
     *
     * @param demandeEspeceDTO the demandeEspeceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeEspeceDTO, or with status {@code 400 (Bad Request)} if the demandeEspece has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-especes")
    public ResponseEntity<DemandeEspeceDTO> createDemandeEspece(@Valid @RequestBody DemandeEspeceDTO demandeEspeceDTO)
        throws URISyntaxException {
        log.debug("REST request to save DemandeEspece : {}", demandeEspeceDTO);
        if (demandeEspeceDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandeEspece cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeEspeceDTO result = demandeEspeceService.save(demandeEspeceDTO);
        return ResponseEntity
            .created(new URI("/api/demande-especes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-especes/:id} : Updates an existing demandeEspece.
     *
     * @param id the id of the demandeEspeceDTO to save.
     * @param demandeEspeceDTO the demandeEspeceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeEspeceDTO,
     * or with status {@code 400 (Bad Request)} if the demandeEspeceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeEspeceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-especes/{id}")
    public ResponseEntity<DemandeEspeceDTO> updateDemandeEspece(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemandeEspeceDTO demandeEspeceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeEspece : {}, {}", id, demandeEspeceDTO);
        if (demandeEspeceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeEspeceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeEspeceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeEspeceDTO result = demandeEspeceService.update(demandeEspeceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeEspeceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-especes/:id} : Partial updates given fields of an existing demandeEspece, field will ignore if it is null
     *
     * @param id the id of the demandeEspeceDTO to save.
     * @param demandeEspeceDTO the demandeEspeceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeEspeceDTO,
     * or with status {@code 400 (Bad Request)} if the demandeEspeceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandeEspeceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeEspeceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demande-especes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeEspeceDTO> partialUpdateDemandeEspece(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemandeEspeceDTO demandeEspeceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeEspece partially : {}, {}", id, demandeEspeceDTO);
        if (demandeEspeceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeEspeceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeEspeceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeEspeceDTO> result = demandeEspeceService.partialUpdate(demandeEspeceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeEspeceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-especes} : get all the demandeEspeces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeEspeces in body.
     */
    @GetMapping("/demande-especes")
    public ResponseEntity<List<DemandeEspeceDTO>> getAllDemandeEspeces(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DemandeEspeces");
        Page<DemandeEspeceDTO> page = demandeEspeceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-especes/:id} : get the "id" demandeEspece.
     *
     * @param id the id of the demandeEspeceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeEspeceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-especes/{id}")
    public ResponseEntity<DemandeEspeceDTO> getDemandeEspece(@PathVariable Long id) {
        log.debug("REST request to get DemandeEspece : {}", id);
        Optional<DemandeEspeceDTO> demandeEspeceDTO = demandeEspeceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeEspeceDTO);
    }

    /**
     * {@code DELETE  /demande-especes/:id} : delete the "id" demandeEspece.
     *
     * @param id the id of the demandeEspeceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-especes/{id}")
    public ResponseEntity<Void> deleteDemandeEspece(@PathVariable Long id) {
        log.debug("REST request to delete DemandeEspece : {}", id);
        demandeEspeceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

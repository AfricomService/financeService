package com.gpm.finance.web.rest;

import com.gpm.finance.repository.OtExterneRepository;
import com.gpm.finance.service.OtExterneService;
import com.gpm.finance.service.dto.OtExterneDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.OtExterne}.
 */
@RestController
@RequestMapping("/api")
public class OtExterneResource {

    private final Logger log = LoggerFactory.getLogger(OtExterneResource.class);

    private static final String ENTITY_NAME = "financeServiceOtExterne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OtExterneService otExterneService;

    private final OtExterneRepository otExterneRepository;

    public OtExterneResource(OtExterneService otExterneService, OtExterneRepository otExterneRepository) {
        this.otExterneService = otExterneService;
        this.otExterneRepository = otExterneRepository;
    }

    /**
     * {@code POST  /ot-externes} : Create a new otExterne.
     *
     * @param otExterneDTO the otExterneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new otExterneDTO, or with status {@code 400 (Bad Request)} if the otExterne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ot-externes")
    public ResponseEntity<OtExterneDTO> createOtExterne(@Valid @RequestBody OtExterneDTO otExterneDTO) throws URISyntaxException {
        log.debug("REST request to save OtExterne : {}", otExterneDTO);
        if (otExterneDTO.getId() != null) {
            throw new BadRequestAlertException("A new otExterne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OtExterneDTO result = otExterneService.save(otExterneDTO);
        return ResponseEntity
            .created(new URI("/api/ot-externes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ot-externes/:id} : Updates an existing otExterne.
     *
     * @param id the id of the otExterneDTO to save.
     * @param otExterneDTO the otExterneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otExterneDTO,
     * or with status {@code 400 (Bad Request)} if the otExterneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the otExterneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ot-externes/{id}")
    public ResponseEntity<OtExterneDTO> updateOtExterne(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OtExterneDTO otExterneDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OtExterne : {}, {}", id, otExterneDTO);
        if (otExterneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otExterneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otExterneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OtExterneDTO result = otExterneService.update(otExterneDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otExterneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ot-externes/:id} : Partial updates given fields of an existing otExterne, field will ignore if it is null
     *
     * @param id the id of the otExterneDTO to save.
     * @param otExterneDTO the otExterneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otExterneDTO,
     * or with status {@code 400 (Bad Request)} if the otExterneDTO is not valid,
     * or with status {@code 404 (Not Found)} if the otExterneDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the otExterneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ot-externes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OtExterneDTO> partialUpdateOtExterne(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OtExterneDTO otExterneDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OtExterne partially : {}, {}", id, otExterneDTO);
        if (otExterneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otExterneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otExterneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OtExterneDTO> result = otExterneService.partialUpdate(otExterneDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otExterneDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ot-externes} : get all the otExternes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of otExternes in body.
     */
    @GetMapping("/ot-externes")
    public ResponseEntity<List<OtExterneDTO>> getAllOtExternes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OtExternes");
        Page<OtExterneDTO> page = otExterneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ot-externes/:id} : get the "id" otExterne.
     *
     * @param id the id of the otExterneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the otExterneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ot-externes/{id}")
    public ResponseEntity<OtExterneDTO> getOtExterne(@PathVariable Long id) {
        log.debug("REST request to get OtExterne : {}", id);
        Optional<OtExterneDTO> otExterneDTO = otExterneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(otExterneDTO);
    }

    /**
     * {@code DELETE  /ot-externes/:id} : delete the "id" otExterne.
     *
     * @param id the id of the otExterneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ot-externes/{id}")
    public ResponseEntity<Void> deleteOtExterne(@PathVariable Long id) {
        log.debug("REST request to delete OtExterne : {}", id);
        otExterneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

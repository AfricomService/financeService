package com.gpm.finance.web.rest;

import com.gpm.finance.repository.OtArticlesRepository;
import com.gpm.finance.service.OtArticlesService;
import com.gpm.finance.service.dto.OtArticlesDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.OtArticles}.
 */
@RestController
@RequestMapping("/api")
public class OtArticlesResource {

    private final Logger log = LoggerFactory.getLogger(OtArticlesResource.class);

    private static final String ENTITY_NAME = "financeServiceOtArticles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OtArticlesService otArticlesService;

    private final OtArticlesRepository otArticlesRepository;

    public OtArticlesResource(OtArticlesService otArticlesService, OtArticlesRepository otArticlesRepository) {
        this.otArticlesService = otArticlesService;
        this.otArticlesRepository = otArticlesRepository;
    }

    /**
     * {@code POST  /ot-articles} : Create a new otArticles.
     *
     * @param otArticlesDTO the otArticlesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new otArticlesDTO, or with status {@code 400 (Bad Request)} if the otArticles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ot-articles")
    public ResponseEntity<OtArticlesDTO> createOtArticles(@RequestBody OtArticlesDTO otArticlesDTO) throws URISyntaxException {
        log.debug("REST request to save OtArticles : {}", otArticlesDTO);
        if (otArticlesDTO.getId() != null) {
            throw new BadRequestAlertException("A new otArticles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OtArticlesDTO result = otArticlesService.save(otArticlesDTO);
        return ResponseEntity
            .created(new URI("/api/ot-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ot-articles/:id} : Updates an existing otArticles.
     *
     * @param id the id of the otArticlesDTO to save.
     * @param otArticlesDTO the otArticlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otArticlesDTO,
     * or with status {@code 400 (Bad Request)} if the otArticlesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the otArticlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ot-articles/{id}")
    public ResponseEntity<OtArticlesDTO> updateOtArticles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OtArticlesDTO otArticlesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OtArticles : {}, {}", id, otArticlesDTO);
        if (otArticlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otArticlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otArticlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OtArticlesDTO result = otArticlesService.update(otArticlesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otArticlesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ot-articles/:id} : Partial updates given fields of an existing otArticles, field will ignore if it is null
     *
     * @param id the id of the otArticlesDTO to save.
     * @param otArticlesDTO the otArticlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otArticlesDTO,
     * or with status {@code 400 (Bad Request)} if the otArticlesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the otArticlesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the otArticlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ot-articles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OtArticlesDTO> partialUpdateOtArticles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OtArticlesDTO otArticlesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OtArticles partially : {}, {}", id, otArticlesDTO);
        if (otArticlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otArticlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otArticlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OtArticlesDTO> result = otArticlesService.partialUpdate(otArticlesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otArticlesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ot-articles} : get all the otArticles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of otArticles in body.
     */
    @GetMapping("/ot-articles")
    public List<OtArticlesDTO> getAllOtArticles() {
        log.debug("REST request to get all OtArticles");
        return otArticlesService.findAll();
    }

    /**
     * {@code GET  /ot-articles/:id} : get the "id" otArticles.
     *
     * @param id the id of the otArticlesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the otArticlesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ot-articles/{id}")
    public ResponseEntity<OtArticlesDTO> getOtArticles(@PathVariable Long id) {
        log.debug("REST request to get OtArticles : {}", id);
        Optional<OtArticlesDTO> otArticlesDTO = otArticlesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(otArticlesDTO);
    }

    /**
     * {@code DELETE  /ot-articles/:id} : delete the "id" otArticles.
     *
     * @param id the id of the otArticlesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ot-articles/{id}")
    public ResponseEntity<Void> deleteOtArticles(@PathVariable Long id) {
        log.debug("REST request to delete OtArticles : {}", id);
        otArticlesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

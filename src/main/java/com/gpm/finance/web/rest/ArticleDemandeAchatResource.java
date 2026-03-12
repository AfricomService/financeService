package com.gpm.finance.web.rest;

import com.gpm.finance.repository.ArticleDemandeAchatRepository;
import com.gpm.finance.service.ArticleDemandeAchatService;
import com.gpm.finance.service.dto.ArticleDemandeAchatDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.ArticleDemandeAchat}.
 */
@RestController
@RequestMapping("/api")
public class ArticleDemandeAchatResource {

    private final Logger log = LoggerFactory.getLogger(ArticleDemandeAchatResource.class);

    private static final String ENTITY_NAME = "financeServiceArticleDemandeAchat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleDemandeAchatService articleDemandeAchatService;

    private final ArticleDemandeAchatRepository articleDemandeAchatRepository;

    public ArticleDemandeAchatResource(
        ArticleDemandeAchatService articleDemandeAchatService,
        ArticleDemandeAchatRepository articleDemandeAchatRepository
    ) {
        this.articleDemandeAchatService = articleDemandeAchatService;
        this.articleDemandeAchatRepository = articleDemandeAchatRepository;
    }

    /**
     * {@code POST  /article-demande-achats} : Create a new articleDemandeAchat.
     *
     * @param articleDemandeAchatDTO the articleDemandeAchatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleDemandeAchatDTO, or with status {@code 400 (Bad Request)} if the articleDemandeAchat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/article-demande-achats")
    public ResponseEntity<ArticleDemandeAchatDTO> createArticleDemandeAchat(
        @Valid @RequestBody ArticleDemandeAchatDTO articleDemandeAchatDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ArticleDemandeAchat : {}", articleDemandeAchatDTO);
        if (articleDemandeAchatDTO.getId() != null) {
            throw new BadRequestAlertException("A new articleDemandeAchat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleDemandeAchatDTO result = articleDemandeAchatService.save(articleDemandeAchatDTO);
        return ResponseEntity
            .created(new URI("/api/article-demande-achats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-demande-achats/:id} : Updates an existing articleDemandeAchat.
     *
     * @param id the id of the articleDemandeAchatDTO to save.
     * @param articleDemandeAchatDTO the articleDemandeAchatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleDemandeAchatDTO,
     * or with status {@code 400 (Bad Request)} if the articleDemandeAchatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleDemandeAchatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/article-demande-achats/{id}")
    public ResponseEntity<ArticleDemandeAchatDTO> updateArticleDemandeAchat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArticleDemandeAchatDTO articleDemandeAchatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArticleDemandeAchat : {}, {}", id, articleDemandeAchatDTO);
        if (articleDemandeAchatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleDemandeAchatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleDemandeAchatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArticleDemandeAchatDTO result = articleDemandeAchatService.update(articleDemandeAchatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleDemandeAchatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /article-demande-achats/:id} : Partial updates given fields of an existing articleDemandeAchat, field will ignore if it is null
     *
     * @param id the id of the articleDemandeAchatDTO to save.
     * @param articleDemandeAchatDTO the articleDemandeAchatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleDemandeAchatDTO,
     * or with status {@code 400 (Bad Request)} if the articleDemandeAchatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the articleDemandeAchatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleDemandeAchatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/article-demande-achats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticleDemandeAchatDTO> partialUpdateArticleDemandeAchat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArticleDemandeAchatDTO articleDemandeAchatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArticleDemandeAchat partially : {}, {}", id, articleDemandeAchatDTO);
        if (articleDemandeAchatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleDemandeAchatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleDemandeAchatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticleDemandeAchatDTO> result = articleDemandeAchatService.partialUpdate(articleDemandeAchatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleDemandeAchatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /article-demande-achats} : get all the articleDemandeAchats.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleDemandeAchats in body.
     */
    @GetMapping("/article-demande-achats")
    public ResponseEntity<List<ArticleDemandeAchatDTO>> getAllArticleDemandeAchats(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ArticleDemandeAchats");
        Page<ArticleDemandeAchatDTO> page;
        if (eagerload) {
            page = articleDemandeAchatService.findAllWithEagerRelationships(pageable);
        } else {
            page = articleDemandeAchatService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-demande-achats/:id} : get the "id" articleDemandeAchat.
     *
     * @param id the id of the articleDemandeAchatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleDemandeAchatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/article-demande-achats/{id}")
    public ResponseEntity<ArticleDemandeAchatDTO> getArticleDemandeAchat(@PathVariable Long id) {
        log.debug("REST request to get ArticleDemandeAchat : {}", id);
        Optional<ArticleDemandeAchatDTO> articleDemandeAchatDTO = articleDemandeAchatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleDemandeAchatDTO);
    }

    /**
     * {@code DELETE  /article-demande-achats/:id} : delete the "id" articleDemandeAchat.
     *
     * @param id the id of the articleDemandeAchatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/article-demande-achats/{id}")
    public ResponseEntity<Void> deleteArticleDemandeAchat(@PathVariable Long id) {
        log.debug("REST request to delete ArticleDemandeAchat : {}", id);
        articleDemandeAchatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

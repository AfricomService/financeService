package com.gpm.finance.web.rest;

import com.gpm.finance.repository.BonCommandeArticlesRepository;
import com.gpm.finance.service.BonCommandeArticlesService;
import com.gpm.finance.service.dto.BonCommandeArticlesDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.BonCommandeArticles}.
 */
@RestController
@RequestMapping("/api")
public class BonCommandeArticlesResource {

    private final Logger log = LoggerFactory.getLogger(BonCommandeArticlesResource.class);

    private static final String ENTITY_NAME = "financeServiceBonCommandeArticles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonCommandeArticlesService bonCommandeArticlesService;

    private final BonCommandeArticlesRepository bonCommandeArticlesRepository;

    public BonCommandeArticlesResource(
        BonCommandeArticlesService bonCommandeArticlesService,
        BonCommandeArticlesRepository bonCommandeArticlesRepository
    ) {
        this.bonCommandeArticlesService = bonCommandeArticlesService;
        this.bonCommandeArticlesRepository = bonCommandeArticlesRepository;
    }

    /**
     * {@code POST  /bon-commande-articles} : Create a new bonCommandeArticles.
     *
     * @param bonCommandeArticlesDTO the bonCommandeArticlesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonCommandeArticlesDTO, or with status {@code 400 (Bad Request)} if the bonCommandeArticles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bon-commande-articles")
    public ResponseEntity<BonCommandeArticlesDTO> createBonCommandeArticles(@RequestBody BonCommandeArticlesDTO bonCommandeArticlesDTO)
        throws URISyntaxException {
        log.debug("REST request to save BonCommandeArticles : {}", bonCommandeArticlesDTO);
        if (bonCommandeArticlesDTO.getId() != null) {
            throw new BadRequestAlertException("A new bonCommandeArticles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BonCommandeArticlesDTO result = bonCommandeArticlesService.save(bonCommandeArticlesDTO);
        return ResponseEntity
            .created(new URI("/api/bon-commande-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bon-commande-articles/:id} : Updates an existing bonCommandeArticles.
     *
     * @param id the id of the bonCommandeArticlesDTO to save.
     * @param bonCommandeArticlesDTO the bonCommandeArticlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommandeArticlesDTO,
     * or with status {@code 400 (Bad Request)} if the bonCommandeArticlesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonCommandeArticlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bon-commande-articles/{id}")
    public ResponseEntity<BonCommandeArticlesDTO> updateBonCommandeArticles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonCommandeArticlesDTO bonCommandeArticlesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BonCommandeArticles : {}, {}", id, bonCommandeArticlesDTO);
        if (bonCommandeArticlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonCommandeArticlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonCommandeArticlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BonCommandeArticlesDTO result = bonCommandeArticlesService.update(bonCommandeArticlesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonCommandeArticlesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bon-commande-articles/:id} : Partial updates given fields of an existing bonCommandeArticles, field will ignore if it is null
     *
     * @param id the id of the bonCommandeArticlesDTO to save.
     * @param bonCommandeArticlesDTO the bonCommandeArticlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommandeArticlesDTO,
     * or with status {@code 400 (Bad Request)} if the bonCommandeArticlesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bonCommandeArticlesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bonCommandeArticlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bon-commande-articles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BonCommandeArticlesDTO> partialUpdateBonCommandeArticles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonCommandeArticlesDTO bonCommandeArticlesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BonCommandeArticles partially : {}, {}", id, bonCommandeArticlesDTO);
        if (bonCommandeArticlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonCommandeArticlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonCommandeArticlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BonCommandeArticlesDTO> result = bonCommandeArticlesService.partialUpdate(bonCommandeArticlesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonCommandeArticlesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bon-commande-articles} : get all the bonCommandeArticles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonCommandeArticles in body.
     */
    @GetMapping("/bon-commande-articles")
    public List<BonCommandeArticlesDTO> getAllBonCommandeArticles() {
        log.debug("REST request to get all BonCommandeArticles");
        return bonCommandeArticlesService.findAll();
    }

    /**
     * {@code GET  /bon-commande-articles/:id} : get the "id" bonCommandeArticles.
     *
     * @param id the id of the bonCommandeArticlesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonCommandeArticlesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bon-commande-articles/{id}")
    public ResponseEntity<BonCommandeArticlesDTO> getBonCommandeArticles(@PathVariable Long id) {
        log.debug("REST request to get BonCommandeArticles : {}", id);
        Optional<BonCommandeArticlesDTO> bonCommandeArticlesDTO = bonCommandeArticlesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonCommandeArticlesDTO);
    }

    /**
     * {@code DELETE  /bon-commande-articles/:id} : delete the "id" bonCommandeArticles.
     *
     * @param id the id of the bonCommandeArticlesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bon-commande-articles/{id}")
    public ResponseEntity<Void> deleteBonCommandeArticles(@PathVariable Long id) {
        log.debug("REST request to delete BonCommandeArticles : {}", id);
        bonCommandeArticlesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /bon-commande-articles/by-bon-commande/:bonCommandeId} : récupère les articles
     * affectés à un bon de commande.
     *
     * @param bonCommandeId l'id du bon de commande.
     * @return la liste des affectations (200).
     */
    @GetMapping("/bon-commande-articles/by-bon-commande/{bonCommandeId}")
    public List<BonCommandeArticlesDTO> getBonCommandeArticlesByBonCommande(@PathVariable Long bonCommandeId) {
        log.debug("REST request to get BonCommandeArticles by BonCommande : {}", bonCommandeId);
        return bonCommandeArticlesService.findByBonCommandeId(bonCommandeId);
    }

    /**
     * {@code PUT  /bon-commande-articles/bon-commande/:bonCommandeId} : remplace la liste des articles
     * affectés à un bon de commande.
     *
     * @param bonCommandeId l'id du bon de commande.
     * @param bonCommandeArticlesDTOs la nouvelle liste d'affectations.
     * @return la liste persistée (200).
     */
    @PutMapping("/bon-commande-articles/bon-commande/{bonCommandeId}")
    public ResponseEntity<List<BonCommandeArticlesDTO>> replaceBonCommandeArticles(
        @PathVariable Long bonCommandeId,
        @RequestBody List<BonCommandeArticlesDTO> bonCommandeArticlesDTOs
    ) {
        log.debug("REST request to replace BonCommandeArticles for BonCommande : {}, {}", bonCommandeId, bonCommandeArticlesDTOs);
        List<BonCommandeArticlesDTO> result = bonCommandeArticlesService.replaceForBonCommande(bonCommandeId, bonCommandeArticlesDTOs);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonCommandeId.toString()))
            .body(result);
    }
}

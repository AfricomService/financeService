package com.gpm.finance.web.rest;

import com.gpm.finance.repository.BonCommandeAutreResponsableRepository;
import com.gpm.finance.service.BonCommandeAutreResponsableService;
import com.gpm.finance.service.dto.BonCommandeAutreResponsableDTO;
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
 * REST controller for managing {@link com.gpm.finance.domain.BonCommandeAutreResponsable}.
 */
@RestController
@RequestMapping("/api")
public class BonCommandeAutreResponsableResource {

    private final Logger log = LoggerFactory.getLogger(BonCommandeAutreResponsableResource.class);

    private static final String ENTITY_NAME = "financeServiceBonCommandeAutreResponsable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonCommandeAutreResponsableService bonCommandeAutreResponsableService;

    private final BonCommandeAutreResponsableRepository bonCommandeAutreResponsableRepository;

    public BonCommandeAutreResponsableResource(
        BonCommandeAutreResponsableService bonCommandeAutreResponsableService,
        BonCommandeAutreResponsableRepository bonCommandeAutreResponsableRepository
    ) {
        this.bonCommandeAutreResponsableService = bonCommandeAutreResponsableService;
        this.bonCommandeAutreResponsableRepository = bonCommandeAutreResponsableRepository;
    }

    /**
     * {@code POST  /bon-commande-autre-responsables} : Create a new bonCommandeAutreResponsable.
     *
     * @param bonCommandeAutreResponsableDTO the bonCommandeAutreResponsableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonCommandeAutreResponsableDTO, or with status {@code 400 (Bad Request)} if the bonCommandeAutreResponsable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bon-commande-autre-responsables")
    public ResponseEntity<BonCommandeAutreResponsableDTO> createBonCommandeAutreResponsable(
        @RequestBody BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO
    ) throws URISyntaxException {
        log.debug("REST request to save BonCommandeAutreResponsable : {}", bonCommandeAutreResponsableDTO);
        if (bonCommandeAutreResponsableDTO.getId() != null) {
            throw new BadRequestAlertException("A new bonCommandeAutreResponsable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BonCommandeAutreResponsableDTO result = bonCommandeAutreResponsableService.save(bonCommandeAutreResponsableDTO);
        return ResponseEntity
            .created(new URI("/api/bon-commande-autre-responsables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bon-commande-autre-responsables/:id} : Updates an existing bonCommandeAutreResponsable.
     *
     * @param id the id of the bonCommandeAutreResponsableDTO to save.
     * @param bonCommandeAutreResponsableDTO the bonCommandeAutreResponsableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommandeAutreResponsableDTO,
     * or with status {@code 400 (Bad Request)} if the bonCommandeAutreResponsableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonCommandeAutreResponsableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bon-commande-autre-responsables/{id}")
    public ResponseEntity<BonCommandeAutreResponsableDTO> updateBonCommandeAutreResponsable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BonCommandeAutreResponsable : {}, {}", id, bonCommandeAutreResponsableDTO);
        if (bonCommandeAutreResponsableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonCommandeAutreResponsableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonCommandeAutreResponsableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BonCommandeAutreResponsableDTO result = bonCommandeAutreResponsableService.save(bonCommandeAutreResponsableDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonCommandeAutreResponsableDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /bon-commande-autre-responsables/:id} : Partial updates given fields of an existing bonCommandeAutreResponsable, field will ignore if it is null
     *
     * @param id the id of the bonCommandeAutreResponsableDTO to save.
     * @param bonCommandeAutreResponsableDTO the bonCommandeAutreResponsableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommandeAutreResponsableDTO,
     * or with status {@code 400 (Bad Request)} if the bonCommandeAutreResponsableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bonCommandeAutreResponsableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bonCommandeAutreResponsableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bon-commande-autre-responsables/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BonCommandeAutreResponsableDTO> partialUpdateBonCommandeAutreResponsable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BonCommandeAutreResponsable partially : {}, {}", id, bonCommandeAutreResponsableDTO);
        if (bonCommandeAutreResponsableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonCommandeAutreResponsableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonCommandeAutreResponsableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BonCommandeAutreResponsableDTO> result = bonCommandeAutreResponsableService.partialUpdate(bonCommandeAutreResponsableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonCommandeAutreResponsableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bon-commande-autre-responsables} : get all the bonCommandeAutreResponsables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonCommandeAutreResponsables in body.
     */
    @GetMapping("/bon-commande-autre-responsables")
    public List<BonCommandeAutreResponsableDTO> getAllBonCommandeAutreResponsables() {
        log.debug("REST request to get all BonCommandeAutreResponsables");
        return bonCommandeAutreResponsableService.findAll();
    }

    /**
     * {@code GET  /bon-commande-autre-responsables/:id} : get the "id" bonCommandeAutreResponsable.
     *
     * @param id the id of the bonCommandeAutreResponsableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonCommandeAutreResponsableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bon-commande-autre-responsables/{id}")
    public ResponseEntity<BonCommandeAutreResponsableDTO> getBonCommandeAutreResponsable(@PathVariable Long id) {
        log.debug("REST request to get BonCommandeAutreResponsable : {}", id);
        Optional<BonCommandeAutreResponsableDTO> bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonCommandeAutreResponsableDTO);
    }

    /**
     * {@code DELETE  /bon-commande-autre-responsables/:id} : delete the "id" bonCommandeAutreResponsable.
     *
     * @param id the id of the bonCommandeAutreResponsableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bon-commande-autre-responsables/{id}")
    public ResponseEntity<Void> deleteBonCommandeAutreResponsable(@PathVariable Long id) {
        log.debug("REST request to delete BonCommandeAutreResponsable : {}", id);
        bonCommandeAutreResponsableService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /bon-commande-autre-responsables/by-bon-commande/:bonCommandeId} :
     * récupère les autres responsables d'un bon de commande.
     */
    @GetMapping("/bon-commande-autre-responsables/by-bon-commande/{bonCommandeId}")
    public List<BonCommandeAutreResponsableDTO> getByBonCommande(@PathVariable Long bonCommandeId) {
        log.debug("REST request to get BonCommandeAutreResponsables by bonCommandeId : {}", bonCommandeId);
        return bonCommandeAutreResponsableService.findByBonCommandeId(bonCommandeId);
    }

    /**
     * {@code PUT  /bon-commande-autre-responsables/by-bon-commande/:bonCommandeId} :
     * remplace la liste des autres responsables d'un bon de commande.
     */
    @PutMapping("/bon-commande-autre-responsables/by-bon-commande/{bonCommandeId}")
    public List<BonCommandeAutreResponsableDTO> replaceForBonCommande(
        @PathVariable Long bonCommandeId,
        @RequestBody List<Long> contactSocieteIds
    ) {
        log.debug("REST request to replace autres responsables for bonCommandeId : {}", bonCommandeId);
        return bonCommandeAutreResponsableService.replaceForBonCommande(bonCommandeId, contactSocieteIds);
    }
}

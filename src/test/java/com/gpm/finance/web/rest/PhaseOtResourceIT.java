package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.PhaseOt;
import com.gpm.finance.repository.PhaseOtRepository;
import com.gpm.finance.service.dto.PhaseOtDTO;
import com.gpm.finance.service.mapper.PhaseOtMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PhaseOtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhaseOtResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;

    private static final Boolean DEFAULT_BLOQUANTE = false;
    private static final Boolean UPDATED_BLOQUANTE = true;

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DLC = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DLC = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_PHASE_PARENT_ID = 1L;
    private static final Long UPDATED_PHASE_PARENT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/phase-ots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhaseOtRepository phaseOtRepository;

    @Autowired
    private PhaseOtMapper phaseOtMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhaseOtMockMvc;

    private PhaseOt phaseOt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhaseOt createEntity(EntityManager em) {
        PhaseOt phaseOt = new PhaseOt()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .duree(DEFAULT_DUREE)
            .bloquante(DEFAULT_BLOQUANTE)
            .statut(DEFAULT_STATUT)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dl(DEFAULT_DL)
            .dlc(DEFAULT_DLC)
            .phaseParentId(DEFAULT_PHASE_PARENT_ID);
        return phaseOt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhaseOt createUpdatedEntity(EntityManager em) {
        PhaseOt phaseOt = new PhaseOt()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .duree(UPDATED_DUREE)
            .bloquante(UPDATED_BLOQUANTE)
            .statut(UPDATED_STATUT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dl(UPDATED_DL)
            .dlc(UPDATED_DLC)
            .phaseParentId(UPDATED_PHASE_PARENT_ID);
        return phaseOt;
    }

    @BeforeEach
    public void initTest() {
        phaseOt = createEntity(em);
    }

    @Test
    @Transactional
    void createPhaseOt() throws Exception {
        int databaseSizeBeforeCreate = phaseOtRepository.findAll().size();
        // Create the PhaseOt
        PhaseOtDTO phaseOtDTO = phaseOtMapper.toDto(phaseOt);
        restPhaseOtMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phaseOtDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeCreate + 1);
        PhaseOt testPhaseOt = phaseOtList.get(phaseOtList.size() - 1);
        assertThat(testPhaseOt.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPhaseOt.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhaseOt.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testPhaseOt.getBloquante()).isEqualTo(DEFAULT_BLOQUANTE);
        assertThat(testPhaseOt.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testPhaseOt.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPhaseOt.getDl()).isEqualTo(DEFAULT_DL);
        assertThat(testPhaseOt.getDlc()).isEqualTo(DEFAULT_DLC);
        assertThat(testPhaseOt.getPhaseParentId()).isEqualTo(DEFAULT_PHASE_PARENT_ID);
    }

    @Test
    @Transactional
    void createPhaseOtWithExistingId() throws Exception {
        // Create the PhaseOt with an existing ID
        phaseOt.setId(1L);
        PhaseOtDTO phaseOtDTO = phaseOtMapper.toDto(phaseOt);

        int databaseSizeBeforeCreate = phaseOtRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhaseOtMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phaseOtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPhaseOts() throws Exception {
        // Initialize the database
        phaseOtRepository.saveAndFlush(phaseOt);

        // Get all the phaseOtList
        restPhaseOtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phaseOt.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].bloquante").value(hasItem(DEFAULT_BLOQUANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].dl").value(hasItem(sameInstant(DEFAULT_DL))))
            .andExpect(jsonPath("$.[*].dlc").value(hasItem(sameInstant(DEFAULT_DLC))))
            .andExpect(jsonPath("$.[*].phaseParentId").value(hasItem(DEFAULT_PHASE_PARENT_ID.intValue())));
    }

    @Test
    @Transactional
    void getPhaseOt() throws Exception {
        // Initialize the database
        phaseOtRepository.saveAndFlush(phaseOt);

        // Get the phaseOt
        restPhaseOtMockMvc
            .perform(get(ENTITY_API_URL_ID, phaseOt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phaseOt.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.bloquante").value(DEFAULT_BLOQUANTE.booleanValue()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.dateDebut").value(sameInstant(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.dl").value(sameInstant(DEFAULT_DL)))
            .andExpect(jsonPath("$.dlc").value(sameInstant(DEFAULT_DLC)))
            .andExpect(jsonPath("$.phaseParentId").value(DEFAULT_PHASE_PARENT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPhaseOt() throws Exception {
        // Get the phaseOt
        restPhaseOtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPhaseOt() throws Exception {
        // Initialize the database
        phaseOtRepository.saveAndFlush(phaseOt);

        int databaseSizeBeforeUpdate = phaseOtRepository.findAll().size();

        // Update the phaseOt
        PhaseOt updatedPhaseOt = phaseOtRepository.findById(phaseOt.getId()).get();
        // Disconnect from session so that the updates on updatedPhaseOt are not directly saved in db
        em.detach(updatedPhaseOt);
        updatedPhaseOt
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .duree(UPDATED_DUREE)
            .bloquante(UPDATED_BLOQUANTE)
            .statut(UPDATED_STATUT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dl(UPDATED_DL)
            .dlc(UPDATED_DLC)
            .phaseParentId(UPDATED_PHASE_PARENT_ID);
        PhaseOtDTO phaseOtDTO = phaseOtMapper.toDto(updatedPhaseOt);

        restPhaseOtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phaseOtDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phaseOtDTO))
            )
            .andExpect(status().isOk());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeUpdate);
        PhaseOt testPhaseOt = phaseOtList.get(phaseOtList.size() - 1);
        assertThat(testPhaseOt.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPhaseOt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhaseOt.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testPhaseOt.getBloquante()).isEqualTo(UPDATED_BLOQUANTE);
        assertThat(testPhaseOt.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testPhaseOt.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPhaseOt.getDl()).isEqualTo(UPDATED_DL);
        assertThat(testPhaseOt.getDlc()).isEqualTo(UPDATED_DLC);
        assertThat(testPhaseOt.getPhaseParentId()).isEqualTo(UPDATED_PHASE_PARENT_ID);
    }

    @Test
    @Transactional
    void putNonExistingPhaseOt() throws Exception {
        int databaseSizeBeforeUpdate = phaseOtRepository.findAll().size();
        phaseOt.setId(count.incrementAndGet());

        // Create the PhaseOt
        PhaseOtDTO phaseOtDTO = phaseOtMapper.toDto(phaseOt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhaseOtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phaseOtDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phaseOtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhaseOt() throws Exception {
        int databaseSizeBeforeUpdate = phaseOtRepository.findAll().size();
        phaseOt.setId(count.incrementAndGet());

        // Create the PhaseOt
        PhaseOtDTO phaseOtDTO = phaseOtMapper.toDto(phaseOt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhaseOtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phaseOtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhaseOt() throws Exception {
        int databaseSizeBeforeUpdate = phaseOtRepository.findAll().size();
        phaseOt.setId(count.incrementAndGet());

        // Create the PhaseOt
        PhaseOtDTO phaseOtDTO = phaseOtMapper.toDto(phaseOt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhaseOtMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phaseOtDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhaseOtWithPatch() throws Exception {
        // Initialize the database
        phaseOtRepository.saveAndFlush(phaseOt);

        int databaseSizeBeforeUpdate = phaseOtRepository.findAll().size();

        // Update the phaseOt using partial update
        PhaseOt partialUpdatedPhaseOt = new PhaseOt();
        partialUpdatedPhaseOt.setId(phaseOt.getId());

        partialUpdatedPhaseOt.nom(UPDATED_NOM).bloquante(UPDATED_BLOQUANTE).dateDebut(UPDATED_DATE_DEBUT).dl(UPDATED_DL);

        restPhaseOtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhaseOt.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhaseOt))
            )
            .andExpect(status().isOk());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeUpdate);
        PhaseOt testPhaseOt = phaseOtList.get(phaseOtList.size() - 1);
        assertThat(testPhaseOt.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPhaseOt.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhaseOt.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testPhaseOt.getBloquante()).isEqualTo(UPDATED_BLOQUANTE);
        assertThat(testPhaseOt.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testPhaseOt.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPhaseOt.getDl()).isEqualTo(UPDATED_DL);
        assertThat(testPhaseOt.getDlc()).isEqualTo(DEFAULT_DLC);
        assertThat(testPhaseOt.getPhaseParentId()).isEqualTo(DEFAULT_PHASE_PARENT_ID);
    }

    @Test
    @Transactional
    void fullUpdatePhaseOtWithPatch() throws Exception {
        // Initialize the database
        phaseOtRepository.saveAndFlush(phaseOt);

        int databaseSizeBeforeUpdate = phaseOtRepository.findAll().size();

        // Update the phaseOt using partial update
        PhaseOt partialUpdatedPhaseOt = new PhaseOt();
        partialUpdatedPhaseOt.setId(phaseOt.getId());

        partialUpdatedPhaseOt
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .duree(UPDATED_DUREE)
            .bloquante(UPDATED_BLOQUANTE)
            .statut(UPDATED_STATUT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dl(UPDATED_DL)
            .dlc(UPDATED_DLC)
            .phaseParentId(UPDATED_PHASE_PARENT_ID);

        restPhaseOtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhaseOt.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhaseOt))
            )
            .andExpect(status().isOk());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeUpdate);
        PhaseOt testPhaseOt = phaseOtList.get(phaseOtList.size() - 1);
        assertThat(testPhaseOt.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPhaseOt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhaseOt.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testPhaseOt.getBloquante()).isEqualTo(UPDATED_BLOQUANTE);
        assertThat(testPhaseOt.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testPhaseOt.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPhaseOt.getDl()).isEqualTo(UPDATED_DL);
        assertThat(testPhaseOt.getDlc()).isEqualTo(UPDATED_DLC);
        assertThat(testPhaseOt.getPhaseParentId()).isEqualTo(UPDATED_PHASE_PARENT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingPhaseOt() throws Exception {
        int databaseSizeBeforeUpdate = phaseOtRepository.findAll().size();
        phaseOt.setId(count.incrementAndGet());

        // Create the PhaseOt
        PhaseOtDTO phaseOtDTO = phaseOtMapper.toDto(phaseOt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhaseOtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phaseOtDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phaseOtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhaseOt() throws Exception {
        int databaseSizeBeforeUpdate = phaseOtRepository.findAll().size();
        phaseOt.setId(count.incrementAndGet());

        // Create the PhaseOt
        PhaseOtDTO phaseOtDTO = phaseOtMapper.toDto(phaseOt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhaseOtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phaseOtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhaseOt() throws Exception {
        int databaseSizeBeforeUpdate = phaseOtRepository.findAll().size();
        phaseOt.setId(count.incrementAndGet());

        // Create the PhaseOt
        PhaseOtDTO phaseOtDTO = phaseOtMapper.toDto(phaseOt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhaseOtMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phaseOtDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhaseOt in the database
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhaseOt() throws Exception {
        // Initialize the database
        phaseOtRepository.saveAndFlush(phaseOt);

        int databaseSizeBeforeDelete = phaseOtRepository.findAll().size();

        // Delete the phaseOt
        restPhaseOtMockMvc
            .perform(delete(ENTITY_API_URL_ID, phaseOt.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhaseOt> phaseOtList = phaseOtRepository.findAll();
        assertThat(phaseOtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

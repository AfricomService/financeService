package com.gpm.finance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.LiaisonModelPhaseOT;
import com.gpm.finance.repository.LiaisonModelPhaseOTRepository;
import com.gpm.finance.service.dto.LiaisonModelPhaseOTDTO;
import com.gpm.finance.service.mapper.LiaisonModelPhaseOTMapper;
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
 * Integration tests for the {@link LiaisonModelPhaseOTResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LiaisonModelPhaseOTResourceIT {

    private static final Long DEFAULT_MODEL_PHASE_OT_ID = 1L;
    private static final Long UPDATED_MODEL_PHASE_OT_ID = 2L;

    private static final Long DEFAULT_PHASE_ID = 1L;
    private static final Long UPDATED_PHASE_ID = 2L;

    private static final Integer DEFAULT_CLASSEMENT_PHASE = 1;
    private static final Integer UPDATED_CLASSEMENT_PHASE = 2;

    private static final String ENTITY_API_URL = "/api/liaison-model-phase-ots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LiaisonModelPhaseOTRepository liaisonModelPhaseOTRepository;

    @Autowired
    private LiaisonModelPhaseOTMapper liaisonModelPhaseOTMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLiaisonModelPhaseOTMockMvc;

    private LiaisonModelPhaseOT liaisonModelPhaseOT;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LiaisonModelPhaseOT createEntity(EntityManager em) {
        LiaisonModelPhaseOT liaisonModelPhaseOT = new LiaisonModelPhaseOT()
            .modelPhaseOtId(DEFAULT_MODEL_PHASE_OT_ID)
            .phaseId(DEFAULT_PHASE_ID)
            .classementPhase(DEFAULT_CLASSEMENT_PHASE);
        return liaisonModelPhaseOT;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LiaisonModelPhaseOT createUpdatedEntity(EntityManager em) {
        LiaisonModelPhaseOT liaisonModelPhaseOT = new LiaisonModelPhaseOT()
            .modelPhaseOtId(UPDATED_MODEL_PHASE_OT_ID)
            .phaseId(UPDATED_PHASE_ID)
            .classementPhase(UPDATED_CLASSEMENT_PHASE);
        return liaisonModelPhaseOT;
    }

    @BeforeEach
    public void initTest() {
        liaisonModelPhaseOT = createEntity(em);
    }

    @Test
    @Transactional
    void createLiaisonModelPhaseOT() throws Exception {
        int databaseSizeBeforeCreate = liaisonModelPhaseOTRepository.findAll().size();
        // Create the LiaisonModelPhaseOT
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);
        restLiaisonModelPhaseOTMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liaisonModelPhaseOTDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeCreate + 1);
        LiaisonModelPhaseOT testLiaisonModelPhaseOT = liaisonModelPhaseOTList.get(liaisonModelPhaseOTList.size() - 1);
        assertThat(testLiaisonModelPhaseOT.getModelPhaseOtId()).isEqualTo(DEFAULT_MODEL_PHASE_OT_ID);
        assertThat(testLiaisonModelPhaseOT.getPhaseId()).isEqualTo(DEFAULT_PHASE_ID);
        assertThat(testLiaisonModelPhaseOT.getClassementPhase()).isEqualTo(DEFAULT_CLASSEMENT_PHASE);
    }

    @Test
    @Transactional
    void createLiaisonModelPhaseOTWithExistingId() throws Exception {
        // Create the LiaisonModelPhaseOT with an existing ID
        liaisonModelPhaseOT.setId(1L);
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);

        int databaseSizeBeforeCreate = liaisonModelPhaseOTRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLiaisonModelPhaseOTMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liaisonModelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLiaisonModelPhaseOTS() throws Exception {
        // Initialize the database
        liaisonModelPhaseOTRepository.saveAndFlush(liaisonModelPhaseOT);

        // Get all the liaisonModelPhaseOTList
        restLiaisonModelPhaseOTMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(liaisonModelPhaseOT.getId().intValue())))
            .andExpect(jsonPath("$.[*].modelPhaseOtId").value(hasItem(DEFAULT_MODEL_PHASE_OT_ID.intValue())))
            .andExpect(jsonPath("$.[*].phaseId").value(hasItem(DEFAULT_PHASE_ID.intValue())))
            .andExpect(jsonPath("$.[*].classementPhase").value(hasItem(DEFAULT_CLASSEMENT_PHASE)));
    }

    @Test
    @Transactional
    void getLiaisonModelPhaseOT() throws Exception {
        // Initialize the database
        liaisonModelPhaseOTRepository.saveAndFlush(liaisonModelPhaseOT);

        // Get the liaisonModelPhaseOT
        restLiaisonModelPhaseOTMockMvc
            .perform(get(ENTITY_API_URL_ID, liaisonModelPhaseOT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(liaisonModelPhaseOT.getId().intValue()))
            .andExpect(jsonPath("$.modelPhaseOtId").value(DEFAULT_MODEL_PHASE_OT_ID.intValue()))
            .andExpect(jsonPath("$.phaseId").value(DEFAULT_PHASE_ID.intValue()))
            .andExpect(jsonPath("$.classementPhase").value(DEFAULT_CLASSEMENT_PHASE));
    }

    @Test
    @Transactional
    void getNonExistingLiaisonModelPhaseOT() throws Exception {
        // Get the liaisonModelPhaseOT
        restLiaisonModelPhaseOTMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLiaisonModelPhaseOT() throws Exception {
        // Initialize the database
        liaisonModelPhaseOTRepository.saveAndFlush(liaisonModelPhaseOT);

        int databaseSizeBeforeUpdate = liaisonModelPhaseOTRepository.findAll().size();

        // Update the liaisonModelPhaseOT
        LiaisonModelPhaseOT updatedLiaisonModelPhaseOT = liaisonModelPhaseOTRepository.findById(liaisonModelPhaseOT.getId()).get();
        // Disconnect from session so that the updates on updatedLiaisonModelPhaseOT are not directly saved in db
        em.detach(updatedLiaisonModelPhaseOT);
        updatedLiaisonModelPhaseOT
            .modelPhaseOtId(UPDATED_MODEL_PHASE_OT_ID)
            .phaseId(UPDATED_PHASE_ID)
            .classementPhase(UPDATED_CLASSEMENT_PHASE);
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = liaisonModelPhaseOTMapper.toDto(updatedLiaisonModelPhaseOT);

        restLiaisonModelPhaseOTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, liaisonModelPhaseOTDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liaisonModelPhaseOTDTO))
            )
            .andExpect(status().isOk());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
        LiaisonModelPhaseOT testLiaisonModelPhaseOT = liaisonModelPhaseOTList.get(liaisonModelPhaseOTList.size() - 1);
        assertThat(testLiaisonModelPhaseOT.getModelPhaseOtId()).isEqualTo(UPDATED_MODEL_PHASE_OT_ID);
        assertThat(testLiaisonModelPhaseOT.getPhaseId()).isEqualTo(UPDATED_PHASE_ID);
        assertThat(testLiaisonModelPhaseOT.getClassementPhase()).isEqualTo(UPDATED_CLASSEMENT_PHASE);
    }

    @Test
    @Transactional
    void putNonExistingLiaisonModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = liaisonModelPhaseOTRepository.findAll().size();
        liaisonModelPhaseOT.setId(count.incrementAndGet());

        // Create the LiaisonModelPhaseOT
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLiaisonModelPhaseOTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, liaisonModelPhaseOTDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liaisonModelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLiaisonModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = liaisonModelPhaseOTRepository.findAll().size();
        liaisonModelPhaseOT.setId(count.incrementAndGet());

        // Create the LiaisonModelPhaseOT
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiaisonModelPhaseOTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liaisonModelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLiaisonModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = liaisonModelPhaseOTRepository.findAll().size();
        liaisonModelPhaseOT.setId(count.incrementAndGet());

        // Create the LiaisonModelPhaseOT
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiaisonModelPhaseOTMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liaisonModelPhaseOTDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLiaisonModelPhaseOTWithPatch() throws Exception {
        // Initialize the database
        liaisonModelPhaseOTRepository.saveAndFlush(liaisonModelPhaseOT);

        int databaseSizeBeforeUpdate = liaisonModelPhaseOTRepository.findAll().size();

        // Update the liaisonModelPhaseOT using partial update
        LiaisonModelPhaseOT partialUpdatedLiaisonModelPhaseOT = new LiaisonModelPhaseOT();
        partialUpdatedLiaisonModelPhaseOT.setId(liaisonModelPhaseOT.getId());

        partialUpdatedLiaisonModelPhaseOT.modelPhaseOtId(UPDATED_MODEL_PHASE_OT_ID).phaseId(UPDATED_PHASE_ID);

        restLiaisonModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLiaisonModelPhaseOT.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLiaisonModelPhaseOT))
            )
            .andExpect(status().isOk());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
        LiaisonModelPhaseOT testLiaisonModelPhaseOT = liaisonModelPhaseOTList.get(liaisonModelPhaseOTList.size() - 1);
        assertThat(testLiaisonModelPhaseOT.getModelPhaseOtId()).isEqualTo(UPDATED_MODEL_PHASE_OT_ID);
        assertThat(testLiaisonModelPhaseOT.getPhaseId()).isEqualTo(UPDATED_PHASE_ID);
        assertThat(testLiaisonModelPhaseOT.getClassementPhase()).isEqualTo(DEFAULT_CLASSEMENT_PHASE);
    }

    @Test
    @Transactional
    void fullUpdateLiaisonModelPhaseOTWithPatch() throws Exception {
        // Initialize the database
        liaisonModelPhaseOTRepository.saveAndFlush(liaisonModelPhaseOT);

        int databaseSizeBeforeUpdate = liaisonModelPhaseOTRepository.findAll().size();

        // Update the liaisonModelPhaseOT using partial update
        LiaisonModelPhaseOT partialUpdatedLiaisonModelPhaseOT = new LiaisonModelPhaseOT();
        partialUpdatedLiaisonModelPhaseOT.setId(liaisonModelPhaseOT.getId());

        partialUpdatedLiaisonModelPhaseOT
            .modelPhaseOtId(UPDATED_MODEL_PHASE_OT_ID)
            .phaseId(UPDATED_PHASE_ID)
            .classementPhase(UPDATED_CLASSEMENT_PHASE);

        restLiaisonModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLiaisonModelPhaseOT.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLiaisonModelPhaseOT))
            )
            .andExpect(status().isOk());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
        LiaisonModelPhaseOT testLiaisonModelPhaseOT = liaisonModelPhaseOTList.get(liaisonModelPhaseOTList.size() - 1);
        assertThat(testLiaisonModelPhaseOT.getModelPhaseOtId()).isEqualTo(UPDATED_MODEL_PHASE_OT_ID);
        assertThat(testLiaisonModelPhaseOT.getPhaseId()).isEqualTo(UPDATED_PHASE_ID);
        assertThat(testLiaisonModelPhaseOT.getClassementPhase()).isEqualTo(UPDATED_CLASSEMENT_PHASE);
    }

    @Test
    @Transactional
    void patchNonExistingLiaisonModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = liaisonModelPhaseOTRepository.findAll().size();
        liaisonModelPhaseOT.setId(count.incrementAndGet());

        // Create the LiaisonModelPhaseOT
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLiaisonModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, liaisonModelPhaseOTDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(liaisonModelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLiaisonModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = liaisonModelPhaseOTRepository.findAll().size();
        liaisonModelPhaseOT.setId(count.incrementAndGet());

        // Create the LiaisonModelPhaseOT
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiaisonModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(liaisonModelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLiaisonModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = liaisonModelPhaseOTRepository.findAll().size();
        liaisonModelPhaseOT.setId(count.incrementAndGet());

        // Create the LiaisonModelPhaseOT
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = liaisonModelPhaseOTMapper.toDto(liaisonModelPhaseOT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiaisonModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(liaisonModelPhaseOTDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LiaisonModelPhaseOT in the database
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLiaisonModelPhaseOT() throws Exception {
        // Initialize the database
        liaisonModelPhaseOTRepository.saveAndFlush(liaisonModelPhaseOT);

        int databaseSizeBeforeDelete = liaisonModelPhaseOTRepository.findAll().size();

        // Delete the liaisonModelPhaseOT
        restLiaisonModelPhaseOTMockMvc
            .perform(delete(ENTITY_API_URL_ID, liaisonModelPhaseOT.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LiaisonModelPhaseOT> liaisonModelPhaseOTList = liaisonModelPhaseOTRepository.findAll();
        assertThat(liaisonModelPhaseOTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

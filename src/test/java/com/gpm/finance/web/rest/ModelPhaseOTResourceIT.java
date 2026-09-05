package com.gpm.finance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.ModelPhaseOT;
import com.gpm.finance.repository.ModelPhaseOTRepository;
import com.gpm.finance.service.dto.ModelPhaseOTDTO;
import com.gpm.finance.service.mapper.ModelPhaseOTMapper;
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
 * Integration tests for the {@link ModelPhaseOTResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModelPhaseOTResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/model-phase-ots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ModelPhaseOTRepository modelPhaseOTRepository;

    @Autowired
    private ModelPhaseOTMapper modelPhaseOTMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModelPhaseOTMockMvc;

    private ModelPhaseOT modelPhaseOT;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModelPhaseOT createEntity(EntityManager em) {
        ModelPhaseOT modelPhaseOT = new ModelPhaseOT().nom(DEFAULT_NOM).description(DEFAULT_DESCRIPTION);
        return modelPhaseOT;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModelPhaseOT createUpdatedEntity(EntityManager em) {
        ModelPhaseOT modelPhaseOT = new ModelPhaseOT().nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        return modelPhaseOT;
    }

    @BeforeEach
    public void initTest() {
        modelPhaseOT = createEntity(em);
    }

    @Test
    @Transactional
    void createModelPhaseOT() throws Exception {
        int databaseSizeBeforeCreate = modelPhaseOTRepository.findAll().size();
        // Create the ModelPhaseOT
        ModelPhaseOTDTO modelPhaseOTDTO = modelPhaseOTMapper.toDto(modelPhaseOT);
        restModelPhaseOTMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelPhaseOTDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeCreate + 1);
        ModelPhaseOT testModelPhaseOT = modelPhaseOTList.get(modelPhaseOTList.size() - 1);
        assertThat(testModelPhaseOT.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testModelPhaseOT.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createModelPhaseOTWithExistingId() throws Exception {
        // Create the ModelPhaseOT with an existing ID
        modelPhaseOT.setId(1L);
        ModelPhaseOTDTO modelPhaseOTDTO = modelPhaseOTMapper.toDto(modelPhaseOT);

        int databaseSizeBeforeCreate = modelPhaseOTRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelPhaseOTMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllModelPhaseOTS() throws Exception {
        // Initialize the database
        modelPhaseOTRepository.saveAndFlush(modelPhaseOT);

        // Get all the modelPhaseOTList
        restModelPhaseOTMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelPhaseOT.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getModelPhaseOT() throws Exception {
        // Initialize the database
        modelPhaseOTRepository.saveAndFlush(modelPhaseOT);

        // Get the modelPhaseOT
        restModelPhaseOTMockMvc
            .perform(get(ENTITY_API_URL_ID, modelPhaseOT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modelPhaseOT.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingModelPhaseOT() throws Exception {
        // Get the modelPhaseOT
        restModelPhaseOTMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingModelPhaseOT() throws Exception {
        // Initialize the database
        modelPhaseOTRepository.saveAndFlush(modelPhaseOT);

        int databaseSizeBeforeUpdate = modelPhaseOTRepository.findAll().size();

        // Update the modelPhaseOT
        ModelPhaseOT updatedModelPhaseOT = modelPhaseOTRepository.findById(modelPhaseOT.getId()).get();
        // Disconnect from session so that the updates on updatedModelPhaseOT are not directly saved in db
        em.detach(updatedModelPhaseOT);
        updatedModelPhaseOT.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        ModelPhaseOTDTO modelPhaseOTDTO = modelPhaseOTMapper.toDto(updatedModelPhaseOT);

        restModelPhaseOTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modelPhaseOTDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelPhaseOTDTO))
            )
            .andExpect(status().isOk());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
        ModelPhaseOT testModelPhaseOT = modelPhaseOTList.get(modelPhaseOTList.size() - 1);
        assertThat(testModelPhaseOT.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testModelPhaseOT.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = modelPhaseOTRepository.findAll().size();
        modelPhaseOT.setId(count.incrementAndGet());

        // Create the ModelPhaseOT
        ModelPhaseOTDTO modelPhaseOTDTO = modelPhaseOTMapper.toDto(modelPhaseOT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelPhaseOTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modelPhaseOTDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = modelPhaseOTRepository.findAll().size();
        modelPhaseOT.setId(count.incrementAndGet());

        // Create the ModelPhaseOT
        ModelPhaseOTDTO modelPhaseOTDTO = modelPhaseOTMapper.toDto(modelPhaseOT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelPhaseOTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = modelPhaseOTRepository.findAll().size();
        modelPhaseOT.setId(count.incrementAndGet());

        // Create the ModelPhaseOT
        ModelPhaseOTDTO modelPhaseOTDTO = modelPhaseOTMapper.toDto(modelPhaseOT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelPhaseOTMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelPhaseOTDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModelPhaseOTWithPatch() throws Exception {
        // Initialize the database
        modelPhaseOTRepository.saveAndFlush(modelPhaseOT);

        int databaseSizeBeforeUpdate = modelPhaseOTRepository.findAll().size();

        // Update the modelPhaseOT using partial update
        ModelPhaseOT partialUpdatedModelPhaseOT = new ModelPhaseOT();
        partialUpdatedModelPhaseOT.setId(modelPhaseOT.getId());

        restModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModelPhaseOT.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModelPhaseOT))
            )
            .andExpect(status().isOk());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
        ModelPhaseOT testModelPhaseOT = modelPhaseOTList.get(modelPhaseOTList.size() - 1);
        assertThat(testModelPhaseOT.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testModelPhaseOT.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateModelPhaseOTWithPatch() throws Exception {
        // Initialize the database
        modelPhaseOTRepository.saveAndFlush(modelPhaseOT);

        int databaseSizeBeforeUpdate = modelPhaseOTRepository.findAll().size();

        // Update the modelPhaseOT using partial update
        ModelPhaseOT partialUpdatedModelPhaseOT = new ModelPhaseOT();
        partialUpdatedModelPhaseOT.setId(modelPhaseOT.getId());

        partialUpdatedModelPhaseOT.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);

        restModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModelPhaseOT.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModelPhaseOT))
            )
            .andExpect(status().isOk());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
        ModelPhaseOT testModelPhaseOT = modelPhaseOTList.get(modelPhaseOTList.size() - 1);
        assertThat(testModelPhaseOT.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testModelPhaseOT.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = modelPhaseOTRepository.findAll().size();
        modelPhaseOT.setId(count.incrementAndGet());

        // Create the ModelPhaseOT
        ModelPhaseOTDTO modelPhaseOTDTO = modelPhaseOTMapper.toDto(modelPhaseOT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modelPhaseOTDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = modelPhaseOTRepository.findAll().size();
        modelPhaseOT.setId(count.incrementAndGet());

        // Create the ModelPhaseOT
        ModelPhaseOTDTO modelPhaseOTDTO = modelPhaseOTMapper.toDto(modelPhaseOT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modelPhaseOTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModelPhaseOT() throws Exception {
        int databaseSizeBeforeUpdate = modelPhaseOTRepository.findAll().size();
        modelPhaseOT.setId(count.incrementAndGet());

        // Create the ModelPhaseOT
        ModelPhaseOTDTO modelPhaseOTDTO = modelPhaseOTMapper.toDto(modelPhaseOT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelPhaseOTMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modelPhaseOTDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModelPhaseOT in the database
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModelPhaseOT() throws Exception {
        // Initialize the database
        modelPhaseOTRepository.saveAndFlush(modelPhaseOT);

        int databaseSizeBeforeDelete = modelPhaseOTRepository.findAll().size();

        // Delete the modelPhaseOT
        restModelPhaseOTMockMvc
            .perform(delete(ENTITY_API_URL_ID, modelPhaseOT.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModelPhaseOT> modelPhaseOTList = modelPhaseOTRepository.findAll();
        assertThat(modelPhaseOTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

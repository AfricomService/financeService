package com.gpm.finance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.OtExterneAutreResponsable;
import com.gpm.finance.repository.OtExterneAutreResponsableRepository;
import com.gpm.finance.service.dto.OtExterneAutreResponsableDTO;
import com.gpm.finance.service.mapper.OtExterneAutreResponsableMapper;
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
 * Integration tests for the {@link OtExterneAutreResponsableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OtExterneAutreResponsableResourceIT {

    private static final Long DEFAULT_OT_EXTERNE_ID = 1L;
    private static final Long UPDATED_OT_EXTERNE_ID = 2L;

    private static final Long DEFAULT_CONTACT_SOCIETE_ID = 1L;
    private static final Long UPDATED_CONTACT_SOCIETE_ID = 2L;

    private static final String ENTITY_API_URL = "/api/ot-externe-autre-responsables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OtExterneAutreResponsableRepository otExterneAutreResponsableRepository;

    @Autowired
    private OtExterneAutreResponsableMapper otExterneAutreResponsableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtExterneAutreResponsableMockMvc;

    private OtExterneAutreResponsable otExterneAutreResponsable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtExterneAutreResponsable createEntity(EntityManager em) {
        OtExterneAutreResponsable otExterneAutreResponsable = new OtExterneAutreResponsable()
            .otExterneId(DEFAULT_OT_EXTERNE_ID)
            .contactSocieteId(DEFAULT_CONTACT_SOCIETE_ID);
        return otExterneAutreResponsable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtExterneAutreResponsable createUpdatedEntity(EntityManager em) {
        OtExterneAutreResponsable otExterneAutreResponsable = new OtExterneAutreResponsable()
            .otExterneId(UPDATED_OT_EXTERNE_ID)
            .contactSocieteId(UPDATED_CONTACT_SOCIETE_ID);
        return otExterneAutreResponsable;
    }

    @BeforeEach
    public void initTest() {
        otExterneAutreResponsable = createEntity(em);
    }

    @Test
    @Transactional
    void createOtExterneAutreResponsable() throws Exception {
        int databaseSizeBeforeCreate = otExterneAutreResponsableRepository.findAll().size();
        // Create the OtExterneAutreResponsable
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = otExterneAutreResponsableMapper.toDto(otExterneAutreResponsable);
        restOtExterneAutreResponsableMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneAutreResponsableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeCreate + 1);
        OtExterneAutreResponsable testOtExterneAutreResponsable = otExterneAutreResponsableList.get(
            otExterneAutreResponsableList.size() - 1
        );
        assertThat(testOtExterneAutreResponsable.getOtExterneId()).isEqualTo(DEFAULT_OT_EXTERNE_ID);
        assertThat(testOtExterneAutreResponsable.getContactSocieteId()).isEqualTo(DEFAULT_CONTACT_SOCIETE_ID);
    }

    @Test
    @Transactional
    void createOtExterneAutreResponsableWithExistingId() throws Exception {
        // Create the OtExterneAutreResponsable with an existing ID
        otExterneAutreResponsable.setId(1L);
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = otExterneAutreResponsableMapper.toDto(otExterneAutreResponsable);

        int databaseSizeBeforeCreate = otExterneAutreResponsableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtExterneAutreResponsableMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOtExterneAutreResponsables() throws Exception {
        // Initialize the database
        otExterneAutreResponsableRepository.saveAndFlush(otExterneAutreResponsable);

        // Get all the otExterneAutreResponsableList
        restOtExterneAutreResponsableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otExterneAutreResponsable.getId().intValue())))
            .andExpect(jsonPath("$.[*].otExterneId").value(hasItem(DEFAULT_OT_EXTERNE_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactSocieteId").value(hasItem(DEFAULT_CONTACT_SOCIETE_ID.intValue())));
    }

    @Test
    @Transactional
    void getOtExterneAutreResponsable() throws Exception {
        // Initialize the database
        otExterneAutreResponsableRepository.saveAndFlush(otExterneAutreResponsable);

        // Get the otExterneAutreResponsable
        restOtExterneAutreResponsableMockMvc
            .perform(get(ENTITY_API_URL_ID, otExterneAutreResponsable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(otExterneAutreResponsable.getId().intValue()))
            .andExpect(jsonPath("$.otExterneId").value(DEFAULT_OT_EXTERNE_ID.intValue()))
            .andExpect(jsonPath("$.contactSocieteId").value(DEFAULT_CONTACT_SOCIETE_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOtExterneAutreResponsable() throws Exception {
        // Get the otExterneAutreResponsable
        restOtExterneAutreResponsableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOtExterneAutreResponsable() throws Exception {
        // Initialize the database
        otExterneAutreResponsableRepository.saveAndFlush(otExterneAutreResponsable);

        int databaseSizeBeforeUpdate = otExterneAutreResponsableRepository.findAll().size();

        // Update the otExterneAutreResponsable
        OtExterneAutreResponsable updatedOtExterneAutreResponsable = otExterneAutreResponsableRepository
            .findById(otExterneAutreResponsable.getId())
            .get();
        // Disconnect from session so that the updates on updatedOtExterneAutreResponsable are not directly saved in db
        em.detach(updatedOtExterneAutreResponsable);
        updatedOtExterneAutreResponsable.otExterneId(UPDATED_OT_EXTERNE_ID).contactSocieteId(UPDATED_CONTACT_SOCIETE_ID);
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = otExterneAutreResponsableMapper.toDto(updatedOtExterneAutreResponsable);

        restOtExterneAutreResponsableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otExterneAutreResponsableDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneAutreResponsableDTO))
            )
            .andExpect(status().isOk());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
        OtExterneAutreResponsable testOtExterneAutreResponsable = otExterneAutreResponsableList.get(
            otExterneAutreResponsableList.size() - 1
        );
        assertThat(testOtExterneAutreResponsable.getOtExterneId()).isEqualTo(UPDATED_OT_EXTERNE_ID);
        assertThat(testOtExterneAutreResponsable.getContactSocieteId()).isEqualTo(UPDATED_CONTACT_SOCIETE_ID);
    }

    @Test
    @Transactional
    void putNonExistingOtExterneAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = otExterneAutreResponsableRepository.findAll().size();
        otExterneAutreResponsable.setId(count.incrementAndGet());

        // Create the OtExterneAutreResponsable
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = otExterneAutreResponsableMapper.toDto(otExterneAutreResponsable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtExterneAutreResponsableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otExterneAutreResponsableDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOtExterneAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = otExterneAutreResponsableRepository.findAll().size();
        otExterneAutreResponsable.setId(count.incrementAndGet());

        // Create the OtExterneAutreResponsable
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = otExterneAutreResponsableMapper.toDto(otExterneAutreResponsable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtExterneAutreResponsableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOtExterneAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = otExterneAutreResponsableRepository.findAll().size();
        otExterneAutreResponsable.setId(count.incrementAndGet());

        // Create the OtExterneAutreResponsable
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = otExterneAutreResponsableMapper.toDto(otExterneAutreResponsable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtExterneAutreResponsableMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneAutreResponsableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOtExterneAutreResponsableWithPatch() throws Exception {
        // Initialize the database
        otExterneAutreResponsableRepository.saveAndFlush(otExterneAutreResponsable);

        int databaseSizeBeforeUpdate = otExterneAutreResponsableRepository.findAll().size();

        // Update the otExterneAutreResponsable using partial update
        OtExterneAutreResponsable partialUpdatedOtExterneAutreResponsable = new OtExterneAutreResponsable();
        partialUpdatedOtExterneAutreResponsable.setId(otExterneAutreResponsable.getId());

        restOtExterneAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtExterneAutreResponsable.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtExterneAutreResponsable))
            )
            .andExpect(status().isOk());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
        OtExterneAutreResponsable testOtExterneAutreResponsable = otExterneAutreResponsableList.get(
            otExterneAutreResponsableList.size() - 1
        );
        assertThat(testOtExterneAutreResponsable.getOtExterneId()).isEqualTo(DEFAULT_OT_EXTERNE_ID);
        assertThat(testOtExterneAutreResponsable.getContactSocieteId()).isEqualTo(DEFAULT_CONTACT_SOCIETE_ID);
    }

    @Test
    @Transactional
    void fullUpdateOtExterneAutreResponsableWithPatch() throws Exception {
        // Initialize the database
        otExterneAutreResponsableRepository.saveAndFlush(otExterneAutreResponsable);

        int databaseSizeBeforeUpdate = otExterneAutreResponsableRepository.findAll().size();

        // Update the otExterneAutreResponsable using partial update
        OtExterneAutreResponsable partialUpdatedOtExterneAutreResponsable = new OtExterneAutreResponsable();
        partialUpdatedOtExterneAutreResponsable.setId(otExterneAutreResponsable.getId());

        partialUpdatedOtExterneAutreResponsable.otExterneId(UPDATED_OT_EXTERNE_ID).contactSocieteId(UPDATED_CONTACT_SOCIETE_ID);

        restOtExterneAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtExterneAutreResponsable.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtExterneAutreResponsable))
            )
            .andExpect(status().isOk());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
        OtExterneAutreResponsable testOtExterneAutreResponsable = otExterneAutreResponsableList.get(
            otExterneAutreResponsableList.size() - 1
        );
        assertThat(testOtExterneAutreResponsable.getOtExterneId()).isEqualTo(UPDATED_OT_EXTERNE_ID);
        assertThat(testOtExterneAutreResponsable.getContactSocieteId()).isEqualTo(UPDATED_CONTACT_SOCIETE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingOtExterneAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = otExterneAutreResponsableRepository.findAll().size();
        otExterneAutreResponsable.setId(count.incrementAndGet());

        // Create the OtExterneAutreResponsable
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = otExterneAutreResponsableMapper.toDto(otExterneAutreResponsable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtExterneAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, otExterneAutreResponsableDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otExterneAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOtExterneAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = otExterneAutreResponsableRepository.findAll().size();
        otExterneAutreResponsable.setId(count.incrementAndGet());

        // Create the OtExterneAutreResponsable
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = otExterneAutreResponsableMapper.toDto(otExterneAutreResponsable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtExterneAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otExterneAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOtExterneAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = otExterneAutreResponsableRepository.findAll().size();
        otExterneAutreResponsable.setId(count.incrementAndGet());

        // Create the OtExterneAutreResponsable
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = otExterneAutreResponsableMapper.toDto(otExterneAutreResponsable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtExterneAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otExterneAutreResponsableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtExterneAutreResponsable in the database
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOtExterneAutreResponsable() throws Exception {
        // Initialize the database
        otExterneAutreResponsableRepository.saveAndFlush(otExterneAutreResponsable);

        int databaseSizeBeforeDelete = otExterneAutreResponsableRepository.findAll().size();

        // Delete the otExterneAutreResponsable
        restOtExterneAutreResponsableMockMvc
            .perform(delete(ENTITY_API_URL_ID, otExterneAutreResponsable.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OtExterneAutreResponsable> otExterneAutreResponsableList = otExterneAutreResponsableRepository.findAll();
        assertThat(otExterneAutreResponsableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

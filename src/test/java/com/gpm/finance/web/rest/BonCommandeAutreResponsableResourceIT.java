package com.gpm.finance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.BonCommandeAutreResponsable;
import com.gpm.finance.repository.BonCommandeAutreResponsableRepository;
import com.gpm.finance.service.dto.BonCommandeAutreResponsableDTO;
import com.gpm.finance.service.mapper.BonCommandeAutreResponsableMapper;
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
 * Integration tests for the {@link BonCommandeAutreResponsableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BonCommandeAutreResponsableResourceIT {

    private static final Long DEFAULT_BON_COMMANDE_ID = 1L;
    private static final Long UPDATED_BON_COMMANDE_ID = 2L;

    private static final Long DEFAULT_CONTACT_SOCIETE_ID = 1L;
    private static final Long UPDATED_CONTACT_SOCIETE_ID = 2L;

    private static final String ENTITY_API_URL = "/api/bon-commande-autre-responsables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BonCommandeAutreResponsableRepository bonCommandeAutreResponsableRepository;

    @Autowired
    private BonCommandeAutreResponsableMapper bonCommandeAutreResponsableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBonCommandeAutreResponsableMockMvc;

    private BonCommandeAutreResponsable bonCommandeAutreResponsable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonCommandeAutreResponsable createEntity(EntityManager em) {
        BonCommandeAutreResponsable bonCommandeAutreResponsable = new BonCommandeAutreResponsable()
            .bonCommandeId(DEFAULT_BON_COMMANDE_ID)
            .contactSocieteId(DEFAULT_CONTACT_SOCIETE_ID);
        return bonCommandeAutreResponsable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonCommandeAutreResponsable createUpdatedEntity(EntityManager em) {
        BonCommandeAutreResponsable bonCommandeAutreResponsable = new BonCommandeAutreResponsable()
            .bonCommandeId(UPDATED_BON_COMMANDE_ID)
            .contactSocieteId(UPDATED_CONTACT_SOCIETE_ID);
        return bonCommandeAutreResponsable;
    }

    @BeforeEach
    public void initTest() {
        bonCommandeAutreResponsable = createEntity(em);
    }

    @Test
    @Transactional
    void createBonCommandeAutreResponsable() throws Exception {
        int databaseSizeBeforeCreate = bonCommandeAutreResponsableRepository.findAll().size();
        // Create the BonCommandeAutreResponsable
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableMapper.toDto(
            bonCommandeAutreResponsable
        );
        restBonCommandeAutreResponsableMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeAutreResponsableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeCreate + 1);
        BonCommandeAutreResponsable testBonCommandeAutreResponsable = bonCommandeAutreResponsableList.get(
            bonCommandeAutreResponsableList.size() - 1
        );
        assertThat(testBonCommandeAutreResponsable.getBonCommandeId()).isEqualTo(DEFAULT_BON_COMMANDE_ID);
        assertThat(testBonCommandeAutreResponsable.getContactSocieteId()).isEqualTo(DEFAULT_CONTACT_SOCIETE_ID);
    }

    @Test
    @Transactional
    void createBonCommandeAutreResponsableWithExistingId() throws Exception {
        // Create the BonCommandeAutreResponsable with an existing ID
        bonCommandeAutreResponsable.setId(1L);
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableMapper.toDto(
            bonCommandeAutreResponsable
        );

        int databaseSizeBeforeCreate = bonCommandeAutreResponsableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonCommandeAutreResponsableMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBonCommandeAutreResponsables() throws Exception {
        // Initialize the database
        bonCommandeAutreResponsableRepository.saveAndFlush(bonCommandeAutreResponsable);

        // Get all the bonCommandeAutreResponsableList
        restBonCommandeAutreResponsableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonCommandeAutreResponsable.getId().intValue())))
            .andExpect(jsonPath("$.[*].bonCommandeId").value(hasItem(DEFAULT_BON_COMMANDE_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactSocieteId").value(hasItem(DEFAULT_CONTACT_SOCIETE_ID.intValue())));
    }

    @Test
    @Transactional
    void getBonCommandeAutreResponsable() throws Exception {
        // Initialize the database
        bonCommandeAutreResponsableRepository.saveAndFlush(bonCommandeAutreResponsable);

        // Get the bonCommandeAutreResponsable
        restBonCommandeAutreResponsableMockMvc
            .perform(get(ENTITY_API_URL_ID, bonCommandeAutreResponsable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonCommandeAutreResponsable.getId().intValue()))
            .andExpect(jsonPath("$.bonCommandeId").value(DEFAULT_BON_COMMANDE_ID.intValue()))
            .andExpect(jsonPath("$.contactSocieteId").value(DEFAULT_CONTACT_SOCIETE_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBonCommandeAutreResponsable() throws Exception {
        // Get the bonCommandeAutreResponsable
        restBonCommandeAutreResponsableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBonCommandeAutreResponsable() throws Exception {
        // Initialize the database
        bonCommandeAutreResponsableRepository.saveAndFlush(bonCommandeAutreResponsable);

        int databaseSizeBeforeUpdate = bonCommandeAutreResponsableRepository.findAll().size();

        // Update the bonCommandeAutreResponsable
        BonCommandeAutreResponsable updatedBonCommandeAutreResponsable = bonCommandeAutreResponsableRepository
            .findById(bonCommandeAutreResponsable.getId())
            .get();
        // Disconnect from session so that the updates on updatedBonCommandeAutreResponsable are not directly saved in db
        em.detach(updatedBonCommandeAutreResponsable);
        updatedBonCommandeAutreResponsable.bonCommandeId(UPDATED_BON_COMMANDE_ID).contactSocieteId(UPDATED_CONTACT_SOCIETE_ID);
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableMapper.toDto(
            updatedBonCommandeAutreResponsable
        );

        restBonCommandeAutreResponsableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bonCommandeAutreResponsableDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeAutreResponsableDTO))
            )
            .andExpect(status().isOk());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
        BonCommandeAutreResponsable testBonCommandeAutreResponsable = bonCommandeAutreResponsableList.get(
            bonCommandeAutreResponsableList.size() - 1
        );
        assertThat(testBonCommandeAutreResponsable.getBonCommandeId()).isEqualTo(UPDATED_BON_COMMANDE_ID);
        assertThat(testBonCommandeAutreResponsable.getContactSocieteId()).isEqualTo(UPDATED_CONTACT_SOCIETE_ID);
    }

    @Test
    @Transactional
    void putNonExistingBonCommandeAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeAutreResponsableRepository.findAll().size();
        bonCommandeAutreResponsable.setId(count.incrementAndGet());

        // Create the BonCommandeAutreResponsable
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableMapper.toDto(
            bonCommandeAutreResponsable
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonCommandeAutreResponsableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bonCommandeAutreResponsableDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBonCommandeAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeAutreResponsableRepository.findAll().size();
        bonCommandeAutreResponsable.setId(count.incrementAndGet());

        // Create the BonCommandeAutreResponsable
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableMapper.toDto(
            bonCommandeAutreResponsable
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeAutreResponsableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBonCommandeAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeAutreResponsableRepository.findAll().size();
        bonCommandeAutreResponsable.setId(count.incrementAndGet());

        // Create the BonCommandeAutreResponsable
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableMapper.toDto(
            bonCommandeAutreResponsable
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeAutreResponsableMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeAutreResponsableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBonCommandeAutreResponsableWithPatch() throws Exception {
        // Initialize the database
        bonCommandeAutreResponsableRepository.saveAndFlush(bonCommandeAutreResponsable);

        int databaseSizeBeforeUpdate = bonCommandeAutreResponsableRepository.findAll().size();

        // Update the bonCommandeAutreResponsable using partial update
        BonCommandeAutreResponsable partialUpdatedBonCommandeAutreResponsable = new BonCommandeAutreResponsable();
        partialUpdatedBonCommandeAutreResponsable.setId(bonCommandeAutreResponsable.getId());

        partialUpdatedBonCommandeAutreResponsable.bonCommandeId(UPDATED_BON_COMMANDE_ID);

        restBonCommandeAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonCommandeAutreResponsable.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonCommandeAutreResponsable))
            )
            .andExpect(status().isOk());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
        BonCommandeAutreResponsable testBonCommandeAutreResponsable = bonCommandeAutreResponsableList.get(
            bonCommandeAutreResponsableList.size() - 1
        );
        assertThat(testBonCommandeAutreResponsable.getBonCommandeId()).isEqualTo(UPDATED_BON_COMMANDE_ID);
        assertThat(testBonCommandeAutreResponsable.getContactSocieteId()).isEqualTo(DEFAULT_CONTACT_SOCIETE_ID);
    }

    @Test
    @Transactional
    void fullUpdateBonCommandeAutreResponsableWithPatch() throws Exception {
        // Initialize the database
        bonCommandeAutreResponsableRepository.saveAndFlush(bonCommandeAutreResponsable);

        int databaseSizeBeforeUpdate = bonCommandeAutreResponsableRepository.findAll().size();

        // Update the bonCommandeAutreResponsable using partial update
        BonCommandeAutreResponsable partialUpdatedBonCommandeAutreResponsable = new BonCommandeAutreResponsable();
        partialUpdatedBonCommandeAutreResponsable.setId(bonCommandeAutreResponsable.getId());

        partialUpdatedBonCommandeAutreResponsable.bonCommandeId(UPDATED_BON_COMMANDE_ID).contactSocieteId(UPDATED_CONTACT_SOCIETE_ID);

        restBonCommandeAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonCommandeAutreResponsable.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonCommandeAutreResponsable))
            )
            .andExpect(status().isOk());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
        BonCommandeAutreResponsable testBonCommandeAutreResponsable = bonCommandeAutreResponsableList.get(
            bonCommandeAutreResponsableList.size() - 1
        );
        assertThat(testBonCommandeAutreResponsable.getBonCommandeId()).isEqualTo(UPDATED_BON_COMMANDE_ID);
        assertThat(testBonCommandeAutreResponsable.getContactSocieteId()).isEqualTo(UPDATED_CONTACT_SOCIETE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingBonCommandeAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeAutreResponsableRepository.findAll().size();
        bonCommandeAutreResponsable.setId(count.incrementAndGet());

        // Create the BonCommandeAutreResponsable
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableMapper.toDto(
            bonCommandeAutreResponsable
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonCommandeAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bonCommandeAutreResponsableDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBonCommandeAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeAutreResponsableRepository.findAll().size();
        bonCommandeAutreResponsable.setId(count.incrementAndGet());

        // Create the BonCommandeAutreResponsable
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableMapper.toDto(
            bonCommandeAutreResponsable
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeAutreResponsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBonCommandeAutreResponsable() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeAutreResponsableRepository.findAll().size();
        bonCommandeAutreResponsable.setId(count.incrementAndGet());

        // Create the BonCommandeAutreResponsable
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = bonCommandeAutreResponsableMapper.toDto(
            bonCommandeAutreResponsable
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeAutreResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeAutreResponsableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BonCommandeAutreResponsable in the database
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBonCommandeAutreResponsable() throws Exception {
        // Initialize the database
        bonCommandeAutreResponsableRepository.saveAndFlush(bonCommandeAutreResponsable);

        int databaseSizeBeforeDelete = bonCommandeAutreResponsableRepository.findAll().size();

        // Delete the bonCommandeAutreResponsable
        restBonCommandeAutreResponsableMockMvc
            .perform(delete(ENTITY_API_URL_ID, bonCommandeAutreResponsable.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BonCommandeAutreResponsable> bonCommandeAutreResponsableList = bonCommandeAutreResponsableRepository.findAll();
        assertThat(bonCommandeAutreResponsableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

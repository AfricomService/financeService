package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.BonCommandeArticles;
import com.gpm.finance.repository.BonCommandeArticlesRepository;
import com.gpm.finance.service.dto.BonCommandeArticlesDTO;
import com.gpm.finance.service.mapper.BonCommandeArticlesMapper;
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
 * Integration tests for the {@link BonCommandeArticlesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BonCommandeArticlesResourceIT {

    private static final Long DEFAULT_BON_COMMANDE_ID = 1L;
    private static final Long UPDATED_BON_COMMANDE_ID = 2L;

    private static final Long DEFAULT_ARTICLE_ID = 1L;
    private static final Long UPDATED_ARTICLE_ID = 2L;

    private static final ZonedDateTime DEFAULT_DATE_REALISATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_REALISATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_QTE_COMMANDE = 1;
    private static final Integer UPDATED_QTE_COMMANDE = 2;

    private static final Integer DEFAULT_QTE_EFFECTUEE = 1;
    private static final Integer UPDATED_QTE_EFFECTUEE = 2;

    private static final String ENTITY_API_URL = "/api/bon-commande-articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BonCommandeArticlesRepository bonCommandeArticlesRepository;

    @Autowired
    private BonCommandeArticlesMapper bonCommandeArticlesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBonCommandeArticlesMockMvc;

    private BonCommandeArticles bonCommandeArticles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonCommandeArticles createEntity(EntityManager em) {
        BonCommandeArticles bonCommandeArticles = new BonCommandeArticles()
            .bonCommandeId(DEFAULT_BON_COMMANDE_ID)
            .articleId(DEFAULT_ARTICLE_ID)
            .dateRealisation(DEFAULT_DATE_REALISATION)
            .qteCommande(DEFAULT_QTE_COMMANDE)
            .qteEffectuee(DEFAULT_QTE_EFFECTUEE);
        return bonCommandeArticles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonCommandeArticles createUpdatedEntity(EntityManager em) {
        BonCommandeArticles bonCommandeArticles = new BonCommandeArticles()
            .bonCommandeId(UPDATED_BON_COMMANDE_ID)
            .articleId(UPDATED_ARTICLE_ID)
            .dateRealisation(UPDATED_DATE_REALISATION)
            .qteCommande(UPDATED_QTE_COMMANDE)
            .qteEffectuee(UPDATED_QTE_EFFECTUEE);
        return bonCommandeArticles;
    }

    @BeforeEach
    public void initTest() {
        bonCommandeArticles = createEntity(em);
    }

    @Test
    @Transactional
    void createBonCommandeArticles() throws Exception {
        int databaseSizeBeforeCreate = bonCommandeArticlesRepository.findAll().size();
        // Create the BonCommandeArticles
        BonCommandeArticlesDTO bonCommandeArticlesDTO = bonCommandeArticlesMapper.toDto(bonCommandeArticles);
        restBonCommandeArticlesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeArticlesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeCreate + 1);
        BonCommandeArticles testBonCommandeArticles = bonCommandeArticlesList.get(bonCommandeArticlesList.size() - 1);
        assertThat(testBonCommandeArticles.getBonCommandeId()).isEqualTo(DEFAULT_BON_COMMANDE_ID);
        assertThat(testBonCommandeArticles.getArticleId()).isEqualTo(DEFAULT_ARTICLE_ID);
        assertThat(testBonCommandeArticles.getDateRealisation()).isEqualTo(DEFAULT_DATE_REALISATION);
        assertThat(testBonCommandeArticles.getQteCommande()).isEqualTo(DEFAULT_QTE_COMMANDE);
        assertThat(testBonCommandeArticles.getQteEffectuee()).isEqualTo(DEFAULT_QTE_EFFECTUEE);
    }

    @Test
    @Transactional
    void createBonCommandeArticlesWithExistingId() throws Exception {
        // Create the BonCommandeArticles with an existing ID
        bonCommandeArticles.setId(1L);
        BonCommandeArticlesDTO bonCommandeArticlesDTO = bonCommandeArticlesMapper.toDto(bonCommandeArticles);

        int databaseSizeBeforeCreate = bonCommandeArticlesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonCommandeArticlesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBonCommandeArticles() throws Exception {
        // Initialize the database
        bonCommandeArticlesRepository.saveAndFlush(bonCommandeArticles);

        // Get all the bonCommandeArticlesList
        restBonCommandeArticlesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonCommandeArticles.getId().intValue())))
            .andExpect(jsonPath("$.[*].bonCommandeId").value(hasItem(DEFAULT_BON_COMMANDE_ID.intValue())))
            .andExpect(jsonPath("$.[*].articleId").value(hasItem(DEFAULT_ARTICLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dateRealisation").value(hasItem(sameInstant(DEFAULT_DATE_REALISATION))))
            .andExpect(jsonPath("$.[*].qteCommande").value(hasItem(DEFAULT_QTE_COMMANDE)))
            .andExpect(jsonPath("$.[*].qteEffectuee").value(hasItem(DEFAULT_QTE_EFFECTUEE)));
    }

    @Test
    @Transactional
    void getBonCommandeArticles() throws Exception {
        // Initialize the database
        bonCommandeArticlesRepository.saveAndFlush(bonCommandeArticles);

        // Get the bonCommandeArticles
        restBonCommandeArticlesMockMvc
            .perform(get(ENTITY_API_URL_ID, bonCommandeArticles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonCommandeArticles.getId().intValue()))
            .andExpect(jsonPath("$.bonCommandeId").value(DEFAULT_BON_COMMANDE_ID.intValue()))
            .andExpect(jsonPath("$.articleId").value(DEFAULT_ARTICLE_ID.intValue()))
            .andExpect(jsonPath("$.dateRealisation").value(sameInstant(DEFAULT_DATE_REALISATION)))
            .andExpect(jsonPath("$.qteCommande").value(DEFAULT_QTE_COMMANDE))
            .andExpect(jsonPath("$.qteEffectuee").value(DEFAULT_QTE_EFFECTUEE));
    }

    @Test
    @Transactional
    void getNonExistingBonCommandeArticles() throws Exception {
        // Get the bonCommandeArticles
        restBonCommandeArticlesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBonCommandeArticles() throws Exception {
        // Initialize the database
        bonCommandeArticlesRepository.saveAndFlush(bonCommandeArticles);

        int databaseSizeBeforeUpdate = bonCommandeArticlesRepository.findAll().size();

        // Update the bonCommandeArticles
        BonCommandeArticles updatedBonCommandeArticles = bonCommandeArticlesRepository.findById(bonCommandeArticles.getId()).get();
        // Disconnect from session so that the updates on updatedBonCommandeArticles are not directly saved in db
        em.detach(updatedBonCommandeArticles);
        updatedBonCommandeArticles
            .bonCommandeId(UPDATED_BON_COMMANDE_ID)
            .articleId(UPDATED_ARTICLE_ID)
            .dateRealisation(UPDATED_DATE_REALISATION)
            .qteCommande(UPDATED_QTE_COMMANDE)
            .qteEffectuee(UPDATED_QTE_EFFECTUEE);
        BonCommandeArticlesDTO bonCommandeArticlesDTO = bonCommandeArticlesMapper.toDto(updatedBonCommandeArticles);

        restBonCommandeArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bonCommandeArticlesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeArticlesDTO))
            )
            .andExpect(status().isOk());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeUpdate);
        BonCommandeArticles testBonCommandeArticles = bonCommandeArticlesList.get(bonCommandeArticlesList.size() - 1);
        assertThat(testBonCommandeArticles.getBonCommandeId()).isEqualTo(UPDATED_BON_COMMANDE_ID);
        assertThat(testBonCommandeArticles.getArticleId()).isEqualTo(UPDATED_ARTICLE_ID);
        assertThat(testBonCommandeArticles.getDateRealisation()).isEqualTo(UPDATED_DATE_REALISATION);
        assertThat(testBonCommandeArticles.getQteCommande()).isEqualTo(UPDATED_QTE_COMMANDE);
        assertThat(testBonCommandeArticles.getQteEffectuee()).isEqualTo(UPDATED_QTE_EFFECTUEE);
    }

    @Test
    @Transactional
    void putNonExistingBonCommandeArticles() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeArticlesRepository.findAll().size();
        bonCommandeArticles.setId(count.incrementAndGet());

        // Create the BonCommandeArticles
        BonCommandeArticlesDTO bonCommandeArticlesDTO = bonCommandeArticlesMapper.toDto(bonCommandeArticles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonCommandeArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bonCommandeArticlesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBonCommandeArticles() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeArticlesRepository.findAll().size();
        bonCommandeArticles.setId(count.incrementAndGet());

        // Create the BonCommandeArticles
        BonCommandeArticlesDTO bonCommandeArticlesDTO = bonCommandeArticlesMapper.toDto(bonCommandeArticles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBonCommandeArticles() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeArticlesRepository.findAll().size();
        bonCommandeArticles.setId(count.incrementAndGet());

        // Create the BonCommandeArticles
        BonCommandeArticlesDTO bonCommandeArticlesDTO = bonCommandeArticlesMapper.toDto(bonCommandeArticles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeArticlesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeArticlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBonCommandeArticlesWithPatch() throws Exception {
        // Initialize the database
        bonCommandeArticlesRepository.saveAndFlush(bonCommandeArticles);

        int databaseSizeBeforeUpdate = bonCommandeArticlesRepository.findAll().size();

        // Update the bonCommandeArticles using partial update
        BonCommandeArticles partialUpdatedBonCommandeArticles = new BonCommandeArticles();
        partialUpdatedBonCommandeArticles.setId(bonCommandeArticles.getId());

        partialUpdatedBonCommandeArticles
            .bonCommandeId(UPDATED_BON_COMMANDE_ID)
            .dateRealisation(UPDATED_DATE_REALISATION)
            .qteCommande(UPDATED_QTE_COMMANDE)
            .qteEffectuee(UPDATED_QTE_EFFECTUEE);

        restBonCommandeArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonCommandeArticles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonCommandeArticles))
            )
            .andExpect(status().isOk());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeUpdate);
        BonCommandeArticles testBonCommandeArticles = bonCommandeArticlesList.get(bonCommandeArticlesList.size() - 1);
        assertThat(testBonCommandeArticles.getBonCommandeId()).isEqualTo(UPDATED_BON_COMMANDE_ID);
        assertThat(testBonCommandeArticles.getArticleId()).isEqualTo(DEFAULT_ARTICLE_ID);
        assertThat(testBonCommandeArticles.getDateRealisation()).isEqualTo(UPDATED_DATE_REALISATION);
        assertThat(testBonCommandeArticles.getQteCommande()).isEqualTo(UPDATED_QTE_COMMANDE);
        assertThat(testBonCommandeArticles.getQteEffectuee()).isEqualTo(UPDATED_QTE_EFFECTUEE);
    }

    @Test
    @Transactional
    void fullUpdateBonCommandeArticlesWithPatch() throws Exception {
        // Initialize the database
        bonCommandeArticlesRepository.saveAndFlush(bonCommandeArticles);

        int databaseSizeBeforeUpdate = bonCommandeArticlesRepository.findAll().size();

        // Update the bonCommandeArticles using partial update
        BonCommandeArticles partialUpdatedBonCommandeArticles = new BonCommandeArticles();
        partialUpdatedBonCommandeArticles.setId(bonCommandeArticles.getId());

        partialUpdatedBonCommandeArticles
            .bonCommandeId(UPDATED_BON_COMMANDE_ID)
            .articleId(UPDATED_ARTICLE_ID)
            .dateRealisation(UPDATED_DATE_REALISATION)
            .qteCommande(UPDATED_QTE_COMMANDE)
            .qteEffectuee(UPDATED_QTE_EFFECTUEE);

        restBonCommandeArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonCommandeArticles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonCommandeArticles))
            )
            .andExpect(status().isOk());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeUpdate);
        BonCommandeArticles testBonCommandeArticles = bonCommandeArticlesList.get(bonCommandeArticlesList.size() - 1);
        assertThat(testBonCommandeArticles.getBonCommandeId()).isEqualTo(UPDATED_BON_COMMANDE_ID);
        assertThat(testBonCommandeArticles.getArticleId()).isEqualTo(UPDATED_ARTICLE_ID);
        assertThat(testBonCommandeArticles.getDateRealisation()).isEqualTo(UPDATED_DATE_REALISATION);
        assertThat(testBonCommandeArticles.getQteCommande()).isEqualTo(UPDATED_QTE_COMMANDE);
        assertThat(testBonCommandeArticles.getQteEffectuee()).isEqualTo(UPDATED_QTE_EFFECTUEE);
    }

    @Test
    @Transactional
    void patchNonExistingBonCommandeArticles() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeArticlesRepository.findAll().size();
        bonCommandeArticles.setId(count.incrementAndGet());

        // Create the BonCommandeArticles
        BonCommandeArticlesDTO bonCommandeArticlesDTO = bonCommandeArticlesMapper.toDto(bonCommandeArticles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonCommandeArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bonCommandeArticlesDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBonCommandeArticles() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeArticlesRepository.findAll().size();
        bonCommandeArticles.setId(count.incrementAndGet());

        // Create the BonCommandeArticles
        BonCommandeArticlesDTO bonCommandeArticlesDTO = bonCommandeArticlesMapper.toDto(bonCommandeArticles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBonCommandeArticles() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeArticlesRepository.findAll().size();
        bonCommandeArticles.setId(count.incrementAndGet());

        // Create the BonCommandeArticles
        BonCommandeArticlesDTO bonCommandeArticlesDTO = bonCommandeArticlesMapper.toDto(bonCommandeArticles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeArticlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BonCommandeArticles in the database
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBonCommandeArticles() throws Exception {
        // Initialize the database
        bonCommandeArticlesRepository.saveAndFlush(bonCommandeArticles);

        int databaseSizeBeforeDelete = bonCommandeArticlesRepository.findAll().size();

        // Delete the bonCommandeArticles
        restBonCommandeArticlesMockMvc
            .perform(delete(ENTITY_API_URL_ID, bonCommandeArticles.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BonCommandeArticles> bonCommandeArticlesList = bonCommandeArticlesRepository.findAll();
        assertThat(bonCommandeArticlesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

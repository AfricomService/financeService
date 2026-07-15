package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static com.gpm.finance.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.OtArticles;
import com.gpm.finance.repository.OtArticlesRepository;
import com.gpm.finance.service.dto.OtArticlesDTO;
import com.gpm.finance.service.mapper.OtArticlesMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link OtArticlesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OtArticlesResourceIT {

    private static final Long DEFAULT_OT_ID = 1L;
    private static final Long UPDATED_OT_ID = 2L;

    private static final Long DEFAULT_ARTICLE_ID = 1L;
    private static final Long UPDATED_ARTICLE_ID = 2L;

    private static final BigDecimal DEFAULT_PRIX_PROPOSE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_PROPOSE = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_DATE_AFFECTATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_AFFECTATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/ot-articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OtArticlesRepository otArticlesRepository;

    @Autowired
    private OtArticlesMapper otArticlesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtArticlesMockMvc;

    private OtArticles otArticles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtArticles createEntity(EntityManager em) {
        OtArticles otArticles = new OtArticles()
            .otId(DEFAULT_OT_ID)
            .articleId(DEFAULT_ARTICLE_ID)
            .prixPropose(DEFAULT_PRIX_PROPOSE)
            .dateAffectation(DEFAULT_DATE_AFFECTATION);
        return otArticles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtArticles createUpdatedEntity(EntityManager em) {
        OtArticles otArticles = new OtArticles()
            .otId(UPDATED_OT_ID)
            .articleId(UPDATED_ARTICLE_ID)
            .prixPropose(UPDATED_PRIX_PROPOSE)
            .dateAffectation(UPDATED_DATE_AFFECTATION);
        return otArticles;
    }

    @BeforeEach
    public void initTest() {
        otArticles = createEntity(em);
    }

    @Test
    @Transactional
    void createOtArticles() throws Exception {
        int databaseSizeBeforeCreate = otArticlesRepository.findAll().size();
        // Create the OtArticles
        OtArticlesDTO otArticlesDTO = otArticlesMapper.toDto(otArticles);
        restOtArticlesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otArticlesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeCreate + 1);
        OtArticles testOtArticles = otArticlesList.get(otArticlesList.size() - 1);
        assertThat(testOtArticles.getOtId()).isEqualTo(DEFAULT_OT_ID);
        assertThat(testOtArticles.getArticleId()).isEqualTo(DEFAULT_ARTICLE_ID);
        assertThat(testOtArticles.getPrixPropose()).isEqualByComparingTo(DEFAULT_PRIX_PROPOSE);
        assertThat(testOtArticles.getDateAffectation()).isEqualTo(DEFAULT_DATE_AFFECTATION);
    }

    @Test
    @Transactional
    void createOtArticlesWithExistingId() throws Exception {
        // Create the OtArticles with an existing ID
        otArticles.setId(1L);
        OtArticlesDTO otArticlesDTO = otArticlesMapper.toDto(otArticles);

        int databaseSizeBeforeCreate = otArticlesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtArticlesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOtArticles() throws Exception {
        // Initialize the database
        otArticlesRepository.saveAndFlush(otArticles);

        // Get all the otArticlesList
        restOtArticlesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otArticles.getId().intValue())))
            .andExpect(jsonPath("$.[*].otId").value(hasItem(DEFAULT_OT_ID.intValue())))
            .andExpect(jsonPath("$.[*].articleId").value(hasItem(DEFAULT_ARTICLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].prixPropose").value(hasItem(sameNumber(DEFAULT_PRIX_PROPOSE))))
            .andExpect(jsonPath("$.[*].dateAffectation").value(hasItem(sameInstant(DEFAULT_DATE_AFFECTATION))));
    }

    @Test
    @Transactional
    void getOtArticles() throws Exception {
        // Initialize the database
        otArticlesRepository.saveAndFlush(otArticles);

        // Get the otArticles
        restOtArticlesMockMvc
            .perform(get(ENTITY_API_URL_ID, otArticles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(otArticles.getId().intValue()))
            .andExpect(jsonPath("$.otId").value(DEFAULT_OT_ID.intValue()))
            .andExpect(jsonPath("$.articleId").value(DEFAULT_ARTICLE_ID.intValue()))
            .andExpect(jsonPath("$.prixPropose").value(sameNumber(DEFAULT_PRIX_PROPOSE)))
            .andExpect(jsonPath("$.dateAffectation").value(sameInstant(DEFAULT_DATE_AFFECTATION)));
    }

    @Test
    @Transactional
    void getNonExistingOtArticles() throws Exception {
        // Get the otArticles
        restOtArticlesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOtArticles() throws Exception {
        // Initialize the database
        otArticlesRepository.saveAndFlush(otArticles);

        int databaseSizeBeforeUpdate = otArticlesRepository.findAll().size();

        // Update the otArticles
        OtArticles updatedOtArticles = otArticlesRepository.findById(otArticles.getId()).get();
        // Disconnect from session so that the updates on updatedOtArticles are not directly saved in db
        em.detach(updatedOtArticles);
        updatedOtArticles
            .otId(UPDATED_OT_ID)
            .articleId(UPDATED_ARTICLE_ID)
            .prixPropose(UPDATED_PRIX_PROPOSE)
            .dateAffectation(UPDATED_DATE_AFFECTATION);
        OtArticlesDTO otArticlesDTO = otArticlesMapper.toDto(updatedOtArticles);

        restOtArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otArticlesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otArticlesDTO))
            )
            .andExpect(status().isOk());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeUpdate);
        OtArticles testOtArticles = otArticlesList.get(otArticlesList.size() - 1);
        assertThat(testOtArticles.getOtId()).isEqualTo(UPDATED_OT_ID);
        assertThat(testOtArticles.getArticleId()).isEqualTo(UPDATED_ARTICLE_ID);
        assertThat(testOtArticles.getPrixPropose()).isEqualByComparingTo(UPDATED_PRIX_PROPOSE);
        assertThat(testOtArticles.getDateAffectation()).isEqualTo(UPDATED_DATE_AFFECTATION);
    }

    @Test
    @Transactional
    void putNonExistingOtArticles() throws Exception {
        int databaseSizeBeforeUpdate = otArticlesRepository.findAll().size();
        otArticles.setId(count.incrementAndGet());

        // Create the OtArticles
        OtArticlesDTO otArticlesDTO = otArticlesMapper.toDto(otArticles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otArticlesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOtArticles() throws Exception {
        int databaseSizeBeforeUpdate = otArticlesRepository.findAll().size();
        otArticles.setId(count.incrementAndGet());

        // Create the OtArticles
        OtArticlesDTO otArticlesDTO = otArticlesMapper.toDto(otArticles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOtArticles() throws Exception {
        int databaseSizeBeforeUpdate = otArticlesRepository.findAll().size();
        otArticles.setId(count.incrementAndGet());

        // Create the OtArticles
        OtArticlesDTO otArticlesDTO = otArticlesMapper.toDto(otArticles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtArticlesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otArticlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOtArticlesWithPatch() throws Exception {
        // Initialize the database
        otArticlesRepository.saveAndFlush(otArticles);

        int databaseSizeBeforeUpdate = otArticlesRepository.findAll().size();

        // Update the otArticles using partial update
        OtArticles partialUpdatedOtArticles = new OtArticles();
        partialUpdatedOtArticles.setId(otArticles.getId());

        partialUpdatedOtArticles.otId(UPDATED_OT_ID).articleId(UPDATED_ARTICLE_ID);

        restOtArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtArticles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtArticles))
            )
            .andExpect(status().isOk());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeUpdate);
        OtArticles testOtArticles = otArticlesList.get(otArticlesList.size() - 1);
        assertThat(testOtArticles.getOtId()).isEqualTo(UPDATED_OT_ID);
        assertThat(testOtArticles.getArticleId()).isEqualTo(UPDATED_ARTICLE_ID);
        assertThat(testOtArticles.getPrixPropose()).isEqualByComparingTo(DEFAULT_PRIX_PROPOSE);
        assertThat(testOtArticles.getDateAffectation()).isEqualTo(DEFAULT_DATE_AFFECTATION);
    }

    @Test
    @Transactional
    void fullUpdateOtArticlesWithPatch() throws Exception {
        // Initialize the database
        otArticlesRepository.saveAndFlush(otArticles);

        int databaseSizeBeforeUpdate = otArticlesRepository.findAll().size();

        // Update the otArticles using partial update
        OtArticles partialUpdatedOtArticles = new OtArticles();
        partialUpdatedOtArticles.setId(otArticles.getId());

        partialUpdatedOtArticles
            .otId(UPDATED_OT_ID)
            .articleId(UPDATED_ARTICLE_ID)
            .prixPropose(UPDATED_PRIX_PROPOSE)
            .dateAffectation(UPDATED_DATE_AFFECTATION);

        restOtArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtArticles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtArticles))
            )
            .andExpect(status().isOk());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeUpdate);
        OtArticles testOtArticles = otArticlesList.get(otArticlesList.size() - 1);
        assertThat(testOtArticles.getOtId()).isEqualTo(UPDATED_OT_ID);
        assertThat(testOtArticles.getArticleId()).isEqualTo(UPDATED_ARTICLE_ID);
        assertThat(testOtArticles.getPrixPropose()).isEqualByComparingTo(UPDATED_PRIX_PROPOSE);
        assertThat(testOtArticles.getDateAffectation()).isEqualTo(UPDATED_DATE_AFFECTATION);
    }

    @Test
    @Transactional
    void patchNonExistingOtArticles() throws Exception {
        int databaseSizeBeforeUpdate = otArticlesRepository.findAll().size();
        otArticles.setId(count.incrementAndGet());

        // Create the OtArticles
        OtArticlesDTO otArticlesDTO = otArticlesMapper.toDto(otArticles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, otArticlesDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOtArticles() throws Exception {
        int databaseSizeBeforeUpdate = otArticlesRepository.findAll().size();
        otArticles.setId(count.incrementAndGet());

        // Create the OtArticles
        OtArticlesDTO otArticlesDTO = otArticlesMapper.toDto(otArticles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otArticlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOtArticles() throws Exception {
        int databaseSizeBeforeUpdate = otArticlesRepository.findAll().size();
        otArticles.setId(count.incrementAndGet());

        // Create the OtArticles
        OtArticlesDTO otArticlesDTO = otArticlesMapper.toDto(otArticles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otArticlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtArticles in the database
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOtArticles() throws Exception {
        // Initialize the database
        otArticlesRepository.saveAndFlush(otArticles);

        int databaseSizeBeforeDelete = otArticlesRepository.findAll().size();

        // Delete the otArticles
        restOtArticlesMockMvc
            .perform(delete(ENTITY_API_URL_ID, otArticles.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OtArticles> otArticlesList = otArticlesRepository.findAll();
        assertThat(otArticlesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

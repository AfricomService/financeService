package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.OtExterne;
import com.gpm.finance.domain.enumeration.StatutOtExterne;
import com.gpm.finance.repository.OtExterneRepository;
import com.gpm.finance.service.dto.OtExterneDTO;
import com.gpm.finance.service.mapper.OtExterneMapper;
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
 * Integration tests for the {@link OtExterneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OtExterneResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final StatutOtExterne DEFAULT_STATUT = StatutOtExterne.Creation;
    private static final StatutOtExterne UPDATED_STATUT = StatutOtExterne.EnCours;

    private static final Long DEFAULT_AFFAIRE_ID = 1L;
    private static final Long UPDATED_AFFAIRE_ID = 2L;

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY_USER_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY_USER_LOGIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ot-externes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OtExterneRepository otExterneRepository;

    @Autowired
    private OtExterneMapper otExterneMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtExterneMockMvc;

    private OtExterne otExterne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtExterne createEntity(EntityManager em) {
        OtExterne otExterne = new OtExterne()
            .reference(DEFAULT_REFERENCE)
            .statut(DEFAULT_STATUT)
            .affaireId(DEFAULT_AFFAIRE_ID)
            .clientId(DEFAULT_CLIENT_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdByUserLogin(DEFAULT_CREATED_BY_USER_LOGIN)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedByUserLogin(DEFAULT_UPDATED_BY_USER_LOGIN);
        return otExterne;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtExterne createUpdatedEntity(EntityManager em) {
        OtExterne otExterne = new OtExterne()
            .reference(UPDATED_REFERENCE)
            .statut(UPDATED_STATUT)
            .affaireId(UPDATED_AFFAIRE_ID)
            .clientId(UPDATED_CLIENT_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        return otExterne;
    }

    @BeforeEach
    public void initTest() {
        otExterne = createEntity(em);
    }

    @Test
    @Transactional
    void createOtExterne() throws Exception {
        int databaseSizeBeforeCreate = otExterneRepository.findAll().size();
        // Create the OtExterne
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);
        restOtExterneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeCreate + 1);
        OtExterne testOtExterne = otExterneList.get(otExterneList.size() - 1);
        assertThat(testOtExterne.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testOtExterne.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testOtExterne.getAffaireId()).isEqualTo(DEFAULT_AFFAIRE_ID);
        assertThat(testOtExterne.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testOtExterne.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOtExterne.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testOtExterne.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOtExterne.getCreatedByUserLogin()).isEqualTo(DEFAULT_CREATED_BY_USER_LOGIN);
        assertThat(testOtExterne.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOtExterne.getUpdatedByUserLogin()).isEqualTo(DEFAULT_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void createOtExterneWithExistingId() throws Exception {
        // Create the OtExterne with an existing ID
        otExterne.setId(1L);
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);

        int databaseSizeBeforeCreate = otExterneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtExterneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = otExterneRepository.findAll().size();
        // set the field null
        otExterne.setReference(null);

        // Create the OtExterne, which fails.
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);

        restOtExterneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isBadRequest());

        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = otExterneRepository.findAll().size();
        // set the field null
        otExterne.setStatut(null);

        // Create the OtExterne, which fails.
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);

        restOtExterneMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isBadRequest());

        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOtExternes() throws Exception {
        // Initialize the database
        otExterneRepository.saveAndFlush(otExterne);

        // Get all the otExterneList
        restOtExterneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otExterne.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].affaireId").value(hasItem(DEFAULT_AFFAIRE_ID.intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdByUserLogin").value(hasItem(DEFAULT_CREATED_BY_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedByUserLogin").value(hasItem(DEFAULT_UPDATED_BY_USER_LOGIN)));
    }

    @Test
    @Transactional
    void getOtExterne() throws Exception {
        // Initialize the database
        otExterneRepository.saveAndFlush(otExterne);

        // Get the otExterne
        restOtExterneMockMvc
            .perform(get(ENTITY_API_URL_ID, otExterne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(otExterne.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.affaireId").value(DEFAULT_AFFAIRE_ID.intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdByUserLogin").value(DEFAULT_CREATED_BY_USER_LOGIN))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedByUserLogin").value(DEFAULT_UPDATED_BY_USER_LOGIN));
    }

    @Test
    @Transactional
    void getNonExistingOtExterne() throws Exception {
        // Get the otExterne
        restOtExterneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOtExterne() throws Exception {
        // Initialize the database
        otExterneRepository.saveAndFlush(otExterne);

        int databaseSizeBeforeUpdate = otExterneRepository.findAll().size();

        // Update the otExterne
        OtExterne updatedOtExterne = otExterneRepository.findById(otExterne.getId()).get();
        // Disconnect from session so that the updates on updatedOtExterne are not directly saved in db
        em.detach(updatedOtExterne);
        updatedOtExterne
            .reference(UPDATED_REFERENCE)
            .statut(UPDATED_STATUT)
            .affaireId(UPDATED_AFFAIRE_ID)
            .clientId(UPDATED_CLIENT_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(updatedOtExterne);

        restOtExterneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otExterneDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isOk());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeUpdate);
        OtExterne testOtExterne = otExterneList.get(otExterneList.size() - 1);
        assertThat(testOtExterne.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testOtExterne.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testOtExterne.getAffaireId()).isEqualTo(UPDATED_AFFAIRE_ID);
        assertThat(testOtExterne.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testOtExterne.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOtExterne.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testOtExterne.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOtExterne.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testOtExterne.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOtExterne.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void putNonExistingOtExterne() throws Exception {
        int databaseSizeBeforeUpdate = otExterneRepository.findAll().size();
        otExterne.setId(count.incrementAndGet());

        // Create the OtExterne
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtExterneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otExterneDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOtExterne() throws Exception {
        int databaseSizeBeforeUpdate = otExterneRepository.findAll().size();
        otExterne.setId(count.incrementAndGet());

        // Create the OtExterne
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtExterneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOtExterne() throws Exception {
        int databaseSizeBeforeUpdate = otExterneRepository.findAll().size();
        otExterne.setId(count.incrementAndGet());

        // Create the OtExterne
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtExterneMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOtExterneWithPatch() throws Exception {
        // Initialize the database
        otExterneRepository.saveAndFlush(otExterne);

        int databaseSizeBeforeUpdate = otExterneRepository.findAll().size();

        // Update the otExterne using partial update
        OtExterne partialUpdatedOtExterne = new OtExterne();
        partialUpdatedOtExterne.setId(otExterne.getId());

        partialUpdatedOtExterne.reference(UPDATED_REFERENCE).updatedBy(UPDATED_UPDATED_BY);

        restOtExterneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtExterne.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtExterne))
            )
            .andExpect(status().isOk());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeUpdate);
        OtExterne testOtExterne = otExterneList.get(otExterneList.size() - 1);
        assertThat(testOtExterne.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testOtExterne.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testOtExterne.getAffaireId()).isEqualTo(DEFAULT_AFFAIRE_ID);
        assertThat(testOtExterne.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testOtExterne.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOtExterne.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testOtExterne.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOtExterne.getCreatedByUserLogin()).isEqualTo(DEFAULT_CREATED_BY_USER_LOGIN);
        assertThat(testOtExterne.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOtExterne.getUpdatedByUserLogin()).isEqualTo(DEFAULT_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void fullUpdateOtExterneWithPatch() throws Exception {
        // Initialize the database
        otExterneRepository.saveAndFlush(otExterne);

        int databaseSizeBeforeUpdate = otExterneRepository.findAll().size();

        // Update the otExterne using partial update
        OtExterne partialUpdatedOtExterne = new OtExterne();
        partialUpdatedOtExterne.setId(otExterne.getId());

        partialUpdatedOtExterne
            .reference(UPDATED_REFERENCE)
            .statut(UPDATED_STATUT)
            .affaireId(UPDATED_AFFAIRE_ID)
            .clientId(UPDATED_CLIENT_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restOtExterneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtExterne.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtExterne))
            )
            .andExpect(status().isOk());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeUpdate);
        OtExterne testOtExterne = otExterneList.get(otExterneList.size() - 1);
        assertThat(testOtExterne.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testOtExterne.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testOtExterne.getAffaireId()).isEqualTo(UPDATED_AFFAIRE_ID);
        assertThat(testOtExterne.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testOtExterne.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOtExterne.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testOtExterne.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOtExterne.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testOtExterne.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOtExterne.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void patchNonExistingOtExterne() throws Exception {
        int databaseSizeBeforeUpdate = otExterneRepository.findAll().size();
        otExterne.setId(count.incrementAndGet());

        // Create the OtExterne
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtExterneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, otExterneDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOtExterne() throws Exception {
        int databaseSizeBeforeUpdate = otExterneRepository.findAll().size();
        otExterne.setId(count.incrementAndGet());

        // Create the OtExterne
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtExterneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOtExterne() throws Exception {
        int databaseSizeBeforeUpdate = otExterneRepository.findAll().size();
        otExterne.setId(count.incrementAndGet());

        // Create the OtExterne
        OtExterneDTO otExterneDTO = otExterneMapper.toDto(otExterne);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtExterneMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otExterneDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtExterne in the database
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOtExterne() throws Exception {
        // Initialize the database
        otExterneRepository.saveAndFlush(otExterne);

        int databaseSizeBeforeDelete = otExterneRepository.findAll().size();

        // Delete the otExterne
        restOtExterneMockMvc
            .perform(delete(ENTITY_API_URL_ID, otExterne.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OtExterne> otExterneList = otExterneRepository.findAll();
        assertThat(otExterneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

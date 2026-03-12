package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.DemandeEspece;
import com.gpm.finance.repository.DemandeEspeceRepository;
import com.gpm.finance.service.dto.DemandeEspeceDTO;
import com.gpm.finance.service.mapper.DemandeEspeceMapper;
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
 * Integration tests for the {@link DemandeEspeceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeEspeceResourceIT {

    private static final Float DEFAULT_MONTANT = 1F;
    private static final Float UPDATED_MONTANT = 2F;

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final Long DEFAULT_WORK_ORDER_ID = 1L;
    private static final Long UPDATED_WORK_ORDER_ID = 2L;

    private static final String DEFAULT_BENEFICIAIRE_ID = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIAIRE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIAIRE_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIAIRE_USER_LOGIN = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/demande-especes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeEspeceRepository demandeEspeceRepository;

    @Autowired
    private DemandeEspeceMapper demandeEspeceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeEspeceMockMvc;

    private DemandeEspece demandeEspece;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeEspece createEntity(EntityManager em) {
        DemandeEspece demandeEspece = new DemandeEspece()
            .montant(DEFAULT_MONTANT)
            .motif(DEFAULT_MOTIF)
            .workOrderId(DEFAULT_WORK_ORDER_ID)
            .beneficiaireId(DEFAULT_BENEFICIAIRE_ID)
            .beneficiaireUserLogin(DEFAULT_BENEFICIAIRE_USER_LOGIN)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdByUserLogin(DEFAULT_CREATED_BY_USER_LOGIN)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedByUserLogin(DEFAULT_UPDATED_BY_USER_LOGIN);
        return demandeEspece;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeEspece createUpdatedEntity(EntityManager em) {
        DemandeEspece demandeEspece = new DemandeEspece()
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .beneficiaireId(UPDATED_BENEFICIAIRE_ID)
            .beneficiaireUserLogin(UPDATED_BENEFICIAIRE_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        return demandeEspece;
    }

    @BeforeEach
    public void initTest() {
        demandeEspece = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeEspece() throws Exception {
        int databaseSizeBeforeCreate = demandeEspeceRepository.findAll().size();
        // Create the DemandeEspece
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);
        restDemandeEspeceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeEspece testDemandeEspece = demandeEspeceList.get(demandeEspeceList.size() - 1);
        assertThat(testDemandeEspece.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testDemandeEspece.getMotif()).isEqualTo(DEFAULT_MOTIF);
        assertThat(testDemandeEspece.getWorkOrderId()).isEqualTo(DEFAULT_WORK_ORDER_ID);
        assertThat(testDemandeEspece.getBeneficiaireId()).isEqualTo(DEFAULT_BENEFICIAIRE_ID);
        assertThat(testDemandeEspece.getBeneficiaireUserLogin()).isEqualTo(DEFAULT_BENEFICIAIRE_USER_LOGIN);
        assertThat(testDemandeEspece.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDemandeEspece.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testDemandeEspece.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDemandeEspece.getCreatedByUserLogin()).isEqualTo(DEFAULT_CREATED_BY_USER_LOGIN);
        assertThat(testDemandeEspece.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDemandeEspece.getUpdatedByUserLogin()).isEqualTo(DEFAULT_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void createDemandeEspeceWithExistingId() throws Exception {
        // Create the DemandeEspece with an existing ID
        demandeEspece.setId(1L);
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);

        int databaseSizeBeforeCreate = demandeEspeceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeEspeceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeEspeceRepository.findAll().size();
        // set the field null
        demandeEspece.setMontant(null);

        // Create the DemandeEspece, which fails.
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);

        restDemandeEspeceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isBadRequest());

        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMotifIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeEspeceRepository.findAll().size();
        // set the field null
        demandeEspece.setMotif(null);

        // Create the DemandeEspece, which fails.
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);

        restDemandeEspeceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isBadRequest());

        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandeEspeces() throws Exception {
        // Initialize the database
        demandeEspeceRepository.saveAndFlush(demandeEspece);

        // Get all the demandeEspeceList
        restDemandeEspeceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeEspece.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF)))
            .andExpect(jsonPath("$.[*].workOrderId").value(hasItem(DEFAULT_WORK_ORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].beneficiaireId").value(hasItem(DEFAULT_BENEFICIAIRE_ID)))
            .andExpect(jsonPath("$.[*].beneficiaireUserLogin").value(hasItem(DEFAULT_BENEFICIAIRE_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdByUserLogin").value(hasItem(DEFAULT_CREATED_BY_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedByUserLogin").value(hasItem(DEFAULT_UPDATED_BY_USER_LOGIN)));
    }

    @Test
    @Transactional
    void getDemandeEspece() throws Exception {
        // Initialize the database
        demandeEspeceRepository.saveAndFlush(demandeEspece);

        // Get the demandeEspece
        restDemandeEspeceMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeEspece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeEspece.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.motif").value(DEFAULT_MOTIF))
            .andExpect(jsonPath("$.workOrderId").value(DEFAULT_WORK_ORDER_ID.intValue()))
            .andExpect(jsonPath("$.beneficiaireId").value(DEFAULT_BENEFICIAIRE_ID))
            .andExpect(jsonPath("$.beneficiaireUserLogin").value(DEFAULT_BENEFICIAIRE_USER_LOGIN))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdByUserLogin").value(DEFAULT_CREATED_BY_USER_LOGIN))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedByUserLogin").value(DEFAULT_UPDATED_BY_USER_LOGIN));
    }

    @Test
    @Transactional
    void getNonExistingDemandeEspece() throws Exception {
        // Get the demandeEspece
        restDemandeEspeceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemandeEspece() throws Exception {
        // Initialize the database
        demandeEspeceRepository.saveAndFlush(demandeEspece);

        int databaseSizeBeforeUpdate = demandeEspeceRepository.findAll().size();

        // Update the demandeEspece
        DemandeEspece updatedDemandeEspece = demandeEspeceRepository.findById(demandeEspece.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeEspece are not directly saved in db
        em.detach(updatedDemandeEspece);
        updatedDemandeEspece
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .beneficiaireId(UPDATED_BENEFICIAIRE_ID)
            .beneficiaireUserLogin(UPDATED_BENEFICIAIRE_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(updatedDemandeEspece);

        restDemandeEspeceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeEspeceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeUpdate);
        DemandeEspece testDemandeEspece = demandeEspeceList.get(demandeEspeceList.size() - 1);
        assertThat(testDemandeEspece.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDemandeEspece.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testDemandeEspece.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDemandeEspece.getBeneficiaireId()).isEqualTo(UPDATED_BENEFICIAIRE_ID);
        assertThat(testDemandeEspece.getBeneficiaireUserLogin()).isEqualTo(UPDATED_BENEFICIAIRE_USER_LOGIN);
        assertThat(testDemandeEspece.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDemandeEspece.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testDemandeEspece.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDemandeEspece.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDemandeEspece.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDemandeEspece.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void putNonExistingDemandeEspece() throws Exception {
        int databaseSizeBeforeUpdate = demandeEspeceRepository.findAll().size();
        demandeEspece.setId(count.incrementAndGet());

        // Create the DemandeEspece
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeEspeceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeEspeceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeEspece() throws Exception {
        int databaseSizeBeforeUpdate = demandeEspeceRepository.findAll().size();
        demandeEspece.setId(count.incrementAndGet());

        // Create the DemandeEspece
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeEspeceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeEspece() throws Exception {
        int databaseSizeBeforeUpdate = demandeEspeceRepository.findAll().size();
        demandeEspece.setId(count.incrementAndGet());

        // Create the DemandeEspece
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeEspeceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeEspeceWithPatch() throws Exception {
        // Initialize the database
        demandeEspeceRepository.saveAndFlush(demandeEspece);

        int databaseSizeBeforeUpdate = demandeEspeceRepository.findAll().size();

        // Update the demandeEspece using partial update
        DemandeEspece partialUpdatedDemandeEspece = new DemandeEspece();
        partialUpdatedDemandeEspece.setId(demandeEspece.getId());

        partialUpdatedDemandeEspece
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .beneficiaireId(UPDATED_BENEFICIAIRE_ID)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restDemandeEspeceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeEspece.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeEspece))
            )
            .andExpect(status().isOk());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeUpdate);
        DemandeEspece testDemandeEspece = demandeEspeceList.get(demandeEspeceList.size() - 1);
        assertThat(testDemandeEspece.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDemandeEspece.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testDemandeEspece.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDemandeEspece.getBeneficiaireId()).isEqualTo(UPDATED_BENEFICIAIRE_ID);
        assertThat(testDemandeEspece.getBeneficiaireUserLogin()).isEqualTo(DEFAULT_BENEFICIAIRE_USER_LOGIN);
        assertThat(testDemandeEspece.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDemandeEspece.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testDemandeEspece.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDemandeEspece.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDemandeEspece.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDemandeEspece.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void fullUpdateDemandeEspeceWithPatch() throws Exception {
        // Initialize the database
        demandeEspeceRepository.saveAndFlush(demandeEspece);

        int databaseSizeBeforeUpdate = demandeEspeceRepository.findAll().size();

        // Update the demandeEspece using partial update
        DemandeEspece partialUpdatedDemandeEspece = new DemandeEspece();
        partialUpdatedDemandeEspece.setId(demandeEspece.getId());

        partialUpdatedDemandeEspece
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .beneficiaireId(UPDATED_BENEFICIAIRE_ID)
            .beneficiaireUserLogin(UPDATED_BENEFICIAIRE_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restDemandeEspeceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeEspece.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeEspece))
            )
            .andExpect(status().isOk());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeUpdate);
        DemandeEspece testDemandeEspece = demandeEspeceList.get(demandeEspeceList.size() - 1);
        assertThat(testDemandeEspece.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDemandeEspece.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testDemandeEspece.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDemandeEspece.getBeneficiaireId()).isEqualTo(UPDATED_BENEFICIAIRE_ID);
        assertThat(testDemandeEspece.getBeneficiaireUserLogin()).isEqualTo(UPDATED_BENEFICIAIRE_USER_LOGIN);
        assertThat(testDemandeEspece.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDemandeEspece.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testDemandeEspece.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDemandeEspece.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDemandeEspece.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDemandeEspece.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeEspece() throws Exception {
        int databaseSizeBeforeUpdate = demandeEspeceRepository.findAll().size();
        demandeEspece.setId(count.incrementAndGet());

        // Create the DemandeEspece
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeEspeceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeEspeceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeEspece() throws Exception {
        int databaseSizeBeforeUpdate = demandeEspeceRepository.findAll().size();
        demandeEspece.setId(count.incrementAndGet());

        // Create the DemandeEspece
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeEspeceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeEspece() throws Exception {
        int databaseSizeBeforeUpdate = demandeEspeceRepository.findAll().size();
        demandeEspece.setId(count.incrementAndGet());

        // Create the DemandeEspece
        DemandeEspeceDTO demandeEspeceDTO = demandeEspeceMapper.toDto(demandeEspece);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeEspeceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeEspeceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeEspece in the database
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeEspece() throws Exception {
        // Initialize the database
        demandeEspeceRepository.saveAndFlush(demandeEspece);

        int databaseSizeBeforeDelete = demandeEspeceRepository.findAll().size();

        // Delete the demandeEspece
        restDemandeEspeceMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeEspece.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeEspece> demandeEspeceList = demandeEspeceRepository.findAll();
        assertThat(demandeEspeceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.DepenseDiverse;
import com.gpm.finance.repository.DepenseDiverseRepository;
import com.gpm.finance.service.dto.DepenseDiverseDTO;
import com.gpm.finance.service.mapper.DepenseDiverseMapper;
import java.time.Instant;
import java.time.LocalDate;
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
 * Integration tests for the {@link DepenseDiverseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepenseDiverseResourceIT {

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final Float DEFAULT_MONTANT = 1F;
    private static final Float UPDATED_MONTANT = 2F;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JUSTIFICATIF = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIF = "BBBBBBBBBB";

    private static final Long DEFAULT_WORK_ORDER_ID = 1L;
    private static final Long UPDATED_WORK_ORDER_ID = 2L;

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

    private static final String ENTITY_API_URL = "/api/depense-diverses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DepenseDiverseRepository depenseDiverseRepository;

    @Autowired
    private DepenseDiverseMapper depenseDiverseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepenseDiverseMockMvc;

    private DepenseDiverse depenseDiverse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepenseDiverse createEntity(EntityManager em) {
        DepenseDiverse depenseDiverse = new DepenseDiverse()
            .motif(DEFAULT_MOTIF)
            .montant(DEFAULT_MONTANT)
            .date(DEFAULT_DATE)
            .justificatif(DEFAULT_JUSTIFICATIF)
            .workOrderId(DEFAULT_WORK_ORDER_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdByUserLogin(DEFAULT_CREATED_BY_USER_LOGIN)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedByUserLogin(DEFAULT_UPDATED_BY_USER_LOGIN);
        return depenseDiverse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepenseDiverse createUpdatedEntity(EntityManager em) {
        DepenseDiverse depenseDiverse = new DepenseDiverse()
            .motif(UPDATED_MOTIF)
            .montant(UPDATED_MONTANT)
            .date(UPDATED_DATE)
            .justificatif(UPDATED_JUSTIFICATIF)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        return depenseDiverse;
    }

    @BeforeEach
    public void initTest() {
        depenseDiverse = createEntity(em);
    }

    @Test
    @Transactional
    void createDepenseDiverse() throws Exception {
        int databaseSizeBeforeCreate = depenseDiverseRepository.findAll().size();
        // Create the DepenseDiverse
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);
        restDepenseDiverseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeCreate + 1);
        DepenseDiverse testDepenseDiverse = depenseDiverseList.get(depenseDiverseList.size() - 1);
        assertThat(testDepenseDiverse.getMotif()).isEqualTo(DEFAULT_MOTIF);
        assertThat(testDepenseDiverse.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testDepenseDiverse.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDepenseDiverse.getJustificatif()).isEqualTo(DEFAULT_JUSTIFICATIF);
        assertThat(testDepenseDiverse.getWorkOrderId()).isEqualTo(DEFAULT_WORK_ORDER_ID);
        assertThat(testDepenseDiverse.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDepenseDiverse.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testDepenseDiverse.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDepenseDiverse.getCreatedByUserLogin()).isEqualTo(DEFAULT_CREATED_BY_USER_LOGIN);
        assertThat(testDepenseDiverse.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDepenseDiverse.getUpdatedByUserLogin()).isEqualTo(DEFAULT_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void createDepenseDiverseWithExistingId() throws Exception {
        // Create the DepenseDiverse with an existing ID
        depenseDiverse.setId(1L);
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);

        int databaseSizeBeforeCreate = depenseDiverseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepenseDiverseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMotifIsRequired() throws Exception {
        int databaseSizeBeforeTest = depenseDiverseRepository.findAll().size();
        // set the field null
        depenseDiverse.setMotif(null);

        // Create the DepenseDiverse, which fails.
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);

        restDepenseDiverseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isBadRequest());

        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = depenseDiverseRepository.findAll().size();
        // set the field null
        depenseDiverse.setMontant(null);

        // Create the DepenseDiverse, which fails.
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);

        restDepenseDiverseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isBadRequest());

        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDepenseDiverses() throws Exception {
        // Initialize the database
        depenseDiverseRepository.saveAndFlush(depenseDiverse);

        // Get all the depenseDiverseList
        restDepenseDiverseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depenseDiverse.getId().intValue())))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].justificatif").value(hasItem(DEFAULT_JUSTIFICATIF)))
            .andExpect(jsonPath("$.[*].workOrderId").value(hasItem(DEFAULT_WORK_ORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdByUserLogin").value(hasItem(DEFAULT_CREATED_BY_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedByUserLogin").value(hasItem(DEFAULT_UPDATED_BY_USER_LOGIN)));
    }

    @Test
    @Transactional
    void getDepenseDiverse() throws Exception {
        // Initialize the database
        depenseDiverseRepository.saveAndFlush(depenseDiverse);

        // Get the depenseDiverse
        restDepenseDiverseMockMvc
            .perform(get(ENTITY_API_URL_ID, depenseDiverse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(depenseDiverse.getId().intValue()))
            .andExpect(jsonPath("$.motif").value(DEFAULT_MOTIF))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.justificatif").value(DEFAULT_JUSTIFICATIF))
            .andExpect(jsonPath("$.workOrderId").value(DEFAULT_WORK_ORDER_ID.intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdByUserLogin").value(DEFAULT_CREATED_BY_USER_LOGIN))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedByUserLogin").value(DEFAULT_UPDATED_BY_USER_LOGIN));
    }

    @Test
    @Transactional
    void getNonExistingDepenseDiverse() throws Exception {
        // Get the depenseDiverse
        restDepenseDiverseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepenseDiverse() throws Exception {
        // Initialize the database
        depenseDiverseRepository.saveAndFlush(depenseDiverse);

        int databaseSizeBeforeUpdate = depenseDiverseRepository.findAll().size();

        // Update the depenseDiverse
        DepenseDiverse updatedDepenseDiverse = depenseDiverseRepository.findById(depenseDiverse.getId()).get();
        // Disconnect from session so that the updates on updatedDepenseDiverse are not directly saved in db
        em.detach(updatedDepenseDiverse);
        updatedDepenseDiverse
            .motif(UPDATED_MOTIF)
            .montant(UPDATED_MONTANT)
            .date(UPDATED_DATE)
            .justificatif(UPDATED_JUSTIFICATIF)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(updatedDepenseDiverse);

        restDepenseDiverseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, depenseDiverseDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isOk());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeUpdate);
        DepenseDiverse testDepenseDiverse = depenseDiverseList.get(depenseDiverseList.size() - 1);
        assertThat(testDepenseDiverse.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testDepenseDiverse.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDepenseDiverse.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDepenseDiverse.getJustificatif()).isEqualTo(UPDATED_JUSTIFICATIF);
        assertThat(testDepenseDiverse.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDepenseDiverse.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDepenseDiverse.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testDepenseDiverse.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDepenseDiverse.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDepenseDiverse.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDepenseDiverse.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void putNonExistingDepenseDiverse() throws Exception {
        int databaseSizeBeforeUpdate = depenseDiverseRepository.findAll().size();
        depenseDiverse.setId(count.incrementAndGet());

        // Create the DepenseDiverse
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepenseDiverseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, depenseDiverseDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepenseDiverse() throws Exception {
        int databaseSizeBeforeUpdate = depenseDiverseRepository.findAll().size();
        depenseDiverse.setId(count.incrementAndGet());

        // Create the DepenseDiverse
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepenseDiverseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepenseDiverse() throws Exception {
        int databaseSizeBeforeUpdate = depenseDiverseRepository.findAll().size();
        depenseDiverse.setId(count.incrementAndGet());

        // Create the DepenseDiverse
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepenseDiverseMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepenseDiverseWithPatch() throws Exception {
        // Initialize the database
        depenseDiverseRepository.saveAndFlush(depenseDiverse);

        int databaseSizeBeforeUpdate = depenseDiverseRepository.findAll().size();

        // Update the depenseDiverse using partial update
        DepenseDiverse partialUpdatedDepenseDiverse = new DepenseDiverse();
        partialUpdatedDepenseDiverse.setId(depenseDiverse.getId());

        partialUpdatedDepenseDiverse
            .motif(UPDATED_MOTIF)
            .justificatif(UPDATED_JUSTIFICATIF)
            .createdBy(UPDATED_CREATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restDepenseDiverseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepenseDiverse.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepenseDiverse))
            )
            .andExpect(status().isOk());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeUpdate);
        DepenseDiverse testDepenseDiverse = depenseDiverseList.get(depenseDiverseList.size() - 1);
        assertThat(testDepenseDiverse.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testDepenseDiverse.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testDepenseDiverse.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDepenseDiverse.getJustificatif()).isEqualTo(UPDATED_JUSTIFICATIF);
        assertThat(testDepenseDiverse.getWorkOrderId()).isEqualTo(DEFAULT_WORK_ORDER_ID);
        assertThat(testDepenseDiverse.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDepenseDiverse.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testDepenseDiverse.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDepenseDiverse.getCreatedByUserLogin()).isEqualTo(DEFAULT_CREATED_BY_USER_LOGIN);
        assertThat(testDepenseDiverse.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDepenseDiverse.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void fullUpdateDepenseDiverseWithPatch() throws Exception {
        // Initialize the database
        depenseDiverseRepository.saveAndFlush(depenseDiverse);

        int databaseSizeBeforeUpdate = depenseDiverseRepository.findAll().size();

        // Update the depenseDiverse using partial update
        DepenseDiverse partialUpdatedDepenseDiverse = new DepenseDiverse();
        partialUpdatedDepenseDiverse.setId(depenseDiverse.getId());

        partialUpdatedDepenseDiverse
            .motif(UPDATED_MOTIF)
            .montant(UPDATED_MONTANT)
            .date(UPDATED_DATE)
            .justificatif(UPDATED_JUSTIFICATIF)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restDepenseDiverseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepenseDiverse.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepenseDiverse))
            )
            .andExpect(status().isOk());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeUpdate);
        DepenseDiverse testDepenseDiverse = depenseDiverseList.get(depenseDiverseList.size() - 1);
        assertThat(testDepenseDiverse.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testDepenseDiverse.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDepenseDiverse.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDepenseDiverse.getJustificatif()).isEqualTo(UPDATED_JUSTIFICATIF);
        assertThat(testDepenseDiverse.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDepenseDiverse.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDepenseDiverse.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testDepenseDiverse.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDepenseDiverse.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDepenseDiverse.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDepenseDiverse.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void patchNonExistingDepenseDiverse() throws Exception {
        int databaseSizeBeforeUpdate = depenseDiverseRepository.findAll().size();
        depenseDiverse.setId(count.incrementAndGet());

        // Create the DepenseDiverse
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepenseDiverseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, depenseDiverseDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepenseDiverse() throws Exception {
        int databaseSizeBeforeUpdate = depenseDiverseRepository.findAll().size();
        depenseDiverse.setId(count.incrementAndGet());

        // Create the DepenseDiverse
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepenseDiverseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepenseDiverse() throws Exception {
        int databaseSizeBeforeUpdate = depenseDiverseRepository.findAll().size();
        depenseDiverse.setId(count.incrementAndGet());

        // Create the DepenseDiverse
        DepenseDiverseDTO depenseDiverseDTO = depenseDiverseMapper.toDto(depenseDiverse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepenseDiverseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(depenseDiverseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepenseDiverse in the database
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepenseDiverse() throws Exception {
        // Initialize the database
        depenseDiverseRepository.saveAndFlush(depenseDiverse);

        int databaseSizeBeforeDelete = depenseDiverseRepository.findAll().size();

        // Delete the depenseDiverse
        restDepenseDiverseMockMvc
            .perform(delete(ENTITY_API_URL_ID, depenseDiverse.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepenseDiverse> depenseDiverseList = depenseDiverseRepository.findAll();
        assertThat(depenseDiverseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

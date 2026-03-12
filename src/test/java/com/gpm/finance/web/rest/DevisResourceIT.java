package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.Devis;
import com.gpm.finance.domain.enumeration.StatutDevis;
import com.gpm.finance.repository.DevisRepository;
import com.gpm.finance.service.DevisService;
import com.gpm.finance.service.dto.DevisDTO;
import com.gpm.finance.service.mapper.DevisMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DevisResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DevisResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Float DEFAULT_MONTANT = 1F;
    private static final Float UPDATED_MONTANT = 2F;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final StatutDevis DEFAULT_STATUT = StatutDevis.Creation;
    private static final StatutDevis UPDATED_STATUT = StatutDevis.ExecutionDesTravaux;

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

    private static final String ENTITY_API_URL = "/api/devis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DevisRepository devisRepository;

    @Mock
    private DevisRepository devisRepositoryMock;

    @Autowired
    private DevisMapper devisMapper;

    @Mock
    private DevisService devisServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDevisMockMvc;

    private Devis devis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devis createEntity(EntityManager em) {
        Devis devis = new Devis()
            .reference(DEFAULT_REFERENCE)
            .montant(DEFAULT_MONTANT)
            .date(DEFAULT_DATE)
            .statut(DEFAULT_STATUT)
            .workOrderId(DEFAULT_WORK_ORDER_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdByUserLogin(DEFAULT_CREATED_BY_USER_LOGIN)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedByUserLogin(DEFAULT_UPDATED_BY_USER_LOGIN);
        return devis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devis createUpdatedEntity(EntityManager em) {
        Devis devis = new Devis()
            .reference(UPDATED_REFERENCE)
            .montant(UPDATED_MONTANT)
            .date(UPDATED_DATE)
            .statut(UPDATED_STATUT)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        return devis;
    }

    @BeforeEach
    public void initTest() {
        devis = createEntity(em);
    }

    @Test
    @Transactional
    void createDevis() throws Exception {
        int databaseSizeBeforeCreate = devisRepository.findAll().size();
        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);
        restDevisMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeCreate + 1);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testDevis.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testDevis.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDevis.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDevis.getWorkOrderId()).isEqualTo(DEFAULT_WORK_ORDER_ID);
        assertThat(testDevis.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDevis.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testDevis.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDevis.getCreatedByUserLogin()).isEqualTo(DEFAULT_CREATED_BY_USER_LOGIN);
        assertThat(testDevis.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDevis.getUpdatedByUserLogin()).isEqualTo(DEFAULT_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void createDevisWithExistingId() throws Exception {
        // Create the Devis with an existing ID
        devis.setId(1L);
        DevisDTO devisDTO = devisMapper.toDto(devis);

        int databaseSizeBeforeCreate = devisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevisMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setReference(null);

        // Create the Devis, which fails.
        DevisDTO devisDTO = devisMapper.toDto(devis);

        restDevisMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setMontant(null);

        // Create the Devis, which fails.
        DevisDTO devisDTO = devisMapper.toDto(devis);

        restDevisMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setDate(null);

        // Create the Devis, which fails.
        DevisDTO devisDTO = devisMapper.toDto(devis);

        restDevisMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setStatut(null);

        // Create the Devis, which fails.
        DevisDTO devisDTO = devisMapper.toDto(devis);

        restDevisMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList
        restDevisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devis.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].workOrderId").value(hasItem(DEFAULT_WORK_ORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdByUserLogin").value(hasItem(DEFAULT_CREATED_BY_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedByUserLogin").value(hasItem(DEFAULT_UPDATED_BY_USER_LOGIN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDevisWithEagerRelationshipsIsEnabled() throws Exception {
        when(devisServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDevisMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(devisServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDevisWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(devisServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDevisMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(devisRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get the devis
        restDevisMockMvc
            .perform(get(ENTITY_API_URL_ID, devis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(devis.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
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
    void getNonExistingDevis() throws Exception {
        // Get the devis
        restDevisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Update the devis
        Devis updatedDevis = devisRepository.findById(devis.getId()).get();
        // Disconnect from session so that the updates on updatedDevis are not directly saved in db
        em.detach(updatedDevis);
        updatedDevis
            .reference(UPDATED_REFERENCE)
            .montant(UPDATED_MONTANT)
            .date(UPDATED_DATE)
            .statut(UPDATED_STATUT)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        DevisDTO devisDTO = devisMapper.toDto(updatedDevis);

        restDevisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, devisDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isOk());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testDevis.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDevis.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDevis.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDevis.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDevis.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDevis.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testDevis.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDevis.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDevis.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDevis.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void putNonExistingDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, devisDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDevisWithPatch() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Update the devis using partial update
        Devis partialUpdatedDevis = new Devis();
        partialUpdatedDevis.setId(devis.getId());

        partialUpdatedDevis
            .reference(UPDATED_REFERENCE)
            .montant(UPDATED_MONTANT)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevis.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevis))
            )
            .andExpect(status().isOk());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testDevis.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDevis.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDevis.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDevis.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDevis.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDevis.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testDevis.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDevis.getCreatedByUserLogin()).isEqualTo(DEFAULT_CREATED_BY_USER_LOGIN);
        assertThat(testDevis.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDevis.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void fullUpdateDevisWithPatch() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Update the devis using partial update
        Devis partialUpdatedDevis = new Devis();
        partialUpdatedDevis.setId(devis.getId());

        partialUpdatedDevis
            .reference(UPDATED_REFERENCE)
            .montant(UPDATED_MONTANT)
            .date(UPDATED_DATE)
            .statut(UPDATED_STATUT)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevis.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevis))
            )
            .andExpect(status().isOk());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testDevis.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDevis.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDevis.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDevis.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDevis.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDevis.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testDevis.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDevis.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDevis.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDevis.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void patchNonExistingDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, devisDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devisDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeDelete = devisRepository.findAll().size();

        // Delete the devis
        restDevisMockMvc
            .perform(delete(ENTITY_API_URL_ID, devis.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

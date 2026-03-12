package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.FraisDeMission;
import com.gpm.finance.domain.enumeration.StatutFraisDeMission;
import com.gpm.finance.repository.FraisDeMissionRepository;
import com.gpm.finance.service.dto.FraisDeMissionDTO;
import com.gpm.finance.service.mapper.FraisDeMissionMapper;
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
 * Integration tests for the {@link FraisDeMissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FraisDeMissionResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_MONTANT_TOTAL = 1F;
    private static final Float UPDATED_MONTANT_TOTAL = 2F;

    private static final Float DEFAULT_AVANCE_RECUE = 1F;
    private static final Float UPDATED_AVANCE_RECUE = 2F;

    private static final Float DEFAULT_NET_A_PAYER = 1F;
    private static final Float UPDATED_NET_A_PAYER = 2F;

    private static final Float DEFAULT_NOTE_RENDEMENT = 1F;
    private static final Float UPDATED_NOTE_RENDEMENT = 2F;

    private static final Float DEFAULT_NOTE_QUALITE = 1F;
    private static final Float UPDATED_NOTE_QUALITE = 2F;

    private static final Float DEFAULT_NOTE_CONDUITE = 1F;
    private static final Float UPDATED_NOTE_CONDUITE = 2F;

    private static final Float DEFAULT_NOTE_TOTALE = 1F;
    private static final Float UPDATED_NOTE_TOTALE = 2F;

    private static final Float DEFAULT_BONUS_EXTRA = 1F;
    private static final Float UPDATED_BONUS_EXTRA = 2F;

    private static final String DEFAULT_JUSTIFICATIF_BONUS = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIF_BONUS = "BBBBBBBBBB";

    private static final String DEFAULT_JUSTIFICATIF_MODIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIF_MODIFICATION = "BBBBBBBBBB";

    private static final StatutFraisDeMission DEFAULT_STATUT = StatutFraisDeMission.Creation;
    private static final StatutFraisDeMission UPDATED_STATUT = StatutFraisDeMission.Verification;

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

    private static final String ENTITY_API_URL = "/api/frais-de-missions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FraisDeMissionRepository fraisDeMissionRepository;

    @Autowired
    private FraisDeMissionMapper fraisDeMissionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFraisDeMissionMockMvc;

    private FraisDeMission fraisDeMission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FraisDeMission createEntity(EntityManager em) {
        FraisDeMission fraisDeMission = new FraisDeMission()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .montantTotal(DEFAULT_MONTANT_TOTAL)
            .avanceRecue(DEFAULT_AVANCE_RECUE)
            .netAPayer(DEFAULT_NET_A_PAYER)
            .noteRendement(DEFAULT_NOTE_RENDEMENT)
            .noteQualite(DEFAULT_NOTE_QUALITE)
            .noteConduite(DEFAULT_NOTE_CONDUITE)
            .noteTotale(DEFAULT_NOTE_TOTALE)
            .bonusExtra(DEFAULT_BONUS_EXTRA)
            .justificatifBonus(DEFAULT_JUSTIFICATIF_BONUS)
            .justificatifModification(DEFAULT_JUSTIFICATIF_MODIFICATION)
            .statut(DEFAULT_STATUT)
            .workOrderId(DEFAULT_WORK_ORDER_ID)
            .beneficiaireId(DEFAULT_BENEFICIAIRE_ID)
            .beneficiaireUserLogin(DEFAULT_BENEFICIAIRE_USER_LOGIN)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdByUserLogin(DEFAULT_CREATED_BY_USER_LOGIN)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedByUserLogin(DEFAULT_UPDATED_BY_USER_LOGIN);
        return fraisDeMission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FraisDeMission createUpdatedEntity(EntityManager em) {
        FraisDeMission fraisDeMission = new FraisDeMission()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .avanceRecue(UPDATED_AVANCE_RECUE)
            .netAPayer(UPDATED_NET_A_PAYER)
            .noteRendement(UPDATED_NOTE_RENDEMENT)
            .noteQualite(UPDATED_NOTE_QUALITE)
            .noteConduite(UPDATED_NOTE_CONDUITE)
            .noteTotale(UPDATED_NOTE_TOTALE)
            .bonusExtra(UPDATED_BONUS_EXTRA)
            .justificatifBonus(UPDATED_JUSTIFICATIF_BONUS)
            .justificatifModification(UPDATED_JUSTIFICATIF_MODIFICATION)
            .statut(UPDATED_STATUT)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .beneficiaireId(UPDATED_BENEFICIAIRE_ID)
            .beneficiaireUserLogin(UPDATED_BENEFICIAIRE_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        return fraisDeMission;
    }

    @BeforeEach
    public void initTest() {
        fraisDeMission = createEntity(em);
    }

    @Test
    @Transactional
    void createFraisDeMission() throws Exception {
        int databaseSizeBeforeCreate = fraisDeMissionRepository.findAll().size();
        // Create the FraisDeMission
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);
        restFraisDeMissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeCreate + 1);
        FraisDeMission testFraisDeMission = fraisDeMissionList.get(fraisDeMissionList.size() - 1);
        assertThat(testFraisDeMission.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testFraisDeMission.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testFraisDeMission.getMontantTotal()).isEqualTo(DEFAULT_MONTANT_TOTAL);
        assertThat(testFraisDeMission.getAvanceRecue()).isEqualTo(DEFAULT_AVANCE_RECUE);
        assertThat(testFraisDeMission.getNetAPayer()).isEqualTo(DEFAULT_NET_A_PAYER);
        assertThat(testFraisDeMission.getNoteRendement()).isEqualTo(DEFAULT_NOTE_RENDEMENT);
        assertThat(testFraisDeMission.getNoteQualite()).isEqualTo(DEFAULT_NOTE_QUALITE);
        assertThat(testFraisDeMission.getNoteConduite()).isEqualTo(DEFAULT_NOTE_CONDUITE);
        assertThat(testFraisDeMission.getNoteTotale()).isEqualTo(DEFAULT_NOTE_TOTALE);
        assertThat(testFraisDeMission.getBonusExtra()).isEqualTo(DEFAULT_BONUS_EXTRA);
        assertThat(testFraisDeMission.getJustificatifBonus()).isEqualTo(DEFAULT_JUSTIFICATIF_BONUS);
        assertThat(testFraisDeMission.getJustificatifModification()).isEqualTo(DEFAULT_JUSTIFICATIF_MODIFICATION);
        assertThat(testFraisDeMission.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testFraisDeMission.getWorkOrderId()).isEqualTo(DEFAULT_WORK_ORDER_ID);
        assertThat(testFraisDeMission.getBeneficiaireId()).isEqualTo(DEFAULT_BENEFICIAIRE_ID);
        assertThat(testFraisDeMission.getBeneficiaireUserLogin()).isEqualTo(DEFAULT_BENEFICIAIRE_USER_LOGIN);
        assertThat(testFraisDeMission.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFraisDeMission.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testFraisDeMission.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFraisDeMission.getCreatedByUserLogin()).isEqualTo(DEFAULT_CREATED_BY_USER_LOGIN);
        assertThat(testFraisDeMission.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFraisDeMission.getUpdatedByUserLogin()).isEqualTo(DEFAULT_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void createFraisDeMissionWithExistingId() throws Exception {
        // Create the FraisDeMission with an existing ID
        fraisDeMission.setId(1L);
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        int databaseSizeBeforeCreate = fraisDeMissionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraisDeMissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraisDeMissionRepository.findAll().size();
        // set the field null
        fraisDeMission.setDateDebut(null);

        // Create the FraisDeMission, which fails.
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        restFraisDeMissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraisDeMissionRepository.findAll().size();
        // set the field null
        fraisDeMission.setDateFin(null);

        // Create the FraisDeMission, which fails.
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        restFraisDeMissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraisDeMissionRepository.findAll().size();
        // set the field null
        fraisDeMission.setMontantTotal(null);

        // Create the FraisDeMission, which fails.
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        restFraisDeMissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNetAPayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraisDeMissionRepository.findAll().size();
        // set the field null
        fraisDeMission.setNetAPayer(null);

        // Create the FraisDeMission, which fails.
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        restFraisDeMissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraisDeMissionRepository.findAll().size();
        // set the field null
        fraisDeMission.setStatut(null);

        // Create the FraisDeMission, which fails.
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        restFraisDeMissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFraisDeMissions() throws Exception {
        // Initialize the database
        fraisDeMissionRepository.saveAndFlush(fraisDeMission);

        // Get all the fraisDeMissionList
        restFraisDeMissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraisDeMission.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].montantTotal").value(hasItem(DEFAULT_MONTANT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].avanceRecue").value(hasItem(DEFAULT_AVANCE_RECUE.doubleValue())))
            .andExpect(jsonPath("$.[*].netAPayer").value(hasItem(DEFAULT_NET_A_PAYER.doubleValue())))
            .andExpect(jsonPath("$.[*].noteRendement").value(hasItem(DEFAULT_NOTE_RENDEMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].noteQualite").value(hasItem(DEFAULT_NOTE_QUALITE.doubleValue())))
            .andExpect(jsonPath("$.[*].noteConduite").value(hasItem(DEFAULT_NOTE_CONDUITE.doubleValue())))
            .andExpect(jsonPath("$.[*].noteTotale").value(hasItem(DEFAULT_NOTE_TOTALE.doubleValue())))
            .andExpect(jsonPath("$.[*].bonusExtra").value(hasItem(DEFAULT_BONUS_EXTRA.doubleValue())))
            .andExpect(jsonPath("$.[*].justificatifBonus").value(hasItem(DEFAULT_JUSTIFICATIF_BONUS)))
            .andExpect(jsonPath("$.[*].justificatifModification").value(hasItem(DEFAULT_JUSTIFICATIF_MODIFICATION)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
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
    void getFraisDeMission() throws Exception {
        // Initialize the database
        fraisDeMissionRepository.saveAndFlush(fraisDeMission);

        // Get the fraisDeMission
        restFraisDeMissionMockMvc
            .perform(get(ENTITY_API_URL_ID, fraisDeMission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fraisDeMission.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.montantTotal").value(DEFAULT_MONTANT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.avanceRecue").value(DEFAULT_AVANCE_RECUE.doubleValue()))
            .andExpect(jsonPath("$.netAPayer").value(DEFAULT_NET_A_PAYER.doubleValue()))
            .andExpect(jsonPath("$.noteRendement").value(DEFAULT_NOTE_RENDEMENT.doubleValue()))
            .andExpect(jsonPath("$.noteQualite").value(DEFAULT_NOTE_QUALITE.doubleValue()))
            .andExpect(jsonPath("$.noteConduite").value(DEFAULT_NOTE_CONDUITE.doubleValue()))
            .andExpect(jsonPath("$.noteTotale").value(DEFAULT_NOTE_TOTALE.doubleValue()))
            .andExpect(jsonPath("$.bonusExtra").value(DEFAULT_BONUS_EXTRA.doubleValue()))
            .andExpect(jsonPath("$.justificatifBonus").value(DEFAULT_JUSTIFICATIF_BONUS))
            .andExpect(jsonPath("$.justificatifModification").value(DEFAULT_JUSTIFICATIF_MODIFICATION))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
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
    void getNonExistingFraisDeMission() throws Exception {
        // Get the fraisDeMission
        restFraisDeMissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFraisDeMission() throws Exception {
        // Initialize the database
        fraisDeMissionRepository.saveAndFlush(fraisDeMission);

        int databaseSizeBeforeUpdate = fraisDeMissionRepository.findAll().size();

        // Update the fraisDeMission
        FraisDeMission updatedFraisDeMission = fraisDeMissionRepository.findById(fraisDeMission.getId()).get();
        // Disconnect from session so that the updates on updatedFraisDeMission are not directly saved in db
        em.detach(updatedFraisDeMission);
        updatedFraisDeMission
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .avanceRecue(UPDATED_AVANCE_RECUE)
            .netAPayer(UPDATED_NET_A_PAYER)
            .noteRendement(UPDATED_NOTE_RENDEMENT)
            .noteQualite(UPDATED_NOTE_QUALITE)
            .noteConduite(UPDATED_NOTE_CONDUITE)
            .noteTotale(UPDATED_NOTE_TOTALE)
            .bonusExtra(UPDATED_BONUS_EXTRA)
            .justificatifBonus(UPDATED_JUSTIFICATIF_BONUS)
            .justificatifModification(UPDATED_JUSTIFICATIF_MODIFICATION)
            .statut(UPDATED_STATUT)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .beneficiaireId(UPDATED_BENEFICIAIRE_ID)
            .beneficiaireUserLogin(UPDATED_BENEFICIAIRE_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(updatedFraisDeMission);

        restFraisDeMissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fraisDeMissionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isOk());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeUpdate);
        FraisDeMission testFraisDeMission = fraisDeMissionList.get(fraisDeMissionList.size() - 1);
        assertThat(testFraisDeMission.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testFraisDeMission.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testFraisDeMission.getMontantTotal()).isEqualTo(UPDATED_MONTANT_TOTAL);
        assertThat(testFraisDeMission.getAvanceRecue()).isEqualTo(UPDATED_AVANCE_RECUE);
        assertThat(testFraisDeMission.getNetAPayer()).isEqualTo(UPDATED_NET_A_PAYER);
        assertThat(testFraisDeMission.getNoteRendement()).isEqualTo(UPDATED_NOTE_RENDEMENT);
        assertThat(testFraisDeMission.getNoteQualite()).isEqualTo(UPDATED_NOTE_QUALITE);
        assertThat(testFraisDeMission.getNoteConduite()).isEqualTo(UPDATED_NOTE_CONDUITE);
        assertThat(testFraisDeMission.getNoteTotale()).isEqualTo(UPDATED_NOTE_TOTALE);
        assertThat(testFraisDeMission.getBonusExtra()).isEqualTo(UPDATED_BONUS_EXTRA);
        assertThat(testFraisDeMission.getJustificatifBonus()).isEqualTo(UPDATED_JUSTIFICATIF_BONUS);
        assertThat(testFraisDeMission.getJustificatifModification()).isEqualTo(UPDATED_JUSTIFICATIF_MODIFICATION);
        assertThat(testFraisDeMission.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testFraisDeMission.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testFraisDeMission.getBeneficiaireId()).isEqualTo(UPDATED_BENEFICIAIRE_ID);
        assertThat(testFraisDeMission.getBeneficiaireUserLogin()).isEqualTo(UPDATED_BENEFICIAIRE_USER_LOGIN);
        assertThat(testFraisDeMission.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFraisDeMission.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testFraisDeMission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFraisDeMission.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testFraisDeMission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFraisDeMission.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void putNonExistingFraisDeMission() throws Exception {
        int databaseSizeBeforeUpdate = fraisDeMissionRepository.findAll().size();
        fraisDeMission.setId(count.incrementAndGet());

        // Create the FraisDeMission
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraisDeMissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fraisDeMissionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFraisDeMission() throws Exception {
        int databaseSizeBeforeUpdate = fraisDeMissionRepository.findAll().size();
        fraisDeMission.setId(count.incrementAndGet());

        // Create the FraisDeMission
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraisDeMissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFraisDeMission() throws Exception {
        int databaseSizeBeforeUpdate = fraisDeMissionRepository.findAll().size();
        fraisDeMission.setId(count.incrementAndGet());

        // Create the FraisDeMission
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraisDeMissionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFraisDeMissionWithPatch() throws Exception {
        // Initialize the database
        fraisDeMissionRepository.saveAndFlush(fraisDeMission);

        int databaseSizeBeforeUpdate = fraisDeMissionRepository.findAll().size();

        // Update the fraisDeMission using partial update
        FraisDeMission partialUpdatedFraisDeMission = new FraisDeMission();
        partialUpdatedFraisDeMission.setId(fraisDeMission.getId());

        partialUpdatedFraisDeMission
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .netAPayer(UPDATED_NET_A_PAYER)
            .noteRendement(UPDATED_NOTE_RENDEMENT)
            .noteQualite(UPDATED_NOTE_QUALITE)
            .noteTotale(UPDATED_NOTE_TOTALE)
            .bonusExtra(UPDATED_BONUS_EXTRA)
            .justificatifModification(UPDATED_JUSTIFICATIF_MODIFICATION)
            .statut(UPDATED_STATUT)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .beneficiaireUserLogin(UPDATED_BENEFICIAIRE_USER_LOGIN)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restFraisDeMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraisDeMission.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFraisDeMission))
            )
            .andExpect(status().isOk());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeUpdate);
        FraisDeMission testFraisDeMission = fraisDeMissionList.get(fraisDeMissionList.size() - 1);
        assertThat(testFraisDeMission.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testFraisDeMission.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testFraisDeMission.getMontantTotal()).isEqualTo(DEFAULT_MONTANT_TOTAL);
        assertThat(testFraisDeMission.getAvanceRecue()).isEqualTo(DEFAULT_AVANCE_RECUE);
        assertThat(testFraisDeMission.getNetAPayer()).isEqualTo(UPDATED_NET_A_PAYER);
        assertThat(testFraisDeMission.getNoteRendement()).isEqualTo(UPDATED_NOTE_RENDEMENT);
        assertThat(testFraisDeMission.getNoteQualite()).isEqualTo(UPDATED_NOTE_QUALITE);
        assertThat(testFraisDeMission.getNoteConduite()).isEqualTo(DEFAULT_NOTE_CONDUITE);
        assertThat(testFraisDeMission.getNoteTotale()).isEqualTo(UPDATED_NOTE_TOTALE);
        assertThat(testFraisDeMission.getBonusExtra()).isEqualTo(UPDATED_BONUS_EXTRA);
        assertThat(testFraisDeMission.getJustificatifBonus()).isEqualTo(DEFAULT_JUSTIFICATIF_BONUS);
        assertThat(testFraisDeMission.getJustificatifModification()).isEqualTo(UPDATED_JUSTIFICATIF_MODIFICATION);
        assertThat(testFraisDeMission.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testFraisDeMission.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testFraisDeMission.getBeneficiaireId()).isEqualTo(DEFAULT_BENEFICIAIRE_ID);
        assertThat(testFraisDeMission.getBeneficiaireUserLogin()).isEqualTo(UPDATED_BENEFICIAIRE_USER_LOGIN);
        assertThat(testFraisDeMission.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFraisDeMission.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testFraisDeMission.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFraisDeMission.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testFraisDeMission.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFraisDeMission.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void fullUpdateFraisDeMissionWithPatch() throws Exception {
        // Initialize the database
        fraisDeMissionRepository.saveAndFlush(fraisDeMission);

        int databaseSizeBeforeUpdate = fraisDeMissionRepository.findAll().size();

        // Update the fraisDeMission using partial update
        FraisDeMission partialUpdatedFraisDeMission = new FraisDeMission();
        partialUpdatedFraisDeMission.setId(fraisDeMission.getId());

        partialUpdatedFraisDeMission
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .avanceRecue(UPDATED_AVANCE_RECUE)
            .netAPayer(UPDATED_NET_A_PAYER)
            .noteRendement(UPDATED_NOTE_RENDEMENT)
            .noteQualite(UPDATED_NOTE_QUALITE)
            .noteConduite(UPDATED_NOTE_CONDUITE)
            .noteTotale(UPDATED_NOTE_TOTALE)
            .bonusExtra(UPDATED_BONUS_EXTRA)
            .justificatifBonus(UPDATED_JUSTIFICATIF_BONUS)
            .justificatifModification(UPDATED_JUSTIFICATIF_MODIFICATION)
            .statut(UPDATED_STATUT)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .beneficiaireId(UPDATED_BENEFICIAIRE_ID)
            .beneficiaireUserLogin(UPDATED_BENEFICIAIRE_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restFraisDeMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraisDeMission.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFraisDeMission))
            )
            .andExpect(status().isOk());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeUpdate);
        FraisDeMission testFraisDeMission = fraisDeMissionList.get(fraisDeMissionList.size() - 1);
        assertThat(testFraisDeMission.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testFraisDeMission.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testFraisDeMission.getMontantTotal()).isEqualTo(UPDATED_MONTANT_TOTAL);
        assertThat(testFraisDeMission.getAvanceRecue()).isEqualTo(UPDATED_AVANCE_RECUE);
        assertThat(testFraisDeMission.getNetAPayer()).isEqualTo(UPDATED_NET_A_PAYER);
        assertThat(testFraisDeMission.getNoteRendement()).isEqualTo(UPDATED_NOTE_RENDEMENT);
        assertThat(testFraisDeMission.getNoteQualite()).isEqualTo(UPDATED_NOTE_QUALITE);
        assertThat(testFraisDeMission.getNoteConduite()).isEqualTo(UPDATED_NOTE_CONDUITE);
        assertThat(testFraisDeMission.getNoteTotale()).isEqualTo(UPDATED_NOTE_TOTALE);
        assertThat(testFraisDeMission.getBonusExtra()).isEqualTo(UPDATED_BONUS_EXTRA);
        assertThat(testFraisDeMission.getJustificatifBonus()).isEqualTo(UPDATED_JUSTIFICATIF_BONUS);
        assertThat(testFraisDeMission.getJustificatifModification()).isEqualTo(UPDATED_JUSTIFICATIF_MODIFICATION);
        assertThat(testFraisDeMission.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testFraisDeMission.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testFraisDeMission.getBeneficiaireId()).isEqualTo(UPDATED_BENEFICIAIRE_ID);
        assertThat(testFraisDeMission.getBeneficiaireUserLogin()).isEqualTo(UPDATED_BENEFICIAIRE_USER_LOGIN);
        assertThat(testFraisDeMission.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFraisDeMission.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testFraisDeMission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFraisDeMission.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testFraisDeMission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFraisDeMission.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void patchNonExistingFraisDeMission() throws Exception {
        int databaseSizeBeforeUpdate = fraisDeMissionRepository.findAll().size();
        fraisDeMission.setId(count.incrementAndGet());

        // Create the FraisDeMission
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraisDeMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fraisDeMissionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFraisDeMission() throws Exception {
        int databaseSizeBeforeUpdate = fraisDeMissionRepository.findAll().size();
        fraisDeMission.setId(count.incrementAndGet());

        // Create the FraisDeMission
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraisDeMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFraisDeMission() throws Exception {
        int databaseSizeBeforeUpdate = fraisDeMissionRepository.findAll().size();
        fraisDeMission.setId(count.incrementAndGet());

        // Create the FraisDeMission
        FraisDeMissionDTO fraisDeMissionDTO = fraisDeMissionMapper.toDto(fraisDeMission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraisDeMissionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraisDeMissionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FraisDeMission in the database
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFraisDeMission() throws Exception {
        // Initialize the database
        fraisDeMissionRepository.saveAndFlush(fraisDeMission);

        int databaseSizeBeforeDelete = fraisDeMissionRepository.findAll().size();

        // Delete the fraisDeMission
        restFraisDeMissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, fraisDeMission.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FraisDeMission> fraisDeMissionList = fraisDeMissionRepository.findAll();
        assertThat(fraisDeMissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

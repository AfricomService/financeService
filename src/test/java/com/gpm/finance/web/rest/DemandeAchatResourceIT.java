package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.DemandeAchat;
import com.gpm.finance.domain.enumeration.StatutDemandeAchat;
import com.gpm.finance.domain.enumeration.TypeDemandeAchat;
import com.gpm.finance.repository.DemandeAchatRepository;
import com.gpm.finance.service.dto.DemandeAchatDTO;
import com.gpm.finance.service.mapper.DemandeAchatMapper;
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
 * Integration tests for the {@link DemandeAchatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeAchatResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final StatutDemandeAchat DEFAULT_STATUT = StatutDemandeAchat.Soumis;
    private static final StatutDemandeAchat UPDATED_STATUT = StatutDemandeAchat.Valide;

    private static final TypeDemandeAchat DEFAULT_TYPE = TypeDemandeAchat.OffreDePrix;
    private static final TypeDemandeAchat UPDATED_TYPE = TypeDemandeAchat.Achat;

    private static final ZonedDateTime DEFAULT_DATE_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_DATE_MISE_A_DISPOSITION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MISE_A_DISPOSITION = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_WORK_ORDER_ID = 1L;
    private static final Long UPDATED_WORK_ORDER_ID = 2L;

    private static final Long DEFAULT_AFFAIRE_ID = 1L;
    private static final Long UPDATED_AFFAIRE_ID = 2L;

    private static final String DEFAULT_DEMANDEUR_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEMANDEUR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEMANDEUR_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_DEMANDEUR_USER_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATEUR_ID = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATEUR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATEUR_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATEUR_USER_LOGIN = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/demande-achats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeAchatRepository demandeAchatRepository;

    @Autowired
    private DemandeAchatMapper demandeAchatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeAchatMockMvc;

    private DemandeAchat demandeAchat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeAchat createEntity(EntityManager em) {
        DemandeAchat demandeAchat = new DemandeAchat()
            .code(DEFAULT_CODE)
            .statut(DEFAULT_STATUT)
            .type(DEFAULT_TYPE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateMiseADisposition(DEFAULT_DATE_MISE_A_DISPOSITION)
            .workOrderId(DEFAULT_WORK_ORDER_ID)
            .affaireId(DEFAULT_AFFAIRE_ID)
            .demandeurId(DEFAULT_DEMANDEUR_ID)
            .demandeurUserLogin(DEFAULT_DEMANDEUR_USER_LOGIN)
            .validateurId(DEFAULT_VALIDATEUR_ID)
            .validateurUserLogin(DEFAULT_VALIDATEUR_USER_LOGIN)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdByUserLogin(DEFAULT_CREATED_BY_USER_LOGIN)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedByUserLogin(DEFAULT_UPDATED_BY_USER_LOGIN);
        return demandeAchat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeAchat createUpdatedEntity(EntityManager em) {
        DemandeAchat demandeAchat = new DemandeAchat()
            .code(UPDATED_CODE)
            .statut(UPDATED_STATUT)
            .type(UPDATED_TYPE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateMiseADisposition(UPDATED_DATE_MISE_A_DISPOSITION)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .affaireId(UPDATED_AFFAIRE_ID)
            .demandeurId(UPDATED_DEMANDEUR_ID)
            .demandeurUserLogin(UPDATED_DEMANDEUR_USER_LOGIN)
            .validateurId(UPDATED_VALIDATEUR_ID)
            .validateurUserLogin(UPDATED_VALIDATEUR_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        return demandeAchat;
    }

    @BeforeEach
    public void initTest() {
        demandeAchat = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeAchat() throws Exception {
        int databaseSizeBeforeCreate = demandeAchatRepository.findAll().size();
        // Create the DemandeAchat
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);
        restDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeAchat testDemandeAchat = demandeAchatList.get(demandeAchatList.size() - 1);
        assertThat(testDemandeAchat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDemandeAchat.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDemandeAchat.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDemandeAchat.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testDemandeAchat.getDateMiseADisposition()).isEqualTo(DEFAULT_DATE_MISE_A_DISPOSITION);
        assertThat(testDemandeAchat.getWorkOrderId()).isEqualTo(DEFAULT_WORK_ORDER_ID);
        assertThat(testDemandeAchat.getAffaireId()).isEqualTo(DEFAULT_AFFAIRE_ID);
        assertThat(testDemandeAchat.getDemandeurId()).isEqualTo(DEFAULT_DEMANDEUR_ID);
        assertThat(testDemandeAchat.getDemandeurUserLogin()).isEqualTo(DEFAULT_DEMANDEUR_USER_LOGIN);
        assertThat(testDemandeAchat.getValidateurId()).isEqualTo(DEFAULT_VALIDATEUR_ID);
        assertThat(testDemandeAchat.getValidateurUserLogin()).isEqualTo(DEFAULT_VALIDATEUR_USER_LOGIN);
        assertThat(testDemandeAchat.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDemandeAchat.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testDemandeAchat.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDemandeAchat.getCreatedByUserLogin()).isEqualTo(DEFAULT_CREATED_BY_USER_LOGIN);
        assertThat(testDemandeAchat.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDemandeAchat.getUpdatedByUserLogin()).isEqualTo(DEFAULT_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void createDemandeAchatWithExistingId() throws Exception {
        // Create the DemandeAchat with an existing ID
        demandeAchat.setId(1L);
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        int databaseSizeBeforeCreate = demandeAchatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeAchatRepository.findAll().size();
        // set the field null
        demandeAchat.setCode(null);

        // Create the DemandeAchat, which fails.
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        restDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeAchatRepository.findAll().size();
        // set the field null
        demandeAchat.setStatut(null);

        // Create the DemandeAchat, which fails.
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        restDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeAchatRepository.findAll().size();
        // set the field null
        demandeAchat.setType(null);

        // Create the DemandeAchat, which fails.
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        restDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeAchatRepository.findAll().size();
        // set the field null
        demandeAchat.setDateCreation(null);

        // Create the DemandeAchat, which fails.
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        restDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateMiseADispositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeAchatRepository.findAll().size();
        // set the field null
        demandeAchat.setDateMiseADisposition(null);

        // Create the DemandeAchat, which fails.
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        restDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandeAchats() throws Exception {
        // Initialize the database
        demandeAchatRepository.saveAndFlush(demandeAchat);

        // Get all the demandeAchatList
        restDemandeAchatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeAchat.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(sameInstant(DEFAULT_DATE_CREATION))))
            .andExpect(jsonPath("$.[*].dateMiseADisposition").value(hasItem(DEFAULT_DATE_MISE_A_DISPOSITION.toString())))
            .andExpect(jsonPath("$.[*].workOrderId").value(hasItem(DEFAULT_WORK_ORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].affaireId").value(hasItem(DEFAULT_AFFAIRE_ID.intValue())))
            .andExpect(jsonPath("$.[*].demandeurId").value(hasItem(DEFAULT_DEMANDEUR_ID)))
            .andExpect(jsonPath("$.[*].demandeurUserLogin").value(hasItem(DEFAULT_DEMANDEUR_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].validateurId").value(hasItem(DEFAULT_VALIDATEUR_ID)))
            .andExpect(jsonPath("$.[*].validateurUserLogin").value(hasItem(DEFAULT_VALIDATEUR_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdByUserLogin").value(hasItem(DEFAULT_CREATED_BY_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedByUserLogin").value(hasItem(DEFAULT_UPDATED_BY_USER_LOGIN)));
    }

    @Test
    @Transactional
    void getDemandeAchat() throws Exception {
        // Initialize the database
        demandeAchatRepository.saveAndFlush(demandeAchat);

        // Get the demandeAchat
        restDemandeAchatMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeAchat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeAchat.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.dateCreation").value(sameInstant(DEFAULT_DATE_CREATION)))
            .andExpect(jsonPath("$.dateMiseADisposition").value(DEFAULT_DATE_MISE_A_DISPOSITION.toString()))
            .andExpect(jsonPath("$.workOrderId").value(DEFAULT_WORK_ORDER_ID.intValue()))
            .andExpect(jsonPath("$.affaireId").value(DEFAULT_AFFAIRE_ID.intValue()))
            .andExpect(jsonPath("$.demandeurId").value(DEFAULT_DEMANDEUR_ID))
            .andExpect(jsonPath("$.demandeurUserLogin").value(DEFAULT_DEMANDEUR_USER_LOGIN))
            .andExpect(jsonPath("$.validateurId").value(DEFAULT_VALIDATEUR_ID))
            .andExpect(jsonPath("$.validateurUserLogin").value(DEFAULT_VALIDATEUR_USER_LOGIN))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdByUserLogin").value(DEFAULT_CREATED_BY_USER_LOGIN))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedByUserLogin").value(DEFAULT_UPDATED_BY_USER_LOGIN));
    }

    @Test
    @Transactional
    void getNonExistingDemandeAchat() throws Exception {
        // Get the demandeAchat
        restDemandeAchatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemandeAchat() throws Exception {
        // Initialize the database
        demandeAchatRepository.saveAndFlush(demandeAchat);

        int databaseSizeBeforeUpdate = demandeAchatRepository.findAll().size();

        // Update the demandeAchat
        DemandeAchat updatedDemandeAchat = demandeAchatRepository.findById(demandeAchat.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeAchat are not directly saved in db
        em.detach(updatedDemandeAchat);
        updatedDemandeAchat
            .code(UPDATED_CODE)
            .statut(UPDATED_STATUT)
            .type(UPDATED_TYPE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateMiseADisposition(UPDATED_DATE_MISE_A_DISPOSITION)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .affaireId(UPDATED_AFFAIRE_ID)
            .demandeurId(UPDATED_DEMANDEUR_ID)
            .demandeurUserLogin(UPDATED_DEMANDEUR_USER_LOGIN)
            .validateurId(UPDATED_VALIDATEUR_ID)
            .validateurUserLogin(UPDATED_VALIDATEUR_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(updatedDemandeAchat);

        restDemandeAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeAchatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeUpdate);
        DemandeAchat testDemandeAchat = demandeAchatList.get(demandeAchatList.size() - 1);
        assertThat(testDemandeAchat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDemandeAchat.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDemandeAchat.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDemandeAchat.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemandeAchat.getDateMiseADisposition()).isEqualTo(UPDATED_DATE_MISE_A_DISPOSITION);
        assertThat(testDemandeAchat.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDemandeAchat.getAffaireId()).isEqualTo(UPDATED_AFFAIRE_ID);
        assertThat(testDemandeAchat.getDemandeurId()).isEqualTo(UPDATED_DEMANDEUR_ID);
        assertThat(testDemandeAchat.getDemandeurUserLogin()).isEqualTo(UPDATED_DEMANDEUR_USER_LOGIN);
        assertThat(testDemandeAchat.getValidateurId()).isEqualTo(UPDATED_VALIDATEUR_ID);
        assertThat(testDemandeAchat.getValidateurUserLogin()).isEqualTo(UPDATED_VALIDATEUR_USER_LOGIN);
        assertThat(testDemandeAchat.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDemandeAchat.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testDemandeAchat.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDemandeAchat.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDemandeAchat.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDemandeAchat.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void putNonExistingDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = demandeAchatRepository.findAll().size();
        demandeAchat.setId(count.incrementAndGet());

        // Create the DemandeAchat
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeAchatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = demandeAchatRepository.findAll().size();
        demandeAchat.setId(count.incrementAndGet());

        // Create the DemandeAchat
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = demandeAchatRepository.findAll().size();
        demandeAchat.setId(count.incrementAndGet());

        // Create the DemandeAchat
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeAchatMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeAchatWithPatch() throws Exception {
        // Initialize the database
        demandeAchatRepository.saveAndFlush(demandeAchat);

        int databaseSizeBeforeUpdate = demandeAchatRepository.findAll().size();

        // Update the demandeAchat using partial update
        DemandeAchat partialUpdatedDemandeAchat = new DemandeAchat();
        partialUpdatedDemandeAchat.setId(demandeAchat.getId());

        partialUpdatedDemandeAchat
            .dateCreation(UPDATED_DATE_CREATION)
            .dateMiseADisposition(UPDATED_DATE_MISE_A_DISPOSITION)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .demandeurId(UPDATED_DEMANDEUR_ID)
            .demandeurUserLogin(UPDATED_DEMANDEUR_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY);

        restDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeAchat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeAchat))
            )
            .andExpect(status().isOk());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeUpdate);
        DemandeAchat testDemandeAchat = demandeAchatList.get(demandeAchatList.size() - 1);
        assertThat(testDemandeAchat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDemandeAchat.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDemandeAchat.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDemandeAchat.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemandeAchat.getDateMiseADisposition()).isEqualTo(UPDATED_DATE_MISE_A_DISPOSITION);
        assertThat(testDemandeAchat.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDemandeAchat.getAffaireId()).isEqualTo(DEFAULT_AFFAIRE_ID);
        assertThat(testDemandeAchat.getDemandeurId()).isEqualTo(UPDATED_DEMANDEUR_ID);
        assertThat(testDemandeAchat.getDemandeurUserLogin()).isEqualTo(UPDATED_DEMANDEUR_USER_LOGIN);
        assertThat(testDemandeAchat.getValidateurId()).isEqualTo(DEFAULT_VALIDATEUR_ID);
        assertThat(testDemandeAchat.getValidateurUserLogin()).isEqualTo(DEFAULT_VALIDATEUR_USER_LOGIN);
        assertThat(testDemandeAchat.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDemandeAchat.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testDemandeAchat.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDemandeAchat.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDemandeAchat.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDemandeAchat.getUpdatedByUserLogin()).isEqualTo(DEFAULT_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void fullUpdateDemandeAchatWithPatch() throws Exception {
        // Initialize the database
        demandeAchatRepository.saveAndFlush(demandeAchat);

        int databaseSizeBeforeUpdate = demandeAchatRepository.findAll().size();

        // Update the demandeAchat using partial update
        DemandeAchat partialUpdatedDemandeAchat = new DemandeAchat();
        partialUpdatedDemandeAchat.setId(demandeAchat.getId());

        partialUpdatedDemandeAchat
            .code(UPDATED_CODE)
            .statut(UPDATED_STATUT)
            .type(UPDATED_TYPE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateMiseADisposition(UPDATED_DATE_MISE_A_DISPOSITION)
            .workOrderId(UPDATED_WORK_ORDER_ID)
            .affaireId(UPDATED_AFFAIRE_ID)
            .demandeurId(UPDATED_DEMANDEUR_ID)
            .demandeurUserLogin(UPDATED_DEMANDEUR_USER_LOGIN)
            .validateurId(UPDATED_VALIDATEUR_ID)
            .validateurUserLogin(UPDATED_VALIDATEUR_USER_LOGIN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdByUserLogin(UPDATED_CREATED_BY_USER_LOGIN)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedByUserLogin(UPDATED_UPDATED_BY_USER_LOGIN);

        restDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeAchat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeAchat))
            )
            .andExpect(status().isOk());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeUpdate);
        DemandeAchat testDemandeAchat = demandeAchatList.get(demandeAchatList.size() - 1);
        assertThat(testDemandeAchat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDemandeAchat.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDemandeAchat.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDemandeAchat.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemandeAchat.getDateMiseADisposition()).isEqualTo(UPDATED_DATE_MISE_A_DISPOSITION);
        assertThat(testDemandeAchat.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
        assertThat(testDemandeAchat.getAffaireId()).isEqualTo(UPDATED_AFFAIRE_ID);
        assertThat(testDemandeAchat.getDemandeurId()).isEqualTo(UPDATED_DEMANDEUR_ID);
        assertThat(testDemandeAchat.getDemandeurUserLogin()).isEqualTo(UPDATED_DEMANDEUR_USER_LOGIN);
        assertThat(testDemandeAchat.getValidateurId()).isEqualTo(UPDATED_VALIDATEUR_ID);
        assertThat(testDemandeAchat.getValidateurUserLogin()).isEqualTo(UPDATED_VALIDATEUR_USER_LOGIN);
        assertThat(testDemandeAchat.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDemandeAchat.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testDemandeAchat.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDemandeAchat.getCreatedByUserLogin()).isEqualTo(UPDATED_CREATED_BY_USER_LOGIN);
        assertThat(testDemandeAchat.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDemandeAchat.getUpdatedByUserLogin()).isEqualTo(UPDATED_UPDATED_BY_USER_LOGIN);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = demandeAchatRepository.findAll().size();
        demandeAchat.setId(count.incrementAndGet());

        // Create the DemandeAchat
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeAchatDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = demandeAchatRepository.findAll().size();
        demandeAchat.setId(count.incrementAndGet());

        // Create the DemandeAchat
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = demandeAchatRepository.findAll().size();
        demandeAchat.setId(count.incrementAndGet());

        // Create the DemandeAchat
        DemandeAchatDTO demandeAchatDTO = demandeAchatMapper.toDto(demandeAchat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeAchatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeAchat in the database
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeAchat() throws Exception {
        // Initialize the database
        demandeAchatRepository.saveAndFlush(demandeAchat);

        int databaseSizeBeforeDelete = demandeAchatRepository.findAll().size();

        // Delete the demandeAchat
        restDemandeAchatMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeAchat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeAchat> demandeAchatList = demandeAchatRepository.findAll();
        assertThat(demandeAchatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

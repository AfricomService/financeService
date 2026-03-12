package com.gpm.finance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.Facture;
import com.gpm.finance.domain.FactureWO;
import com.gpm.finance.repository.FactureWORepository;
import com.gpm.finance.service.FactureWOService;
import com.gpm.finance.service.dto.FactureWODTO;
import com.gpm.finance.service.mapper.FactureWOMapper;
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
 * Integration tests for the {@link FactureWOResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FactureWOResourceIT {

    private static final Float DEFAULT_POURCENTAGE_FACTURE = 1F;
    private static final Float UPDATED_POURCENTAGE_FACTURE = 2F;

    private static final Float DEFAULT_MONTANT_FACTURE = 1F;
    private static final Float UPDATED_MONTANT_FACTURE = 2F;

    private static final String DEFAULT_REMARQUE = "AAAAAAAAAA";
    private static final String UPDATED_REMARQUE = "BBBBBBBBBB";

    private static final Long DEFAULT_WORK_ORDER_ID = 1L;
    private static final Long UPDATED_WORK_ORDER_ID = 2L;

    private static final String ENTITY_API_URL = "/api/facture-wos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FactureWORepository factureWORepository;

    @Mock
    private FactureWORepository factureWORepositoryMock;

    @Autowired
    private FactureWOMapper factureWOMapper;

    @Mock
    private FactureWOService factureWOServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactureWOMockMvc;

    private FactureWO factureWO;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureWO createEntity(EntityManager em) {
        FactureWO factureWO = new FactureWO()
            .pourcentageFacture(DEFAULT_POURCENTAGE_FACTURE)
            .montantFacture(DEFAULT_MONTANT_FACTURE)
            .remarque(DEFAULT_REMARQUE)
            .workOrderId(DEFAULT_WORK_ORDER_ID);
        // Add required entity
        Facture facture;
        if (TestUtil.findAll(em, Facture.class).isEmpty()) {
            facture = FactureResourceIT.createEntity(em);
            em.persist(facture);
            em.flush();
        } else {
            facture = TestUtil.findAll(em, Facture.class).get(0);
        }
        factureWO.setFacture(facture);
        return factureWO;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureWO createUpdatedEntity(EntityManager em) {
        FactureWO factureWO = new FactureWO()
            .pourcentageFacture(UPDATED_POURCENTAGE_FACTURE)
            .montantFacture(UPDATED_MONTANT_FACTURE)
            .remarque(UPDATED_REMARQUE)
            .workOrderId(UPDATED_WORK_ORDER_ID);
        // Add required entity
        Facture facture;
        if (TestUtil.findAll(em, Facture.class).isEmpty()) {
            facture = FactureResourceIT.createUpdatedEntity(em);
            em.persist(facture);
            em.flush();
        } else {
            facture = TestUtil.findAll(em, Facture.class).get(0);
        }
        factureWO.setFacture(facture);
        return factureWO;
    }

    @BeforeEach
    public void initTest() {
        factureWO = createEntity(em);
    }

    @Test
    @Transactional
    void createFactureWO() throws Exception {
        int databaseSizeBeforeCreate = factureWORepository.findAll().size();
        // Create the FactureWO
        FactureWODTO factureWODTO = factureWOMapper.toDto(factureWO);
        restFactureWOMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureWODTO))
            )
            .andExpect(status().isCreated());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeCreate + 1);
        FactureWO testFactureWO = factureWOList.get(factureWOList.size() - 1);
        assertThat(testFactureWO.getPourcentageFacture()).isEqualTo(DEFAULT_POURCENTAGE_FACTURE);
        assertThat(testFactureWO.getMontantFacture()).isEqualTo(DEFAULT_MONTANT_FACTURE);
        assertThat(testFactureWO.getRemarque()).isEqualTo(DEFAULT_REMARQUE);
        assertThat(testFactureWO.getWorkOrderId()).isEqualTo(DEFAULT_WORK_ORDER_ID);
    }

    @Test
    @Transactional
    void createFactureWOWithExistingId() throws Exception {
        // Create the FactureWO with an existing ID
        factureWO.setId(1L);
        FactureWODTO factureWODTO = factureWOMapper.toDto(factureWO);

        int databaseSizeBeforeCreate = factureWORepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureWOMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureWODTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFactureWOS() throws Exception {
        // Initialize the database
        factureWORepository.saveAndFlush(factureWO);

        // Get all the factureWOList
        restFactureWOMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factureWO.getId().intValue())))
            .andExpect(jsonPath("$.[*].pourcentageFacture").value(hasItem(DEFAULT_POURCENTAGE_FACTURE.doubleValue())))
            .andExpect(jsonPath("$.[*].montantFacture").value(hasItem(DEFAULT_MONTANT_FACTURE.doubleValue())))
            .andExpect(jsonPath("$.[*].remarque").value(hasItem(DEFAULT_REMARQUE)))
            .andExpect(jsonPath("$.[*].workOrderId").value(hasItem(DEFAULT_WORK_ORDER_ID.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFactureWOSWithEagerRelationshipsIsEnabled() throws Exception {
        when(factureWOServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFactureWOMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(factureWOServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFactureWOSWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(factureWOServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFactureWOMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(factureWORepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFactureWO() throws Exception {
        // Initialize the database
        factureWORepository.saveAndFlush(factureWO);

        // Get the factureWO
        restFactureWOMockMvc
            .perform(get(ENTITY_API_URL_ID, factureWO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factureWO.getId().intValue()))
            .andExpect(jsonPath("$.pourcentageFacture").value(DEFAULT_POURCENTAGE_FACTURE.doubleValue()))
            .andExpect(jsonPath("$.montantFacture").value(DEFAULT_MONTANT_FACTURE.doubleValue()))
            .andExpect(jsonPath("$.remarque").value(DEFAULT_REMARQUE))
            .andExpect(jsonPath("$.workOrderId").value(DEFAULT_WORK_ORDER_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFactureWO() throws Exception {
        // Get the factureWO
        restFactureWOMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFactureWO() throws Exception {
        // Initialize the database
        factureWORepository.saveAndFlush(factureWO);

        int databaseSizeBeforeUpdate = factureWORepository.findAll().size();

        // Update the factureWO
        FactureWO updatedFactureWO = factureWORepository.findById(factureWO.getId()).get();
        // Disconnect from session so that the updates on updatedFactureWO are not directly saved in db
        em.detach(updatedFactureWO);
        updatedFactureWO
            .pourcentageFacture(UPDATED_POURCENTAGE_FACTURE)
            .montantFacture(UPDATED_MONTANT_FACTURE)
            .remarque(UPDATED_REMARQUE)
            .workOrderId(UPDATED_WORK_ORDER_ID);
        FactureWODTO factureWODTO = factureWOMapper.toDto(updatedFactureWO);

        restFactureWOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureWODTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureWODTO))
            )
            .andExpect(status().isOk());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeUpdate);
        FactureWO testFactureWO = factureWOList.get(factureWOList.size() - 1);
        assertThat(testFactureWO.getPourcentageFacture()).isEqualTo(UPDATED_POURCENTAGE_FACTURE);
        assertThat(testFactureWO.getMontantFacture()).isEqualTo(UPDATED_MONTANT_FACTURE);
        assertThat(testFactureWO.getRemarque()).isEqualTo(UPDATED_REMARQUE);
        assertThat(testFactureWO.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
    }

    @Test
    @Transactional
    void putNonExistingFactureWO() throws Exception {
        int databaseSizeBeforeUpdate = factureWORepository.findAll().size();
        factureWO.setId(count.incrementAndGet());

        // Create the FactureWO
        FactureWODTO factureWODTO = factureWOMapper.toDto(factureWO);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureWOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureWODTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureWODTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactureWO() throws Exception {
        int databaseSizeBeforeUpdate = factureWORepository.findAll().size();
        factureWO.setId(count.incrementAndGet());

        // Create the FactureWO
        FactureWODTO factureWODTO = factureWOMapper.toDto(factureWO);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureWOMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureWODTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactureWO() throws Exception {
        int databaseSizeBeforeUpdate = factureWORepository.findAll().size();
        factureWO.setId(count.incrementAndGet());

        // Create the FactureWO
        FactureWODTO factureWODTO = factureWOMapper.toDto(factureWO);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureWOMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureWODTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFactureWOWithPatch() throws Exception {
        // Initialize the database
        factureWORepository.saveAndFlush(factureWO);

        int databaseSizeBeforeUpdate = factureWORepository.findAll().size();

        // Update the factureWO using partial update
        FactureWO partialUpdatedFactureWO = new FactureWO();
        partialUpdatedFactureWO.setId(factureWO.getId());

        partialUpdatedFactureWO
            .pourcentageFacture(UPDATED_POURCENTAGE_FACTURE)
            .montantFacture(UPDATED_MONTANT_FACTURE)
            .remarque(UPDATED_REMARQUE)
            .workOrderId(UPDATED_WORK_ORDER_ID);

        restFactureWOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureWO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactureWO))
            )
            .andExpect(status().isOk());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeUpdate);
        FactureWO testFactureWO = factureWOList.get(factureWOList.size() - 1);
        assertThat(testFactureWO.getPourcentageFacture()).isEqualTo(UPDATED_POURCENTAGE_FACTURE);
        assertThat(testFactureWO.getMontantFacture()).isEqualTo(UPDATED_MONTANT_FACTURE);
        assertThat(testFactureWO.getRemarque()).isEqualTo(UPDATED_REMARQUE);
        assertThat(testFactureWO.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
    }

    @Test
    @Transactional
    void fullUpdateFactureWOWithPatch() throws Exception {
        // Initialize the database
        factureWORepository.saveAndFlush(factureWO);

        int databaseSizeBeforeUpdate = factureWORepository.findAll().size();

        // Update the factureWO using partial update
        FactureWO partialUpdatedFactureWO = new FactureWO();
        partialUpdatedFactureWO.setId(factureWO.getId());

        partialUpdatedFactureWO
            .pourcentageFacture(UPDATED_POURCENTAGE_FACTURE)
            .montantFacture(UPDATED_MONTANT_FACTURE)
            .remarque(UPDATED_REMARQUE)
            .workOrderId(UPDATED_WORK_ORDER_ID);

        restFactureWOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureWO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactureWO))
            )
            .andExpect(status().isOk());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeUpdate);
        FactureWO testFactureWO = factureWOList.get(factureWOList.size() - 1);
        assertThat(testFactureWO.getPourcentageFacture()).isEqualTo(UPDATED_POURCENTAGE_FACTURE);
        assertThat(testFactureWO.getMontantFacture()).isEqualTo(UPDATED_MONTANT_FACTURE);
        assertThat(testFactureWO.getRemarque()).isEqualTo(UPDATED_REMARQUE);
        assertThat(testFactureWO.getWorkOrderId()).isEqualTo(UPDATED_WORK_ORDER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingFactureWO() throws Exception {
        int databaseSizeBeforeUpdate = factureWORepository.findAll().size();
        factureWO.setId(count.incrementAndGet());

        // Create the FactureWO
        FactureWODTO factureWODTO = factureWOMapper.toDto(factureWO);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureWOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factureWODTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureWODTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactureWO() throws Exception {
        int databaseSizeBeforeUpdate = factureWORepository.findAll().size();
        factureWO.setId(count.incrementAndGet());

        // Create the FactureWO
        FactureWODTO factureWODTO = factureWOMapper.toDto(factureWO);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureWOMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureWODTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactureWO() throws Exception {
        int databaseSizeBeforeUpdate = factureWORepository.findAll().size();
        factureWO.setId(count.incrementAndGet());

        // Create the FactureWO
        FactureWODTO factureWODTO = factureWOMapper.toDto(factureWO);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureWOMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureWODTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureWO in the database
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactureWO() throws Exception {
        // Initialize the database
        factureWORepository.saveAndFlush(factureWO);

        int databaseSizeBeforeDelete = factureWORepository.findAll().size();

        // Delete the factureWO
        restFactureWOMockMvc
            .perform(delete(ENTITY_API_URL_ID, factureWO.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FactureWO> factureWOList = factureWORepository.findAll();
        assertThat(factureWOList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

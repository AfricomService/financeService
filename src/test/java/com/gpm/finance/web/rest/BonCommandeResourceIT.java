package com.gpm.finance.web.rest;

import static com.gpm.finance.web.rest.TestUtil.sameInstant;
import static com.gpm.finance.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.BonCommande;
import com.gpm.finance.repository.BonCommandeRepository;
import com.gpm.finance.service.dto.BonCommandeDTO;
import com.gpm.finance.service.mapper.BonCommandeMapper;
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
 * Integration tests for the {@link BonCommandeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BonCommandeResourceIT {

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final Long DEFAULT_AFFAIRE_ID = 1L;
    private static final Long UPDATED_AFFAIRE_ID = 2L;

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSABLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSABLE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_CLIENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_BON_COMMANDE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_BON_COMMANDE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_MONTANT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_TOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_COMMANDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_COMMANDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_CONSOMME = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_CONSOMME = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_MISSION_EFFECTUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_MISSION_EFFECTUE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/bon-commandes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BonCommandeRepository bonCommandeRepository;

    @Autowired
    private BonCommandeMapper bonCommandeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBonCommandeMockMvc;

    private BonCommande bonCommande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonCommande createEntity(EntityManager em) {
        BonCommande bonCommande = new BonCommande()
            .clientId(DEFAULT_CLIENT_ID)
            .affaireId(DEFAULT_AFFAIRE_ID)
            .lieu(DEFAULT_LIEU)
            .responsableId(DEFAULT_RESPONSABLE_ID)
            .referenceClient(DEFAULT_REFERENCE_CLIENT)
            .dateBonCommande(DEFAULT_DATE_BON_COMMANDE)
            .montantTotal(DEFAULT_MONTANT_TOTAL)
            .montantCommande(DEFAULT_MONTANT_COMMANDE)
            .montantConsomme(DEFAULT_MONTANT_CONSOMME)
            .montantMissionEffectue(DEFAULT_MONTANT_MISSION_EFFECTUE);
        return bonCommande;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonCommande createUpdatedEntity(EntityManager em) {
        BonCommande bonCommande = new BonCommande()
            .clientId(UPDATED_CLIENT_ID)
            .affaireId(UPDATED_AFFAIRE_ID)
            .lieu(UPDATED_LIEU)
            .responsableId(UPDATED_RESPONSABLE_ID)
            .referenceClient(UPDATED_REFERENCE_CLIENT)
            .dateBonCommande(UPDATED_DATE_BON_COMMANDE)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .montantCommande(UPDATED_MONTANT_COMMANDE)
            .montantConsomme(UPDATED_MONTANT_CONSOMME)
            .montantMissionEffectue(UPDATED_MONTANT_MISSION_EFFECTUE);
        return bonCommande;
    }

    @BeforeEach
    public void initTest() {
        bonCommande = createEntity(em);
    }

    @Test
    @Transactional
    void createBonCommande() throws Exception {
        int databaseSizeBeforeCreate = bonCommandeRepository.findAll().size();
        // Create the BonCommande
        BonCommandeDTO bonCommandeDTO = bonCommandeMapper.toDto(bonCommande);
        restBonCommandeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeCreate + 1);
        BonCommande testBonCommande = bonCommandeList.get(bonCommandeList.size() - 1);
        assertThat(testBonCommande.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testBonCommande.getAffaireId()).isEqualTo(DEFAULT_AFFAIRE_ID);
        assertThat(testBonCommande.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testBonCommande.getResponsableId()).isEqualTo(DEFAULT_RESPONSABLE_ID);
        assertThat(testBonCommande.getReferenceClient()).isEqualTo(DEFAULT_REFERENCE_CLIENT);
        assertThat(testBonCommande.getDateBonCommande()).isEqualTo(DEFAULT_DATE_BON_COMMANDE);
        assertThat(testBonCommande.getMontantTotal()).isEqualByComparingTo(DEFAULT_MONTANT_TOTAL);
        assertThat(testBonCommande.getMontantCommande()).isEqualByComparingTo(DEFAULT_MONTANT_COMMANDE);
        assertThat(testBonCommande.getMontantConsomme()).isEqualByComparingTo(DEFAULT_MONTANT_CONSOMME);
        assertThat(testBonCommande.getMontantMissionEffectue()).isEqualByComparingTo(DEFAULT_MONTANT_MISSION_EFFECTUE);
    }

    @Test
    @Transactional
    void createBonCommandeWithExistingId() throws Exception {
        // Create the BonCommande with an existing ID
        bonCommande.setId(1L);
        BonCommandeDTO bonCommandeDTO = bonCommandeMapper.toDto(bonCommande);

        int databaseSizeBeforeCreate = bonCommandeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonCommandeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBonCommandes() throws Exception {
        // Initialize the database
        bonCommandeRepository.saveAndFlush(bonCommande);

        // Get all the bonCommandeList
        restBonCommandeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonCommande.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].affaireId").value(hasItem(DEFAULT_AFFAIRE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU)))
            .andExpect(jsonPath("$.[*].responsableId").value(hasItem(DEFAULT_RESPONSABLE_ID)))
            .andExpect(jsonPath("$.[*].referenceClient").value(hasItem(DEFAULT_REFERENCE_CLIENT)))
            .andExpect(jsonPath("$.[*].dateBonCommande").value(hasItem(sameInstant(DEFAULT_DATE_BON_COMMANDE))))
            .andExpect(jsonPath("$.[*].montantTotal").value(hasItem(sameNumber(DEFAULT_MONTANT_TOTAL))))
            .andExpect(jsonPath("$.[*].montantCommande").value(hasItem(sameNumber(DEFAULT_MONTANT_COMMANDE))))
            .andExpect(jsonPath("$.[*].montantConsomme").value(hasItem(sameNumber(DEFAULT_MONTANT_CONSOMME))))
            .andExpect(jsonPath("$.[*].montantMissionEffectue").value(hasItem(sameNumber(DEFAULT_MONTANT_MISSION_EFFECTUE))));
    }

    @Test
    @Transactional
    void getBonCommande() throws Exception {
        // Initialize the database
        bonCommandeRepository.saveAndFlush(bonCommande);

        // Get the bonCommande
        restBonCommandeMockMvc
            .perform(get(ENTITY_API_URL_ID, bonCommande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonCommande.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.affaireId").value(DEFAULT_AFFAIRE_ID.intValue()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU))
            .andExpect(jsonPath("$.responsableId").value(DEFAULT_RESPONSABLE_ID))
            .andExpect(jsonPath("$.referenceClient").value(DEFAULT_REFERENCE_CLIENT))
            .andExpect(jsonPath("$.dateBonCommande").value(sameInstant(DEFAULT_DATE_BON_COMMANDE)))
            .andExpect(jsonPath("$.montantTotal").value(sameNumber(DEFAULT_MONTANT_TOTAL)))
            .andExpect(jsonPath("$.montantCommande").value(sameNumber(DEFAULT_MONTANT_COMMANDE)))
            .andExpect(jsonPath("$.montantConsomme").value(sameNumber(DEFAULT_MONTANT_CONSOMME)))
            .andExpect(jsonPath("$.montantMissionEffectue").value(sameNumber(DEFAULT_MONTANT_MISSION_EFFECTUE)));
    }

    @Test
    @Transactional
    void getNonExistingBonCommande() throws Exception {
        // Get the bonCommande
        restBonCommandeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBonCommande() throws Exception {
        // Initialize the database
        bonCommandeRepository.saveAndFlush(bonCommande);

        int databaseSizeBeforeUpdate = bonCommandeRepository.findAll().size();

        // Update the bonCommande
        BonCommande updatedBonCommande = bonCommandeRepository.findById(bonCommande.getId()).get();
        // Disconnect from session so that the updates on updatedBonCommande are not directly saved in db
        em.detach(updatedBonCommande);
        updatedBonCommande
            .clientId(UPDATED_CLIENT_ID)
            .affaireId(UPDATED_AFFAIRE_ID)
            .lieu(UPDATED_LIEU)
            .responsableId(UPDATED_RESPONSABLE_ID)
            .referenceClient(UPDATED_REFERENCE_CLIENT)
            .dateBonCommande(UPDATED_DATE_BON_COMMANDE)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .montantCommande(UPDATED_MONTANT_COMMANDE)
            .montantConsomme(UPDATED_MONTANT_CONSOMME)
            .montantMissionEffectue(UPDATED_MONTANT_MISSION_EFFECTUE);
        BonCommandeDTO bonCommandeDTO = bonCommandeMapper.toDto(updatedBonCommande);

        restBonCommandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bonCommandeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeDTO))
            )
            .andExpect(status().isOk());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeUpdate);
        BonCommande testBonCommande = bonCommandeList.get(bonCommandeList.size() - 1);
        assertThat(testBonCommande.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testBonCommande.getAffaireId()).isEqualTo(UPDATED_AFFAIRE_ID);
        assertThat(testBonCommande.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testBonCommande.getResponsableId()).isEqualTo(UPDATED_RESPONSABLE_ID);
        assertThat(testBonCommande.getReferenceClient()).isEqualTo(UPDATED_REFERENCE_CLIENT);
        assertThat(testBonCommande.getDateBonCommande()).isEqualTo(UPDATED_DATE_BON_COMMANDE);
        assertThat(testBonCommande.getMontantTotal()).isEqualByComparingTo(UPDATED_MONTANT_TOTAL);
        assertThat(testBonCommande.getMontantCommande()).isEqualByComparingTo(UPDATED_MONTANT_COMMANDE);
        assertThat(testBonCommande.getMontantConsomme()).isEqualByComparingTo(UPDATED_MONTANT_CONSOMME);
        assertThat(testBonCommande.getMontantMissionEffectue()).isEqualByComparingTo(UPDATED_MONTANT_MISSION_EFFECTUE);
    }

    @Test
    @Transactional
    void putNonExistingBonCommande() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeRepository.findAll().size();
        bonCommande.setId(count.incrementAndGet());

        // Create the BonCommande
        BonCommandeDTO bonCommandeDTO = bonCommandeMapper.toDto(bonCommande);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonCommandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bonCommandeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBonCommande() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeRepository.findAll().size();
        bonCommande.setId(count.incrementAndGet());

        // Create the BonCommande
        BonCommandeDTO bonCommandeDTO = bonCommandeMapper.toDto(bonCommande);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBonCommande() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeRepository.findAll().size();
        bonCommande.setId(count.incrementAndGet());

        // Create the BonCommande
        BonCommandeDTO bonCommandeDTO = bonCommandeMapper.toDto(bonCommande);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBonCommandeWithPatch() throws Exception {
        // Initialize the database
        bonCommandeRepository.saveAndFlush(bonCommande);

        int databaseSizeBeforeUpdate = bonCommandeRepository.findAll().size();

        // Update the bonCommande using partial update
        BonCommande partialUpdatedBonCommande = new BonCommande();
        partialUpdatedBonCommande.setId(bonCommande.getId());

        partialUpdatedBonCommande
            .affaireId(UPDATED_AFFAIRE_ID)
            .responsableId(UPDATED_RESPONSABLE_ID)
            .referenceClient(UPDATED_REFERENCE_CLIENT)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .montantConsomme(UPDATED_MONTANT_CONSOMME)
            .montantMissionEffectue(UPDATED_MONTANT_MISSION_EFFECTUE);

        restBonCommandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonCommande.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonCommande))
            )
            .andExpect(status().isOk());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeUpdate);
        BonCommande testBonCommande = bonCommandeList.get(bonCommandeList.size() - 1);
        assertThat(testBonCommande.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testBonCommande.getAffaireId()).isEqualTo(UPDATED_AFFAIRE_ID);
        assertThat(testBonCommande.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testBonCommande.getResponsableId()).isEqualTo(UPDATED_RESPONSABLE_ID);
        assertThat(testBonCommande.getReferenceClient()).isEqualTo(UPDATED_REFERENCE_CLIENT);
        assertThat(testBonCommande.getDateBonCommande()).isEqualTo(DEFAULT_DATE_BON_COMMANDE);
        assertThat(testBonCommande.getMontantTotal()).isEqualByComparingTo(UPDATED_MONTANT_TOTAL);
        assertThat(testBonCommande.getMontantCommande()).isEqualByComparingTo(DEFAULT_MONTANT_COMMANDE);
        assertThat(testBonCommande.getMontantConsomme()).isEqualByComparingTo(UPDATED_MONTANT_CONSOMME);
        assertThat(testBonCommande.getMontantMissionEffectue()).isEqualByComparingTo(UPDATED_MONTANT_MISSION_EFFECTUE);
    }

    @Test
    @Transactional
    void fullUpdateBonCommandeWithPatch() throws Exception {
        // Initialize the database
        bonCommandeRepository.saveAndFlush(bonCommande);

        int databaseSizeBeforeUpdate = bonCommandeRepository.findAll().size();

        // Update the bonCommande using partial update
        BonCommande partialUpdatedBonCommande = new BonCommande();
        partialUpdatedBonCommande.setId(bonCommande.getId());

        partialUpdatedBonCommande
            .clientId(UPDATED_CLIENT_ID)
            .affaireId(UPDATED_AFFAIRE_ID)
            .lieu(UPDATED_LIEU)
            .responsableId(UPDATED_RESPONSABLE_ID)
            .referenceClient(UPDATED_REFERENCE_CLIENT)
            .dateBonCommande(UPDATED_DATE_BON_COMMANDE)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .montantCommande(UPDATED_MONTANT_COMMANDE)
            .montantConsomme(UPDATED_MONTANT_CONSOMME)
            .montantMissionEffectue(UPDATED_MONTANT_MISSION_EFFECTUE);

        restBonCommandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonCommande.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonCommande))
            )
            .andExpect(status().isOk());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeUpdate);
        BonCommande testBonCommande = bonCommandeList.get(bonCommandeList.size() - 1);
        assertThat(testBonCommande.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testBonCommande.getAffaireId()).isEqualTo(UPDATED_AFFAIRE_ID);
        assertThat(testBonCommande.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testBonCommande.getResponsableId()).isEqualTo(UPDATED_RESPONSABLE_ID);
        assertThat(testBonCommande.getReferenceClient()).isEqualTo(UPDATED_REFERENCE_CLIENT);
        assertThat(testBonCommande.getDateBonCommande()).isEqualTo(UPDATED_DATE_BON_COMMANDE);
        assertThat(testBonCommande.getMontantTotal()).isEqualByComparingTo(UPDATED_MONTANT_TOTAL);
        assertThat(testBonCommande.getMontantCommande()).isEqualByComparingTo(UPDATED_MONTANT_COMMANDE);
        assertThat(testBonCommande.getMontantConsomme()).isEqualByComparingTo(UPDATED_MONTANT_CONSOMME);
        assertThat(testBonCommande.getMontantMissionEffectue()).isEqualByComparingTo(UPDATED_MONTANT_MISSION_EFFECTUE);
    }

    @Test
    @Transactional
    void patchNonExistingBonCommande() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeRepository.findAll().size();
        bonCommande.setId(count.incrementAndGet());

        // Create the BonCommande
        BonCommandeDTO bonCommandeDTO = bonCommandeMapper.toDto(bonCommande);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonCommandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bonCommandeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBonCommande() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeRepository.findAll().size();
        bonCommande.setId(count.incrementAndGet());

        // Create the BonCommande
        BonCommandeDTO bonCommandeDTO = bonCommandeMapper.toDto(bonCommande);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBonCommande() throws Exception {
        int databaseSizeBeforeUpdate = bonCommandeRepository.findAll().size();
        bonCommande.setId(count.incrementAndGet());

        // Create the BonCommande
        BonCommandeDTO bonCommandeDTO = bonCommandeMapper.toDto(bonCommande);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBonCommandeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonCommandeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BonCommande in the database
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBonCommande() throws Exception {
        // Initialize the database
        bonCommandeRepository.saveAndFlush(bonCommande);

        int databaseSizeBeforeDelete = bonCommandeRepository.findAll().size();

        // Delete the bonCommande
        restBonCommandeMockMvc
            .perform(delete(ENTITY_API_URL_ID, bonCommande.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BonCommande> bonCommandeList = bonCommandeRepository.findAll();
        assertThat(bonCommandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

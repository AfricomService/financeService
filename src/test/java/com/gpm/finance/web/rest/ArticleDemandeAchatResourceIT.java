package com.gpm.finance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpm.finance.IntegrationTest;
import com.gpm.finance.domain.ArticleDemandeAchat;
import com.gpm.finance.domain.DemandeAchat;
import com.gpm.finance.domain.enumeration.TypeArticleDemandeAchat;
import com.gpm.finance.repository.ArticleDemandeAchatRepository;
import com.gpm.finance.service.ArticleDemandeAchatService;
import com.gpm.finance.service.dto.ArticleDemandeAchatDTO;
import com.gpm.finance.service.mapper.ArticleDemandeAchatMapper;
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
 * Integration tests for the {@link ArticleDemandeAchatResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArticleDemandeAchatResourceIT {

    private static final Float DEFAULT_QTE_DEMANDEE = 1F;
    private static final Float UPDATED_QTE_DEMANDEE = 2F;

    private static final TypeArticleDemandeAchat DEFAULT_TYPE = TypeArticleDemandeAchat.Fourniture;
    private static final TypeArticleDemandeAchat UPDATED_TYPE = TypeArticleDemandeAchat.Service;

    private static final String DEFAULT_ARTICLE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/article-demande-achats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArticleDemandeAchatRepository articleDemandeAchatRepository;

    @Mock
    private ArticleDemandeAchatRepository articleDemandeAchatRepositoryMock;

    @Autowired
    private ArticleDemandeAchatMapper articleDemandeAchatMapper;

    @Mock
    private ArticleDemandeAchatService articleDemandeAchatServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleDemandeAchatMockMvc;

    private ArticleDemandeAchat articleDemandeAchat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleDemandeAchat createEntity(EntityManager em) {
        ArticleDemandeAchat articleDemandeAchat = new ArticleDemandeAchat()
            .qteDemandee(DEFAULT_QTE_DEMANDEE)
            .type(DEFAULT_TYPE)
            .articleCode(DEFAULT_ARTICLE_CODE);
        // Add required entity
        DemandeAchat demandeAchat;
        if (TestUtil.findAll(em, DemandeAchat.class).isEmpty()) {
            demandeAchat = DemandeAchatResourceIT.createEntity(em);
            em.persist(demandeAchat);
            em.flush();
        } else {
            demandeAchat = TestUtil.findAll(em, DemandeAchat.class).get(0);
        }
        articleDemandeAchat.setDemandeAchat(demandeAchat);
        return articleDemandeAchat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleDemandeAchat createUpdatedEntity(EntityManager em) {
        ArticleDemandeAchat articleDemandeAchat = new ArticleDemandeAchat()
            .qteDemandee(UPDATED_QTE_DEMANDEE)
            .type(UPDATED_TYPE)
            .articleCode(UPDATED_ARTICLE_CODE);
        // Add required entity
        DemandeAchat demandeAchat;
        if (TestUtil.findAll(em, DemandeAchat.class).isEmpty()) {
            demandeAchat = DemandeAchatResourceIT.createUpdatedEntity(em);
            em.persist(demandeAchat);
            em.flush();
        } else {
            demandeAchat = TestUtil.findAll(em, DemandeAchat.class).get(0);
        }
        articleDemandeAchat.setDemandeAchat(demandeAchat);
        return articleDemandeAchat;
    }

    @BeforeEach
    public void initTest() {
        articleDemandeAchat = createEntity(em);
    }

    @Test
    @Transactional
    void createArticleDemandeAchat() throws Exception {
        int databaseSizeBeforeCreate = articleDemandeAchatRepository.findAll().size();
        // Create the ArticleDemandeAchat
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);
        restArticleDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleDemandeAchat testArticleDemandeAchat = articleDemandeAchatList.get(articleDemandeAchatList.size() - 1);
        assertThat(testArticleDemandeAchat.getQteDemandee()).isEqualTo(DEFAULT_QTE_DEMANDEE);
        assertThat(testArticleDemandeAchat.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testArticleDemandeAchat.getArticleCode()).isEqualTo(DEFAULT_ARTICLE_CODE);
    }

    @Test
    @Transactional
    void createArticleDemandeAchatWithExistingId() throws Exception {
        // Create the ArticleDemandeAchat with an existing ID
        articleDemandeAchat.setId(1L);
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);

        int databaseSizeBeforeCreate = articleDemandeAchatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQteDemandeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleDemandeAchatRepository.findAll().size();
        // set the field null
        articleDemandeAchat.setQteDemandee(null);

        // Create the ArticleDemandeAchat, which fails.
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);

        restArticleDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleDemandeAchatRepository.findAll().size();
        // set the field null
        articleDemandeAchat.setType(null);

        // Create the ArticleDemandeAchat, which fails.
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);

        restArticleDemandeAchatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticleDemandeAchats() throws Exception {
        // Initialize the database
        articleDemandeAchatRepository.saveAndFlush(articleDemandeAchat);

        // Get all the articleDemandeAchatList
        restArticleDemandeAchatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleDemandeAchat.getId().intValue())))
            .andExpect(jsonPath("$.[*].qteDemandee").value(hasItem(DEFAULT_QTE_DEMANDEE.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].articleCode").value(hasItem(DEFAULT_ARTICLE_CODE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArticleDemandeAchatsWithEagerRelationshipsIsEnabled() throws Exception {
        when(articleDemandeAchatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArticleDemandeAchatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(articleDemandeAchatServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArticleDemandeAchatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(articleDemandeAchatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArticleDemandeAchatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(articleDemandeAchatRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getArticleDemandeAchat() throws Exception {
        // Initialize the database
        articleDemandeAchatRepository.saveAndFlush(articleDemandeAchat);

        // Get the articleDemandeAchat
        restArticleDemandeAchatMockMvc
            .perform(get(ENTITY_API_URL_ID, articleDemandeAchat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleDemandeAchat.getId().intValue()))
            .andExpect(jsonPath("$.qteDemandee").value(DEFAULT_QTE_DEMANDEE.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.articleCode").value(DEFAULT_ARTICLE_CODE));
    }

    @Test
    @Transactional
    void getNonExistingArticleDemandeAchat() throws Exception {
        // Get the articleDemandeAchat
        restArticleDemandeAchatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticleDemandeAchat() throws Exception {
        // Initialize the database
        articleDemandeAchatRepository.saveAndFlush(articleDemandeAchat);

        int databaseSizeBeforeUpdate = articleDemandeAchatRepository.findAll().size();

        // Update the articleDemandeAchat
        ArticleDemandeAchat updatedArticleDemandeAchat = articleDemandeAchatRepository.findById(articleDemandeAchat.getId()).get();
        // Disconnect from session so that the updates on updatedArticleDemandeAchat are not directly saved in db
        em.detach(updatedArticleDemandeAchat);
        updatedArticleDemandeAchat.qteDemandee(UPDATED_QTE_DEMANDEE).type(UPDATED_TYPE).articleCode(UPDATED_ARTICLE_CODE);
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(updatedArticleDemandeAchat);

        restArticleDemandeAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleDemandeAchatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeUpdate);
        ArticleDemandeAchat testArticleDemandeAchat = articleDemandeAchatList.get(articleDemandeAchatList.size() - 1);
        assertThat(testArticleDemandeAchat.getQteDemandee()).isEqualTo(UPDATED_QTE_DEMANDEE);
        assertThat(testArticleDemandeAchat.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testArticleDemandeAchat.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
    }

    @Test
    @Transactional
    void putNonExistingArticleDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = articleDemandeAchatRepository.findAll().size();
        articleDemandeAchat.setId(count.incrementAndGet());

        // Create the ArticleDemandeAchat
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleDemandeAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleDemandeAchatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticleDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = articleDemandeAchatRepository.findAll().size();
        articleDemandeAchat.setId(count.incrementAndGet());

        // Create the ArticleDemandeAchat
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleDemandeAchatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticleDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = articleDemandeAchatRepository.findAll().size();
        articleDemandeAchat.setId(count.incrementAndGet());

        // Create the ArticleDemandeAchat
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleDemandeAchatMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleDemandeAchatWithPatch() throws Exception {
        // Initialize the database
        articleDemandeAchatRepository.saveAndFlush(articleDemandeAchat);

        int databaseSizeBeforeUpdate = articleDemandeAchatRepository.findAll().size();

        // Update the articleDemandeAchat using partial update
        ArticleDemandeAchat partialUpdatedArticleDemandeAchat = new ArticleDemandeAchat();
        partialUpdatedArticleDemandeAchat.setId(articleDemandeAchat.getId());

        partialUpdatedArticleDemandeAchat.qteDemandee(UPDATED_QTE_DEMANDEE).articleCode(UPDATED_ARTICLE_CODE);

        restArticleDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleDemandeAchat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleDemandeAchat))
            )
            .andExpect(status().isOk());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeUpdate);
        ArticleDemandeAchat testArticleDemandeAchat = articleDemandeAchatList.get(articleDemandeAchatList.size() - 1);
        assertThat(testArticleDemandeAchat.getQteDemandee()).isEqualTo(UPDATED_QTE_DEMANDEE);
        assertThat(testArticleDemandeAchat.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testArticleDemandeAchat.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
    }

    @Test
    @Transactional
    void fullUpdateArticleDemandeAchatWithPatch() throws Exception {
        // Initialize the database
        articleDemandeAchatRepository.saveAndFlush(articleDemandeAchat);

        int databaseSizeBeforeUpdate = articleDemandeAchatRepository.findAll().size();

        // Update the articleDemandeAchat using partial update
        ArticleDemandeAchat partialUpdatedArticleDemandeAchat = new ArticleDemandeAchat();
        partialUpdatedArticleDemandeAchat.setId(articleDemandeAchat.getId());

        partialUpdatedArticleDemandeAchat.qteDemandee(UPDATED_QTE_DEMANDEE).type(UPDATED_TYPE).articleCode(UPDATED_ARTICLE_CODE);

        restArticleDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleDemandeAchat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleDemandeAchat))
            )
            .andExpect(status().isOk());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeUpdate);
        ArticleDemandeAchat testArticleDemandeAchat = articleDemandeAchatList.get(articleDemandeAchatList.size() - 1);
        assertThat(testArticleDemandeAchat.getQteDemandee()).isEqualTo(UPDATED_QTE_DEMANDEE);
        assertThat(testArticleDemandeAchat.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testArticleDemandeAchat.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingArticleDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = articleDemandeAchatRepository.findAll().size();
        articleDemandeAchat.setId(count.incrementAndGet());

        // Create the ArticleDemandeAchat
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleDemandeAchatDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticleDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = articleDemandeAchatRepository.findAll().size();
        articleDemandeAchat.setId(count.incrementAndGet());

        // Create the ArticleDemandeAchat
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticleDemandeAchat() throws Exception {
        int databaseSizeBeforeUpdate = articleDemandeAchatRepository.findAll().size();
        articleDemandeAchat.setId(count.incrementAndGet());

        // Create the ArticleDemandeAchat
        ArticleDemandeAchatDTO articleDemandeAchatDTO = articleDemandeAchatMapper.toDto(articleDemandeAchat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleDemandeAchatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleDemandeAchatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleDemandeAchat in the database
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticleDemandeAchat() throws Exception {
        // Initialize the database
        articleDemandeAchatRepository.saveAndFlush(articleDemandeAchat);

        int databaseSizeBeforeDelete = articleDemandeAchatRepository.findAll().size();

        // Delete the articleDemandeAchat
        restArticleDemandeAchatMockMvc
            .perform(delete(ENTITY_API_URL_ID, articleDemandeAchat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticleDemandeAchat> articleDemandeAchatList = articleDemandeAchatRepository.findAll();
        assertThat(articleDemandeAchatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

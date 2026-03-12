package com.gpm.finance.service;

import com.gpm.finance.domain.ArticleDemandeAchat;
import com.gpm.finance.repository.ArticleDemandeAchatRepository;
import com.gpm.finance.service.dto.ArticleDemandeAchatDTO;
import com.gpm.finance.service.mapper.ArticleDemandeAchatMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ArticleDemandeAchat}.
 */
@Service
@Transactional
public class ArticleDemandeAchatService {

    private final Logger log = LoggerFactory.getLogger(ArticleDemandeAchatService.class);

    private final ArticleDemandeAchatRepository articleDemandeAchatRepository;

    private final ArticleDemandeAchatMapper articleDemandeAchatMapper;

    public ArticleDemandeAchatService(
        ArticleDemandeAchatRepository articleDemandeAchatRepository,
        ArticleDemandeAchatMapper articleDemandeAchatMapper
    ) {
        this.articleDemandeAchatRepository = articleDemandeAchatRepository;
        this.articleDemandeAchatMapper = articleDemandeAchatMapper;
    }

    /**
     * Save a articleDemandeAchat.
     *
     * @param articleDemandeAchatDTO the entity to save.
     * @return the persisted entity.
     */
    public ArticleDemandeAchatDTO save(ArticleDemandeAchatDTO articleDemandeAchatDTO) {
        log.debug("Request to save ArticleDemandeAchat : {}", articleDemandeAchatDTO);
        ArticleDemandeAchat articleDemandeAchat = articleDemandeAchatMapper.toEntity(articleDemandeAchatDTO);
        articleDemandeAchat = articleDemandeAchatRepository.save(articleDemandeAchat);
        return articleDemandeAchatMapper.toDto(articleDemandeAchat);
    }

    /**
     * Update a articleDemandeAchat.
     *
     * @param articleDemandeAchatDTO the entity to save.
     * @return the persisted entity.
     */
    public ArticleDemandeAchatDTO update(ArticleDemandeAchatDTO articleDemandeAchatDTO) {
        log.debug("Request to update ArticleDemandeAchat : {}", articleDemandeAchatDTO);
        ArticleDemandeAchat articleDemandeAchat = articleDemandeAchatMapper.toEntity(articleDemandeAchatDTO);
        articleDemandeAchat = articleDemandeAchatRepository.save(articleDemandeAchat);
        return articleDemandeAchatMapper.toDto(articleDemandeAchat);
    }

    /**
     * Partially update a articleDemandeAchat.
     *
     * @param articleDemandeAchatDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArticleDemandeAchatDTO> partialUpdate(ArticleDemandeAchatDTO articleDemandeAchatDTO) {
        log.debug("Request to partially update ArticleDemandeAchat : {}", articleDemandeAchatDTO);

        return articleDemandeAchatRepository
            .findById(articleDemandeAchatDTO.getId())
            .map(existingArticleDemandeAchat -> {
                articleDemandeAchatMapper.partialUpdate(existingArticleDemandeAchat, articleDemandeAchatDTO);

                return existingArticleDemandeAchat;
            })
            .map(articleDemandeAchatRepository::save)
            .map(articleDemandeAchatMapper::toDto);
    }

    /**
     * Get all the articleDemandeAchats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticleDemandeAchatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArticleDemandeAchats");
        return articleDemandeAchatRepository.findAll(pageable).map(articleDemandeAchatMapper::toDto);
    }

    /**
     * Get all the articleDemandeAchats with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ArticleDemandeAchatDTO> findAllWithEagerRelationships(Pageable pageable) {
        return articleDemandeAchatRepository.findAllWithEagerRelationships(pageable).map(articleDemandeAchatMapper::toDto);
    }

    /**
     * Get one articleDemandeAchat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArticleDemandeAchatDTO> findOne(Long id) {
        log.debug("Request to get ArticleDemandeAchat : {}", id);
        return articleDemandeAchatRepository.findOneWithEagerRelationships(id).map(articleDemandeAchatMapper::toDto);
    }

    /**
     * Delete the articleDemandeAchat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArticleDemandeAchat : {}", id);
        articleDemandeAchatRepository.deleteById(id);
    }
}

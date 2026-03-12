package com.gpm.finance.repository;

import com.gpm.finance.domain.ArticleDemandeAchat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArticleDemandeAchat entity.
 */
@Repository
public interface ArticleDemandeAchatRepository extends JpaRepository<ArticleDemandeAchat, Long> {
    default Optional<ArticleDemandeAchat> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ArticleDemandeAchat> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ArticleDemandeAchat> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct articleDemandeAchat from ArticleDemandeAchat articleDemandeAchat left join fetch articleDemandeAchat.demandeAchat",
        countQuery = "select count(distinct articleDemandeAchat) from ArticleDemandeAchat articleDemandeAchat"
    )
    Page<ArticleDemandeAchat> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct articleDemandeAchat from ArticleDemandeAchat articleDemandeAchat left join fetch articleDemandeAchat.demandeAchat"
    )
    List<ArticleDemandeAchat> findAllWithToOneRelationships();

    @Query(
        "select articleDemandeAchat from ArticleDemandeAchat articleDemandeAchat left join fetch articleDemandeAchat.demandeAchat where articleDemandeAchat.id =:id"
    )
    Optional<ArticleDemandeAchat> findOneWithToOneRelationships(@Param("id") Long id);
}

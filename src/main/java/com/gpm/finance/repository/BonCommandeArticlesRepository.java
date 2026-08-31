package com.gpm.finance.repository;

import com.gpm.finance.domain.BonCommandeArticles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the BonCommandeArticles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonCommandeArticlesRepository extends JpaRepository<BonCommandeArticles, Long> {
    List<BonCommandeArticles> findAllByBonCommandeId(Long bonCommandeId);

    void deleteAllByBonCommandeId(Long bonCommandeId);
}

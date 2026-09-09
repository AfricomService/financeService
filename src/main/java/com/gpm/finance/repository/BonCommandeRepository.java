package com.gpm.finance.repository;

import com.gpm.finance.domain.BonCommande;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the BonCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonCommandeRepository extends JpaRepository<BonCommande, Long>, JpaSpecificationExecutor<BonCommande> {
    List<BonCommande> findByAffaireId(Long affaireId);
}

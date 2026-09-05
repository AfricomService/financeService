package com.gpm.finance.repository;

import com.gpm.finance.domain.BonCommandeAutreResponsable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the BonCommandeAutreResponsable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonCommandeAutreResponsableRepository extends JpaRepository<BonCommandeAutreResponsable, Long> {
    List<BonCommandeAutreResponsable> findByBonCommandeId(Long bonCommandeId);

    void deleteByBonCommandeId(Long bonCommandeId);
}

package com.gpm.finance.repository;

import com.gpm.finance.domain.BonCommande;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BonCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonCommandeRepository extends JpaRepository<BonCommande, Long>, JpaSpecificationExecutor<BonCommande> {
    List<BonCommande> findByAffaireId(Long affaireId);

    List<BonCommande> findByAffaireIdAndStatus(Long affaireId, String status);

    @Query(
        "select a from BonCommande a where a.id in (" +
        "  select e.objectId from AclEntry e where e.objectType = 'BONCOMMANDE'" +
        "  and e.sidId in :sidIds and (e.canRead = true or e.canWrite = true)" +
        ")"
    )
    Page<BonCommande> findAllAccessible(@Param("sidIds") List<Long> sidIds, Pageable pageable);
}

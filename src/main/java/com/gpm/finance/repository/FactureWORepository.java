package com.gpm.finance.repository;

import com.gpm.finance.domain.FactureWO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FactureWO entity.
 */
@Repository
public interface FactureWORepository extends JpaRepository<FactureWO, Long> {
    default Optional<FactureWO> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<FactureWO> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<FactureWO> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct factureWO from FactureWO factureWO left join fetch factureWO.facture",
        countQuery = "select count(distinct factureWO) from FactureWO factureWO"
    )
    Page<FactureWO> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct factureWO from FactureWO factureWO left join fetch factureWO.facture")
    List<FactureWO> findAllWithToOneRelationships();

    @Query("select factureWO from FactureWO factureWO left join fetch factureWO.facture where factureWO.id =:id")
    Optional<FactureWO> findOneWithToOneRelationships(@Param("id") Long id);
}

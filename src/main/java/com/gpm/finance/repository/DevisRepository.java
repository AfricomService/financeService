package com.gpm.finance.repository;

import com.gpm.finance.domain.Devis;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Devis entity.
 */
@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {
    default Optional<Devis> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Devis> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Devis> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct devis from Devis devis left join fetch devis.facture",
        countQuery = "select count(distinct devis) from Devis devis"
    )
    Page<Devis> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct devis from Devis devis left join fetch devis.facture")
    List<Devis> findAllWithToOneRelationships();

    @Query("select devis from Devis devis left join fetch devis.facture where devis.id =:id")
    Optional<Devis> findOneWithToOneRelationships(@Param("id") Long id);
}

package com.gpm.finance.repository;

import com.gpm.finance.domain.Facture;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Facture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    java.util.List<Facture> findByClientId(Long clientId);

    java.util.List<Facture> findByClientIdAndNumFactureContainingIgnoreCase(Long clientId, String numFacture);

}

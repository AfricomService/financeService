package com.gpm.finance.repository;

import com.gpm.finance.domain.DemandeEspece;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DemandeEspece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeEspeceRepository extends JpaRepository<DemandeEspece, Long> {}

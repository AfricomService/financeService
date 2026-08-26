package com.gpm.finance.repository;

import com.gpm.finance.domain.PhaseOt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PhaseOt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhaseOtRepository extends JpaRepository<PhaseOt, Long> {}

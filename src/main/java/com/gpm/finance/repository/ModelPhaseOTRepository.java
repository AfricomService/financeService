package com.gpm.finance.repository;

import com.gpm.finance.domain.ModelPhaseOT;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ModelPhaseOT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelPhaseOTRepository extends JpaRepository<ModelPhaseOT, Long> {}

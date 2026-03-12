package com.gpm.finance.repository;

import com.gpm.finance.domain.DepenseDiverse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DepenseDiverse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepenseDiverseRepository extends JpaRepository<DepenseDiverse, Long> {}

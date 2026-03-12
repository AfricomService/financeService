package com.gpm.finance.repository;

import com.gpm.finance.domain.FraisDeMission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FraisDeMission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraisDeMissionRepository extends JpaRepository<FraisDeMission, Long> {}

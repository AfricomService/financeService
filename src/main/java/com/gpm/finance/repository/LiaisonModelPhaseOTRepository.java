package com.gpm.finance.repository;

import com.gpm.finance.domain.LiaisonModelPhaseOT;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LiaisonModelPhaseOT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LiaisonModelPhaseOTRepository extends JpaRepository<LiaisonModelPhaseOT, Long> {}

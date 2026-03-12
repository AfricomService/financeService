package com.gpm.finance.repository;

import com.gpm.finance.domain.OtExterne;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OtExterne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtExterneRepository extends JpaRepository<OtExterne, Long> {}

package com.gpm.finance.repository;

import com.gpm.finance.domain.OtExterneAutreResponsable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OtExterneAutreResponsable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtExterneAutreResponsableRepository extends JpaRepository<OtExterneAutreResponsable, Long> {}

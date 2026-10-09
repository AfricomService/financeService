package com.gpm.finance.repository;

import com.gpm.finance.domain.OtExterne;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OtExterne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtExterneRepository extends JpaRepository<OtExterne, Long> {
    @Query(
        "select a from OtExterne a where a.id in (" +
        "  select e.objectId from AclEntry e where e.objectType = 'OTEXTERNE'" +
        "  and e.sidId in :sidIds and (e.canRead = true or e.canWrite = true)" +
        ")"
    )
    Page<OtExterne> findAllAccessible(@Param("sidIds") List<Long> sidIds, Pageable pageable);
}

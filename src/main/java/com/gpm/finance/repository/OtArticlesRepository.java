package com.gpm.finance.repository;

import com.gpm.finance.domain.OtArticles;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OtArticles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtArticlesRepository extends JpaRepository<OtArticles, Long> {
    List<OtArticles> findAllByOtId(Long otId);
}

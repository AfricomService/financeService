package com.gpm.finance.repository;

import com.gpm.finance.domain.DemandeAchat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DemandeAchat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeAchatRepository extends JpaRepository<DemandeAchat, Long> {}

package com.gpm.finance.repository;

import com.gpm.finance.domain.LiaisonModelPhaseOT;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LiaisonModelPhaseOT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LiaisonModelPhaseOTRepository extends JpaRepository<LiaisonModelPhaseOT, Long> {
    /**
     * All phases linked to a given model, ordered by their position (1st, 2nd, ...).
     */
    List<LiaisonModelPhaseOT> findByModelPhaseOtIdOrderByClassementPhaseAsc(Long modelPhaseOtId);

    /**
     * Used to reject duplicate links: the same PhaseOt can't be linked twice to the same model.
     */
    boolean existsByModelPhaseOtIdAndPhaseId(Long modelPhaseOtId, Long phaseId);

    /**
     * Used to compute the next free classement when assigning a new phase.
     */
    Optional<LiaisonModelPhaseOT> findFirstByModelPhaseOtIdOrderByClassementPhaseDesc(Long modelPhaseOtId);

    /**
     * Used to find the neighbour to swap with when moving a phase up/down.
     */
    Optional<LiaisonModelPhaseOT> findByModelPhaseOtIdAndClassementPhase(Long modelPhaseOtId, Integer classementPhase);

    long countByModelPhaseOtId(Long modelPhaseOtId);
}

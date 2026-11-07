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

    // ADMIN : recherche + filtre statut ('' = tous) ; affaireIds ne doit jamais être vide (sentinelle -1)
    @Query(
        "select a from OtExterne a where " +
            "(:statut = '' or str(a.statut) = :statut) " +
            "and (:search = '' or lower(a.reference) like lower(concat('%', :search, '%')) or a.affaireId in :affaireIds)"
    )
    Page<OtExterne> searchAll(
        @Param("statut") String statut,
        @Param("search") String search,
        @Param("affaireIds") List<Long> affaireIds,
        Pageable pageable
    );

    // Utilisateur normal : même recherche, limitée aux OT accessibles via ACL
    @Query(
        "select a from OtExterne a where " +
            "(:statut = '' or str(a.statut) = :statut) " +
            "and (:search = '' or lower(a.reference) like lower(concat('%', :search, '%')) or a.affaireId in :affaireIds) " +
            "and a.id in (" +
            "  select e.objectId from AclEntry e where e.objectType = 'OTEXTERNE'" +
            "  and e.sidId in :sidIds and (e.canRead = true or e.canWrite = true)" +
            ")"
    )
    Page<OtExterne> searchAccessible(
        @Param("statut") String statut,
        @Param("search") String search,
        @Param("affaireIds") List<Long> affaireIds,
        @Param("sidIds") List<Long> sidIds,
        Pageable pageable
    );

    @Query("select a.statut, count(a) from OtExterne a group by a.statut")
    List<Object[]> countGroupByStatut();

    @Query(
        "select a.statut, count(a) from OtExterne a where a.id in (" +
            "  select e.objectId from AclEntry e where e.objectType = 'OTEXTERNE'" +
            "  and e.sidId in :sidIds and (e.canRead = true or e.canWrite = true)" +
            ") group by a.statut"
    )
    List<Object[]> countAccessibleGroupByStatut(@Param("sidIds") List<Long> sidIds);

    // ADMIN : tous les OT externes d'un modèle, filtrés par référence
    @Query(
        "select a from OtExterne a where a.modeleOtId = :modeleOtId " +
            "and lower(a.reference) like lower(concat('%', :search, '%'))"
    )
    Page<OtExterne> findByModeleOtIdAndSearch(
        @Param("modeleOtId") Long modeleOtId,
        @Param("search") String search,
        Pageable pageable
    );

    // Utilisateur normal : seulement ceux accessibles via ACL
    @Query(
        "select a from OtExterne a where a.modeleOtId = :modeleOtId " +
            "and lower(a.reference) like lower(concat('%', :search, '%')) " +
            "and a.id in (" +
            "  select e.objectId from AclEntry e where e.objectType = 'OTEXTERNE'" +
            "  and e.sidId in :sidIds and (e.canRead = true or e.canWrite = true)" +
            ")"
    )
    Page<OtExterne> findAccessibleByModeleOtIdAndSearch(
        @Param("modeleOtId") Long modeleOtId,
        @Param("search") String search,
        @Param("sidIds") List<Long> sidIds,
        Pageable pageable
    );
}

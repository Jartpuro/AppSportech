package co.edu.sena.repository;

import co.edu.sena.domain.BondingTrainer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BondingTrainer entity.
 */
@Repository
public interface BondingTrainerRepository extends JpaRepository<BondingTrainer, Long> {
    default Optional<BondingTrainer> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BondingTrainer> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BondingTrainer> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct bondingTrainer from BondingTrainer bondingTrainer left join fetch bondingTrainer.year left join fetch bondingTrainer.bonding",
        countQuery = "select count(distinct bondingTrainer) from BondingTrainer bondingTrainer"
    )
    Page<BondingTrainer> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct bondingTrainer from BondingTrainer bondingTrainer left join fetch bondingTrainer.year left join fetch bondingTrainer.bonding"
    )
    List<BondingTrainer> findAllWithToOneRelationships();

    @Query(
        "select bondingTrainer from BondingTrainer bondingTrainer left join fetch bondingTrainer.year left join fetch bondingTrainer.bonding where bondingTrainer.id =:id"
    )
    Optional<BondingTrainer> findOneWithToOneRelationships(@Param("id") Long id);
}

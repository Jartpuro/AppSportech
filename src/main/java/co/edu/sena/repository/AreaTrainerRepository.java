package co.edu.sena.repository;

import co.edu.sena.domain.AreaTrainer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AreaTrainer entity.
 */
@Repository
public interface AreaTrainerRepository extends JpaRepository<AreaTrainer, Long> {
    default Optional<AreaTrainer> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AreaTrainer> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AreaTrainer> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct areaTrainer from AreaTrainer areaTrainer left join fetch areaTrainer.area",
        countQuery = "select count(distinct areaTrainer) from AreaTrainer areaTrainer"
    )
    Page<AreaTrainer> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct areaTrainer from AreaTrainer areaTrainer left join fetch areaTrainer.area")
    List<AreaTrainer> findAllWithToOneRelationships();

    @Query("select areaTrainer from AreaTrainer areaTrainer left join fetch areaTrainer.area where areaTrainer.id =:id")
    Optional<AreaTrainer> findOneWithToOneRelationships(@Param("id") Long id);
}

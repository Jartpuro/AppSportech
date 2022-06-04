package co.edu.sena.repository;

import co.edu.sena.domain.Trainee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Trainee entity.
 */
@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    default Optional<Trainee> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Trainee> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Trainee> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct trainee from Trainee trainee left join fetch trainee.trainingStatus",
        countQuery = "select count(distinct trainee) from Trainee trainee"
    )
    Page<Trainee> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct trainee from Trainee trainee left join fetch trainee.trainingStatus")
    List<Trainee> findAllWithToOneRelationships();

    @Query("select trainee from Trainee trainee left join fetch trainee.trainingStatus where trainee.id =:id")
    Optional<Trainee> findOneWithToOneRelationships(@Param("id") Long id);
}

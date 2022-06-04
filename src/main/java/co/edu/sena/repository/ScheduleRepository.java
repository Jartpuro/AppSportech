package co.edu.sena.repository;

import co.edu.sena.domain.Schedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Schedule entity.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    default Optional<Schedule> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Schedule> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Schedule> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct schedule from Schedule schedule left join fetch schedule.modality left join fetch schedule.day",
        countQuery = "select count(distinct schedule) from Schedule schedule"
    )
    Page<Schedule> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct schedule from Schedule schedule left join fetch schedule.modality left join fetch schedule.day")
    List<Schedule> findAllWithToOneRelationships();

    @Query("select schedule from Schedule schedule left join fetch schedule.modality left join fetch schedule.day where schedule.id =:id")
    Optional<Schedule> findOneWithToOneRelationships(@Param("id") Long id);
}

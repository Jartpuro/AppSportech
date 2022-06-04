package co.edu.sena.repository;

import co.edu.sena.domain.BoundingSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BoundingSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoundingScheduleRepository extends JpaRepository<BoundingSchedule, Long> {}

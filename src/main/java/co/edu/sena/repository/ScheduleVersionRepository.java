package co.edu.sena.repository;

import co.edu.sena.domain.ScheduleVersion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ScheduleVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleVersionRepository extends JpaRepository<ScheduleVersion, Long> {}

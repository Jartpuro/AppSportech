package co.edu.sena.repository;

import co.edu.sena.domain.ModuleSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ModuleSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleScheduleRepository extends JpaRepository<ModuleSchedule, Long> {}

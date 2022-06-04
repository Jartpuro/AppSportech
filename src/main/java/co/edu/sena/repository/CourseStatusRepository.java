package co.edu.sena.repository;

import co.edu.sena.domain.CourseStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseStatusRepository extends JpaRepository<CourseStatus, Long> {}

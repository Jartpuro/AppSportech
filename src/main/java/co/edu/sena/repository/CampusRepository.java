package co.edu.sena.repository;

import co.edu.sena.domain.Campus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Campus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {}

package co.edu.sena.repository;

import co.edu.sena.domain.TrainingStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TrainingStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingStatusRepository extends JpaRepository<TrainingStatus, Long> {}

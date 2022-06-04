package co.edu.sena.repository;

import co.edu.sena.domain.TrainingProgram;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TrainingProgram entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {}

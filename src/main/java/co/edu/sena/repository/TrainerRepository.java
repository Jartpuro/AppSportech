package co.edu.sena.repository;

import co.edu.sena.domain.Trainer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Trainer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {}

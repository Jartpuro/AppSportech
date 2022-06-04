package co.edu.sena.repository;

import co.edu.sena.domain.Modality;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Modality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModalityRepository extends JpaRepository<Modality, Long> {}

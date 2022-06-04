package co.edu.sena.repository;

import co.edu.sena.domain.LogError;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LogError entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogErrorRepository extends JpaRepository<LogError, Long> {}

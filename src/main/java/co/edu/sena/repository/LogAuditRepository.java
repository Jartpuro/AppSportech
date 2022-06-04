package co.edu.sena.repository;

import co.edu.sena.domain.LogAudit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LogAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogAuditRepository extends JpaRepository<LogAudit, Long> {}

package co.edu.sena.service;

import co.edu.sena.service.dto.LogAuditDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.LogAudit}.
 */
public interface LogAuditService {
    /**
     * Save a logAudit.
     *
     * @param logAuditDTO the entity to save.
     * @return the persisted entity.
     */
    LogAuditDTO save(LogAuditDTO logAuditDTO);

    /**
     * Updates a logAudit.
     *
     * @param logAuditDTO the entity to update.
     * @return the persisted entity.
     */
    LogAuditDTO update(LogAuditDTO logAuditDTO);

    /**
     * Partially updates a logAudit.
     *
     * @param logAuditDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LogAuditDTO> partialUpdate(LogAuditDTO logAuditDTO);

    /**
     * Get all the logAudits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LogAuditDTO> findAll(Pageable pageable);

    /**
     * Get the "id" logAudit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LogAuditDTO> findOne(Long id);

    /**
     * Delete the "id" logAudit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

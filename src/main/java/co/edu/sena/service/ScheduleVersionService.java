package co.edu.sena.service;

import co.edu.sena.service.dto.ScheduleVersionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.ScheduleVersion}.
 */
public interface ScheduleVersionService {
    /**
     * Save a scheduleVersion.
     *
     * @param scheduleVersionDTO the entity to save.
     * @return the persisted entity.
     */
    ScheduleVersionDTO save(ScheduleVersionDTO scheduleVersionDTO);

    /**
     * Updates a scheduleVersion.
     *
     * @param scheduleVersionDTO the entity to update.
     * @return the persisted entity.
     */
    ScheduleVersionDTO update(ScheduleVersionDTO scheduleVersionDTO);

    /**
     * Partially updates a scheduleVersion.
     *
     * @param scheduleVersionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ScheduleVersionDTO> partialUpdate(ScheduleVersionDTO scheduleVersionDTO);

    /**
     * Get all the scheduleVersions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScheduleVersionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" scheduleVersion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScheduleVersionDTO> findOne(Long id);

    /**
     * Delete the "id" scheduleVersion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

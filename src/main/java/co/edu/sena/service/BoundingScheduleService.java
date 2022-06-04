package co.edu.sena.service;

import co.edu.sena.service.dto.BoundingScheduleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.BoundingSchedule}.
 */
public interface BoundingScheduleService {
    /**
     * Save a boundingSchedule.
     *
     * @param boundingScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    BoundingScheduleDTO save(BoundingScheduleDTO boundingScheduleDTO);

    /**
     * Updates a boundingSchedule.
     *
     * @param boundingScheduleDTO the entity to update.
     * @return the persisted entity.
     */
    BoundingScheduleDTO update(BoundingScheduleDTO boundingScheduleDTO);

    /**
     * Partially updates a boundingSchedule.
     *
     * @param boundingScheduleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BoundingScheduleDTO> partialUpdate(BoundingScheduleDTO boundingScheduleDTO);

    /**
     * Get all the boundingSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BoundingScheduleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" boundingSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BoundingScheduleDTO> findOne(Long id);

    /**
     * Delete the "id" boundingSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

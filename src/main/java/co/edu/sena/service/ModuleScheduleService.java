package co.edu.sena.service;

import co.edu.sena.service.dto.ModuleScheduleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.ModuleSchedule}.
 */
public interface ModuleScheduleService {
    /**
     * Save a moduleSchedule.
     *
     * @param moduleScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    ModuleScheduleDTO save(ModuleScheduleDTO moduleScheduleDTO);

    /**
     * Updates a moduleSchedule.
     *
     * @param moduleScheduleDTO the entity to update.
     * @return the persisted entity.
     */
    ModuleScheduleDTO update(ModuleScheduleDTO moduleScheduleDTO);

    /**
     * Partially updates a moduleSchedule.
     *
     * @param moduleScheduleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ModuleScheduleDTO> partialUpdate(ModuleScheduleDTO moduleScheduleDTO);

    /**
     * Get all the moduleSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModuleScheduleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" moduleSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModuleScheduleDTO> findOne(Long id);

    /**
     * Delete the "id" moduleSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

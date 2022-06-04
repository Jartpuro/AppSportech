package co.edu.sena.service;

import co.edu.sena.service.dto.CourseStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.CourseStatus}.
 */
public interface CourseStatusService {
    /**
     * Save a courseStatus.
     *
     * @param courseStatusDTO the entity to save.
     * @return the persisted entity.
     */
    CourseStatusDTO save(CourseStatusDTO courseStatusDTO);

    /**
     * Updates a courseStatus.
     *
     * @param courseStatusDTO the entity to update.
     * @return the persisted entity.
     */
    CourseStatusDTO update(CourseStatusDTO courseStatusDTO);

    /**
     * Partially updates a courseStatus.
     *
     * @param courseStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseStatusDTO> partialUpdate(CourseStatusDTO courseStatusDTO);

    /**
     * Get all the courseStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseStatusDTO> findOne(Long id);

    /**
     * Delete the "id" courseStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

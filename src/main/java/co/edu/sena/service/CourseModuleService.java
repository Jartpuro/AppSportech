package co.edu.sena.service;

import co.edu.sena.service.dto.CourseModuleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.CourseModule}.
 */
public interface CourseModuleService {
    /**
     * Save a courseModule.
     *
     * @param courseModuleDTO the entity to save.
     * @return the persisted entity.
     */
    CourseModuleDTO save(CourseModuleDTO courseModuleDTO);

    /**
     * Updates a courseModule.
     *
     * @param courseModuleDTO the entity to update.
     * @return the persisted entity.
     */
    CourseModuleDTO update(CourseModuleDTO courseModuleDTO);

    /**
     * Partially updates a courseModule.
     *
     * @param courseModuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseModuleDTO> partialUpdate(CourseModuleDTO courseModuleDTO);

    /**
     * Get all the courseModules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseModuleDTO> findAll(Pageable pageable);

    /**
     * Get all the courseModules with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseModuleDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" courseModule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseModuleDTO> findOne(Long id);

    /**
     * Delete the "id" courseModule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

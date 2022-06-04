package co.edu.sena.service;

import co.edu.sena.service.dto.ClassroomTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.ClassroomType}.
 */
public interface ClassroomTypeService {
    /**
     * Save a classroomType.
     *
     * @param classroomTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ClassroomTypeDTO save(ClassroomTypeDTO classroomTypeDTO);

    /**
     * Updates a classroomType.
     *
     * @param classroomTypeDTO the entity to update.
     * @return the persisted entity.
     */
    ClassroomTypeDTO update(ClassroomTypeDTO classroomTypeDTO);

    /**
     * Partially updates a classroomType.
     *
     * @param classroomTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClassroomTypeDTO> partialUpdate(ClassroomTypeDTO classroomTypeDTO);

    /**
     * Get all the classroomTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClassroomTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" classroomType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassroomTypeDTO> findOne(Long id);

    /**
     * Delete the "id" classroomType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

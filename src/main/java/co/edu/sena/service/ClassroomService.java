package co.edu.sena.service;

import co.edu.sena.service.dto.ClassroomDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Classroom}.
 */
public interface ClassroomService {
    /**
     * Save a classroom.
     *
     * @param classroomDTO the entity to save.
     * @return the persisted entity.
     */
    ClassroomDTO save(ClassroomDTO classroomDTO);

    /**
     * Updates a classroom.
     *
     * @param classroomDTO the entity to update.
     * @return the persisted entity.
     */
    ClassroomDTO update(ClassroomDTO classroomDTO);

    /**
     * Partially updates a classroom.
     *
     * @param classroomDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClassroomDTO> partialUpdate(ClassroomDTO classroomDTO);

    /**
     * Get all the classrooms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClassroomDTO> findAll(Pageable pageable);

    /**
     * Get all the classrooms with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClassroomDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" classroom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassroomDTO> findOne(Long id);

    /**
     * Delete the "id" classroom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

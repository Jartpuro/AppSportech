package co.edu.sena.service;

import co.edu.sena.service.dto.TrainingProgramDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.TrainingProgram}.
 */
public interface TrainingProgramService {
    /**
     * Save a trainingProgram.
     *
     * @param trainingProgramDTO the entity to save.
     * @return the persisted entity.
     */
    TrainingProgramDTO save(TrainingProgramDTO trainingProgramDTO);

    /**
     * Updates a trainingProgram.
     *
     * @param trainingProgramDTO the entity to update.
     * @return the persisted entity.
     */
    TrainingProgramDTO update(TrainingProgramDTO trainingProgramDTO);

    /**
     * Partially updates a trainingProgram.
     *
     * @param trainingProgramDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainingProgramDTO> partialUpdate(TrainingProgramDTO trainingProgramDTO);

    /**
     * Get all the trainingPrograms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainingProgramDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainingProgram.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainingProgramDTO> findOne(Long id);

    /**
     * Delete the "id" trainingProgram.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

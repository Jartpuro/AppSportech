package co.edu.sena.service;

import co.edu.sena.service.dto.TrainingStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.TrainingStatus}.
 */
public interface TrainingStatusService {
    /**
     * Save a trainingStatus.
     *
     * @param trainingStatusDTO the entity to save.
     * @return the persisted entity.
     */
    TrainingStatusDTO save(TrainingStatusDTO trainingStatusDTO);

    /**
     * Updates a trainingStatus.
     *
     * @param trainingStatusDTO the entity to update.
     * @return the persisted entity.
     */
    TrainingStatusDTO update(TrainingStatusDTO trainingStatusDTO);

    /**
     * Partially updates a trainingStatus.
     *
     * @param trainingStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainingStatusDTO> partialUpdate(TrainingStatusDTO trainingStatusDTO);

    /**
     * Get all the trainingStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainingStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainingStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainingStatusDTO> findOne(Long id);

    /**
     * Delete the "id" trainingStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package co.edu.sena.service;

import co.edu.sena.service.dto.TrainerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Trainer}.
 */
public interface TrainerService {
    /**
     * Save a trainer.
     *
     * @param trainerDTO the entity to save.
     * @return the persisted entity.
     */
    TrainerDTO save(TrainerDTO trainerDTO);

    /**
     * Updates a trainer.
     *
     * @param trainerDTO the entity to update.
     * @return the persisted entity.
     */
    TrainerDTO update(TrainerDTO trainerDTO);

    /**
     * Partially updates a trainer.
     *
     * @param trainerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainerDTO> partialUpdate(TrainerDTO trainerDTO);

    /**
     * Get all the trainers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainerDTO> findOne(Long id);

    /**
     * Delete the "id" trainer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

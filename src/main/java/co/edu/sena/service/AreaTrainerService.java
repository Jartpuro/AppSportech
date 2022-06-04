package co.edu.sena.service;

import co.edu.sena.service.dto.AreaTrainerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.AreaTrainer}.
 */
public interface AreaTrainerService {
    /**
     * Save a areaTrainer.
     *
     * @param areaTrainerDTO the entity to save.
     * @return the persisted entity.
     */
    AreaTrainerDTO save(AreaTrainerDTO areaTrainerDTO);

    /**
     * Updates a areaTrainer.
     *
     * @param areaTrainerDTO the entity to update.
     * @return the persisted entity.
     */
    AreaTrainerDTO update(AreaTrainerDTO areaTrainerDTO);

    /**
     * Partially updates a areaTrainer.
     *
     * @param areaTrainerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AreaTrainerDTO> partialUpdate(AreaTrainerDTO areaTrainerDTO);

    /**
     * Get all the areaTrainers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AreaTrainerDTO> findAll(Pageable pageable);

    /**
     * Get all the areaTrainers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AreaTrainerDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" areaTrainer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AreaTrainerDTO> findOne(Long id);

    /**
     * Delete the "id" areaTrainer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

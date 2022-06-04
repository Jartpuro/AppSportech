package co.edu.sena.service;

import co.edu.sena.service.dto.BondingTrainerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.BondingTrainer}.
 */
public interface BondingTrainerService {
    /**
     * Save a bondingTrainer.
     *
     * @param bondingTrainerDTO the entity to save.
     * @return the persisted entity.
     */
    BondingTrainerDTO save(BondingTrainerDTO bondingTrainerDTO);

    /**
     * Updates a bondingTrainer.
     *
     * @param bondingTrainerDTO the entity to update.
     * @return the persisted entity.
     */
    BondingTrainerDTO update(BondingTrainerDTO bondingTrainerDTO);

    /**
     * Partially updates a bondingTrainer.
     *
     * @param bondingTrainerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BondingTrainerDTO> partialUpdate(BondingTrainerDTO bondingTrainerDTO);

    /**
     * Get all the bondingTrainers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BondingTrainerDTO> findAll(Pageable pageable);

    /**
     * Get all the bondingTrainers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BondingTrainerDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" bondingTrainer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BondingTrainerDTO> findOne(Long id);

    /**
     * Delete the "id" bondingTrainer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

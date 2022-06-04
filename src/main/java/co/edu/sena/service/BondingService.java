package co.edu.sena.service;

import co.edu.sena.service.dto.BondingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Bonding}.
 */
public interface BondingService {
    /**
     * Save a bonding.
     *
     * @param bondingDTO the entity to save.
     * @return the persisted entity.
     */
    BondingDTO save(BondingDTO bondingDTO);

    /**
     * Updates a bonding.
     *
     * @param bondingDTO the entity to update.
     * @return the persisted entity.
     */
    BondingDTO update(BondingDTO bondingDTO);

    /**
     * Partially updates a bonding.
     *
     * @param bondingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BondingDTO> partialUpdate(BondingDTO bondingDTO);

    /**
     * Get all the bondings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BondingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bonding.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BondingDTO> findOne(Long id);

    /**
     * Delete the "id" bonding.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

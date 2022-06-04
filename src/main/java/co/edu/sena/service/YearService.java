package co.edu.sena.service;

import co.edu.sena.service.dto.YearDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Year}.
 */
public interface YearService {
    /**
     * Save a year.
     *
     * @param yearDTO the entity to save.
     * @return the persisted entity.
     */
    YearDTO save(YearDTO yearDTO);

    /**
     * Updates a year.
     *
     * @param yearDTO the entity to update.
     * @return the persisted entity.
     */
    YearDTO update(YearDTO yearDTO);

    /**
     * Partially updates a year.
     *
     * @param yearDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<YearDTO> partialUpdate(YearDTO yearDTO);

    /**
     * Get all the years.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<YearDTO> findAll(Pageable pageable);

    /**
     * Get the "id" year.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<YearDTO> findOne(Long id);

    /**
     * Delete the "id" year.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

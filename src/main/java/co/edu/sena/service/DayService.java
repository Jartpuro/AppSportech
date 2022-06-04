package co.edu.sena.service;

import co.edu.sena.service.dto.DayDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Day}.
 */
public interface DayService {
    /**
     * Save a day.
     *
     * @param dayDTO the entity to save.
     * @return the persisted entity.
     */
    DayDTO save(DayDTO dayDTO);

    /**
     * Updates a day.
     *
     * @param dayDTO the entity to update.
     * @return the persisted entity.
     */
    DayDTO update(DayDTO dayDTO);

    /**
     * Partially updates a day.
     *
     * @param dayDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DayDTO> partialUpdate(DayDTO dayDTO);

    /**
     * Get all the days.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DayDTO> findAll(Pageable pageable);

    /**
     * Get the "id" day.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DayDTO> findOne(Long id);

    /**
     * Delete the "id" day.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

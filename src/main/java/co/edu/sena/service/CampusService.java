package co.edu.sena.service;

import co.edu.sena.service.dto.CampusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Campus}.
 */
public interface CampusService {
    /**
     * Save a campus.
     *
     * @param campusDTO the entity to save.
     * @return the persisted entity.
     */
    CampusDTO save(CampusDTO campusDTO);

    /**
     * Updates a campus.
     *
     * @param campusDTO the entity to update.
     * @return the persisted entity.
     */
    CampusDTO update(CampusDTO campusDTO);

    /**
     * Partially updates a campus.
     *
     * @param campusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CampusDTO> partialUpdate(CampusDTO campusDTO);

    /**
     * Get all the campuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" campus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampusDTO> findOne(Long id);

    /**
     * Delete the "id" campus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

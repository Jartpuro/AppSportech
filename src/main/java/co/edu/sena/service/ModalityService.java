package co.edu.sena.service;

import co.edu.sena.service.dto.ModalityDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Modality}.
 */
public interface ModalityService {
    /**
     * Save a modality.
     *
     * @param modalityDTO the entity to save.
     * @return the persisted entity.
     */
    ModalityDTO save(ModalityDTO modalityDTO);

    /**
     * Updates a modality.
     *
     * @param modalityDTO the entity to update.
     * @return the persisted entity.
     */
    ModalityDTO update(ModalityDTO modalityDTO);

    /**
     * Partially updates a modality.
     *
     * @param modalityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ModalityDTO> partialUpdate(ModalityDTO modalityDTO);

    /**
     * Get all the modalities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModalityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" modality.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModalityDTO> findOne(Long id);

    /**
     * Delete the "id" modality.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

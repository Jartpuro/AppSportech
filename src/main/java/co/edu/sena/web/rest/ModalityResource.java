package co.edu.sena.web.rest;

import co.edu.sena.repository.ModalityRepository;
import co.edu.sena.service.ModalityService;
import co.edu.sena.service.dto.ModalityDTO;
import co.edu.sena.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.sena.domain.Modality}.
 */
@RestController
@RequestMapping("/api")
public class ModalityResource {

    private final Logger log = LoggerFactory.getLogger(ModalityResource.class);

    private static final String ENTITY_NAME = "modality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModalityService modalityService;

    private final ModalityRepository modalityRepository;

    public ModalityResource(ModalityService modalityService, ModalityRepository modalityRepository) {
        this.modalityService = modalityService;
        this.modalityRepository = modalityRepository;
    }

    /**
     * {@code POST  /modalities} : Create a new modality.
     *
     * @param modalityDTO the modalityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modalityDTO, or with status {@code 400 (Bad Request)} if the modality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modalities")
    public ResponseEntity<ModalityDTO> createModality(@Valid @RequestBody ModalityDTO modalityDTO) throws URISyntaxException {
        log.debug("REST request to save Modality : {}", modalityDTO);
        if (modalityDTO.getId() != null) {
            throw new BadRequestAlertException("A new modality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModalityDTO result = modalityService.save(modalityDTO);
        return ResponseEntity
            .created(new URI("/api/modalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modalities/:id} : Updates an existing modality.
     *
     * @param id the id of the modalityDTO to save.
     * @param modalityDTO the modalityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modalityDTO,
     * or with status {@code 400 (Bad Request)} if the modalityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modalityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modalities/{id}")
    public ResponseEntity<ModalityDTO> updateModality(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ModalityDTO modalityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Modality : {}, {}", id, modalityDTO);
        if (modalityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modalityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modalityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ModalityDTO result = modalityService.update(modalityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modalityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /modalities/:id} : Partial updates given fields of an existing modality, field will ignore if it is null
     *
     * @param id the id of the modalityDTO to save.
     * @param modalityDTO the modalityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modalityDTO,
     * or with status {@code 400 (Bad Request)} if the modalityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modalityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modalityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/modalities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModalityDTO> partialUpdateModality(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ModalityDTO modalityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Modality partially : {}, {}", id, modalityDTO);
        if (modalityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modalityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modalityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModalityDTO> result = modalityService.partialUpdate(modalityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modalityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /modalities} : get all the modalities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modalities in body.
     */
    @GetMapping("/modalities")
    public ResponseEntity<List<ModalityDTO>> getAllModalities(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Modalities");
        Page<ModalityDTO> page = modalityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /modalities/:id} : get the "id" modality.
     *
     * @param id the id of the modalityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modalityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modalities/{id}")
    public ResponseEntity<ModalityDTO> getModality(@PathVariable Long id) {
        log.debug("REST request to get Modality : {}", id);
        Optional<ModalityDTO> modalityDTO = modalityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modalityDTO);
    }

    /**
     * {@code DELETE  /modalities/:id} : delete the "id" modality.
     *
     * @param id the id of the modalityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modalities/{id}")
    public ResponseEntity<Void> deleteModality(@PathVariable Long id) {
        log.debug("REST request to delete Modality : {}", id);
        modalityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

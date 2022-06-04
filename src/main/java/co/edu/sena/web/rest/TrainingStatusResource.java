package co.edu.sena.web.rest;

import co.edu.sena.repository.TrainingStatusRepository;
import co.edu.sena.service.TrainingStatusService;
import co.edu.sena.service.dto.TrainingStatusDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.TrainingStatus}.
 */
@RestController
@RequestMapping("/api")
public class TrainingStatusResource {

    private final Logger log = LoggerFactory.getLogger(TrainingStatusResource.class);

    private static final String ENTITY_NAME = "trainingStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingStatusService trainingStatusService;

    private final TrainingStatusRepository trainingStatusRepository;

    public TrainingStatusResource(TrainingStatusService trainingStatusService, TrainingStatusRepository trainingStatusRepository) {
        this.trainingStatusService = trainingStatusService;
        this.trainingStatusRepository = trainingStatusRepository;
    }

    /**
     * {@code POST  /training-statuses} : Create a new trainingStatus.
     *
     * @param trainingStatusDTO the trainingStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingStatusDTO, or with status {@code 400 (Bad Request)} if the trainingStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-statuses")
    public ResponseEntity<TrainingStatusDTO> createTrainingStatus(@Valid @RequestBody TrainingStatusDTO trainingStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save TrainingStatus : {}", trainingStatusDTO);
        if (trainingStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainingStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingStatusDTO result = trainingStatusService.save(trainingStatusDTO);
        return ResponseEntity
            .created(new URI("/api/training-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-statuses/:id} : Updates an existing trainingStatus.
     *
     * @param id the id of the trainingStatusDTO to save.
     * @param trainingStatusDTO the trainingStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingStatusDTO,
     * or with status {@code 400 (Bad Request)} if the trainingStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-statuses/{id}")
    public ResponseEntity<TrainingStatusDTO> updateTrainingStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrainingStatusDTO trainingStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrainingStatus : {}, {}", id, trainingStatusDTO);
        if (trainingStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainingStatusDTO result = trainingStatusService.update(trainingStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /training-statuses/:id} : Partial updates given fields of an existing trainingStatus, field will ignore if it is null
     *
     * @param id the id of the trainingStatusDTO to save.
     * @param trainingStatusDTO the trainingStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingStatusDTO,
     * or with status {@code 400 (Bad Request)} if the trainingStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainingStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainingStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/training-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainingStatusDTO> partialUpdateTrainingStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrainingStatusDTO trainingStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrainingStatus partially : {}, {}", id, trainingStatusDTO);
        if (trainingStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainingStatusDTO> result = trainingStatusService.partialUpdate(trainingStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /training-statuses} : get all the trainingStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingStatuses in body.
     */
    @GetMapping("/training-statuses")
    public ResponseEntity<List<TrainingStatusDTO>> getAllTrainingStatuses(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TrainingStatuses");
        Page<TrainingStatusDTO> page = trainingStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /training-statuses/:id} : get the "id" trainingStatus.
     *
     * @param id the id of the trainingStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-statuses/{id}")
    public ResponseEntity<TrainingStatusDTO> getTrainingStatus(@PathVariable Long id) {
        log.debug("REST request to get TrainingStatus : {}", id);
        Optional<TrainingStatusDTO> trainingStatusDTO = trainingStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingStatusDTO);
    }

    /**
     * {@code DELETE  /training-statuses/:id} : delete the "id" trainingStatus.
     *
     * @param id the id of the trainingStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-statuses/{id}")
    public ResponseEntity<Void> deleteTrainingStatus(@PathVariable Long id) {
        log.debug("REST request to delete TrainingStatus : {}", id);
        trainingStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

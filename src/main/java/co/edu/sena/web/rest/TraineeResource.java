package co.edu.sena.web.rest;

import co.edu.sena.repository.TraineeRepository;
import co.edu.sena.service.TraineeService;
import co.edu.sena.service.dto.TraineeDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.Trainee}.
 */
@RestController
@RequestMapping("/api")
public class TraineeResource {

    private final Logger log = LoggerFactory.getLogger(TraineeResource.class);

    private static final String ENTITY_NAME = "trainee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TraineeService traineeService;

    private final TraineeRepository traineeRepository;

    public TraineeResource(TraineeService traineeService, TraineeRepository traineeRepository) {
        this.traineeService = traineeService;
        this.traineeRepository = traineeRepository;
    }

    /**
     * {@code POST  /trainees} : Create a new trainee.
     *
     * @param traineeDTO the traineeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new traineeDTO, or with status {@code 400 (Bad Request)} if the trainee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trainees")
    public ResponseEntity<TraineeDTO> createTrainee(@Valid @RequestBody TraineeDTO traineeDTO) throws URISyntaxException {
        log.debug("REST request to save Trainee : {}", traineeDTO);
        if (traineeDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TraineeDTO result = traineeService.save(traineeDTO);
        return ResponseEntity
            .created(new URI("/api/trainees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trainees/:id} : Updates an existing trainee.
     *
     * @param id the id of the traineeDTO to save.
     * @param traineeDTO the traineeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traineeDTO,
     * or with status {@code 400 (Bad Request)} if the traineeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the traineeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trainees/{id}")
    public ResponseEntity<TraineeDTO> updateTrainee(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TraineeDTO traineeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Trainee : {}, {}", id, traineeDTO);
        if (traineeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traineeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traineeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TraineeDTO result = traineeService.update(traineeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, traineeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trainees/:id} : Partial updates given fields of an existing trainee, field will ignore if it is null
     *
     * @param id the id of the traineeDTO to save.
     * @param traineeDTO the traineeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traineeDTO,
     * or with status {@code 400 (Bad Request)} if the traineeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the traineeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the traineeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trainees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TraineeDTO> partialUpdateTrainee(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TraineeDTO traineeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Trainee partially : {}, {}", id, traineeDTO);
        if (traineeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traineeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traineeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TraineeDTO> result = traineeService.partialUpdate(traineeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, traineeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trainees} : get all the trainees.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainees in body.
     */
    @GetMapping("/trainees")
    public ResponseEntity<List<TraineeDTO>> getAllTrainees(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Trainees");
        Page<TraineeDTO> page;
        if (eagerload) {
            page = traineeService.findAllWithEagerRelationships(pageable);
        } else {
            page = traineeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trainees/:id} : get the "id" trainee.
     *
     * @param id the id of the traineeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the traineeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trainees/{id}")
    public ResponseEntity<TraineeDTO> getTrainee(@PathVariable Long id) {
        log.debug("REST request to get Trainee : {}", id);
        Optional<TraineeDTO> traineeDTO = traineeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(traineeDTO);
    }

    /**
     * {@code DELETE  /trainees/:id} : delete the "id" trainee.
     *
     * @param id the id of the traineeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trainees/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable Long id) {
        log.debug("REST request to delete Trainee : {}", id);
        traineeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

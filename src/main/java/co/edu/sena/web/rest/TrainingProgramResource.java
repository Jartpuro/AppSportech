package co.edu.sena.web.rest;

import co.edu.sena.repository.TrainingProgramRepository;
import co.edu.sena.service.TrainingProgramService;
import co.edu.sena.service.dto.TrainingProgramDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.TrainingProgram}.
 */
@RestController
@RequestMapping("/api")
public class TrainingProgramResource {

    private final Logger log = LoggerFactory.getLogger(TrainingProgramResource.class);

    private static final String ENTITY_NAME = "trainingProgram";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingProgramService trainingProgramService;

    private final TrainingProgramRepository trainingProgramRepository;

    public TrainingProgramResource(TrainingProgramService trainingProgramService, TrainingProgramRepository trainingProgramRepository) {
        this.trainingProgramService = trainingProgramService;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    /**
     * {@code POST  /training-programs} : Create a new trainingProgram.
     *
     * @param trainingProgramDTO the trainingProgramDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingProgramDTO, or with status {@code 400 (Bad Request)} if the trainingProgram has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-programs")
    public ResponseEntity<TrainingProgramDTO> createTrainingProgram(@Valid @RequestBody TrainingProgramDTO trainingProgramDTO)
        throws URISyntaxException {
        log.debug("REST request to save TrainingProgram : {}", trainingProgramDTO);
        if (trainingProgramDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainingProgram cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingProgramDTO result = trainingProgramService.save(trainingProgramDTO);
        return ResponseEntity
            .created(new URI("/api/training-programs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-programs/:id} : Updates an existing trainingProgram.
     *
     * @param id the id of the trainingProgramDTO to save.
     * @param trainingProgramDTO the trainingProgramDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingProgramDTO,
     * or with status {@code 400 (Bad Request)} if the trainingProgramDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingProgramDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-programs/{id}")
    public ResponseEntity<TrainingProgramDTO> updateTrainingProgram(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrainingProgramDTO trainingProgramDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrainingProgram : {}, {}", id, trainingProgramDTO);
        if (trainingProgramDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingProgramDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingProgramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainingProgramDTO result = trainingProgramService.update(trainingProgramDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingProgramDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /training-programs/:id} : Partial updates given fields of an existing trainingProgram, field will ignore if it is null
     *
     * @param id the id of the trainingProgramDTO to save.
     * @param trainingProgramDTO the trainingProgramDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingProgramDTO,
     * or with status {@code 400 (Bad Request)} if the trainingProgramDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainingProgramDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainingProgramDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/training-programs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainingProgramDTO> partialUpdateTrainingProgram(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrainingProgramDTO trainingProgramDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrainingProgram partially : {}, {}", id, trainingProgramDTO);
        if (trainingProgramDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingProgramDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingProgramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainingProgramDTO> result = trainingProgramService.partialUpdate(trainingProgramDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingProgramDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /training-programs} : get all the trainingPrograms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingPrograms in body.
     */
    @GetMapping("/training-programs")
    public ResponseEntity<List<TrainingProgramDTO>> getAllTrainingPrograms(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TrainingPrograms");
        Page<TrainingProgramDTO> page = trainingProgramService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /training-programs/:id} : get the "id" trainingProgram.
     *
     * @param id the id of the trainingProgramDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingProgramDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-programs/{id}")
    public ResponseEntity<TrainingProgramDTO> getTrainingProgram(@PathVariable Long id) {
        log.debug("REST request to get TrainingProgram : {}", id);
        Optional<TrainingProgramDTO> trainingProgramDTO = trainingProgramService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingProgramDTO);
    }

    /**
     * {@code DELETE  /training-programs/:id} : delete the "id" trainingProgram.
     *
     * @param id the id of the trainingProgramDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-programs/{id}")
    public ResponseEntity<Void> deleteTrainingProgram(@PathVariable Long id) {
        log.debug("REST request to delete TrainingProgram : {}", id);
        trainingProgramService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

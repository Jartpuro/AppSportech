package co.edu.sena.web.rest;

import co.edu.sena.repository.BoundingScheduleRepository;
import co.edu.sena.service.BoundingScheduleService;
import co.edu.sena.service.dto.BoundingScheduleDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.BoundingSchedule}.
 */
@RestController
@RequestMapping("/api")
public class BoundingScheduleResource {

    private final Logger log = LoggerFactory.getLogger(BoundingScheduleResource.class);

    private static final String ENTITY_NAME = "boundingSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoundingScheduleService boundingScheduleService;

    private final BoundingScheduleRepository boundingScheduleRepository;

    public BoundingScheduleResource(
        BoundingScheduleService boundingScheduleService,
        BoundingScheduleRepository boundingScheduleRepository
    ) {
        this.boundingScheduleService = boundingScheduleService;
        this.boundingScheduleRepository = boundingScheduleRepository;
    }

    /**
     * {@code POST  /bounding-schedules} : Create a new boundingSchedule.
     *
     * @param boundingScheduleDTO the boundingScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boundingScheduleDTO, or with status {@code 400 (Bad Request)} if the boundingSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bounding-schedules")
    public ResponseEntity<BoundingScheduleDTO> createBoundingSchedule(@Valid @RequestBody BoundingScheduleDTO boundingScheduleDTO)
        throws URISyntaxException {
        log.debug("REST request to save BoundingSchedule : {}", boundingScheduleDTO);
        if (boundingScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new boundingSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoundingScheduleDTO result = boundingScheduleService.save(boundingScheduleDTO);
        return ResponseEntity
            .created(new URI("/api/bounding-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bounding-schedules/:id} : Updates an existing boundingSchedule.
     *
     * @param id the id of the boundingScheduleDTO to save.
     * @param boundingScheduleDTO the boundingScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boundingScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the boundingScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boundingScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bounding-schedules/{id}")
    public ResponseEntity<BoundingScheduleDTO> updateBoundingSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BoundingScheduleDTO boundingScheduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BoundingSchedule : {}, {}", id, boundingScheduleDTO);
        if (boundingScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boundingScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boundingScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BoundingScheduleDTO result = boundingScheduleService.update(boundingScheduleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boundingScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bounding-schedules/:id} : Partial updates given fields of an existing boundingSchedule, field will ignore if it is null
     *
     * @param id the id of the boundingScheduleDTO to save.
     * @param boundingScheduleDTO the boundingScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boundingScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the boundingScheduleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the boundingScheduleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the boundingScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bounding-schedules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BoundingScheduleDTO> partialUpdateBoundingSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BoundingScheduleDTO boundingScheduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BoundingSchedule partially : {}, {}", id, boundingScheduleDTO);
        if (boundingScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boundingScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boundingScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BoundingScheduleDTO> result = boundingScheduleService.partialUpdate(boundingScheduleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boundingScheduleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bounding-schedules} : get all the boundingSchedules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boundingSchedules in body.
     */
    @GetMapping("/bounding-schedules")
    public ResponseEntity<List<BoundingScheduleDTO>> getAllBoundingSchedules(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BoundingSchedules");
        Page<BoundingScheduleDTO> page = boundingScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bounding-schedules/:id} : get the "id" boundingSchedule.
     *
     * @param id the id of the boundingScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boundingScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bounding-schedules/{id}")
    public ResponseEntity<BoundingScheduleDTO> getBoundingSchedule(@PathVariable Long id) {
        log.debug("REST request to get BoundingSchedule : {}", id);
        Optional<BoundingScheduleDTO> boundingScheduleDTO = boundingScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boundingScheduleDTO);
    }

    /**
     * {@code DELETE  /bounding-schedules/:id} : delete the "id" boundingSchedule.
     *
     * @param id the id of the boundingScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bounding-schedules/{id}")
    public ResponseEntity<Void> deleteBoundingSchedule(@PathVariable Long id) {
        log.debug("REST request to delete BoundingSchedule : {}", id);
        boundingScheduleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package co.edu.sena.web.rest;

import co.edu.sena.repository.ScheduleVersionRepository;
import co.edu.sena.service.ScheduleVersionService;
import co.edu.sena.service.dto.ScheduleVersionDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.ScheduleVersion}.
 */
@RestController
@RequestMapping("/api")
public class ScheduleVersionResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleVersionResource.class);

    private static final String ENTITY_NAME = "scheduleVersion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduleVersionService scheduleVersionService;

    private final ScheduleVersionRepository scheduleVersionRepository;

    public ScheduleVersionResource(ScheduleVersionService scheduleVersionService, ScheduleVersionRepository scheduleVersionRepository) {
        this.scheduleVersionService = scheduleVersionService;
        this.scheduleVersionRepository = scheduleVersionRepository;
    }

    /**
     * {@code POST  /schedule-versions} : Create a new scheduleVersion.
     *
     * @param scheduleVersionDTO the scheduleVersionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheduleVersionDTO, or with status {@code 400 (Bad Request)} if the scheduleVersion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schedule-versions")
    public ResponseEntity<ScheduleVersionDTO> createScheduleVersion(@Valid @RequestBody ScheduleVersionDTO scheduleVersionDTO)
        throws URISyntaxException {
        log.debug("REST request to save ScheduleVersion : {}", scheduleVersionDTO);
        if (scheduleVersionDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheduleVersion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleVersionDTO result = scheduleVersionService.save(scheduleVersionDTO);
        return ResponseEntity
            .created(new URI("/api/schedule-versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schedule-versions/:id} : Updates an existing scheduleVersion.
     *
     * @param id the id of the scheduleVersionDTO to save.
     * @param scheduleVersionDTO the scheduleVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduleVersionDTO,
     * or with status {@code 400 (Bad Request)} if the scheduleVersionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheduleVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schedule-versions/{id}")
    public ResponseEntity<ScheduleVersionDTO> updateScheduleVersion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ScheduleVersionDTO scheduleVersionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ScheduleVersion : {}, {}", id, scheduleVersionDTO);
        if (scheduleVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scheduleVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scheduleVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScheduleVersionDTO result = scheduleVersionService.update(scheduleVersionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scheduleVersionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /schedule-versions/:id} : Partial updates given fields of an existing scheduleVersion, field will ignore if it is null
     *
     * @param id the id of the scheduleVersionDTO to save.
     * @param scheduleVersionDTO the scheduleVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduleVersionDTO,
     * or with status {@code 400 (Bad Request)} if the scheduleVersionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the scheduleVersionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the scheduleVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/schedule-versions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScheduleVersionDTO> partialUpdateScheduleVersion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ScheduleVersionDTO scheduleVersionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScheduleVersion partially : {}, {}", id, scheduleVersionDTO);
        if (scheduleVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scheduleVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scheduleVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScheduleVersionDTO> result = scheduleVersionService.partialUpdate(scheduleVersionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scheduleVersionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /schedule-versions} : get all the scheduleVersions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scheduleVersions in body.
     */
    @GetMapping("/schedule-versions")
    public ResponseEntity<List<ScheduleVersionDTO>> getAllScheduleVersions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ScheduleVersions");
        Page<ScheduleVersionDTO> page = scheduleVersionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schedule-versions/:id} : get the "id" scheduleVersion.
     *
     * @param id the id of the scheduleVersionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheduleVersionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schedule-versions/{id}")
    public ResponseEntity<ScheduleVersionDTO> getScheduleVersion(@PathVariable Long id) {
        log.debug("REST request to get ScheduleVersion : {}", id);
        Optional<ScheduleVersionDTO> scheduleVersionDTO = scheduleVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleVersionDTO);
    }

    /**
     * {@code DELETE  /schedule-versions/:id} : delete the "id" scheduleVersion.
     *
     * @param id the id of the scheduleVersionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schedule-versions/{id}")
    public ResponseEntity<Void> deleteScheduleVersion(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleVersion : {}", id);
        scheduleVersionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

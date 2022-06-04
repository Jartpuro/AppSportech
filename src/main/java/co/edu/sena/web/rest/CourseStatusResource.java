package co.edu.sena.web.rest;

import co.edu.sena.repository.CourseStatusRepository;
import co.edu.sena.service.CourseStatusService;
import co.edu.sena.service.dto.CourseStatusDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.CourseStatus}.
 */
@RestController
@RequestMapping("/api")
public class CourseStatusResource {

    private final Logger log = LoggerFactory.getLogger(CourseStatusResource.class);

    private static final String ENTITY_NAME = "courseStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseStatusService courseStatusService;

    private final CourseStatusRepository courseStatusRepository;

    public CourseStatusResource(CourseStatusService courseStatusService, CourseStatusRepository courseStatusRepository) {
        this.courseStatusService = courseStatusService;
        this.courseStatusRepository = courseStatusRepository;
    }

    /**
     * {@code POST  /course-statuses} : Create a new courseStatus.
     *
     * @param courseStatusDTO the courseStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseStatusDTO, or with status {@code 400 (Bad Request)} if the courseStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-statuses")
    public ResponseEntity<CourseStatusDTO> createCourseStatus(@Valid @RequestBody CourseStatusDTO courseStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save CourseStatus : {}", courseStatusDTO);
        if (courseStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseStatusDTO result = courseStatusService.save(courseStatusDTO);
        return ResponseEntity
            .created(new URI("/api/course-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-statuses/:id} : Updates an existing courseStatus.
     *
     * @param id the id of the courseStatusDTO to save.
     * @param courseStatusDTO the courseStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseStatusDTO,
     * or with status {@code 400 (Bad Request)} if the courseStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-statuses/{id}")
    public ResponseEntity<CourseStatusDTO> updateCourseStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseStatusDTO courseStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseStatus : {}, {}", id, courseStatusDTO);
        if (courseStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseStatusDTO result = courseStatusService.update(courseStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-statuses/:id} : Partial updates given fields of an existing courseStatus, field will ignore if it is null
     *
     * @param id the id of the courseStatusDTO to save.
     * @param courseStatusDTO the courseStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseStatusDTO,
     * or with status {@code 400 (Bad Request)} if the courseStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseStatusDTO> partialUpdateCourseStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseStatusDTO courseStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseStatus partially : {}, {}", id, courseStatusDTO);
        if (courseStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseStatusDTO> result = courseStatusService.partialUpdate(courseStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-statuses} : get all the courseStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseStatuses in body.
     */
    @GetMapping("/course-statuses")
    public ResponseEntity<List<CourseStatusDTO>> getAllCourseStatuses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CourseStatuses");
        Page<CourseStatusDTO> page = courseStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-statuses/:id} : get the "id" courseStatus.
     *
     * @param id the id of the courseStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-statuses/{id}")
    public ResponseEntity<CourseStatusDTO> getCourseStatus(@PathVariable Long id) {
        log.debug("REST request to get CourseStatus : {}", id);
        Optional<CourseStatusDTO> courseStatusDTO = courseStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseStatusDTO);
    }

    /**
     * {@code DELETE  /course-statuses/:id} : delete the "id" courseStatus.
     *
     * @param id the id of the courseStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-statuses/{id}")
    public ResponseEntity<Void> deleteCourseStatus(@PathVariable Long id) {
        log.debug("REST request to delete CourseStatus : {}", id);
        courseStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package co.edu.sena.web.rest;

import co.edu.sena.repository.CourseModuleRepository;
import co.edu.sena.service.CourseModuleService;
import co.edu.sena.service.dto.CourseModuleDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.CourseModule}.
 */
@RestController
@RequestMapping("/api")
public class CourseModuleResource {

    private final Logger log = LoggerFactory.getLogger(CourseModuleResource.class);

    private static final String ENTITY_NAME = "courseModule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseModuleService courseModuleService;

    private final CourseModuleRepository courseModuleRepository;

    public CourseModuleResource(CourseModuleService courseModuleService, CourseModuleRepository courseModuleRepository) {
        this.courseModuleService = courseModuleService;
        this.courseModuleRepository = courseModuleRepository;
    }

    /**
     * {@code POST  /course-modules} : Create a new courseModule.
     *
     * @param courseModuleDTO the courseModuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseModuleDTO, or with status {@code 400 (Bad Request)} if the courseModule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-modules")
    public ResponseEntity<CourseModuleDTO> createCourseModule(@Valid @RequestBody CourseModuleDTO courseModuleDTO)
        throws URISyntaxException {
        log.debug("REST request to save CourseModule : {}", courseModuleDTO);
        if (courseModuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseModuleDTO result = courseModuleService.save(courseModuleDTO);
        return ResponseEntity
            .created(new URI("/api/course-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-modules/:id} : Updates an existing courseModule.
     *
     * @param id the id of the courseModuleDTO to save.
     * @param courseModuleDTO the courseModuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseModuleDTO,
     * or with status {@code 400 (Bad Request)} if the courseModuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseModuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-modules/{id}")
    public ResponseEntity<CourseModuleDTO> updateCourseModule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseModuleDTO courseModuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseModule : {}, {}", id, courseModuleDTO);
        if (courseModuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseModuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseModuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseModuleDTO result = courseModuleService.update(courseModuleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseModuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-modules/:id} : Partial updates given fields of an existing courseModule, field will ignore if it is null
     *
     * @param id the id of the courseModuleDTO to save.
     * @param courseModuleDTO the courseModuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseModuleDTO,
     * or with status {@code 400 (Bad Request)} if the courseModuleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseModuleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseModuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-modules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseModuleDTO> partialUpdateCourseModule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseModuleDTO courseModuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseModule partially : {}, {}", id, courseModuleDTO);
        if (courseModuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseModuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseModuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseModuleDTO> result = courseModuleService.partialUpdate(courseModuleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseModuleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-modules} : get all the courseModules.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseModules in body.
     */
    @GetMapping("/course-modules")
    public ResponseEntity<List<CourseModuleDTO>> getAllCourseModules(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of CourseModules");
        Page<CourseModuleDTO> page;
        if (eagerload) {
            page = courseModuleService.findAllWithEagerRelationships(pageable);
        } else {
            page = courseModuleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-modules/:id} : get the "id" courseModule.
     *
     * @param id the id of the courseModuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseModuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-modules/{id}")
    public ResponseEntity<CourseModuleDTO> getCourseModule(@PathVariable Long id) {
        log.debug("REST request to get CourseModule : {}", id);
        Optional<CourseModuleDTO> courseModuleDTO = courseModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseModuleDTO);
    }

    /**
     * {@code DELETE  /course-modules/:id} : delete the "id" courseModule.
     *
     * @param id the id of the courseModuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-modules/{id}")
    public ResponseEntity<Void> deleteCourseModule(@PathVariable Long id) {
        log.debug("REST request to delete CourseModule : {}", id);
        courseModuleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

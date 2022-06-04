package co.edu.sena.web.rest;

import co.edu.sena.repository.ClassroomTypeRepository;
import co.edu.sena.service.ClassroomTypeService;
import co.edu.sena.service.dto.ClassroomTypeDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.ClassroomType}.
 */
@RestController
@RequestMapping("/api")
public class ClassroomTypeResource {

    private final Logger log = LoggerFactory.getLogger(ClassroomTypeResource.class);

    private static final String ENTITY_NAME = "classroomType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassroomTypeService classroomTypeService;

    private final ClassroomTypeRepository classroomTypeRepository;

    public ClassroomTypeResource(ClassroomTypeService classroomTypeService, ClassroomTypeRepository classroomTypeRepository) {
        this.classroomTypeService = classroomTypeService;
        this.classroomTypeRepository = classroomTypeRepository;
    }

    /**
     * {@code POST  /classroom-types} : Create a new classroomType.
     *
     * @param classroomTypeDTO the classroomTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classroomTypeDTO, or with status {@code 400 (Bad Request)} if the classroomType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classroom-types")
    public ResponseEntity<ClassroomTypeDTO> createClassroomType(@Valid @RequestBody ClassroomTypeDTO classroomTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save ClassroomType : {}", classroomTypeDTO);
        if (classroomTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new classroomType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassroomTypeDTO result = classroomTypeService.save(classroomTypeDTO);
        return ResponseEntity
            .created(new URI("/api/classroom-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classroom-types/:id} : Updates an existing classroomType.
     *
     * @param id the id of the classroomTypeDTO to save.
     * @param classroomTypeDTO the classroomTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classroomTypeDTO,
     * or with status {@code 400 (Bad Request)} if the classroomTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classroomTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classroom-types/{id}")
    public ResponseEntity<ClassroomTypeDTO> updateClassroomType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClassroomTypeDTO classroomTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClassroomType : {}, {}", id, classroomTypeDTO);
        if (classroomTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classroomTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classroomTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClassroomTypeDTO result = classroomTypeService.update(classroomTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classroomTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /classroom-types/:id} : Partial updates given fields of an existing classroomType, field will ignore if it is null
     *
     * @param id the id of the classroomTypeDTO to save.
     * @param classroomTypeDTO the classroomTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classroomTypeDTO,
     * or with status {@code 400 (Bad Request)} if the classroomTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classroomTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classroomTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/classroom-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClassroomTypeDTO> partialUpdateClassroomType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClassroomTypeDTO classroomTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClassroomType partially : {}, {}", id, classroomTypeDTO);
        if (classroomTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classroomTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classroomTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClassroomTypeDTO> result = classroomTypeService.partialUpdate(classroomTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classroomTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /classroom-types} : get all the classroomTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classroomTypes in body.
     */
    @GetMapping("/classroom-types")
    public ResponseEntity<List<ClassroomTypeDTO>> getAllClassroomTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ClassroomTypes");
        Page<ClassroomTypeDTO> page = classroomTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /classroom-types/:id} : get the "id" classroomType.
     *
     * @param id the id of the classroomTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classroomTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classroom-types/{id}")
    public ResponseEntity<ClassroomTypeDTO> getClassroomType(@PathVariable Long id) {
        log.debug("REST request to get ClassroomType : {}", id);
        Optional<ClassroomTypeDTO> classroomTypeDTO = classroomTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classroomTypeDTO);
    }

    /**
     * {@code DELETE  /classroom-types/:id} : delete the "id" classroomType.
     *
     * @param id the id of the classroomTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classroom-types/{id}")
    public ResponseEntity<Void> deleteClassroomType(@PathVariable Long id) {
        log.debug("REST request to delete ClassroomType : {}", id);
        classroomTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

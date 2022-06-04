package co.edu.sena.web.rest;

import co.edu.sena.repository.CampusRepository;
import co.edu.sena.service.CampusService;
import co.edu.sena.service.dto.CampusDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.Campus}.
 */
@RestController
@RequestMapping("/api")
public class CampusResource {

    private final Logger log = LoggerFactory.getLogger(CampusResource.class);

    private static final String ENTITY_NAME = "campus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampusService campusService;

    private final CampusRepository campusRepository;

    public CampusResource(CampusService campusService, CampusRepository campusRepository) {
        this.campusService = campusService;
        this.campusRepository = campusRepository;
    }

    /**
     * {@code POST  /campuses} : Create a new campus.
     *
     * @param campusDTO the campusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campusDTO, or with status {@code 400 (Bad Request)} if the campus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campuses")
    public ResponseEntity<CampusDTO> createCampus(@Valid @RequestBody CampusDTO campusDTO) throws URISyntaxException {
        log.debug("REST request to save Campus : {}", campusDTO);
        if (campusDTO.getId() != null) {
            throw new BadRequestAlertException("A new campus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampusDTO result = campusService.save(campusDTO);
        return ResponseEntity
            .created(new URI("/api/campuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campuses/:id} : Updates an existing campus.
     *
     * @param id the id of the campusDTO to save.
     * @param campusDTO the campusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campusDTO,
     * or with status {@code 400 (Bad Request)} if the campusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campuses/{id}")
    public ResponseEntity<CampusDTO> updateCampus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CampusDTO campusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Campus : {}, {}", id, campusDTO);
        if (campusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CampusDTO result = campusService.update(campusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /campuses/:id} : Partial updates given fields of an existing campus, field will ignore if it is null
     *
     * @param id the id of the campusDTO to save.
     * @param campusDTO the campusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campusDTO,
     * or with status {@code 400 (Bad Request)} if the campusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the campusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the campusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/campuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CampusDTO> partialUpdateCampus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CampusDTO campusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Campus partially : {}, {}", id, campusDTO);
        if (campusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CampusDTO> result = campusService.partialUpdate(campusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /campuses} : get all the campuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campuses in body.
     */
    @GetMapping("/campuses")
    public ResponseEntity<List<CampusDTO>> getAllCampuses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Campuses");
        Page<CampusDTO> page = campusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campuses/:id} : get the "id" campus.
     *
     * @param id the id of the campusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campuses/{id}")
    public ResponseEntity<CampusDTO> getCampus(@PathVariable Long id) {
        log.debug("REST request to get Campus : {}", id);
        Optional<CampusDTO> campusDTO = campusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campusDTO);
    }

    /**
     * {@code DELETE  /campuses/:id} : delete the "id" campus.
     *
     * @param id the id of the campusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campuses/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable Long id) {
        log.debug("REST request to delete Campus : {}", id);
        campusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

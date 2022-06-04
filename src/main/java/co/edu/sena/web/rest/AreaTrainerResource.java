package co.edu.sena.web.rest;

import co.edu.sena.repository.AreaTrainerRepository;
import co.edu.sena.service.AreaTrainerService;
import co.edu.sena.service.dto.AreaTrainerDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.AreaTrainer}.
 */
@RestController
@RequestMapping("/api")
public class AreaTrainerResource {

    private final Logger log = LoggerFactory.getLogger(AreaTrainerResource.class);

    private static final String ENTITY_NAME = "areaTrainer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaTrainerService areaTrainerService;

    private final AreaTrainerRepository areaTrainerRepository;

    public AreaTrainerResource(AreaTrainerService areaTrainerService, AreaTrainerRepository areaTrainerRepository) {
        this.areaTrainerService = areaTrainerService;
        this.areaTrainerRepository = areaTrainerRepository;
    }

    /**
     * {@code POST  /area-trainers} : Create a new areaTrainer.
     *
     * @param areaTrainerDTO the areaTrainerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaTrainerDTO, or with status {@code 400 (Bad Request)} if the areaTrainer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/area-trainers")
    public ResponseEntity<AreaTrainerDTO> createAreaTrainer(@Valid @RequestBody AreaTrainerDTO areaTrainerDTO) throws URISyntaxException {
        log.debug("REST request to save AreaTrainer : {}", areaTrainerDTO);
        if (areaTrainerDTO.getId() != null) {
            throw new BadRequestAlertException("A new areaTrainer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaTrainerDTO result = areaTrainerService.save(areaTrainerDTO);
        return ResponseEntity
            .created(new URI("/api/area-trainers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /area-trainers/:id} : Updates an existing areaTrainer.
     *
     * @param id the id of the areaTrainerDTO to save.
     * @param areaTrainerDTO the areaTrainerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaTrainerDTO,
     * or with status {@code 400 (Bad Request)} if the areaTrainerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaTrainerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/area-trainers/{id}")
    public ResponseEntity<AreaTrainerDTO> updateAreaTrainer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AreaTrainerDTO areaTrainerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AreaTrainer : {}, {}", id, areaTrainerDTO);
        if (areaTrainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaTrainerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaTrainerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AreaTrainerDTO result = areaTrainerService.update(areaTrainerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaTrainerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /area-trainers/:id} : Partial updates given fields of an existing areaTrainer, field will ignore if it is null
     *
     * @param id the id of the areaTrainerDTO to save.
     * @param areaTrainerDTO the areaTrainerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaTrainerDTO,
     * or with status {@code 400 (Bad Request)} if the areaTrainerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the areaTrainerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaTrainerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/area-trainers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AreaTrainerDTO> partialUpdateAreaTrainer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AreaTrainerDTO areaTrainerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AreaTrainer partially : {}, {}", id, areaTrainerDTO);
        if (areaTrainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaTrainerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaTrainerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaTrainerDTO> result = areaTrainerService.partialUpdate(areaTrainerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaTrainerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /area-trainers} : get all the areaTrainers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaTrainers in body.
     */
    @GetMapping("/area-trainers")
    public ResponseEntity<List<AreaTrainerDTO>> getAllAreaTrainers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of AreaTrainers");
        Page<AreaTrainerDTO> page;
        if (eagerload) {
            page = areaTrainerService.findAllWithEagerRelationships(pageable);
        } else {
            page = areaTrainerService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /area-trainers/:id} : get the "id" areaTrainer.
     *
     * @param id the id of the areaTrainerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaTrainerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/area-trainers/{id}")
    public ResponseEntity<AreaTrainerDTO> getAreaTrainer(@PathVariable Long id) {
        log.debug("REST request to get AreaTrainer : {}", id);
        Optional<AreaTrainerDTO> areaTrainerDTO = areaTrainerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaTrainerDTO);
    }

    /**
     * {@code DELETE  /area-trainers/:id} : delete the "id" areaTrainer.
     *
     * @param id the id of the areaTrainerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/area-trainers/{id}")
    public ResponseEntity<Void> deleteAreaTrainer(@PathVariable Long id) {
        log.debug("REST request to delete AreaTrainer : {}", id);
        areaTrainerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

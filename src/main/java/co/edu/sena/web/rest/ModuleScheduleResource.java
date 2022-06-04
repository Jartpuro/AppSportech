package co.edu.sena.web.rest;

import co.edu.sena.repository.ModuleScheduleRepository;
import co.edu.sena.service.ModuleScheduleService;
import co.edu.sena.service.dto.ModuleScheduleDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.ModuleSchedule}.
 */
@RestController
@RequestMapping("/api")
public class ModuleScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ModuleScheduleResource.class);

    private static final String ENTITY_NAME = "moduleSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuleScheduleService moduleScheduleService;

    private final ModuleScheduleRepository moduleScheduleRepository;

    public ModuleScheduleResource(ModuleScheduleService moduleScheduleService, ModuleScheduleRepository moduleScheduleRepository) {
        this.moduleScheduleService = moduleScheduleService;
        this.moduleScheduleRepository = moduleScheduleRepository;
    }

    /**
     * {@code POST  /module-schedules} : Create a new moduleSchedule.
     *
     * @param moduleScheduleDTO the moduleScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moduleScheduleDTO, or with status {@code 400 (Bad Request)} if the moduleSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/module-schedules")
    public ResponseEntity<ModuleScheduleDTO> createModuleSchedule(@Valid @RequestBody ModuleScheduleDTO moduleScheduleDTO)
        throws URISyntaxException {
        log.debug("REST request to save ModuleSchedule : {}", moduleScheduleDTO);
        if (moduleScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new moduleSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModuleScheduleDTO result = moduleScheduleService.save(moduleScheduleDTO);
        return ResponseEntity
            .created(new URI("/api/module-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /module-schedules/:id} : Updates an existing moduleSchedule.
     *
     * @param id the id of the moduleScheduleDTO to save.
     * @param moduleScheduleDTO the moduleScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the moduleScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moduleScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/module-schedules/{id}")
    public ResponseEntity<ModuleScheduleDTO> updateModuleSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ModuleScheduleDTO moduleScheduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ModuleSchedule : {}, {}", id, moduleScheduleDTO);
        if (moduleScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moduleScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ModuleScheduleDTO result = moduleScheduleService.update(moduleScheduleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moduleScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /module-schedules/:id} : Partial updates given fields of an existing moduleSchedule, field will ignore if it is null
     *
     * @param id the id of the moduleScheduleDTO to save.
     * @param moduleScheduleDTO the moduleScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the moduleScheduleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moduleScheduleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moduleScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/module-schedules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModuleScheduleDTO> partialUpdateModuleSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ModuleScheduleDTO moduleScheduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModuleSchedule partially : {}, {}", id, moduleScheduleDTO);
        if (moduleScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moduleScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModuleScheduleDTO> result = moduleScheduleService.partialUpdate(moduleScheduleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moduleScheduleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /module-schedules} : get all the moduleSchedules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moduleSchedules in body.
     */
    @GetMapping("/module-schedules")
    public ResponseEntity<List<ModuleScheduleDTO>> getAllModuleSchedules(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ModuleSchedules");
        Page<ModuleScheduleDTO> page = moduleScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /module-schedules/:id} : get the "id" moduleSchedule.
     *
     * @param id the id of the moduleScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moduleScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/module-schedules/{id}")
    public ResponseEntity<ModuleScheduleDTO> getModuleSchedule(@PathVariable Long id) {
        log.debug("REST request to get ModuleSchedule : {}", id);
        Optional<ModuleScheduleDTO> moduleScheduleDTO = moduleScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moduleScheduleDTO);
    }

    /**
     * {@code DELETE  /module-schedules/:id} : delete the "id" moduleSchedule.
     *
     * @param id the id of the moduleScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/module-schedules/{id}")
    public ResponseEntity<Void> deleteModuleSchedule(@PathVariable Long id) {
        log.debug("REST request to delete ModuleSchedule : {}", id);
        moduleScheduleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

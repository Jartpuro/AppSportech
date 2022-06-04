package co.edu.sena.web.rest;

import co.edu.sena.repository.LogAuditRepository;
import co.edu.sena.service.LogAuditService;
import co.edu.sena.service.dto.LogAuditDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.LogAudit}.
 */
@RestController
@RequestMapping("/api")
public class LogAuditResource {

    private final Logger log = LoggerFactory.getLogger(LogAuditResource.class);

    private static final String ENTITY_NAME = "logAudit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogAuditService logAuditService;

    private final LogAuditRepository logAuditRepository;

    public LogAuditResource(LogAuditService logAuditService, LogAuditRepository logAuditRepository) {
        this.logAuditService = logAuditService;
        this.logAuditRepository = logAuditRepository;
    }

    /**
     * {@code POST  /log-audits} : Create a new logAudit.
     *
     * @param logAuditDTO the logAuditDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logAuditDTO, or with status {@code 400 (Bad Request)} if the logAudit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/log-audits")
    public ResponseEntity<LogAuditDTO> createLogAudit(@Valid @RequestBody LogAuditDTO logAuditDTO) throws URISyntaxException {
        log.debug("REST request to save LogAudit : {}", logAuditDTO);
        if (logAuditDTO.getId() != null) {
            throw new BadRequestAlertException("A new logAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogAuditDTO result = logAuditService.save(logAuditDTO);
        return ResponseEntity
            .created(new URI("/api/log-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /log-audits/:id} : Updates an existing logAudit.
     *
     * @param id the id of the logAuditDTO to save.
     * @param logAuditDTO the logAuditDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logAuditDTO,
     * or with status {@code 400 (Bad Request)} if the logAuditDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logAuditDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/log-audits/{id}")
    public ResponseEntity<LogAuditDTO> updateLogAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LogAuditDTO logAuditDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LogAudit : {}, {}", id, logAuditDTO);
        if (logAuditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, logAuditDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!logAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LogAuditDTO result = logAuditService.update(logAuditDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logAuditDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /log-audits/:id} : Partial updates given fields of an existing logAudit, field will ignore if it is null
     *
     * @param id the id of the logAuditDTO to save.
     * @param logAuditDTO the logAuditDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logAuditDTO,
     * or with status {@code 400 (Bad Request)} if the logAuditDTO is not valid,
     * or with status {@code 404 (Not Found)} if the logAuditDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the logAuditDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/log-audits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LogAuditDTO> partialUpdateLogAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LogAuditDTO logAuditDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LogAudit partially : {}, {}", id, logAuditDTO);
        if (logAuditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, logAuditDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!logAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LogAuditDTO> result = logAuditService.partialUpdate(logAuditDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logAuditDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /log-audits} : get all the logAudits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logAudits in body.
     */
    @GetMapping("/log-audits")
    public ResponseEntity<List<LogAuditDTO>> getAllLogAudits(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LogAudits");
        Page<LogAuditDTO> page = logAuditService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /log-audits/:id} : get the "id" logAudit.
     *
     * @param id the id of the logAuditDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logAuditDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/log-audits/{id}")
    public ResponseEntity<LogAuditDTO> getLogAudit(@PathVariable Long id) {
        log.debug("REST request to get LogAudit : {}", id);
        Optional<LogAuditDTO> logAuditDTO = logAuditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logAuditDTO);
    }

    /**
     * {@code DELETE  /log-audits/:id} : delete the "id" logAudit.
     *
     * @param id the id of the logAuditDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/log-audits/{id}")
    public ResponseEntity<Void> deleteLogAudit(@PathVariable Long id) {
        log.debug("REST request to delete LogAudit : {}", id);
        logAuditService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

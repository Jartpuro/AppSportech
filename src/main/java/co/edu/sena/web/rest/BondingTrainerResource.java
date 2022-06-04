package co.edu.sena.web.rest;

import co.edu.sena.repository.BondingTrainerRepository;
import co.edu.sena.service.BondingTrainerService;
import co.edu.sena.service.dto.BondingTrainerDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.BondingTrainer}.
 */
@RestController
@RequestMapping("/api")
public class BondingTrainerResource {

    private final Logger log = LoggerFactory.getLogger(BondingTrainerResource.class);

    private static final String ENTITY_NAME = "bondingTrainer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BondingTrainerService bondingTrainerService;

    private final BondingTrainerRepository bondingTrainerRepository;

    public BondingTrainerResource(BondingTrainerService bondingTrainerService, BondingTrainerRepository bondingTrainerRepository) {
        this.bondingTrainerService = bondingTrainerService;
        this.bondingTrainerRepository = bondingTrainerRepository;
    }

    /**
     * {@code POST  /bonding-trainers} : Create a new bondingTrainer.
     *
     * @param bondingTrainerDTO the bondingTrainerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bondingTrainerDTO, or with status {@code 400 (Bad Request)} if the bondingTrainer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bonding-trainers")
    public ResponseEntity<BondingTrainerDTO> createBondingTrainer(@Valid @RequestBody BondingTrainerDTO bondingTrainerDTO)
        throws URISyntaxException {
        log.debug("REST request to save BondingTrainer : {}", bondingTrainerDTO);
        if (bondingTrainerDTO.getId() != null) {
            throw new BadRequestAlertException("A new bondingTrainer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BondingTrainerDTO result = bondingTrainerService.save(bondingTrainerDTO);
        return ResponseEntity
            .created(new URI("/api/bonding-trainers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bonding-trainers/:id} : Updates an existing bondingTrainer.
     *
     * @param id the id of the bondingTrainerDTO to save.
     * @param bondingTrainerDTO the bondingTrainerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bondingTrainerDTO,
     * or with status {@code 400 (Bad Request)} if the bondingTrainerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bondingTrainerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bonding-trainers/{id}")
    public ResponseEntity<BondingTrainerDTO> updateBondingTrainer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BondingTrainerDTO bondingTrainerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BondingTrainer : {}, {}", id, bondingTrainerDTO);
        if (bondingTrainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bondingTrainerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bondingTrainerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BondingTrainerDTO result = bondingTrainerService.update(bondingTrainerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bondingTrainerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bonding-trainers/:id} : Partial updates given fields of an existing bondingTrainer, field will ignore if it is null
     *
     * @param id the id of the bondingTrainerDTO to save.
     * @param bondingTrainerDTO the bondingTrainerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bondingTrainerDTO,
     * or with status {@code 400 (Bad Request)} if the bondingTrainerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bondingTrainerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bondingTrainerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bonding-trainers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BondingTrainerDTO> partialUpdateBondingTrainer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BondingTrainerDTO bondingTrainerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BondingTrainer partially : {}, {}", id, bondingTrainerDTO);
        if (bondingTrainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bondingTrainerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bondingTrainerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BondingTrainerDTO> result = bondingTrainerService.partialUpdate(bondingTrainerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bondingTrainerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bonding-trainers} : get all the bondingTrainers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bondingTrainers in body.
     */
    @GetMapping("/bonding-trainers")
    public ResponseEntity<List<BondingTrainerDTO>> getAllBondingTrainers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of BondingTrainers");
        Page<BondingTrainerDTO> page;
        if (eagerload) {
            page = bondingTrainerService.findAllWithEagerRelationships(pageable);
        } else {
            page = bondingTrainerService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bonding-trainers/:id} : get the "id" bondingTrainer.
     *
     * @param id the id of the bondingTrainerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bondingTrainerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bonding-trainers/{id}")
    public ResponseEntity<BondingTrainerDTO> getBondingTrainer(@PathVariable Long id) {
        log.debug("REST request to get BondingTrainer : {}", id);
        Optional<BondingTrainerDTO> bondingTrainerDTO = bondingTrainerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bondingTrainerDTO);
    }

    /**
     * {@code DELETE  /bonding-trainers/:id} : delete the "id" bondingTrainer.
     *
     * @param id the id of the bondingTrainerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bonding-trainers/{id}")
    public ResponseEntity<Void> deleteBondingTrainer(@PathVariable Long id) {
        log.debug("REST request to delete BondingTrainer : {}", id);
        bondingTrainerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

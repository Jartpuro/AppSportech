package co.edu.sena.web.rest;

import co.edu.sena.repository.BondingRepository;
import co.edu.sena.service.BondingService;
import co.edu.sena.service.dto.BondingDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.Bonding}.
 */
@RestController
@RequestMapping("/api")
public class BondingResource {

    private final Logger log = LoggerFactory.getLogger(BondingResource.class);

    private static final String ENTITY_NAME = "bonding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BondingService bondingService;

    private final BondingRepository bondingRepository;

    public BondingResource(BondingService bondingService, BondingRepository bondingRepository) {
        this.bondingService = bondingService;
        this.bondingRepository = bondingRepository;
    }

    /**
     * {@code POST  /bondings} : Create a new bonding.
     *
     * @param bondingDTO the bondingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bondingDTO, or with status {@code 400 (Bad Request)} if the bonding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bondings")
    public ResponseEntity<BondingDTO> createBonding(@Valid @RequestBody BondingDTO bondingDTO) throws URISyntaxException {
        log.debug("REST request to save Bonding : {}", bondingDTO);
        if (bondingDTO.getId() != null) {
            throw new BadRequestAlertException("A new bonding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BondingDTO result = bondingService.save(bondingDTO);
        return ResponseEntity
            .created(new URI("/api/bondings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bondings/:id} : Updates an existing bonding.
     *
     * @param id the id of the bondingDTO to save.
     * @param bondingDTO the bondingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bondingDTO,
     * or with status {@code 400 (Bad Request)} if the bondingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bondingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bondings/{id}")
    public ResponseEntity<BondingDTO> updateBonding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BondingDTO bondingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Bonding : {}, {}", id, bondingDTO);
        if (bondingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bondingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bondingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BondingDTO result = bondingService.update(bondingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bondingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bondings/:id} : Partial updates given fields of an existing bonding, field will ignore if it is null
     *
     * @param id the id of the bondingDTO to save.
     * @param bondingDTO the bondingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bondingDTO,
     * or with status {@code 400 (Bad Request)} if the bondingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bondingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bondingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bondings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BondingDTO> partialUpdateBonding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BondingDTO bondingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bonding partially : {}, {}", id, bondingDTO);
        if (bondingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bondingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bondingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BondingDTO> result = bondingService.partialUpdate(bondingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bondingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bondings} : get all the bondings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bondings in body.
     */
    @GetMapping("/bondings")
    public ResponseEntity<List<BondingDTO>> getAllBondings(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Bondings");
        Page<BondingDTO> page = bondingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bondings/:id} : get the "id" bonding.
     *
     * @param id the id of the bondingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bondingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bondings/{id}")
    public ResponseEntity<BondingDTO> getBonding(@PathVariable Long id) {
        log.debug("REST request to get Bonding : {}", id);
        Optional<BondingDTO> bondingDTO = bondingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bondingDTO);
    }

    /**
     * {@code DELETE  /bondings/:id} : delete the "id" bonding.
     *
     * @param id the id of the bondingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bondings/{id}")
    public ResponseEntity<Void> deleteBonding(@PathVariable Long id) {
        log.debug("REST request to delete Bonding : {}", id);
        bondingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

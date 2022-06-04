package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.BondingTrainer;
import co.edu.sena.domain.BoundingSchedule;
import co.edu.sena.repository.BoundingScheduleRepository;
import co.edu.sena.service.dto.BoundingScheduleDTO;
import co.edu.sena.service.mapper.BoundingScheduleMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BoundingScheduleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BoundingScheduleResourceIT {

    private static final String ENTITY_API_URL = "/api/bounding-schedules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BoundingScheduleRepository boundingScheduleRepository;

    @Autowired
    private BoundingScheduleMapper boundingScheduleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoundingScheduleMockMvc;

    private BoundingSchedule boundingSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoundingSchedule createEntity(EntityManager em) {
        BoundingSchedule boundingSchedule = new BoundingSchedule();
        // Add required entity
        BondingTrainer bondingTrainer;
        if (TestUtil.findAll(em, BondingTrainer.class).isEmpty()) {
            bondingTrainer = BondingTrainerResourceIT.createEntity(em);
            em.persist(bondingTrainer);
            em.flush();
        } else {
            bondingTrainer = TestUtil.findAll(em, BondingTrainer.class).get(0);
        }
        boundingSchedule.setBondingTrainer(bondingTrainer);
        return boundingSchedule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoundingSchedule createUpdatedEntity(EntityManager em) {
        BoundingSchedule boundingSchedule = new BoundingSchedule();
        // Add required entity
        BondingTrainer bondingTrainer;
        if (TestUtil.findAll(em, BondingTrainer.class).isEmpty()) {
            bondingTrainer = BondingTrainerResourceIT.createUpdatedEntity(em);
            em.persist(bondingTrainer);
            em.flush();
        } else {
            bondingTrainer = TestUtil.findAll(em, BondingTrainer.class).get(0);
        }
        boundingSchedule.setBondingTrainer(bondingTrainer);
        return boundingSchedule;
    }

    @BeforeEach
    public void initTest() {
        boundingSchedule = createEntity(em);
    }

    @Test
    @Transactional
    void createBoundingSchedule() throws Exception {
        int databaseSizeBeforeCreate = boundingScheduleRepository.findAll().size();
        // Create the BoundingSchedule
        BoundingScheduleDTO boundingScheduleDTO = boundingScheduleMapper.toDto(boundingSchedule);
        restBoundingScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(boundingScheduleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        BoundingSchedule testBoundingSchedule = boundingScheduleList.get(boundingScheduleList.size() - 1);
    }

    @Test
    @Transactional
    void createBoundingScheduleWithExistingId() throws Exception {
        // Create the BoundingSchedule with an existing ID
        boundingSchedule.setId(1L);
        BoundingScheduleDTO boundingScheduleDTO = boundingScheduleMapper.toDto(boundingSchedule);

        int databaseSizeBeforeCreate = boundingScheduleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoundingScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(boundingScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBoundingSchedules() throws Exception {
        // Initialize the database
        boundingScheduleRepository.saveAndFlush(boundingSchedule);

        // Get all the boundingScheduleList
        restBoundingScheduleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boundingSchedule.getId().intValue())));
    }

    @Test
    @Transactional
    void getBoundingSchedule() throws Exception {
        // Initialize the database
        boundingScheduleRepository.saveAndFlush(boundingSchedule);

        // Get the boundingSchedule
        restBoundingScheduleMockMvc
            .perform(get(ENTITY_API_URL_ID, boundingSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boundingSchedule.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBoundingSchedule() throws Exception {
        // Get the boundingSchedule
        restBoundingScheduleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBoundingSchedule() throws Exception {
        // Initialize the database
        boundingScheduleRepository.saveAndFlush(boundingSchedule);

        int databaseSizeBeforeUpdate = boundingScheduleRepository.findAll().size();

        // Update the boundingSchedule
        BoundingSchedule updatedBoundingSchedule = boundingScheduleRepository.findById(boundingSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedBoundingSchedule are not directly saved in db
        em.detach(updatedBoundingSchedule);
        BoundingScheduleDTO boundingScheduleDTO = boundingScheduleMapper.toDto(updatedBoundingSchedule);

        restBoundingScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, boundingScheduleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(boundingScheduleDTO))
            )
            .andExpect(status().isOk());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeUpdate);
        BoundingSchedule testBoundingSchedule = boundingScheduleList.get(boundingScheduleList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBoundingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = boundingScheduleRepository.findAll().size();
        boundingSchedule.setId(count.incrementAndGet());

        // Create the BoundingSchedule
        BoundingScheduleDTO boundingScheduleDTO = boundingScheduleMapper.toDto(boundingSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoundingScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, boundingScheduleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(boundingScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBoundingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = boundingScheduleRepository.findAll().size();
        boundingSchedule.setId(count.incrementAndGet());

        // Create the BoundingSchedule
        BoundingScheduleDTO boundingScheduleDTO = boundingScheduleMapper.toDto(boundingSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoundingScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(boundingScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBoundingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = boundingScheduleRepository.findAll().size();
        boundingSchedule.setId(count.incrementAndGet());

        // Create the BoundingSchedule
        BoundingScheduleDTO boundingScheduleDTO = boundingScheduleMapper.toDto(boundingSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoundingScheduleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(boundingScheduleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBoundingScheduleWithPatch() throws Exception {
        // Initialize the database
        boundingScheduleRepository.saveAndFlush(boundingSchedule);

        int databaseSizeBeforeUpdate = boundingScheduleRepository.findAll().size();

        // Update the boundingSchedule using partial update
        BoundingSchedule partialUpdatedBoundingSchedule = new BoundingSchedule();
        partialUpdatedBoundingSchedule.setId(boundingSchedule.getId());

        restBoundingScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoundingSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBoundingSchedule))
            )
            .andExpect(status().isOk());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeUpdate);
        BoundingSchedule testBoundingSchedule = boundingScheduleList.get(boundingScheduleList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBoundingScheduleWithPatch() throws Exception {
        // Initialize the database
        boundingScheduleRepository.saveAndFlush(boundingSchedule);

        int databaseSizeBeforeUpdate = boundingScheduleRepository.findAll().size();

        // Update the boundingSchedule using partial update
        BoundingSchedule partialUpdatedBoundingSchedule = new BoundingSchedule();
        partialUpdatedBoundingSchedule.setId(boundingSchedule.getId());

        restBoundingScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoundingSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBoundingSchedule))
            )
            .andExpect(status().isOk());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeUpdate);
        BoundingSchedule testBoundingSchedule = boundingScheduleList.get(boundingScheduleList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBoundingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = boundingScheduleRepository.findAll().size();
        boundingSchedule.setId(count.incrementAndGet());

        // Create the BoundingSchedule
        BoundingScheduleDTO boundingScheduleDTO = boundingScheduleMapper.toDto(boundingSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoundingScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, boundingScheduleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(boundingScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBoundingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = boundingScheduleRepository.findAll().size();
        boundingSchedule.setId(count.incrementAndGet());

        // Create the BoundingSchedule
        BoundingScheduleDTO boundingScheduleDTO = boundingScheduleMapper.toDto(boundingSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoundingScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(boundingScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBoundingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = boundingScheduleRepository.findAll().size();
        boundingSchedule.setId(count.incrementAndGet());

        // Create the BoundingSchedule
        BoundingScheduleDTO boundingScheduleDTO = boundingScheduleMapper.toDto(boundingSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoundingScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(boundingScheduleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BoundingSchedule in the database
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBoundingSchedule() throws Exception {
        // Initialize the database
        boundingScheduleRepository.saveAndFlush(boundingSchedule);

        int databaseSizeBeforeDelete = boundingScheduleRepository.findAll().size();

        // Delete the boundingSchedule
        restBoundingScheduleMockMvc
            .perform(delete(ENTITY_API_URL_ID, boundingSchedule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoundingSchedule> boundingScheduleList = boundingScheduleRepository.findAll();
        assertThat(boundingScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

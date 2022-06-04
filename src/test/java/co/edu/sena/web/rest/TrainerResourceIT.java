package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Customer;
import co.edu.sena.domain.Trainer;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.TrainerRepository;
import co.edu.sena.service.dto.TrainerDTO;
import co.edu.sena.service.mapper.TrainerMapper;
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
 * Integration tests for the {@link TrainerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainerResourceIT {

    private static final State DEFAULT_TRAINER_STATE = State.ACTIVE;
    private static final State UPDATED_TRAINER_STATE = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/trainers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainerMapper trainerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainerMockMvc;

    private Trainer trainer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trainer createEntity(EntityManager em) {
        Trainer trainer = new Trainer().trainerState(DEFAULT_TRAINER_STATE);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        trainer.setCustomer(customer);
        return trainer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trainer createUpdatedEntity(EntityManager em) {
        Trainer trainer = new Trainer().trainerState(UPDATED_TRAINER_STATE);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createUpdatedEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        trainer.setCustomer(customer);
        return trainer;
    }

    @BeforeEach
    public void initTest() {
        trainer = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainer() throws Exception {
        int databaseSizeBeforeCreate = trainerRepository.findAll().size();
        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);
        restTrainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isCreated());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeCreate + 1);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
        assertThat(testTrainer.getTrainerState()).isEqualTo(DEFAULT_TRAINER_STATE);
    }

    @Test
    @Transactional
    void createTrainerWithExistingId() throws Exception {
        // Create the Trainer with an existing ID
        trainer.setId(1L);
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        int databaseSizeBeforeCreate = trainerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrainerStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainerRepository.findAll().size();
        // set the field null
        trainer.setTrainerState(null);

        // Create the Trainer, which fails.
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        restTrainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isBadRequest());

        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrainers() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList
        restTrainerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainer.getId().intValue())))
            .andExpect(jsonPath("$.[*].trainerState").value(hasItem(DEFAULT_TRAINER_STATE.toString())));
    }

    @Test
    @Transactional
    void getTrainer() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get the trainer
        restTrainerMockMvc
            .perform(get(ENTITY_API_URL_ID, trainer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainer.getId().intValue()))
            .andExpect(jsonPath("$.trainerState").value(DEFAULT_TRAINER_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTrainer() throws Exception {
        // Get the trainer
        restTrainerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrainer() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();

        // Update the trainer
        Trainer updatedTrainer = trainerRepository.findById(trainer.getId()).get();
        // Disconnect from session so that the updates on updatedTrainer are not directly saved in db
        em.detach(updatedTrainer);
        updatedTrainer.trainerState(UPDATED_TRAINER_STATE);
        TrainerDTO trainerDTO = trainerMapper.toDto(updatedTrainer);

        restTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
        assertThat(testTrainer.getTrainerState()).isEqualTo(UPDATED_TRAINER_STATE);
    }

    @Test
    @Transactional
    void putNonExistingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainerWithPatch() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();

        // Update the trainer using partial update
        Trainer partialUpdatedTrainer = new Trainer();
        partialUpdatedTrainer.setId(trainer.getId());

        partialUpdatedTrainer.trainerState(UPDATED_TRAINER_STATE);

        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainer))
            )
            .andExpect(status().isOk());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
        assertThat(testTrainer.getTrainerState()).isEqualTo(UPDATED_TRAINER_STATE);
    }

    @Test
    @Transactional
    void fullUpdateTrainerWithPatch() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();

        // Update the trainer using partial update
        Trainer partialUpdatedTrainer = new Trainer();
        partialUpdatedTrainer.setId(trainer.getId());

        partialUpdatedTrainer.trainerState(UPDATED_TRAINER_STATE);

        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainer))
            )
            .andExpect(status().isOk());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
        assertThat(testTrainer.getTrainerState()).isEqualTo(UPDATED_TRAINER_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainer() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeDelete = trainerRepository.findAll().size();

        // Delete the trainer
        restTrainerMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

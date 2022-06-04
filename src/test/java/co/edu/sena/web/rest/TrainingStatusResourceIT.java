package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.TrainingStatus;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.TrainingStatusRepository;
import co.edu.sena.service.dto.TrainingStatusDTO;
import co.edu.sena.service.mapper.TrainingStatusMapper;
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
 * Integration tests for the {@link TrainingStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainingStatusResourceIT {

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    private static final State DEFAULT_STATE_TRAINING = State.ACTIVE;
    private static final State UPDATED_STATE_TRAINING = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/training-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainingStatusRepository trainingStatusRepository;

    @Autowired
    private TrainingStatusMapper trainingStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingStatusMockMvc;

    private TrainingStatus trainingStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingStatus createEntity(EntityManager em) {
        TrainingStatus trainingStatus = new TrainingStatus().statusName(DEFAULT_STATUS_NAME).stateTraining(DEFAULT_STATE_TRAINING);
        return trainingStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingStatus createUpdatedEntity(EntityManager em) {
        TrainingStatus trainingStatus = new TrainingStatus().statusName(UPDATED_STATUS_NAME).stateTraining(UPDATED_STATE_TRAINING);
        return trainingStatus;
    }

    @BeforeEach
    public void initTest() {
        trainingStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainingStatus() throws Exception {
        int databaseSizeBeforeCreate = trainingStatusRepository.findAll().size();
        // Create the TrainingStatus
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);
        restTrainingStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingStatus testTrainingStatus = trainingStatusList.get(trainingStatusList.size() - 1);
        assertThat(testTrainingStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testTrainingStatus.getStateTraining()).isEqualTo(DEFAULT_STATE_TRAINING);
    }

    @Test
    @Transactional
    void createTrainingStatusWithExistingId() throws Exception {
        // Create the TrainingStatus with an existing ID
        trainingStatus.setId(1L);
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);

        int databaseSizeBeforeCreate = trainingStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingStatusRepository.findAll().size();
        // set the field null
        trainingStatus.setStatusName(null);

        // Create the TrainingStatus, which fails.
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);

        restTrainingStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateTrainingIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingStatusRepository.findAll().size();
        // set the field null
        trainingStatus.setStateTraining(null);

        // Create the TrainingStatus, which fails.
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);

        restTrainingStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrainingStatuses() throws Exception {
        // Initialize the database
        trainingStatusRepository.saveAndFlush(trainingStatus);

        // Get all the trainingStatusList
        restTrainingStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].stateTraining").value(hasItem(DEFAULT_STATE_TRAINING.toString())));
    }

    @Test
    @Transactional
    void getTrainingStatus() throws Exception {
        // Initialize the database
        trainingStatusRepository.saveAndFlush(trainingStatus);

        // Get the trainingStatus
        restTrainingStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, trainingStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingStatus.getId().intValue()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME))
            .andExpect(jsonPath("$.stateTraining").value(DEFAULT_STATE_TRAINING.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTrainingStatus() throws Exception {
        // Get the trainingStatus
        restTrainingStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrainingStatus() throws Exception {
        // Initialize the database
        trainingStatusRepository.saveAndFlush(trainingStatus);

        int databaseSizeBeforeUpdate = trainingStatusRepository.findAll().size();

        // Update the trainingStatus
        TrainingStatus updatedTrainingStatus = trainingStatusRepository.findById(trainingStatus.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingStatus are not directly saved in db
        em.detach(updatedTrainingStatus);
        updatedTrainingStatus.statusName(UPDATED_STATUS_NAME).stateTraining(UPDATED_STATE_TRAINING);
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(updatedTrainingStatus);

        restTrainingStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeUpdate);
        TrainingStatus testTrainingStatus = trainingStatusList.get(trainingStatusList.size() - 1);
        assertThat(testTrainingStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testTrainingStatus.getStateTraining()).isEqualTo(UPDATED_STATE_TRAINING);
    }

    @Test
    @Transactional
    void putNonExistingTrainingStatus() throws Exception {
        int databaseSizeBeforeUpdate = trainingStatusRepository.findAll().size();
        trainingStatus.setId(count.incrementAndGet());

        // Create the TrainingStatus
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainingStatus() throws Exception {
        int databaseSizeBeforeUpdate = trainingStatusRepository.findAll().size();
        trainingStatus.setId(count.incrementAndGet());

        // Create the TrainingStatus
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainingStatus() throws Exception {
        int databaseSizeBeforeUpdate = trainingStatusRepository.findAll().size();
        trainingStatus.setId(count.incrementAndGet());

        // Create the TrainingStatus
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainingStatusWithPatch() throws Exception {
        // Initialize the database
        trainingStatusRepository.saveAndFlush(trainingStatus);

        int databaseSizeBeforeUpdate = trainingStatusRepository.findAll().size();

        // Update the trainingStatus using partial update
        TrainingStatus partialUpdatedTrainingStatus = new TrainingStatus();
        partialUpdatedTrainingStatus.setId(trainingStatus.getId());

        partialUpdatedTrainingStatus.statusName(UPDATED_STATUS_NAME).stateTraining(UPDATED_STATE_TRAINING);

        restTrainingStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainingStatus))
            )
            .andExpect(status().isOk());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeUpdate);
        TrainingStatus testTrainingStatus = trainingStatusList.get(trainingStatusList.size() - 1);
        assertThat(testTrainingStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testTrainingStatus.getStateTraining()).isEqualTo(UPDATED_STATE_TRAINING);
    }

    @Test
    @Transactional
    void fullUpdateTrainingStatusWithPatch() throws Exception {
        // Initialize the database
        trainingStatusRepository.saveAndFlush(trainingStatus);

        int databaseSizeBeforeUpdate = trainingStatusRepository.findAll().size();

        // Update the trainingStatus using partial update
        TrainingStatus partialUpdatedTrainingStatus = new TrainingStatus();
        partialUpdatedTrainingStatus.setId(trainingStatus.getId());

        partialUpdatedTrainingStatus.statusName(UPDATED_STATUS_NAME).stateTraining(UPDATED_STATE_TRAINING);

        restTrainingStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainingStatus))
            )
            .andExpect(status().isOk());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeUpdate);
        TrainingStatus testTrainingStatus = trainingStatusList.get(trainingStatusList.size() - 1);
        assertThat(testTrainingStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testTrainingStatus.getStateTraining()).isEqualTo(UPDATED_STATE_TRAINING);
    }

    @Test
    @Transactional
    void patchNonExistingTrainingStatus() throws Exception {
        int databaseSizeBeforeUpdate = trainingStatusRepository.findAll().size();
        trainingStatus.setId(count.incrementAndGet());

        // Create the TrainingStatus
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainingStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainingStatus() throws Exception {
        int databaseSizeBeforeUpdate = trainingStatusRepository.findAll().size();
        trainingStatus.setId(count.incrementAndGet());

        // Create the TrainingStatus
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainingStatus() throws Exception {
        int databaseSizeBeforeUpdate = trainingStatusRepository.findAll().size();
        trainingStatus.setId(count.incrementAndGet());

        // Create the TrainingStatus
        TrainingStatusDTO trainingStatusDTO = trainingStatusMapper.toDto(trainingStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingStatus in the database
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainingStatus() throws Exception {
        // Initialize the database
        trainingStatusRepository.saveAndFlush(trainingStatus);

        int databaseSizeBeforeDelete = trainingStatusRepository.findAll().size();

        // Delete the trainingStatus
        restTrainingStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainingStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingStatus> trainingStatusList = trainingStatusRepository.findAll();
        assertThat(trainingStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

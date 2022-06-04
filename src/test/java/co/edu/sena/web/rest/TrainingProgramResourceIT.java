package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.TrainingProgram;
import co.edu.sena.domain.enumeration.Offer;
import co.edu.sena.domain.enumeration.StateProgram;
import co.edu.sena.repository.TrainingProgramRepository;
import co.edu.sena.service.dto.TrainingProgramDTO;
import co.edu.sena.service.mapper.TrainingProgramMapper;
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
 * Integration tests for the {@link TrainingProgramResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainingProgramResourceIT {

    private static final String DEFAULT_PROGRAM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_VERSION = "BBBBBBBBBB";

    private static final Offer DEFAULT_PROGRAM_NAME = Offer.ATHLETICS;
    private static final Offer UPDATED_PROGRAM_NAME = Offer.BADMINTON;

    private static final String DEFAULT_PROGRAM_INITIALS = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_INITIALS = "BBBBBBBBBB";

    private static final StateProgram DEFAULT_PROGRAM_STATE = StateProgram.EXECUTION;
    private static final StateProgram UPDATED_PROGRAM_STATE = StateProgram.DISCONTINUED;

    private static final String ENTITY_API_URL = "/api/training-programs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private TrainingProgramMapper trainingProgramMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingProgramMockMvc;

    private TrainingProgram trainingProgram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingProgram createEntity(EntityManager em) {
        TrainingProgram trainingProgram = new TrainingProgram()
            .programCode(DEFAULT_PROGRAM_CODE)
            .programVersion(DEFAULT_PROGRAM_VERSION)
            .programName(DEFAULT_PROGRAM_NAME)
            .programInitials(DEFAULT_PROGRAM_INITIALS)
            .programState(DEFAULT_PROGRAM_STATE);
        return trainingProgram;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingProgram createUpdatedEntity(EntityManager em) {
        TrainingProgram trainingProgram = new TrainingProgram()
            .programCode(UPDATED_PROGRAM_CODE)
            .programVersion(UPDATED_PROGRAM_VERSION)
            .programName(UPDATED_PROGRAM_NAME)
            .programInitials(UPDATED_PROGRAM_INITIALS)
            .programState(UPDATED_PROGRAM_STATE);
        return trainingProgram;
    }

    @BeforeEach
    public void initTest() {
        trainingProgram = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainingProgram() throws Exception {
        int databaseSizeBeforeCreate = trainingProgramRepository.findAll().size();
        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);
        restTrainingProgramMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingProgram testTrainingProgram = trainingProgramList.get(trainingProgramList.size() - 1);
        assertThat(testTrainingProgram.getProgramCode()).isEqualTo(DEFAULT_PROGRAM_CODE);
        assertThat(testTrainingProgram.getProgramVersion()).isEqualTo(DEFAULT_PROGRAM_VERSION);
        assertThat(testTrainingProgram.getProgramName()).isEqualTo(DEFAULT_PROGRAM_NAME);
        assertThat(testTrainingProgram.getProgramInitials()).isEqualTo(DEFAULT_PROGRAM_INITIALS);
        assertThat(testTrainingProgram.getProgramState()).isEqualTo(DEFAULT_PROGRAM_STATE);
    }

    @Test
    @Transactional
    void createTrainingProgramWithExistingId() throws Exception {
        // Create the TrainingProgram with an existing ID
        trainingProgram.setId(1L);
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        int databaseSizeBeforeCreate = trainingProgramRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingProgramMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkProgramCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingProgramRepository.findAll().size();
        // set the field null
        trainingProgram.setProgramCode(null);

        // Create the TrainingProgram, which fails.
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        restTrainingProgramMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProgramVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingProgramRepository.findAll().size();
        // set the field null
        trainingProgram.setProgramVersion(null);

        // Create the TrainingProgram, which fails.
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        restTrainingProgramMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProgramNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingProgramRepository.findAll().size();
        // set the field null
        trainingProgram.setProgramName(null);

        // Create the TrainingProgram, which fails.
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        restTrainingProgramMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProgramInitialsIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingProgramRepository.findAll().size();
        // set the field null
        trainingProgram.setProgramInitials(null);

        // Create the TrainingProgram, which fails.
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        restTrainingProgramMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProgramStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingProgramRepository.findAll().size();
        // set the field null
        trainingProgram.setProgramState(null);

        // Create the TrainingProgram, which fails.
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        restTrainingProgramMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrainingPrograms() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList
        restTrainingProgramMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingProgram.getId().intValue())))
            .andExpect(jsonPath("$.[*].programCode").value(hasItem(DEFAULT_PROGRAM_CODE)))
            .andExpect(jsonPath("$.[*].programVersion").value(hasItem(DEFAULT_PROGRAM_VERSION)))
            .andExpect(jsonPath("$.[*].programName").value(hasItem(DEFAULT_PROGRAM_NAME.toString())))
            .andExpect(jsonPath("$.[*].programInitials").value(hasItem(DEFAULT_PROGRAM_INITIALS)))
            .andExpect(jsonPath("$.[*].programState").value(hasItem(DEFAULT_PROGRAM_STATE.toString())));
    }

    @Test
    @Transactional
    void getTrainingProgram() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get the trainingProgram
        restTrainingProgramMockMvc
            .perform(get(ENTITY_API_URL_ID, trainingProgram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingProgram.getId().intValue()))
            .andExpect(jsonPath("$.programCode").value(DEFAULT_PROGRAM_CODE))
            .andExpect(jsonPath("$.programVersion").value(DEFAULT_PROGRAM_VERSION))
            .andExpect(jsonPath("$.programName").value(DEFAULT_PROGRAM_NAME.toString()))
            .andExpect(jsonPath("$.programInitials").value(DEFAULT_PROGRAM_INITIALS))
            .andExpect(jsonPath("$.programState").value(DEFAULT_PROGRAM_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTrainingProgram() throws Exception {
        // Get the trainingProgram
        restTrainingProgramMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrainingProgram() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        int databaseSizeBeforeUpdate = trainingProgramRepository.findAll().size();

        // Update the trainingProgram
        TrainingProgram updatedTrainingProgram = trainingProgramRepository.findById(trainingProgram.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingProgram are not directly saved in db
        em.detach(updatedTrainingProgram);
        updatedTrainingProgram
            .programCode(UPDATED_PROGRAM_CODE)
            .programVersion(UPDATED_PROGRAM_VERSION)
            .programName(UPDATED_PROGRAM_NAME)
            .programInitials(UPDATED_PROGRAM_INITIALS)
            .programState(UPDATED_PROGRAM_STATE);
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(updatedTrainingProgram);

        restTrainingProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingProgramDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeUpdate);
        TrainingProgram testTrainingProgram = trainingProgramList.get(trainingProgramList.size() - 1);
        assertThat(testTrainingProgram.getProgramCode()).isEqualTo(UPDATED_PROGRAM_CODE);
        assertThat(testTrainingProgram.getProgramVersion()).isEqualTo(UPDATED_PROGRAM_VERSION);
        assertThat(testTrainingProgram.getProgramName()).isEqualTo(UPDATED_PROGRAM_NAME);
        assertThat(testTrainingProgram.getProgramInitials()).isEqualTo(UPDATED_PROGRAM_INITIALS);
        assertThat(testTrainingProgram.getProgramState()).isEqualTo(UPDATED_PROGRAM_STATE);
    }

    @Test
    @Transactional
    void putNonExistingTrainingProgram() throws Exception {
        int databaseSizeBeforeUpdate = trainingProgramRepository.findAll().size();
        trainingProgram.setId(count.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingProgramDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainingProgram() throws Exception {
        int databaseSizeBeforeUpdate = trainingProgramRepository.findAll().size();
        trainingProgram.setId(count.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainingProgram() throws Exception {
        int databaseSizeBeforeUpdate = trainingProgramRepository.findAll().size();
        trainingProgram.setId(count.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainingProgramWithPatch() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        int databaseSizeBeforeUpdate = trainingProgramRepository.findAll().size();

        // Update the trainingProgram using partial update
        TrainingProgram partialUpdatedTrainingProgram = new TrainingProgram();
        partialUpdatedTrainingProgram.setId(trainingProgram.getId());

        partialUpdatedTrainingProgram.programInitials(UPDATED_PROGRAM_INITIALS).programState(UPDATED_PROGRAM_STATE);

        restTrainingProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainingProgram))
            )
            .andExpect(status().isOk());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeUpdate);
        TrainingProgram testTrainingProgram = trainingProgramList.get(trainingProgramList.size() - 1);
        assertThat(testTrainingProgram.getProgramCode()).isEqualTo(DEFAULT_PROGRAM_CODE);
        assertThat(testTrainingProgram.getProgramVersion()).isEqualTo(DEFAULT_PROGRAM_VERSION);
        assertThat(testTrainingProgram.getProgramName()).isEqualTo(DEFAULT_PROGRAM_NAME);
        assertThat(testTrainingProgram.getProgramInitials()).isEqualTo(UPDATED_PROGRAM_INITIALS);
        assertThat(testTrainingProgram.getProgramState()).isEqualTo(UPDATED_PROGRAM_STATE);
    }

    @Test
    @Transactional
    void fullUpdateTrainingProgramWithPatch() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        int databaseSizeBeforeUpdate = trainingProgramRepository.findAll().size();

        // Update the trainingProgram using partial update
        TrainingProgram partialUpdatedTrainingProgram = new TrainingProgram();
        partialUpdatedTrainingProgram.setId(trainingProgram.getId());

        partialUpdatedTrainingProgram
            .programCode(UPDATED_PROGRAM_CODE)
            .programVersion(UPDATED_PROGRAM_VERSION)
            .programName(UPDATED_PROGRAM_NAME)
            .programInitials(UPDATED_PROGRAM_INITIALS)
            .programState(UPDATED_PROGRAM_STATE);

        restTrainingProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainingProgram))
            )
            .andExpect(status().isOk());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeUpdate);
        TrainingProgram testTrainingProgram = trainingProgramList.get(trainingProgramList.size() - 1);
        assertThat(testTrainingProgram.getProgramCode()).isEqualTo(UPDATED_PROGRAM_CODE);
        assertThat(testTrainingProgram.getProgramVersion()).isEqualTo(UPDATED_PROGRAM_VERSION);
        assertThat(testTrainingProgram.getProgramName()).isEqualTo(UPDATED_PROGRAM_NAME);
        assertThat(testTrainingProgram.getProgramInitials()).isEqualTo(UPDATED_PROGRAM_INITIALS);
        assertThat(testTrainingProgram.getProgramState()).isEqualTo(UPDATED_PROGRAM_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingTrainingProgram() throws Exception {
        int databaseSizeBeforeUpdate = trainingProgramRepository.findAll().size();
        trainingProgram.setId(count.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainingProgramDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainingProgram() throws Exception {
        int databaseSizeBeforeUpdate = trainingProgramRepository.findAll().size();
        trainingProgram.setId(count.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainingProgram() throws Exception {
        int databaseSizeBeforeUpdate = trainingProgramRepository.findAll().size();
        trainingProgram.setId(count.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingProgramDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingProgram in the database
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainingProgram() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        int databaseSizeBeforeDelete = trainingProgramRepository.findAll().size();

        // Delete the trainingProgram
        restTrainingProgramMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainingProgram.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingProgram> trainingProgramList = trainingProgramRepository.findAll();
        assertThat(trainingProgramList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

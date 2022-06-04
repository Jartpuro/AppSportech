package co.edu.sena.web.rest;

import static co.edu.sena.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Customer;
import co.edu.sena.domain.LogError;
import co.edu.sena.repository.LogErrorRepository;
import co.edu.sena.service.dto.LogErrorDTO;
import co.edu.sena.service.mapper.LogErrorMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link LogErrorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LogErrorResourceIT {

    private static final String DEFAULT_LEVEL_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL_ERROR = "BBBBBBBBBB";

    private static final String DEFAULT_LOG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ERROR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_ERROR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ERROR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/log-errors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LogErrorRepository logErrorRepository;

    @Autowired
    private LogErrorMapper logErrorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogErrorMockMvc;

    private LogError logError;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogError createEntity(EntityManager em) {
        LogError logError = new LogError()
            .levelError(DEFAULT_LEVEL_ERROR)
            .logName(DEFAULT_LOG_NAME)
            .messageError(DEFAULT_MESSAGE_ERROR)
            .dateError(DEFAULT_DATE_ERROR);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        logError.setCustomer(customer);
        return logError;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogError createUpdatedEntity(EntityManager em) {
        LogError logError = new LogError()
            .levelError(UPDATED_LEVEL_ERROR)
            .logName(UPDATED_LOG_NAME)
            .messageError(UPDATED_MESSAGE_ERROR)
            .dateError(UPDATED_DATE_ERROR);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createUpdatedEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        logError.setCustomer(customer);
        return logError;
    }

    @BeforeEach
    public void initTest() {
        logError = createEntity(em);
    }

    @Test
    @Transactional
    void createLogError() throws Exception {
        int databaseSizeBeforeCreate = logErrorRepository.findAll().size();
        // Create the LogError
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);
        restLogErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logErrorDTO)))
            .andExpect(status().isCreated());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeCreate + 1);
        LogError testLogError = logErrorList.get(logErrorList.size() - 1);
        assertThat(testLogError.getLevelError()).isEqualTo(DEFAULT_LEVEL_ERROR);
        assertThat(testLogError.getLogName()).isEqualTo(DEFAULT_LOG_NAME);
        assertThat(testLogError.getMessageError()).isEqualTo(DEFAULT_MESSAGE_ERROR);
        assertThat(testLogError.getDateError()).isEqualTo(DEFAULT_DATE_ERROR);
    }

    @Test
    @Transactional
    void createLogErrorWithExistingId() throws Exception {
        // Create the LogError with an existing ID
        logError.setId(1L);
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        int databaseSizeBeforeCreate = logErrorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logErrorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLevelErrorIsRequired() throws Exception {
        int databaseSizeBeforeTest = logErrorRepository.findAll().size();
        // set the field null
        logError.setLevelError(null);

        // Create the LogError, which fails.
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        restLogErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logErrorDTO)))
            .andExpect(status().isBadRequest());

        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLogNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = logErrorRepository.findAll().size();
        // set the field null
        logError.setLogName(null);

        // Create the LogError, which fails.
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        restLogErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logErrorDTO)))
            .andExpect(status().isBadRequest());

        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMessageErrorIsRequired() throws Exception {
        int databaseSizeBeforeTest = logErrorRepository.findAll().size();
        // set the field null
        logError.setMessageError(null);

        // Create the LogError, which fails.
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        restLogErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logErrorDTO)))
            .andExpect(status().isBadRequest());

        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateErrorIsRequired() throws Exception {
        int databaseSizeBeforeTest = logErrorRepository.findAll().size();
        // set the field null
        logError.setDateError(null);

        // Create the LogError, which fails.
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        restLogErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logErrorDTO)))
            .andExpect(status().isBadRequest());

        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLogErrors() throws Exception {
        // Initialize the database
        logErrorRepository.saveAndFlush(logError);

        // Get all the logErrorList
        restLogErrorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logError.getId().intValue())))
            .andExpect(jsonPath("$.[*].levelError").value(hasItem(DEFAULT_LEVEL_ERROR)))
            .andExpect(jsonPath("$.[*].logName").value(hasItem(DEFAULT_LOG_NAME)))
            .andExpect(jsonPath("$.[*].messageError").value(hasItem(DEFAULT_MESSAGE_ERROR)))
            .andExpect(jsonPath("$.[*].dateError").value(hasItem(sameInstant(DEFAULT_DATE_ERROR))));
    }

    @Test
    @Transactional
    void getLogError() throws Exception {
        // Initialize the database
        logErrorRepository.saveAndFlush(logError);

        // Get the logError
        restLogErrorMockMvc
            .perform(get(ENTITY_API_URL_ID, logError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logError.getId().intValue()))
            .andExpect(jsonPath("$.levelError").value(DEFAULT_LEVEL_ERROR))
            .andExpect(jsonPath("$.logName").value(DEFAULT_LOG_NAME))
            .andExpect(jsonPath("$.messageError").value(DEFAULT_MESSAGE_ERROR))
            .andExpect(jsonPath("$.dateError").value(sameInstant(DEFAULT_DATE_ERROR)));
    }

    @Test
    @Transactional
    void getNonExistingLogError() throws Exception {
        // Get the logError
        restLogErrorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLogError() throws Exception {
        // Initialize the database
        logErrorRepository.saveAndFlush(logError);

        int databaseSizeBeforeUpdate = logErrorRepository.findAll().size();

        // Update the logError
        LogError updatedLogError = logErrorRepository.findById(logError.getId()).get();
        // Disconnect from session so that the updates on updatedLogError are not directly saved in db
        em.detach(updatedLogError);
        updatedLogError
            .levelError(UPDATED_LEVEL_ERROR)
            .logName(UPDATED_LOG_NAME)
            .messageError(UPDATED_MESSAGE_ERROR)
            .dateError(UPDATED_DATE_ERROR);
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(updatedLogError);

        restLogErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, logErrorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logErrorDTO))
            )
            .andExpect(status().isOk());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeUpdate);
        LogError testLogError = logErrorList.get(logErrorList.size() - 1);
        assertThat(testLogError.getLevelError()).isEqualTo(UPDATED_LEVEL_ERROR);
        assertThat(testLogError.getLogName()).isEqualTo(UPDATED_LOG_NAME);
        assertThat(testLogError.getMessageError()).isEqualTo(UPDATED_MESSAGE_ERROR);
        assertThat(testLogError.getDateError()).isEqualTo(UPDATED_DATE_ERROR);
    }

    @Test
    @Transactional
    void putNonExistingLogError() throws Exception {
        int databaseSizeBeforeUpdate = logErrorRepository.findAll().size();
        logError.setId(count.incrementAndGet());

        // Create the LogError
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, logErrorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logErrorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLogError() throws Exception {
        int databaseSizeBeforeUpdate = logErrorRepository.findAll().size();
        logError.setId(count.incrementAndGet());

        // Create the LogError
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logErrorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLogError() throws Exception {
        int databaseSizeBeforeUpdate = logErrorRepository.findAll().size();
        logError.setId(count.incrementAndGet());

        // Create the LogError
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogErrorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logErrorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLogErrorWithPatch() throws Exception {
        // Initialize the database
        logErrorRepository.saveAndFlush(logError);

        int databaseSizeBeforeUpdate = logErrorRepository.findAll().size();

        // Update the logError using partial update
        LogError partialUpdatedLogError = new LogError();
        partialUpdatedLogError.setId(logError.getId());

        partialUpdatedLogError.logName(UPDATED_LOG_NAME).messageError(UPDATED_MESSAGE_ERROR);

        restLogErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogError.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogError))
            )
            .andExpect(status().isOk());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeUpdate);
        LogError testLogError = logErrorList.get(logErrorList.size() - 1);
        assertThat(testLogError.getLevelError()).isEqualTo(DEFAULT_LEVEL_ERROR);
        assertThat(testLogError.getLogName()).isEqualTo(UPDATED_LOG_NAME);
        assertThat(testLogError.getMessageError()).isEqualTo(UPDATED_MESSAGE_ERROR);
        assertThat(testLogError.getDateError()).isEqualTo(DEFAULT_DATE_ERROR);
    }

    @Test
    @Transactional
    void fullUpdateLogErrorWithPatch() throws Exception {
        // Initialize the database
        logErrorRepository.saveAndFlush(logError);

        int databaseSizeBeforeUpdate = logErrorRepository.findAll().size();

        // Update the logError using partial update
        LogError partialUpdatedLogError = new LogError();
        partialUpdatedLogError.setId(logError.getId());

        partialUpdatedLogError
            .levelError(UPDATED_LEVEL_ERROR)
            .logName(UPDATED_LOG_NAME)
            .messageError(UPDATED_MESSAGE_ERROR)
            .dateError(UPDATED_DATE_ERROR);

        restLogErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogError.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogError))
            )
            .andExpect(status().isOk());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeUpdate);
        LogError testLogError = logErrorList.get(logErrorList.size() - 1);
        assertThat(testLogError.getLevelError()).isEqualTo(UPDATED_LEVEL_ERROR);
        assertThat(testLogError.getLogName()).isEqualTo(UPDATED_LOG_NAME);
        assertThat(testLogError.getMessageError()).isEqualTo(UPDATED_MESSAGE_ERROR);
        assertThat(testLogError.getDateError()).isEqualTo(UPDATED_DATE_ERROR);
    }

    @Test
    @Transactional
    void patchNonExistingLogError() throws Exception {
        int databaseSizeBeforeUpdate = logErrorRepository.findAll().size();
        logError.setId(count.incrementAndGet());

        // Create the LogError
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, logErrorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logErrorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLogError() throws Exception {
        int databaseSizeBeforeUpdate = logErrorRepository.findAll().size();
        logError.setId(count.incrementAndGet());

        // Create the LogError
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logErrorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLogError() throws Exception {
        int databaseSizeBeforeUpdate = logErrorRepository.findAll().size();
        logError.setId(count.incrementAndGet());

        // Create the LogError
        LogErrorDTO logErrorDTO = logErrorMapper.toDto(logError);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogErrorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(logErrorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LogError in the database
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLogError() throws Exception {
        // Initialize the database
        logErrorRepository.saveAndFlush(logError);

        int databaseSizeBeforeDelete = logErrorRepository.findAll().size();

        // Delete the logError
        restLogErrorMockMvc
            .perform(delete(ENTITY_API_URL_ID, logError.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogError> logErrorList = logErrorRepository.findAll();
        assertThat(logErrorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

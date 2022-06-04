package co.edu.sena.web.rest;

import static co.edu.sena.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Customer;
import co.edu.sena.domain.LogAudit;
import co.edu.sena.repository.LogAuditRepository;
import co.edu.sena.service.dto.LogAuditDTO;
import co.edu.sena.service.mapper.LogAuditMapper;
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
 * Integration tests for the {@link LogAuditResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LogAuditResourceIT {

    private static final String DEFAULT_LEVEL_AUDIT = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL_AUDIT = "BBBBBBBBBB";

    private static final String DEFAULT_LOG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_AUDIT = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_AUDIT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_AUDIT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_AUDIT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/log-audits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LogAuditRepository logAuditRepository;

    @Autowired
    private LogAuditMapper logAuditMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogAuditMockMvc;

    private LogAudit logAudit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogAudit createEntity(EntityManager em) {
        LogAudit logAudit = new LogAudit()
            .levelAudit(DEFAULT_LEVEL_AUDIT)
            .logName(DEFAULT_LOG_NAME)
            .messageAudit(DEFAULT_MESSAGE_AUDIT)
            .dateAudit(DEFAULT_DATE_AUDIT);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        logAudit.setCustomer(customer);
        return logAudit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogAudit createUpdatedEntity(EntityManager em) {
        LogAudit logAudit = new LogAudit()
            .levelAudit(UPDATED_LEVEL_AUDIT)
            .logName(UPDATED_LOG_NAME)
            .messageAudit(UPDATED_MESSAGE_AUDIT)
            .dateAudit(UPDATED_DATE_AUDIT);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createUpdatedEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        logAudit.setCustomer(customer);
        return logAudit;
    }

    @BeforeEach
    public void initTest() {
        logAudit = createEntity(em);
    }

    @Test
    @Transactional
    void createLogAudit() throws Exception {
        int databaseSizeBeforeCreate = logAuditRepository.findAll().size();
        // Create the LogAudit
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);
        restLogAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logAuditDTO)))
            .andExpect(status().isCreated());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeCreate + 1);
        LogAudit testLogAudit = logAuditList.get(logAuditList.size() - 1);
        assertThat(testLogAudit.getLevelAudit()).isEqualTo(DEFAULT_LEVEL_AUDIT);
        assertThat(testLogAudit.getLogName()).isEqualTo(DEFAULT_LOG_NAME);
        assertThat(testLogAudit.getMessageAudit()).isEqualTo(DEFAULT_MESSAGE_AUDIT);
        assertThat(testLogAudit.getDateAudit()).isEqualTo(DEFAULT_DATE_AUDIT);
    }

    @Test
    @Transactional
    void createLogAuditWithExistingId() throws Exception {
        // Create the LogAudit with an existing ID
        logAudit.setId(1L);
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        int databaseSizeBeforeCreate = logAuditRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLevelAuditIsRequired() throws Exception {
        int databaseSizeBeforeTest = logAuditRepository.findAll().size();
        // set the field null
        logAudit.setLevelAudit(null);

        // Create the LogAudit, which fails.
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        restLogAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logAuditDTO)))
            .andExpect(status().isBadRequest());

        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLogNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = logAuditRepository.findAll().size();
        // set the field null
        logAudit.setLogName(null);

        // Create the LogAudit, which fails.
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        restLogAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logAuditDTO)))
            .andExpect(status().isBadRequest());

        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMessageAuditIsRequired() throws Exception {
        int databaseSizeBeforeTest = logAuditRepository.findAll().size();
        // set the field null
        logAudit.setMessageAudit(null);

        // Create the LogAudit, which fails.
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        restLogAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logAuditDTO)))
            .andExpect(status().isBadRequest());

        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateAuditIsRequired() throws Exception {
        int databaseSizeBeforeTest = logAuditRepository.findAll().size();
        // set the field null
        logAudit.setDateAudit(null);

        // Create the LogAudit, which fails.
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        restLogAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logAuditDTO)))
            .andExpect(status().isBadRequest());

        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLogAudits() throws Exception {
        // Initialize the database
        logAuditRepository.saveAndFlush(logAudit);

        // Get all the logAuditList
        restLogAuditMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].levelAudit").value(hasItem(DEFAULT_LEVEL_AUDIT)))
            .andExpect(jsonPath("$.[*].logName").value(hasItem(DEFAULT_LOG_NAME)))
            .andExpect(jsonPath("$.[*].messageAudit").value(hasItem(DEFAULT_MESSAGE_AUDIT)))
            .andExpect(jsonPath("$.[*].dateAudit").value(hasItem(sameInstant(DEFAULT_DATE_AUDIT))));
    }

    @Test
    @Transactional
    void getLogAudit() throws Exception {
        // Initialize the database
        logAuditRepository.saveAndFlush(logAudit);

        // Get the logAudit
        restLogAuditMockMvc
            .perform(get(ENTITY_API_URL_ID, logAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logAudit.getId().intValue()))
            .andExpect(jsonPath("$.levelAudit").value(DEFAULT_LEVEL_AUDIT))
            .andExpect(jsonPath("$.logName").value(DEFAULT_LOG_NAME))
            .andExpect(jsonPath("$.messageAudit").value(DEFAULT_MESSAGE_AUDIT))
            .andExpect(jsonPath("$.dateAudit").value(sameInstant(DEFAULT_DATE_AUDIT)));
    }

    @Test
    @Transactional
    void getNonExistingLogAudit() throws Exception {
        // Get the logAudit
        restLogAuditMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLogAudit() throws Exception {
        // Initialize the database
        logAuditRepository.saveAndFlush(logAudit);

        int databaseSizeBeforeUpdate = logAuditRepository.findAll().size();

        // Update the logAudit
        LogAudit updatedLogAudit = logAuditRepository.findById(logAudit.getId()).get();
        // Disconnect from session so that the updates on updatedLogAudit are not directly saved in db
        em.detach(updatedLogAudit);
        updatedLogAudit
            .levelAudit(UPDATED_LEVEL_AUDIT)
            .logName(UPDATED_LOG_NAME)
            .messageAudit(UPDATED_MESSAGE_AUDIT)
            .dateAudit(UPDATED_DATE_AUDIT);
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(updatedLogAudit);

        restLogAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, logAuditDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logAuditDTO))
            )
            .andExpect(status().isOk());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeUpdate);
        LogAudit testLogAudit = logAuditList.get(logAuditList.size() - 1);
        assertThat(testLogAudit.getLevelAudit()).isEqualTo(UPDATED_LEVEL_AUDIT);
        assertThat(testLogAudit.getLogName()).isEqualTo(UPDATED_LOG_NAME);
        assertThat(testLogAudit.getMessageAudit()).isEqualTo(UPDATED_MESSAGE_AUDIT);
        assertThat(testLogAudit.getDateAudit()).isEqualTo(UPDATED_DATE_AUDIT);
    }

    @Test
    @Transactional
    void putNonExistingLogAudit() throws Exception {
        int databaseSizeBeforeUpdate = logAuditRepository.findAll().size();
        logAudit.setId(count.incrementAndGet());

        // Create the LogAudit
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, logAuditDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logAuditDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLogAudit() throws Exception {
        int databaseSizeBeforeUpdate = logAuditRepository.findAll().size();
        logAudit.setId(count.incrementAndGet());

        // Create the LogAudit
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logAuditDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLogAudit() throws Exception {
        int databaseSizeBeforeUpdate = logAuditRepository.findAll().size();
        logAudit.setId(count.incrementAndGet());

        // Create the LogAudit
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogAuditMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logAuditDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLogAuditWithPatch() throws Exception {
        // Initialize the database
        logAuditRepository.saveAndFlush(logAudit);

        int databaseSizeBeforeUpdate = logAuditRepository.findAll().size();

        // Update the logAudit using partial update
        LogAudit partialUpdatedLogAudit = new LogAudit();
        partialUpdatedLogAudit.setId(logAudit.getId());

        partialUpdatedLogAudit
            .levelAudit(UPDATED_LEVEL_AUDIT)
            .logName(UPDATED_LOG_NAME)
            .messageAudit(UPDATED_MESSAGE_AUDIT)
            .dateAudit(UPDATED_DATE_AUDIT);

        restLogAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogAudit))
            )
            .andExpect(status().isOk());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeUpdate);
        LogAudit testLogAudit = logAuditList.get(logAuditList.size() - 1);
        assertThat(testLogAudit.getLevelAudit()).isEqualTo(UPDATED_LEVEL_AUDIT);
        assertThat(testLogAudit.getLogName()).isEqualTo(UPDATED_LOG_NAME);
        assertThat(testLogAudit.getMessageAudit()).isEqualTo(UPDATED_MESSAGE_AUDIT);
        assertThat(testLogAudit.getDateAudit()).isEqualTo(UPDATED_DATE_AUDIT);
    }

    @Test
    @Transactional
    void fullUpdateLogAuditWithPatch() throws Exception {
        // Initialize the database
        logAuditRepository.saveAndFlush(logAudit);

        int databaseSizeBeforeUpdate = logAuditRepository.findAll().size();

        // Update the logAudit using partial update
        LogAudit partialUpdatedLogAudit = new LogAudit();
        partialUpdatedLogAudit.setId(logAudit.getId());

        partialUpdatedLogAudit
            .levelAudit(UPDATED_LEVEL_AUDIT)
            .logName(UPDATED_LOG_NAME)
            .messageAudit(UPDATED_MESSAGE_AUDIT)
            .dateAudit(UPDATED_DATE_AUDIT);

        restLogAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogAudit))
            )
            .andExpect(status().isOk());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeUpdate);
        LogAudit testLogAudit = logAuditList.get(logAuditList.size() - 1);
        assertThat(testLogAudit.getLevelAudit()).isEqualTo(UPDATED_LEVEL_AUDIT);
        assertThat(testLogAudit.getLogName()).isEqualTo(UPDATED_LOG_NAME);
        assertThat(testLogAudit.getMessageAudit()).isEqualTo(UPDATED_MESSAGE_AUDIT);
        assertThat(testLogAudit.getDateAudit()).isEqualTo(UPDATED_DATE_AUDIT);
    }

    @Test
    @Transactional
    void patchNonExistingLogAudit() throws Exception {
        int databaseSizeBeforeUpdate = logAuditRepository.findAll().size();
        logAudit.setId(count.incrementAndGet());

        // Create the LogAudit
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, logAuditDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logAuditDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLogAudit() throws Exception {
        int databaseSizeBeforeUpdate = logAuditRepository.findAll().size();
        logAudit.setId(count.incrementAndGet());

        // Create the LogAudit
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logAuditDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLogAudit() throws Exception {
        int databaseSizeBeforeUpdate = logAuditRepository.findAll().size();
        logAudit.setId(count.incrementAndGet());

        // Create the LogAudit
        LogAuditDTO logAuditDTO = logAuditMapper.toDto(logAudit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogAuditMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(logAuditDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LogAudit in the database
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLogAudit() throws Exception {
        // Initialize the database
        logAuditRepository.saveAndFlush(logAudit);

        int databaseSizeBeforeDelete = logAuditRepository.findAll().size();

        // Delete the logAudit
        restLogAuditMockMvc
            .perform(delete(ENTITY_API_URL_ID, logAudit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogAudit> logAuditList = logAuditRepository.findAll();
        assertThat(logAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

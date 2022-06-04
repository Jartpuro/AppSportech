package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.ScheduleVersion;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.ScheduleVersionRepository;
import co.edu.sena.service.dto.ScheduleVersionDTO;
import co.edu.sena.service.mapper.ScheduleVersionMapper;
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
 * Integration tests for the {@link ScheduleVersionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScheduleVersionResourceIT {

    private static final String DEFAULT_VERSION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VERSION_NUMBER = "BBBBBBBBBB";

    private static final State DEFAULT_VERSION_STATE = State.ACTIVE;
    private static final State UPDATED_VERSION_STATE = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/schedule-versions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScheduleVersionRepository scheduleVersionRepository;

    @Autowired
    private ScheduleVersionMapper scheduleVersionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduleVersionMockMvc;

    private ScheduleVersion scheduleVersion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleVersion createEntity(EntityManager em) {
        ScheduleVersion scheduleVersion = new ScheduleVersion().versionNumber(DEFAULT_VERSION_NUMBER).versionState(DEFAULT_VERSION_STATE);
        return scheduleVersion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleVersion createUpdatedEntity(EntityManager em) {
        ScheduleVersion scheduleVersion = new ScheduleVersion().versionNumber(UPDATED_VERSION_NUMBER).versionState(UPDATED_VERSION_STATE);
        return scheduleVersion;
    }

    @BeforeEach
    public void initTest() {
        scheduleVersion = createEntity(em);
    }

    @Test
    @Transactional
    void createScheduleVersion() throws Exception {
        int databaseSizeBeforeCreate = scheduleVersionRepository.findAll().size();
        // Create the ScheduleVersion
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);
        restScheduleVersionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleVersion testScheduleVersion = scheduleVersionList.get(scheduleVersionList.size() - 1);
        assertThat(testScheduleVersion.getVersionNumber()).isEqualTo(DEFAULT_VERSION_NUMBER);
        assertThat(testScheduleVersion.getVersionState()).isEqualTo(DEFAULT_VERSION_STATE);
    }

    @Test
    @Transactional
    void createScheduleVersionWithExistingId() throws Exception {
        // Create the ScheduleVersion with an existing ID
        scheduleVersion.setId(1L);
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);

        int databaseSizeBeforeCreate = scheduleVersionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleVersionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVersionNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleVersionRepository.findAll().size();
        // set the field null
        scheduleVersion.setVersionNumber(null);

        // Create the ScheduleVersion, which fails.
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);

        restScheduleVersionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleVersionRepository.findAll().size();
        // set the field null
        scheduleVersion.setVersionState(null);

        // Create the ScheduleVersion, which fails.
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);

        restScheduleVersionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllScheduleVersions() throws Exception {
        // Initialize the database
        scheduleVersionRepository.saveAndFlush(scheduleVersion);

        // Get all the scheduleVersionList
        restScheduleVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].versionNumber").value(hasItem(DEFAULT_VERSION_NUMBER)))
            .andExpect(jsonPath("$.[*].versionState").value(hasItem(DEFAULT_VERSION_STATE.toString())));
    }

    @Test
    @Transactional
    void getScheduleVersion() throws Exception {
        // Initialize the database
        scheduleVersionRepository.saveAndFlush(scheduleVersion);

        // Get the scheduleVersion
        restScheduleVersionMockMvc
            .perform(get(ENTITY_API_URL_ID, scheduleVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleVersion.getId().intValue()))
            .andExpect(jsonPath("$.versionNumber").value(DEFAULT_VERSION_NUMBER))
            .andExpect(jsonPath("$.versionState").value(DEFAULT_VERSION_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingScheduleVersion() throws Exception {
        // Get the scheduleVersion
        restScheduleVersionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewScheduleVersion() throws Exception {
        // Initialize the database
        scheduleVersionRepository.saveAndFlush(scheduleVersion);

        int databaseSizeBeforeUpdate = scheduleVersionRepository.findAll().size();

        // Update the scheduleVersion
        ScheduleVersion updatedScheduleVersion = scheduleVersionRepository.findById(scheduleVersion.getId()).get();
        // Disconnect from session so that the updates on updatedScheduleVersion are not directly saved in db
        em.detach(updatedScheduleVersion);
        updatedScheduleVersion.versionNumber(UPDATED_VERSION_NUMBER).versionState(UPDATED_VERSION_STATE);
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(updatedScheduleVersion);

        restScheduleVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scheduleVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeUpdate);
        ScheduleVersion testScheduleVersion = scheduleVersionList.get(scheduleVersionList.size() - 1);
        assertThat(testScheduleVersion.getVersionNumber()).isEqualTo(UPDATED_VERSION_NUMBER);
        assertThat(testScheduleVersion.getVersionState()).isEqualTo(UPDATED_VERSION_STATE);
    }

    @Test
    @Transactional
    void putNonExistingScheduleVersion() throws Exception {
        int databaseSizeBeforeUpdate = scheduleVersionRepository.findAll().size();
        scheduleVersion.setId(count.incrementAndGet());

        // Create the ScheduleVersion
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scheduleVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScheduleVersion() throws Exception {
        int databaseSizeBeforeUpdate = scheduleVersionRepository.findAll().size();
        scheduleVersion.setId(count.incrementAndGet());

        // Create the ScheduleVersion
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduleVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScheduleVersion() throws Exception {
        int databaseSizeBeforeUpdate = scheduleVersionRepository.findAll().size();
        scheduleVersion.setId(count.incrementAndGet());

        // Create the ScheduleVersion
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduleVersionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScheduleVersionWithPatch() throws Exception {
        // Initialize the database
        scheduleVersionRepository.saveAndFlush(scheduleVersion);

        int databaseSizeBeforeUpdate = scheduleVersionRepository.findAll().size();

        // Update the scheduleVersion using partial update
        ScheduleVersion partialUpdatedScheduleVersion = new ScheduleVersion();
        partialUpdatedScheduleVersion.setId(scheduleVersion.getId());

        partialUpdatedScheduleVersion.versionNumber(UPDATED_VERSION_NUMBER).versionState(UPDATED_VERSION_STATE);

        restScheduleVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScheduleVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScheduleVersion))
            )
            .andExpect(status().isOk());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeUpdate);
        ScheduleVersion testScheduleVersion = scheduleVersionList.get(scheduleVersionList.size() - 1);
        assertThat(testScheduleVersion.getVersionNumber()).isEqualTo(UPDATED_VERSION_NUMBER);
        assertThat(testScheduleVersion.getVersionState()).isEqualTo(UPDATED_VERSION_STATE);
    }

    @Test
    @Transactional
    void fullUpdateScheduleVersionWithPatch() throws Exception {
        // Initialize the database
        scheduleVersionRepository.saveAndFlush(scheduleVersion);

        int databaseSizeBeforeUpdate = scheduleVersionRepository.findAll().size();

        // Update the scheduleVersion using partial update
        ScheduleVersion partialUpdatedScheduleVersion = new ScheduleVersion();
        partialUpdatedScheduleVersion.setId(scheduleVersion.getId());

        partialUpdatedScheduleVersion.versionNumber(UPDATED_VERSION_NUMBER).versionState(UPDATED_VERSION_STATE);

        restScheduleVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScheduleVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScheduleVersion))
            )
            .andExpect(status().isOk());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeUpdate);
        ScheduleVersion testScheduleVersion = scheduleVersionList.get(scheduleVersionList.size() - 1);
        assertThat(testScheduleVersion.getVersionNumber()).isEqualTo(UPDATED_VERSION_NUMBER);
        assertThat(testScheduleVersion.getVersionState()).isEqualTo(UPDATED_VERSION_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingScheduleVersion() throws Exception {
        int databaseSizeBeforeUpdate = scheduleVersionRepository.findAll().size();
        scheduleVersion.setId(count.incrementAndGet());

        // Create the ScheduleVersion
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scheduleVersionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScheduleVersion() throws Exception {
        int databaseSizeBeforeUpdate = scheduleVersionRepository.findAll().size();
        scheduleVersion.setId(count.incrementAndGet());

        // Create the ScheduleVersion
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduleVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScheduleVersion() throws Exception {
        int databaseSizeBeforeUpdate = scheduleVersionRepository.findAll().size();
        scheduleVersion.setId(count.incrementAndGet());

        // Create the ScheduleVersion
        ScheduleVersionDTO scheduleVersionDTO = scheduleVersionMapper.toDto(scheduleVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduleVersionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scheduleVersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScheduleVersion in the database
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScheduleVersion() throws Exception {
        // Initialize the database
        scheduleVersionRepository.saveAndFlush(scheduleVersion);

        int databaseSizeBeforeDelete = scheduleVersionRepository.findAll().size();

        // Delete the scheduleVersion
        restScheduleVersionMockMvc
            .perform(delete(ENTITY_API_URL_ID, scheduleVersion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScheduleVersion> scheduleVersionList = scheduleVersionRepository.findAll();
        assertThat(scheduleVersionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

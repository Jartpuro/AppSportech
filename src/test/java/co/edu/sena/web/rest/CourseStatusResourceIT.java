package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.CourseStatus;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.CourseStatusRepository;
import co.edu.sena.service.dto.CourseStatusDTO;
import co.edu.sena.service.mapper.CourseStatusMapper;
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
 * Integration tests for the {@link CourseStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseStatusResourceIT {

    private static final String DEFAULT_NAME_COURSE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_NAME_COURSE_STATUS = "BBBBBBBBBB";

    private static final State DEFAULT_STATE_COURSE = State.ACTIVE;
    private static final State UPDATED_STATE_COURSE = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/course-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseStatusRepository courseStatusRepository;

    @Autowired
    private CourseStatusMapper courseStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseStatusMockMvc;

    private CourseStatus courseStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseStatus createEntity(EntityManager em) {
        CourseStatus courseStatus = new CourseStatus().nameCourseStatus(DEFAULT_NAME_COURSE_STATUS).stateCourse(DEFAULT_STATE_COURSE);
        return courseStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseStatus createUpdatedEntity(EntityManager em) {
        CourseStatus courseStatus = new CourseStatus().nameCourseStatus(UPDATED_NAME_COURSE_STATUS).stateCourse(UPDATED_STATE_COURSE);
        return courseStatus;
    }

    @BeforeEach
    public void initTest() {
        courseStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseStatus() throws Exception {
        int databaseSizeBeforeCreate = courseStatusRepository.findAll().size();
        // Create the CourseStatus
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);
        restCourseStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CourseStatus testCourseStatus = courseStatusList.get(courseStatusList.size() - 1);
        assertThat(testCourseStatus.getNameCourseStatus()).isEqualTo(DEFAULT_NAME_COURSE_STATUS);
        assertThat(testCourseStatus.getStateCourse()).isEqualTo(DEFAULT_STATE_COURSE);
    }

    @Test
    @Transactional
    void createCourseStatusWithExistingId() throws Exception {
        // Create the CourseStatus with an existing ID
        courseStatus.setId(1L);
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);

        int databaseSizeBeforeCreate = courseStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameCourseStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseStatusRepository.findAll().size();
        // set the field null
        courseStatus.setNameCourseStatus(null);

        // Create the CourseStatus, which fails.
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);

        restCourseStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateCourseIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseStatusRepository.findAll().size();
        // set the field null
        courseStatus.setStateCourse(null);

        // Create the CourseStatus, which fails.
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);

        restCourseStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseStatuses() throws Exception {
        // Initialize the database
        courseStatusRepository.saveAndFlush(courseStatus);

        // Get all the courseStatusList
        restCourseStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameCourseStatus").value(hasItem(DEFAULT_NAME_COURSE_STATUS)))
            .andExpect(jsonPath("$.[*].stateCourse").value(hasItem(DEFAULT_STATE_COURSE.toString())));
    }

    @Test
    @Transactional
    void getCourseStatus() throws Exception {
        // Initialize the database
        courseStatusRepository.saveAndFlush(courseStatus);

        // Get the courseStatus
        restCourseStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, courseStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseStatus.getId().intValue()))
            .andExpect(jsonPath("$.nameCourseStatus").value(DEFAULT_NAME_COURSE_STATUS))
            .andExpect(jsonPath("$.stateCourse").value(DEFAULT_STATE_COURSE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCourseStatus() throws Exception {
        // Get the courseStatus
        restCourseStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseStatus() throws Exception {
        // Initialize the database
        courseStatusRepository.saveAndFlush(courseStatus);

        int databaseSizeBeforeUpdate = courseStatusRepository.findAll().size();

        // Update the courseStatus
        CourseStatus updatedCourseStatus = courseStatusRepository.findById(courseStatus.getId()).get();
        // Disconnect from session so that the updates on updatedCourseStatus are not directly saved in db
        em.detach(updatedCourseStatus);
        updatedCourseStatus.nameCourseStatus(UPDATED_NAME_COURSE_STATUS).stateCourse(UPDATED_STATE_COURSE);
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(updatedCourseStatus);

        restCourseStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeUpdate);
        CourseStatus testCourseStatus = courseStatusList.get(courseStatusList.size() - 1);
        assertThat(testCourseStatus.getNameCourseStatus()).isEqualTo(UPDATED_NAME_COURSE_STATUS);
        assertThat(testCourseStatus.getStateCourse()).isEqualTo(UPDATED_STATE_COURSE);
    }

    @Test
    @Transactional
    void putNonExistingCourseStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseStatusRepository.findAll().size();
        courseStatus.setId(count.incrementAndGet());

        // Create the CourseStatus
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseStatusRepository.findAll().size();
        courseStatus.setId(count.incrementAndGet());

        // Create the CourseStatus
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseStatusRepository.findAll().size();
        courseStatus.setId(count.incrementAndGet());

        // Create the CourseStatus
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseStatusWithPatch() throws Exception {
        // Initialize the database
        courseStatusRepository.saveAndFlush(courseStatus);

        int databaseSizeBeforeUpdate = courseStatusRepository.findAll().size();

        // Update the courseStatus using partial update
        CourseStatus partialUpdatedCourseStatus = new CourseStatus();
        partialUpdatedCourseStatus.setId(courseStatus.getId());

        restCourseStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseStatus))
            )
            .andExpect(status().isOk());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeUpdate);
        CourseStatus testCourseStatus = courseStatusList.get(courseStatusList.size() - 1);
        assertThat(testCourseStatus.getNameCourseStatus()).isEqualTo(DEFAULT_NAME_COURSE_STATUS);
        assertThat(testCourseStatus.getStateCourse()).isEqualTo(DEFAULT_STATE_COURSE);
    }

    @Test
    @Transactional
    void fullUpdateCourseStatusWithPatch() throws Exception {
        // Initialize the database
        courseStatusRepository.saveAndFlush(courseStatus);

        int databaseSizeBeforeUpdate = courseStatusRepository.findAll().size();

        // Update the courseStatus using partial update
        CourseStatus partialUpdatedCourseStatus = new CourseStatus();
        partialUpdatedCourseStatus.setId(courseStatus.getId());

        partialUpdatedCourseStatus.nameCourseStatus(UPDATED_NAME_COURSE_STATUS).stateCourse(UPDATED_STATE_COURSE);

        restCourseStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseStatus))
            )
            .andExpect(status().isOk());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeUpdate);
        CourseStatus testCourseStatus = courseStatusList.get(courseStatusList.size() - 1);
        assertThat(testCourseStatus.getNameCourseStatus()).isEqualTo(UPDATED_NAME_COURSE_STATUS);
        assertThat(testCourseStatus.getStateCourse()).isEqualTo(UPDATED_STATE_COURSE);
    }

    @Test
    @Transactional
    void patchNonExistingCourseStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseStatusRepository.findAll().size();
        courseStatus.setId(count.incrementAndGet());

        // Create the CourseStatus
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseStatusRepository.findAll().size();
        courseStatus.setId(count.incrementAndGet());

        // Create the CourseStatus
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseStatusRepository.findAll().size();
        courseStatus.setId(count.incrementAndGet());

        // Create the CourseStatus
        CourseStatusDTO courseStatusDTO = courseStatusMapper.toDto(courseStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseStatus in the database
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseStatus() throws Exception {
        // Initialize the database
        courseStatusRepository.saveAndFlush(courseStatus);

        int databaseSizeBeforeDelete = courseStatusRepository.findAll().size();

        // Delete the courseStatus
        restCourseStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseStatus> courseStatusList = courseStatusRepository.findAll();
        assertThat(courseStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

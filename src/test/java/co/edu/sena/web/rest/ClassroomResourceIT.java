package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Campus;
import co.edu.sena.domain.Classroom;
import co.edu.sena.domain.ClassroomType;
import co.edu.sena.repository.ClassroomRepository;
import co.edu.sena.service.ClassroomService;
import co.edu.sena.service.dto.ClassroomDTO;
import co.edu.sena.service.mapper.ClassroomMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClassroomResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClassroomResourceIT {

    private static final String DEFAULT_CLASSROOM_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CLASSROOM_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSROOM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CLASSROOM_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSROOM_STATE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSROOM_STATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/classrooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClassroomRepository classroomRepository;

    @Mock
    private ClassroomRepository classroomRepositoryMock;

    @Autowired
    private ClassroomMapper classroomMapper;

    @Mock
    private ClassroomService classroomServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassroomMockMvc;

    private Classroom classroom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classroom createEntity(EntityManager em) {
        Classroom classroom = new Classroom()
            .classroomNumber(DEFAULT_CLASSROOM_NUMBER)
            .classroomDescription(DEFAULT_CLASSROOM_DESCRIPTION)
            .classroomState(DEFAULT_CLASSROOM_STATE);
        // Add required entity
        ClassroomType classroomType;
        if (TestUtil.findAll(em, ClassroomType.class).isEmpty()) {
            classroomType = ClassroomTypeResourceIT.createEntity(em);
            em.persist(classroomType);
            em.flush();
        } else {
            classroomType = TestUtil.findAll(em, ClassroomType.class).get(0);
        }
        classroom.setClassroomType(classroomType);
        // Add required entity
        Campus campus;
        if (TestUtil.findAll(em, Campus.class).isEmpty()) {
            campus = CampusResourceIT.createEntity(em);
            em.persist(campus);
            em.flush();
        } else {
            campus = TestUtil.findAll(em, Campus.class).get(0);
        }
        classroom.setCampus(campus);
        return classroom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classroom createUpdatedEntity(EntityManager em) {
        Classroom classroom = new Classroom()
            .classroomNumber(UPDATED_CLASSROOM_NUMBER)
            .classroomDescription(UPDATED_CLASSROOM_DESCRIPTION)
            .classroomState(UPDATED_CLASSROOM_STATE);
        // Add required entity
        ClassroomType classroomType;
        if (TestUtil.findAll(em, ClassroomType.class).isEmpty()) {
            classroomType = ClassroomTypeResourceIT.createUpdatedEntity(em);
            em.persist(classroomType);
            em.flush();
        } else {
            classroomType = TestUtil.findAll(em, ClassroomType.class).get(0);
        }
        classroom.setClassroomType(classroomType);
        // Add required entity
        Campus campus;
        if (TestUtil.findAll(em, Campus.class).isEmpty()) {
            campus = CampusResourceIT.createUpdatedEntity(em);
            em.persist(campus);
            em.flush();
        } else {
            campus = TestUtil.findAll(em, Campus.class).get(0);
        }
        classroom.setCampus(campus);
        return classroom;
    }

    @BeforeEach
    public void initTest() {
        classroom = createEntity(em);
    }

    @Test
    @Transactional
    void createClassroom() throws Exception {
        int databaseSizeBeforeCreate = classroomRepository.findAll().size();
        // Create the Classroom
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);
        restClassroomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomDTO)))
            .andExpect(status().isCreated());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeCreate + 1);
        Classroom testClassroom = classroomList.get(classroomList.size() - 1);
        assertThat(testClassroom.getClassroomNumber()).isEqualTo(DEFAULT_CLASSROOM_NUMBER);
        assertThat(testClassroom.getClassroomDescription()).isEqualTo(DEFAULT_CLASSROOM_DESCRIPTION);
        assertThat(testClassroom.getClassroomState()).isEqualTo(DEFAULT_CLASSROOM_STATE);
    }

    @Test
    @Transactional
    void createClassroomWithExistingId() throws Exception {
        // Create the Classroom with an existing ID
        classroom.setId(1L);
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        int databaseSizeBeforeCreate = classroomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassroomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkClassroomNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = classroomRepository.findAll().size();
        // set the field null
        classroom.setClassroomNumber(null);

        // Create the Classroom, which fails.
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        restClassroomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomDTO)))
            .andExpect(status().isBadRequest());

        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClassroomDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = classroomRepository.findAll().size();
        // set the field null
        classroom.setClassroomDescription(null);

        // Create the Classroom, which fails.
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        restClassroomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomDTO)))
            .andExpect(status().isBadRequest());

        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClassroomStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = classroomRepository.findAll().size();
        // set the field null
        classroom.setClassroomState(null);

        // Create the Classroom, which fails.
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        restClassroomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomDTO)))
            .andExpect(status().isBadRequest());

        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClassrooms() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        // Get all the classroomList
        restClassroomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classroom.getId().intValue())))
            .andExpect(jsonPath("$.[*].classroomNumber").value(hasItem(DEFAULT_CLASSROOM_NUMBER)))
            .andExpect(jsonPath("$.[*].classroomDescription").value(hasItem(DEFAULT_CLASSROOM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].classroomState").value(hasItem(DEFAULT_CLASSROOM_STATE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClassroomsWithEagerRelationshipsIsEnabled() throws Exception {
        when(classroomServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClassroomMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(classroomServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClassroomsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(classroomServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClassroomMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(classroomServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getClassroom() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        // Get the classroom
        restClassroomMockMvc
            .perform(get(ENTITY_API_URL_ID, classroom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classroom.getId().intValue()))
            .andExpect(jsonPath("$.classroomNumber").value(DEFAULT_CLASSROOM_NUMBER))
            .andExpect(jsonPath("$.classroomDescription").value(DEFAULT_CLASSROOM_DESCRIPTION))
            .andExpect(jsonPath("$.classroomState").value(DEFAULT_CLASSROOM_STATE));
    }

    @Test
    @Transactional
    void getNonExistingClassroom() throws Exception {
        // Get the classroom
        restClassroomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClassroom() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();

        // Update the classroom
        Classroom updatedClassroom = classroomRepository.findById(classroom.getId()).get();
        // Disconnect from session so that the updates on updatedClassroom are not directly saved in db
        em.detach(updatedClassroom);
        updatedClassroom
            .classroomNumber(UPDATED_CLASSROOM_NUMBER)
            .classroomDescription(UPDATED_CLASSROOM_DESCRIPTION)
            .classroomState(UPDATED_CLASSROOM_STATE);
        ClassroomDTO classroomDTO = classroomMapper.toDto(updatedClassroom);

        restClassroomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classroomDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classroomDTO))
            )
            .andExpect(status().isOk());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
        Classroom testClassroom = classroomList.get(classroomList.size() - 1);
        assertThat(testClassroom.getClassroomNumber()).isEqualTo(UPDATED_CLASSROOM_NUMBER);
        assertThat(testClassroom.getClassroomDescription()).isEqualTo(UPDATED_CLASSROOM_DESCRIPTION);
        assertThat(testClassroom.getClassroomState()).isEqualTo(UPDATED_CLASSROOM_STATE);
    }

    @Test
    @Transactional
    void putNonExistingClassroom() throws Exception {
        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();
        classroom.setId(count.incrementAndGet());

        // Create the Classroom
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassroomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classroomDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classroomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassroom() throws Exception {
        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();
        classroom.setId(count.incrementAndGet());

        // Create the Classroom
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassroomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classroomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassroom() throws Exception {
        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();
        classroom.setId(count.incrementAndGet());

        // Create the Classroom
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassroomMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClassroomWithPatch() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();

        // Update the classroom using partial update
        Classroom partialUpdatedClassroom = new Classroom();
        partialUpdatedClassroom.setId(classroom.getId());

        partialUpdatedClassroom
            .classroomNumber(UPDATED_CLASSROOM_NUMBER)
            .classroomDescription(UPDATED_CLASSROOM_DESCRIPTION)
            .classroomState(UPDATED_CLASSROOM_STATE);

        restClassroomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassroom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassroom))
            )
            .andExpect(status().isOk());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
        Classroom testClassroom = classroomList.get(classroomList.size() - 1);
        assertThat(testClassroom.getClassroomNumber()).isEqualTo(UPDATED_CLASSROOM_NUMBER);
        assertThat(testClassroom.getClassroomDescription()).isEqualTo(UPDATED_CLASSROOM_DESCRIPTION);
        assertThat(testClassroom.getClassroomState()).isEqualTo(UPDATED_CLASSROOM_STATE);
    }

    @Test
    @Transactional
    void fullUpdateClassroomWithPatch() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();

        // Update the classroom using partial update
        Classroom partialUpdatedClassroom = new Classroom();
        partialUpdatedClassroom.setId(classroom.getId());

        partialUpdatedClassroom
            .classroomNumber(UPDATED_CLASSROOM_NUMBER)
            .classroomDescription(UPDATED_CLASSROOM_DESCRIPTION)
            .classroomState(UPDATED_CLASSROOM_STATE);

        restClassroomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassroom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassroom))
            )
            .andExpect(status().isOk());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
        Classroom testClassroom = classroomList.get(classroomList.size() - 1);
        assertThat(testClassroom.getClassroomNumber()).isEqualTo(UPDATED_CLASSROOM_NUMBER);
        assertThat(testClassroom.getClassroomDescription()).isEqualTo(UPDATED_CLASSROOM_DESCRIPTION);
        assertThat(testClassroom.getClassroomState()).isEqualTo(UPDATED_CLASSROOM_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingClassroom() throws Exception {
        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();
        classroom.setId(count.incrementAndGet());

        // Create the Classroom
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassroomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classroomDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classroomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassroom() throws Exception {
        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();
        classroom.setId(count.incrementAndGet());

        // Create the Classroom
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassroomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classroomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassroom() throws Exception {
        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();
        classroom.setId(count.incrementAndGet());

        // Create the Classroom
        ClassroomDTO classroomDTO = classroomMapper.toDto(classroom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassroomMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(classroomDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClassroom() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        int databaseSizeBeforeDelete = classroomRepository.findAll().size();

        // Delete the classroom
        restClassroomMockMvc
            .perform(delete(ENTITY_API_URL_ID, classroom.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

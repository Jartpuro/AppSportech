package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Course;
import co.edu.sena.domain.CourseModule;
import co.edu.sena.domain.Module;
import co.edu.sena.repository.CourseModuleRepository;
import co.edu.sena.service.CourseModuleService;
import co.edu.sena.service.dto.CourseModuleDTO;
import co.edu.sena.service.mapper.CourseModuleMapper;
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
 * Integration tests for the {@link CourseModuleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CourseModuleResourceIT {

    private static final String ENTITY_API_URL = "/api/course-modules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Mock
    private CourseModuleRepository courseModuleRepositoryMock;

    @Autowired
    private CourseModuleMapper courseModuleMapper;

    @Mock
    private CourseModuleService courseModuleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseModuleMockMvc;

    private CourseModule courseModule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseModule createEntity(EntityManager em) {
        CourseModule courseModule = new CourseModule();
        // Add required entity
        Course course;
        if (TestUtil.findAll(em, Course.class).isEmpty()) {
            course = CourseResourceIT.createEntity(em);
            em.persist(course);
            em.flush();
        } else {
            course = TestUtil.findAll(em, Course.class).get(0);
        }
        courseModule.setCourse(course);
        // Add required entity
        Module module;
        if (TestUtil.findAll(em, Module.class).isEmpty()) {
            module = ModuleResourceIT.createEntity(em);
            em.persist(module);
            em.flush();
        } else {
            module = TestUtil.findAll(em, Module.class).get(0);
        }
        courseModule.setModule(module);
        return courseModule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseModule createUpdatedEntity(EntityManager em) {
        CourseModule courseModule = new CourseModule();
        // Add required entity
        Course course;
        if (TestUtil.findAll(em, Course.class).isEmpty()) {
            course = CourseResourceIT.createUpdatedEntity(em);
            em.persist(course);
            em.flush();
        } else {
            course = TestUtil.findAll(em, Course.class).get(0);
        }
        courseModule.setCourse(course);
        // Add required entity
        Module module;
        if (TestUtil.findAll(em, Module.class).isEmpty()) {
            module = ModuleResourceIT.createUpdatedEntity(em);
            em.persist(module);
            em.flush();
        } else {
            module = TestUtil.findAll(em, Module.class).get(0);
        }
        courseModule.setModule(module);
        return courseModule;
    }

    @BeforeEach
    public void initTest() {
        courseModule = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseModule() throws Exception {
        int databaseSizeBeforeCreate = courseModuleRepository.findAll().size();
        // Create the CourseModule
        CourseModuleDTO courseModuleDTO = courseModuleMapper.toDto(courseModule);
        restCourseModuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseModuleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeCreate + 1);
        CourseModule testCourseModule = courseModuleList.get(courseModuleList.size() - 1);
    }

    @Test
    @Transactional
    void createCourseModuleWithExistingId() throws Exception {
        // Create the CourseModule with an existing ID
        courseModule.setId(1L);
        CourseModuleDTO courseModuleDTO = courseModuleMapper.toDto(courseModule);

        int databaseSizeBeforeCreate = courseModuleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseModuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseModuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourseModules() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);

        // Get all the courseModuleList
        restCourseModuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseModule.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCourseModulesWithEagerRelationshipsIsEnabled() throws Exception {
        when(courseModuleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCourseModuleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(courseModuleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCourseModulesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(courseModuleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCourseModuleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(courseModuleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCourseModule() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);

        // Get the courseModule
        restCourseModuleMockMvc
            .perform(get(ENTITY_API_URL_ID, courseModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseModule.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCourseModule() throws Exception {
        // Get the courseModule
        restCourseModuleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseModule() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);

        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();

        // Update the courseModule
        CourseModule updatedCourseModule = courseModuleRepository.findById(courseModule.getId()).get();
        // Disconnect from session so that the updates on updatedCourseModule are not directly saved in db
        em.detach(updatedCourseModule);
        CourseModuleDTO courseModuleDTO = courseModuleMapper.toDto(updatedCourseModule);

        restCourseModuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseModuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseModuleDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
        CourseModule testCourseModule = courseModuleList.get(courseModuleList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCourseModule() throws Exception {
        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();
        courseModule.setId(count.incrementAndGet());

        // Create the CourseModule
        CourseModuleDTO courseModuleDTO = courseModuleMapper.toDto(courseModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseModuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseModuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseModuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseModule() throws Exception {
        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();
        courseModule.setId(count.incrementAndGet());

        // Create the CourseModule
        CourseModuleDTO courseModuleDTO = courseModuleMapper.toDto(courseModule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseModuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseModuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseModule() throws Exception {
        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();
        courseModule.setId(count.incrementAndGet());

        // Create the CourseModule
        CourseModuleDTO courseModuleDTO = courseModuleMapper.toDto(courseModule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseModuleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseModuleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseModuleWithPatch() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);

        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();

        // Update the courseModule using partial update
        CourseModule partialUpdatedCourseModule = new CourseModule();
        partialUpdatedCourseModule.setId(courseModule.getId());

        restCourseModuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseModule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseModule))
            )
            .andExpect(status().isOk());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
        CourseModule testCourseModule = courseModuleList.get(courseModuleList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCourseModuleWithPatch() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);

        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();

        // Update the courseModule using partial update
        CourseModule partialUpdatedCourseModule = new CourseModule();
        partialUpdatedCourseModule.setId(courseModule.getId());

        restCourseModuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseModule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseModule))
            )
            .andExpect(status().isOk());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
        CourseModule testCourseModule = courseModuleList.get(courseModuleList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCourseModule() throws Exception {
        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();
        courseModule.setId(count.incrementAndGet());

        // Create the CourseModule
        CourseModuleDTO courseModuleDTO = courseModuleMapper.toDto(courseModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseModuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseModuleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseModuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseModule() throws Exception {
        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();
        courseModule.setId(count.incrementAndGet());

        // Create the CourseModule
        CourseModuleDTO courseModuleDTO = courseModuleMapper.toDto(courseModule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseModuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseModuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseModule() throws Exception {
        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();
        courseModule.setId(count.incrementAndGet());

        // Create the CourseModule
        CourseModuleDTO courseModuleDTO = courseModuleMapper.toDto(courseModule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseModuleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseModuleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseModule() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);

        int databaseSizeBeforeDelete = courseModuleRepository.findAll().size();

        // Delete the courseModule
        restCourseModuleMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseModule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

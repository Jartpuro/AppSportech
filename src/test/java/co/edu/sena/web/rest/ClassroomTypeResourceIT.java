package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.ClassroomType;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.ClassroomTypeRepository;
import co.edu.sena.service.dto.ClassroomTypeDTO;
import co.edu.sena.service.mapper.ClassroomTypeMapper;
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
 * Integration tests for the {@link ClassroomTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassroomTypeResourceIT {

    private static final String DEFAULT_TYPE_CLASSROOM = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CLASSROOM = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSROOM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CLASSROOM_DESCRIPTION = "BBBBBBBBBB";

    private static final State DEFAULT_CLASSROOM_STATE = State.ACTIVE;
    private static final State UPDATED_CLASSROOM_STATE = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/classroom-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClassroomTypeRepository classroomTypeRepository;

    @Autowired
    private ClassroomTypeMapper classroomTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassroomTypeMockMvc;

    private ClassroomType classroomType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassroomType createEntity(EntityManager em) {
        ClassroomType classroomType = new ClassroomType()
            .typeClassroom(DEFAULT_TYPE_CLASSROOM)
            .classroomDescription(DEFAULT_CLASSROOM_DESCRIPTION)
            .classroomState(DEFAULT_CLASSROOM_STATE);
        return classroomType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassroomType createUpdatedEntity(EntityManager em) {
        ClassroomType classroomType = new ClassroomType()
            .typeClassroom(UPDATED_TYPE_CLASSROOM)
            .classroomDescription(UPDATED_CLASSROOM_DESCRIPTION)
            .classroomState(UPDATED_CLASSROOM_STATE);
        return classroomType;
    }

    @BeforeEach
    public void initTest() {
        classroomType = createEntity(em);
    }

    @Test
    @Transactional
    void createClassroomType() throws Exception {
        int databaseSizeBeforeCreate = classroomTypeRepository.findAll().size();
        // Create the ClassroomType
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);
        restClassroomTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ClassroomType testClassroomType = classroomTypeList.get(classroomTypeList.size() - 1);
        assertThat(testClassroomType.getTypeClassroom()).isEqualTo(DEFAULT_TYPE_CLASSROOM);
        assertThat(testClassroomType.getClassroomDescription()).isEqualTo(DEFAULT_CLASSROOM_DESCRIPTION);
        assertThat(testClassroomType.getClassroomState()).isEqualTo(DEFAULT_CLASSROOM_STATE);
    }

    @Test
    @Transactional
    void createClassroomTypeWithExistingId() throws Exception {
        // Create the ClassroomType with an existing ID
        classroomType.setId(1L);
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        int databaseSizeBeforeCreate = classroomTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassroomTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeClassroomIsRequired() throws Exception {
        int databaseSizeBeforeTest = classroomTypeRepository.findAll().size();
        // set the field null
        classroomType.setTypeClassroom(null);

        // Create the ClassroomType, which fails.
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        restClassroomTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClassroomDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = classroomTypeRepository.findAll().size();
        // set the field null
        classroomType.setClassroomDescription(null);

        // Create the ClassroomType, which fails.
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        restClassroomTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClassroomStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = classroomTypeRepository.findAll().size();
        // set the field null
        classroomType.setClassroomState(null);

        // Create the ClassroomType, which fails.
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        restClassroomTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClassroomTypes() throws Exception {
        // Initialize the database
        classroomTypeRepository.saveAndFlush(classroomType);

        // Get all the classroomTypeList
        restClassroomTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classroomType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeClassroom").value(hasItem(DEFAULT_TYPE_CLASSROOM)))
            .andExpect(jsonPath("$.[*].classroomDescription").value(hasItem(DEFAULT_CLASSROOM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].classroomState").value(hasItem(DEFAULT_CLASSROOM_STATE.toString())));
    }

    @Test
    @Transactional
    void getClassroomType() throws Exception {
        // Initialize the database
        classroomTypeRepository.saveAndFlush(classroomType);

        // Get the classroomType
        restClassroomTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, classroomType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classroomType.getId().intValue()))
            .andExpect(jsonPath("$.typeClassroom").value(DEFAULT_TYPE_CLASSROOM))
            .andExpect(jsonPath("$.classroomDescription").value(DEFAULT_CLASSROOM_DESCRIPTION))
            .andExpect(jsonPath("$.classroomState").value(DEFAULT_CLASSROOM_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingClassroomType() throws Exception {
        // Get the classroomType
        restClassroomTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClassroomType() throws Exception {
        // Initialize the database
        classroomTypeRepository.saveAndFlush(classroomType);

        int databaseSizeBeforeUpdate = classroomTypeRepository.findAll().size();

        // Update the classroomType
        ClassroomType updatedClassroomType = classroomTypeRepository.findById(classroomType.getId()).get();
        // Disconnect from session so that the updates on updatedClassroomType are not directly saved in db
        em.detach(updatedClassroomType);
        updatedClassroomType
            .typeClassroom(UPDATED_TYPE_CLASSROOM)
            .classroomDescription(UPDATED_CLASSROOM_DESCRIPTION)
            .classroomState(UPDATED_CLASSROOM_STATE);
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(updatedClassroomType);

        restClassroomTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classroomTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeUpdate);
        ClassroomType testClassroomType = classroomTypeList.get(classroomTypeList.size() - 1);
        assertThat(testClassroomType.getTypeClassroom()).isEqualTo(UPDATED_TYPE_CLASSROOM);
        assertThat(testClassroomType.getClassroomDescription()).isEqualTo(UPDATED_CLASSROOM_DESCRIPTION);
        assertThat(testClassroomType.getClassroomState()).isEqualTo(UPDATED_CLASSROOM_STATE);
    }

    @Test
    @Transactional
    void putNonExistingClassroomType() throws Exception {
        int databaseSizeBeforeUpdate = classroomTypeRepository.findAll().size();
        classroomType.setId(count.incrementAndGet());

        // Create the ClassroomType
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassroomTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classroomTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassroomType() throws Exception {
        int databaseSizeBeforeUpdate = classroomTypeRepository.findAll().size();
        classroomType.setId(count.incrementAndGet());

        // Create the ClassroomType
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassroomTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassroomType() throws Exception {
        int databaseSizeBeforeUpdate = classroomTypeRepository.findAll().size();
        classroomType.setId(count.incrementAndGet());

        // Create the ClassroomType
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassroomTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClassroomTypeWithPatch() throws Exception {
        // Initialize the database
        classroomTypeRepository.saveAndFlush(classroomType);

        int databaseSizeBeforeUpdate = classroomTypeRepository.findAll().size();

        // Update the classroomType using partial update
        ClassroomType partialUpdatedClassroomType = new ClassroomType();
        partialUpdatedClassroomType.setId(classroomType.getId());

        partialUpdatedClassroomType.typeClassroom(UPDATED_TYPE_CLASSROOM).classroomDescription(UPDATED_CLASSROOM_DESCRIPTION);

        restClassroomTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassroomType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassroomType))
            )
            .andExpect(status().isOk());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeUpdate);
        ClassroomType testClassroomType = classroomTypeList.get(classroomTypeList.size() - 1);
        assertThat(testClassroomType.getTypeClassroom()).isEqualTo(UPDATED_TYPE_CLASSROOM);
        assertThat(testClassroomType.getClassroomDescription()).isEqualTo(UPDATED_CLASSROOM_DESCRIPTION);
        assertThat(testClassroomType.getClassroomState()).isEqualTo(DEFAULT_CLASSROOM_STATE);
    }

    @Test
    @Transactional
    void fullUpdateClassroomTypeWithPatch() throws Exception {
        // Initialize the database
        classroomTypeRepository.saveAndFlush(classroomType);

        int databaseSizeBeforeUpdate = classroomTypeRepository.findAll().size();

        // Update the classroomType using partial update
        ClassroomType partialUpdatedClassroomType = new ClassroomType();
        partialUpdatedClassroomType.setId(classroomType.getId());

        partialUpdatedClassroomType
            .typeClassroom(UPDATED_TYPE_CLASSROOM)
            .classroomDescription(UPDATED_CLASSROOM_DESCRIPTION)
            .classroomState(UPDATED_CLASSROOM_STATE);

        restClassroomTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassroomType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassroomType))
            )
            .andExpect(status().isOk());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeUpdate);
        ClassroomType testClassroomType = classroomTypeList.get(classroomTypeList.size() - 1);
        assertThat(testClassroomType.getTypeClassroom()).isEqualTo(UPDATED_TYPE_CLASSROOM);
        assertThat(testClassroomType.getClassroomDescription()).isEqualTo(UPDATED_CLASSROOM_DESCRIPTION);
        assertThat(testClassroomType.getClassroomState()).isEqualTo(UPDATED_CLASSROOM_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingClassroomType() throws Exception {
        int databaseSizeBeforeUpdate = classroomTypeRepository.findAll().size();
        classroomType.setId(count.incrementAndGet());

        // Create the ClassroomType
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassroomTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classroomTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassroomType() throws Exception {
        int databaseSizeBeforeUpdate = classroomTypeRepository.findAll().size();
        classroomType.setId(count.incrementAndGet());

        // Create the ClassroomType
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassroomTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassroomType() throws Exception {
        int databaseSizeBeforeUpdate = classroomTypeRepository.findAll().size();
        classroomType.setId(count.incrementAndGet());

        // Create the ClassroomType
        ClassroomTypeDTO classroomTypeDTO = classroomTypeMapper.toDto(classroomType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassroomTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classroomTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassroomType in the database
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClassroomType() throws Exception {
        // Initialize the database
        classroomTypeRepository.saveAndFlush(classroomType);

        int databaseSizeBeforeDelete = classroomTypeRepository.findAll().size();

        // Delete the classroomType
        restClassroomTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, classroomType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassroomType> classroomTypeList = classroomTypeRepository.findAll();
        assertThat(classroomTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

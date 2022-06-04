package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Campus;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.CampusRepository;
import co.edu.sena.service.dto.CampusDTO;
import co.edu.sena.service.mapper.CampusMapper;
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
 * Integration tests for the {@link CampusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CampusResourceIT {

    private static final String DEFAULT_CAMPUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAMPUS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAMPUS_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CAMPUS_ADDRESS = "BBBBBBBBBB";

    private static final State DEFAULT_CAMPUS_STATE = State.ACTIVE;
    private static final State UPDATED_CAMPUS_STATE = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/campuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private CampusMapper campusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCampusMockMvc;

    private Campus campus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campus createEntity(EntityManager em) {
        Campus campus = new Campus()
            .campusName(DEFAULT_CAMPUS_NAME)
            .campusAddress(DEFAULT_CAMPUS_ADDRESS)
            .campusState(DEFAULT_CAMPUS_STATE);
        return campus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campus createUpdatedEntity(EntityManager em) {
        Campus campus = new Campus()
            .campusName(UPDATED_CAMPUS_NAME)
            .campusAddress(UPDATED_CAMPUS_ADDRESS)
            .campusState(UPDATED_CAMPUS_STATE);
        return campus;
    }

    @BeforeEach
    public void initTest() {
        campus = createEntity(em);
    }

    @Test
    @Transactional
    void createCampus() throws Exception {
        int databaseSizeBeforeCreate = campusRepository.findAll().size();
        // Create the Campus
        CampusDTO campusDTO = campusMapper.toDto(campus);
        restCampusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campusDTO)))
            .andExpect(status().isCreated());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeCreate + 1);
        Campus testCampus = campusList.get(campusList.size() - 1);
        assertThat(testCampus.getCampusName()).isEqualTo(DEFAULT_CAMPUS_NAME);
        assertThat(testCampus.getCampusAddress()).isEqualTo(DEFAULT_CAMPUS_ADDRESS);
        assertThat(testCampus.getCampusState()).isEqualTo(DEFAULT_CAMPUS_STATE);
    }

    @Test
    @Transactional
    void createCampusWithExistingId() throws Exception {
        // Create the Campus with an existing ID
        campus.setId(1L);
        CampusDTO campusDTO = campusMapper.toDto(campus);

        int databaseSizeBeforeCreate = campusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCampusNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = campusRepository.findAll().size();
        // set the field null
        campus.setCampusName(null);

        // Create the Campus, which fails.
        CampusDTO campusDTO = campusMapper.toDto(campus);

        restCampusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campusDTO)))
            .andExpect(status().isBadRequest());

        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCampusAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = campusRepository.findAll().size();
        // set the field null
        campus.setCampusAddress(null);

        // Create the Campus, which fails.
        CampusDTO campusDTO = campusMapper.toDto(campus);

        restCampusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campusDTO)))
            .andExpect(status().isBadRequest());

        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCampusStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = campusRepository.findAll().size();
        // set the field null
        campus.setCampusState(null);

        // Create the Campus, which fails.
        CampusDTO campusDTO = campusMapper.toDto(campus);

        restCampusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campusDTO)))
            .andExpect(status().isBadRequest());

        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCampuses() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        // Get all the campusList
        restCampusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campus.getId().intValue())))
            .andExpect(jsonPath("$.[*].campusName").value(hasItem(DEFAULT_CAMPUS_NAME)))
            .andExpect(jsonPath("$.[*].campusAddress").value(hasItem(DEFAULT_CAMPUS_ADDRESS)))
            .andExpect(jsonPath("$.[*].campusState").value(hasItem(DEFAULT_CAMPUS_STATE.toString())));
    }

    @Test
    @Transactional
    void getCampus() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        // Get the campus
        restCampusMockMvc
            .perform(get(ENTITY_API_URL_ID, campus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(campus.getId().intValue()))
            .andExpect(jsonPath("$.campusName").value(DEFAULT_CAMPUS_NAME))
            .andExpect(jsonPath("$.campusAddress").value(DEFAULT_CAMPUS_ADDRESS))
            .andExpect(jsonPath("$.campusState").value(DEFAULT_CAMPUS_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCampus() throws Exception {
        // Get the campus
        restCampusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCampus() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        int databaseSizeBeforeUpdate = campusRepository.findAll().size();

        // Update the campus
        Campus updatedCampus = campusRepository.findById(campus.getId()).get();
        // Disconnect from session so that the updates on updatedCampus are not directly saved in db
        em.detach(updatedCampus);
        updatedCampus.campusName(UPDATED_CAMPUS_NAME).campusAddress(UPDATED_CAMPUS_ADDRESS).campusState(UPDATED_CAMPUS_STATE);
        CampusDTO campusDTO = campusMapper.toDto(updatedCampus);

        restCampusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, campusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campusDTO))
            )
            .andExpect(status().isOk());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
        Campus testCampus = campusList.get(campusList.size() - 1);
        assertThat(testCampus.getCampusName()).isEqualTo(UPDATED_CAMPUS_NAME);
        assertThat(testCampus.getCampusAddress()).isEqualTo(UPDATED_CAMPUS_ADDRESS);
        assertThat(testCampus.getCampusState()).isEqualTo(UPDATED_CAMPUS_STATE);
    }

    @Test
    @Transactional
    void putNonExistingCampus() throws Exception {
        int databaseSizeBeforeUpdate = campusRepository.findAll().size();
        campus.setId(count.incrementAndGet());

        // Create the Campus
        CampusDTO campusDTO = campusMapper.toDto(campus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, campusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCampus() throws Exception {
        int databaseSizeBeforeUpdate = campusRepository.findAll().size();
        campus.setId(count.incrementAndGet());

        // Create the Campus
        CampusDTO campusDTO = campusMapper.toDto(campus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCampus() throws Exception {
        int databaseSizeBeforeUpdate = campusRepository.findAll().size();
        campus.setId(count.incrementAndGet());

        // Create the Campus
        CampusDTO campusDTO = campusMapper.toDto(campus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCampusWithPatch() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        int databaseSizeBeforeUpdate = campusRepository.findAll().size();

        // Update the campus using partial update
        Campus partialUpdatedCampus = new Campus();
        partialUpdatedCampus.setId(campus.getId());

        partialUpdatedCampus.campusAddress(UPDATED_CAMPUS_ADDRESS);

        restCampusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCampus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCampus))
            )
            .andExpect(status().isOk());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
        Campus testCampus = campusList.get(campusList.size() - 1);
        assertThat(testCampus.getCampusName()).isEqualTo(DEFAULT_CAMPUS_NAME);
        assertThat(testCampus.getCampusAddress()).isEqualTo(UPDATED_CAMPUS_ADDRESS);
        assertThat(testCampus.getCampusState()).isEqualTo(DEFAULT_CAMPUS_STATE);
    }

    @Test
    @Transactional
    void fullUpdateCampusWithPatch() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        int databaseSizeBeforeUpdate = campusRepository.findAll().size();

        // Update the campus using partial update
        Campus partialUpdatedCampus = new Campus();
        partialUpdatedCampus.setId(campus.getId());

        partialUpdatedCampus.campusName(UPDATED_CAMPUS_NAME).campusAddress(UPDATED_CAMPUS_ADDRESS).campusState(UPDATED_CAMPUS_STATE);

        restCampusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCampus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCampus))
            )
            .andExpect(status().isOk());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
        Campus testCampus = campusList.get(campusList.size() - 1);
        assertThat(testCampus.getCampusName()).isEqualTo(UPDATED_CAMPUS_NAME);
        assertThat(testCampus.getCampusAddress()).isEqualTo(UPDATED_CAMPUS_ADDRESS);
        assertThat(testCampus.getCampusState()).isEqualTo(UPDATED_CAMPUS_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingCampus() throws Exception {
        int databaseSizeBeforeUpdate = campusRepository.findAll().size();
        campus.setId(count.incrementAndGet());

        // Create the Campus
        CampusDTO campusDTO = campusMapper.toDto(campus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, campusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCampus() throws Exception {
        int databaseSizeBeforeUpdate = campusRepository.findAll().size();
        campus.setId(count.incrementAndGet());

        // Create the Campus
        CampusDTO campusDTO = campusMapper.toDto(campus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCampus() throws Exception {
        int databaseSizeBeforeUpdate = campusRepository.findAll().size();
        campus.setId(count.incrementAndGet());

        // Create the Campus
        CampusDTO campusDTO = campusMapper.toDto(campus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(campusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCampus() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        int databaseSizeBeforeDelete = campusRepository.findAll().size();

        // Delete the campus
        restCampusMockMvc
            .perform(delete(ENTITY_API_URL_ID, campus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

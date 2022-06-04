package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Year;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.YearRepository;
import co.edu.sena.service.dto.YearDTO;
import co.edu.sena.service.mapper.YearMapper;
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
 * Integration tests for the {@link YearResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class YearResourceIT {

    private static final Integer DEFAULT_YEAR_NUMBER = 1;
    private static final Integer UPDATED_YEAR_NUMBER = 2;

    private static final State DEFAULT_YEAR_STATE = State.ACTIVE;
    private static final State UPDATED_YEAR_STATE = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/years";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private YearMapper yearMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restYearMockMvc;

    private Year year;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Year createEntity(EntityManager em) {
        Year year = new Year().yearNumber(DEFAULT_YEAR_NUMBER).yearState(DEFAULT_YEAR_STATE);
        return year;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Year createUpdatedEntity(EntityManager em) {
        Year year = new Year().yearNumber(UPDATED_YEAR_NUMBER).yearState(UPDATED_YEAR_STATE);
        return year;
    }

    @BeforeEach
    public void initTest() {
        year = createEntity(em);
    }

    @Test
    @Transactional
    void createYear() throws Exception {
        int databaseSizeBeforeCreate = yearRepository.findAll().size();
        // Create the Year
        YearDTO yearDTO = yearMapper.toDto(year);
        restYearMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yearDTO)))
            .andExpect(status().isCreated());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeCreate + 1);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getYearNumber()).isEqualTo(DEFAULT_YEAR_NUMBER);
        assertThat(testYear.getYearState()).isEqualTo(DEFAULT_YEAR_STATE);
    }

    @Test
    @Transactional
    void createYearWithExistingId() throws Exception {
        // Create the Year with an existing ID
        year.setId(1L);
        YearDTO yearDTO = yearMapper.toDto(year);

        int databaseSizeBeforeCreate = yearRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restYearMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkYearNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = yearRepository.findAll().size();
        // set the field null
        year.setYearNumber(null);

        // Create the Year, which fails.
        YearDTO yearDTO = yearMapper.toDto(year);

        restYearMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yearDTO)))
            .andExpect(status().isBadRequest());

        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkYearStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = yearRepository.findAll().size();
        // set the field null
        year.setYearState(null);

        // Create the Year, which fails.
        YearDTO yearDTO = yearMapper.toDto(year);

        restYearMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yearDTO)))
            .andExpect(status().isBadRequest());

        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllYears() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        // Get all the yearList
        restYearMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(year.getId().intValue())))
            .andExpect(jsonPath("$.[*].yearNumber").value(hasItem(DEFAULT_YEAR_NUMBER)))
            .andExpect(jsonPath("$.[*].yearState").value(hasItem(DEFAULT_YEAR_STATE.toString())));
    }

    @Test
    @Transactional
    void getYear() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        // Get the year
        restYearMockMvc
            .perform(get(ENTITY_API_URL_ID, year.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(year.getId().intValue()))
            .andExpect(jsonPath("$.yearNumber").value(DEFAULT_YEAR_NUMBER))
            .andExpect(jsonPath("$.yearState").value(DEFAULT_YEAR_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingYear() throws Exception {
        // Get the year
        restYearMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewYear() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        int databaseSizeBeforeUpdate = yearRepository.findAll().size();

        // Update the year
        Year updatedYear = yearRepository.findById(year.getId()).get();
        // Disconnect from session so that the updates on updatedYear are not directly saved in db
        em.detach(updatedYear);
        updatedYear.yearNumber(UPDATED_YEAR_NUMBER).yearState(UPDATED_YEAR_STATE);
        YearDTO yearDTO = yearMapper.toDto(updatedYear);

        restYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, yearDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(yearDTO))
            )
            .andExpect(status().isOk());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getYearNumber()).isEqualTo(UPDATED_YEAR_NUMBER);
        assertThat(testYear.getYearState()).isEqualTo(UPDATED_YEAR_STATE);
    }

    @Test
    @Transactional
    void putNonExistingYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // Create the Year
        YearDTO yearDTO = yearMapper.toDto(year);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, yearDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(yearDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // Create the Year
        YearDTO yearDTO = yearMapper.toDto(year);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(yearDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // Create the Year
        YearDTO yearDTO = yearMapper.toDto(year);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yearDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateYearWithPatch() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        int databaseSizeBeforeUpdate = yearRepository.findAll().size();

        // Update the year using partial update
        Year partialUpdatedYear = new Year();
        partialUpdatedYear.setId(year.getId());

        partialUpdatedYear.yearState(UPDATED_YEAR_STATE);

        restYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedYear.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedYear))
            )
            .andExpect(status().isOk());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getYearNumber()).isEqualTo(DEFAULT_YEAR_NUMBER);
        assertThat(testYear.getYearState()).isEqualTo(UPDATED_YEAR_STATE);
    }

    @Test
    @Transactional
    void fullUpdateYearWithPatch() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        int databaseSizeBeforeUpdate = yearRepository.findAll().size();

        // Update the year using partial update
        Year partialUpdatedYear = new Year();
        partialUpdatedYear.setId(year.getId());

        partialUpdatedYear.yearNumber(UPDATED_YEAR_NUMBER).yearState(UPDATED_YEAR_STATE);

        restYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedYear.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedYear))
            )
            .andExpect(status().isOk());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getYearNumber()).isEqualTo(UPDATED_YEAR_NUMBER);
        assertThat(testYear.getYearState()).isEqualTo(UPDATED_YEAR_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // Create the Year
        YearDTO yearDTO = yearMapper.toDto(year);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, yearDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(yearDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // Create the Year
        YearDTO yearDTO = yearMapper.toDto(year);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(yearDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();
        year.setId(count.incrementAndGet());

        // Create the Year
        YearDTO yearDTO = yearMapper.toDto(year);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restYearMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(yearDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteYear() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        int databaseSizeBeforeDelete = yearRepository.findAll().size();

        // Delete the year
        restYearMockMvc
            .perform(delete(ENTITY_API_URL_ID, year.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

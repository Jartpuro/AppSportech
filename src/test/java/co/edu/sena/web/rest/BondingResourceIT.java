package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Bonding;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.BondingRepository;
import co.edu.sena.service.dto.BondingDTO;
import co.edu.sena.service.mapper.BondingMapper;
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
 * Integration tests for the {@link BondingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BondingResourceIT {

    private static final String DEFAULT_BONDING_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BONDING_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORKING_HOURS = 1;
    private static final Integer UPDATED_WORKING_HOURS = 2;

    private static final State DEFAULT_BONDING_STATE = State.ACTIVE;
    private static final State UPDATED_BONDING_STATE = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/bondings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BondingRepository bondingRepository;

    @Autowired
    private BondingMapper bondingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBondingMockMvc;

    private Bonding bonding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonding createEntity(EntityManager em) {
        Bonding bonding = new Bonding()
            .bondingType(DEFAULT_BONDING_TYPE)
            .workingHours(DEFAULT_WORKING_HOURS)
            .bondingState(DEFAULT_BONDING_STATE);
        return bonding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonding createUpdatedEntity(EntityManager em) {
        Bonding bonding = new Bonding()
            .bondingType(UPDATED_BONDING_TYPE)
            .workingHours(UPDATED_WORKING_HOURS)
            .bondingState(UPDATED_BONDING_STATE);
        return bonding;
    }

    @BeforeEach
    public void initTest() {
        bonding = createEntity(em);
    }

    @Test
    @Transactional
    void createBonding() throws Exception {
        int databaseSizeBeforeCreate = bondingRepository.findAll().size();
        // Create the Bonding
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);
        restBondingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingDTO)))
            .andExpect(status().isCreated());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeCreate + 1);
        Bonding testBonding = bondingList.get(bondingList.size() - 1);
        assertThat(testBonding.getBondingType()).isEqualTo(DEFAULT_BONDING_TYPE);
        assertThat(testBonding.getWorkingHours()).isEqualTo(DEFAULT_WORKING_HOURS);
        assertThat(testBonding.getBondingState()).isEqualTo(DEFAULT_BONDING_STATE);
    }

    @Test
    @Transactional
    void createBondingWithExistingId() throws Exception {
        // Create the Bonding with an existing ID
        bonding.setId(1L);
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        int databaseSizeBeforeCreate = bondingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBondingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBondingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondingRepository.findAll().size();
        // set the field null
        bonding.setBondingType(null);

        // Create the Bonding, which fails.
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        restBondingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingDTO)))
            .andExpect(status().isBadRequest());

        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWorkingHoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondingRepository.findAll().size();
        // set the field null
        bonding.setWorkingHours(null);

        // Create the Bonding, which fails.
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        restBondingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingDTO)))
            .andExpect(status().isBadRequest());

        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBondingStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondingRepository.findAll().size();
        // set the field null
        bonding.setBondingState(null);

        // Create the Bonding, which fails.
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        restBondingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingDTO)))
            .andExpect(status().isBadRequest());

        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBondings() throws Exception {
        // Initialize the database
        bondingRepository.saveAndFlush(bonding);

        // Get all the bondingList
        restBondingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonding.getId().intValue())))
            .andExpect(jsonPath("$.[*].bondingType").value(hasItem(DEFAULT_BONDING_TYPE)))
            .andExpect(jsonPath("$.[*].workingHours").value(hasItem(DEFAULT_WORKING_HOURS)))
            .andExpect(jsonPath("$.[*].bondingState").value(hasItem(DEFAULT_BONDING_STATE.toString())));
    }

    @Test
    @Transactional
    void getBonding() throws Exception {
        // Initialize the database
        bondingRepository.saveAndFlush(bonding);

        // Get the bonding
        restBondingMockMvc
            .perform(get(ENTITY_API_URL_ID, bonding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonding.getId().intValue()))
            .andExpect(jsonPath("$.bondingType").value(DEFAULT_BONDING_TYPE))
            .andExpect(jsonPath("$.workingHours").value(DEFAULT_WORKING_HOURS))
            .andExpect(jsonPath("$.bondingState").value(DEFAULT_BONDING_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBonding() throws Exception {
        // Get the bonding
        restBondingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBonding() throws Exception {
        // Initialize the database
        bondingRepository.saveAndFlush(bonding);

        int databaseSizeBeforeUpdate = bondingRepository.findAll().size();

        // Update the bonding
        Bonding updatedBonding = bondingRepository.findById(bonding.getId()).get();
        // Disconnect from session so that the updates on updatedBonding are not directly saved in db
        em.detach(updatedBonding);
        updatedBonding.bondingType(UPDATED_BONDING_TYPE).workingHours(UPDATED_WORKING_HOURS).bondingState(UPDATED_BONDING_STATE);
        BondingDTO bondingDTO = bondingMapper.toDto(updatedBonding);

        restBondingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bondingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bondingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeUpdate);
        Bonding testBonding = bondingList.get(bondingList.size() - 1);
        assertThat(testBonding.getBondingType()).isEqualTo(UPDATED_BONDING_TYPE);
        assertThat(testBonding.getWorkingHours()).isEqualTo(UPDATED_WORKING_HOURS);
        assertThat(testBonding.getBondingState()).isEqualTo(UPDATED_BONDING_STATE);
    }

    @Test
    @Transactional
    void putNonExistingBonding() throws Exception {
        int databaseSizeBeforeUpdate = bondingRepository.findAll().size();
        bonding.setId(count.incrementAndGet());

        // Create the Bonding
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBondingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bondingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bondingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBonding() throws Exception {
        int databaseSizeBeforeUpdate = bondingRepository.findAll().size();
        bonding.setId(count.incrementAndGet());

        // Create the Bonding
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bondingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBonding() throws Exception {
        int databaseSizeBeforeUpdate = bondingRepository.findAll().size();
        bonding.setId(count.incrementAndGet());

        // Create the Bonding
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBondingWithPatch() throws Exception {
        // Initialize the database
        bondingRepository.saveAndFlush(bonding);

        int databaseSizeBeforeUpdate = bondingRepository.findAll().size();

        // Update the bonding using partial update
        Bonding partialUpdatedBonding = new Bonding();
        partialUpdatedBonding.setId(bonding.getId());

        partialUpdatedBonding.bondingType(UPDATED_BONDING_TYPE).workingHours(UPDATED_WORKING_HOURS);

        restBondingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonding.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonding))
            )
            .andExpect(status().isOk());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeUpdate);
        Bonding testBonding = bondingList.get(bondingList.size() - 1);
        assertThat(testBonding.getBondingType()).isEqualTo(UPDATED_BONDING_TYPE);
        assertThat(testBonding.getWorkingHours()).isEqualTo(UPDATED_WORKING_HOURS);
        assertThat(testBonding.getBondingState()).isEqualTo(DEFAULT_BONDING_STATE);
    }

    @Test
    @Transactional
    void fullUpdateBondingWithPatch() throws Exception {
        // Initialize the database
        bondingRepository.saveAndFlush(bonding);

        int databaseSizeBeforeUpdate = bondingRepository.findAll().size();

        // Update the bonding using partial update
        Bonding partialUpdatedBonding = new Bonding();
        partialUpdatedBonding.setId(bonding.getId());

        partialUpdatedBonding.bondingType(UPDATED_BONDING_TYPE).workingHours(UPDATED_WORKING_HOURS).bondingState(UPDATED_BONDING_STATE);

        restBondingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonding.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonding))
            )
            .andExpect(status().isOk());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeUpdate);
        Bonding testBonding = bondingList.get(bondingList.size() - 1);
        assertThat(testBonding.getBondingType()).isEqualTo(UPDATED_BONDING_TYPE);
        assertThat(testBonding.getWorkingHours()).isEqualTo(UPDATED_WORKING_HOURS);
        assertThat(testBonding.getBondingState()).isEqualTo(UPDATED_BONDING_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingBonding() throws Exception {
        int databaseSizeBeforeUpdate = bondingRepository.findAll().size();
        bonding.setId(count.incrementAndGet());

        // Create the Bonding
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBondingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bondingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bondingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBonding() throws Exception {
        int databaseSizeBeforeUpdate = bondingRepository.findAll().size();
        bonding.setId(count.incrementAndGet());

        // Create the Bonding
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bondingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBonding() throws Exception {
        int databaseSizeBeforeUpdate = bondingRepository.findAll().size();
        bonding.setId(count.incrementAndGet());

        // Create the Bonding
        BondingDTO bondingDTO = bondingMapper.toDto(bonding);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bondingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bonding in the database
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBonding() throws Exception {
        // Initialize the database
        bondingRepository.saveAndFlush(bonding);

        int databaseSizeBeforeDelete = bondingRepository.findAll().size();

        // Delete the bonding
        restBondingMockMvc
            .perform(delete(ENTITY_API_URL_ID, bonding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bonding> bondingList = bondingRepository.findAll();
        assertThat(bondingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

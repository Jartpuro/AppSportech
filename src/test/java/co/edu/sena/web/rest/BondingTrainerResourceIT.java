package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Bonding;
import co.edu.sena.domain.BondingTrainer;
import co.edu.sena.domain.Trainer;
import co.edu.sena.domain.Year;
import co.edu.sena.repository.BondingTrainerRepository;
import co.edu.sena.service.BondingTrainerService;
import co.edu.sena.service.dto.BondingTrainerDTO;
import co.edu.sena.service.mapper.BondingTrainerMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link BondingTrainerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BondingTrainerResourceIT {

    private static final LocalDate DEFAULT_START_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/bonding-trainers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BondingTrainerRepository bondingTrainerRepository;

    @Mock
    private BondingTrainerRepository bondingTrainerRepositoryMock;

    @Autowired
    private BondingTrainerMapper bondingTrainerMapper;

    @Mock
    private BondingTrainerService bondingTrainerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBondingTrainerMockMvc;

    private BondingTrainer bondingTrainer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BondingTrainer createEntity(EntityManager em) {
        BondingTrainer bondingTrainer = new BondingTrainer().startTime(DEFAULT_START_TIME).endTime(DEFAULT_END_TIME);
        // Add required entity
        Year year;
        if (TestUtil.findAll(em, Year.class).isEmpty()) {
            year = YearResourceIT.createEntity(em);
            em.persist(year);
            em.flush();
        } else {
            year = TestUtil.findAll(em, Year.class).get(0);
        }
        bondingTrainer.setYear(year);
        // Add required entity
        Trainer trainer;
        if (TestUtil.findAll(em, Trainer.class).isEmpty()) {
            trainer = TrainerResourceIT.createEntity(em);
            em.persist(trainer);
            em.flush();
        } else {
            trainer = TestUtil.findAll(em, Trainer.class).get(0);
        }
        bondingTrainer.setTrainer(trainer);
        // Add required entity
        Bonding bonding;
        if (TestUtil.findAll(em, Bonding.class).isEmpty()) {
            bonding = BondingResourceIT.createEntity(em);
            em.persist(bonding);
            em.flush();
        } else {
            bonding = TestUtil.findAll(em, Bonding.class).get(0);
        }
        bondingTrainer.setBonding(bonding);
        return bondingTrainer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BondingTrainer createUpdatedEntity(EntityManager em) {
        BondingTrainer bondingTrainer = new BondingTrainer().startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);
        // Add required entity
        Year year;
        if (TestUtil.findAll(em, Year.class).isEmpty()) {
            year = YearResourceIT.createUpdatedEntity(em);
            em.persist(year);
            em.flush();
        } else {
            year = TestUtil.findAll(em, Year.class).get(0);
        }
        bondingTrainer.setYear(year);
        // Add required entity
        Trainer trainer;
        if (TestUtil.findAll(em, Trainer.class).isEmpty()) {
            trainer = TrainerResourceIT.createUpdatedEntity(em);
            em.persist(trainer);
            em.flush();
        } else {
            trainer = TestUtil.findAll(em, Trainer.class).get(0);
        }
        bondingTrainer.setTrainer(trainer);
        // Add required entity
        Bonding bonding;
        if (TestUtil.findAll(em, Bonding.class).isEmpty()) {
            bonding = BondingResourceIT.createUpdatedEntity(em);
            em.persist(bonding);
            em.flush();
        } else {
            bonding = TestUtil.findAll(em, Bonding.class).get(0);
        }
        bondingTrainer.setBonding(bonding);
        return bondingTrainer;
    }

    @BeforeEach
    public void initTest() {
        bondingTrainer = createEntity(em);
    }

    @Test
    @Transactional
    void createBondingTrainer() throws Exception {
        int databaseSizeBeforeCreate = bondingTrainerRepository.findAll().size();
        // Create the BondingTrainer
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);
        restBondingTrainerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeCreate + 1);
        BondingTrainer testBondingTrainer = bondingTrainerList.get(bondingTrainerList.size() - 1);
        assertThat(testBondingTrainer.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBondingTrainer.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createBondingTrainerWithExistingId() throws Exception {
        // Create the BondingTrainer with an existing ID
        bondingTrainer.setId(1L);
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);

        int databaseSizeBeforeCreate = bondingTrainerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBondingTrainerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondingTrainerRepository.findAll().size();
        // set the field null
        bondingTrainer.setStartTime(null);

        // Create the BondingTrainer, which fails.
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);

        restBondingTrainerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondingTrainerRepository.findAll().size();
        // set the field null
        bondingTrainer.setEndTime(null);

        // Create the BondingTrainer, which fails.
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);

        restBondingTrainerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBondingTrainers() throws Exception {
        // Initialize the database
        bondingTrainerRepository.saveAndFlush(bondingTrainer);

        // Get all the bondingTrainerList
        restBondingTrainerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bondingTrainer.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBondingTrainersWithEagerRelationshipsIsEnabled() throws Exception {
        when(bondingTrainerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBondingTrainerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bondingTrainerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBondingTrainersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bondingTrainerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBondingTrainerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bondingTrainerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getBondingTrainer() throws Exception {
        // Initialize the database
        bondingTrainerRepository.saveAndFlush(bondingTrainer);

        // Get the bondingTrainer
        restBondingTrainerMockMvc
            .perform(get(ENTITY_API_URL_ID, bondingTrainer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bondingTrainer.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBondingTrainer() throws Exception {
        // Get the bondingTrainer
        restBondingTrainerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBondingTrainer() throws Exception {
        // Initialize the database
        bondingTrainerRepository.saveAndFlush(bondingTrainer);

        int databaseSizeBeforeUpdate = bondingTrainerRepository.findAll().size();

        // Update the bondingTrainer
        BondingTrainer updatedBondingTrainer = bondingTrainerRepository.findById(bondingTrainer.getId()).get();
        // Disconnect from session so that the updates on updatedBondingTrainer are not directly saved in db
        em.detach(updatedBondingTrainer);
        updatedBondingTrainer.startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(updatedBondingTrainer);

        restBondingTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bondingTrainerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isOk());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeUpdate);
        BondingTrainer testBondingTrainer = bondingTrainerList.get(bondingTrainerList.size() - 1);
        assertThat(testBondingTrainer.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBondingTrainer.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBondingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = bondingTrainerRepository.findAll().size();
        bondingTrainer.setId(count.incrementAndGet());

        // Create the BondingTrainer
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBondingTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bondingTrainerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBondingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = bondingTrainerRepository.findAll().size();
        bondingTrainer.setId(count.incrementAndGet());

        // Create the BondingTrainer
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondingTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBondingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = bondingTrainerRepository.findAll().size();
        bondingTrainer.setId(count.incrementAndGet());

        // Create the BondingTrainer
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondingTrainerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBondingTrainerWithPatch() throws Exception {
        // Initialize the database
        bondingTrainerRepository.saveAndFlush(bondingTrainer);

        int databaseSizeBeforeUpdate = bondingTrainerRepository.findAll().size();

        // Update the bondingTrainer using partial update
        BondingTrainer partialUpdatedBondingTrainer = new BondingTrainer();
        partialUpdatedBondingTrainer.setId(bondingTrainer.getId());

        partialUpdatedBondingTrainer.startTime(UPDATED_START_TIME);

        restBondingTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBondingTrainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBondingTrainer))
            )
            .andExpect(status().isOk());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeUpdate);
        BondingTrainer testBondingTrainer = bondingTrainerList.get(bondingTrainerList.size() - 1);
        assertThat(testBondingTrainer.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBondingTrainer.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBondingTrainerWithPatch() throws Exception {
        // Initialize the database
        bondingTrainerRepository.saveAndFlush(bondingTrainer);

        int databaseSizeBeforeUpdate = bondingTrainerRepository.findAll().size();

        // Update the bondingTrainer using partial update
        BondingTrainer partialUpdatedBondingTrainer = new BondingTrainer();
        partialUpdatedBondingTrainer.setId(bondingTrainer.getId());

        partialUpdatedBondingTrainer.startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);

        restBondingTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBondingTrainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBondingTrainer))
            )
            .andExpect(status().isOk());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeUpdate);
        BondingTrainer testBondingTrainer = bondingTrainerList.get(bondingTrainerList.size() - 1);
        assertThat(testBondingTrainer.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBondingTrainer.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBondingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = bondingTrainerRepository.findAll().size();
        bondingTrainer.setId(count.incrementAndGet());

        // Create the BondingTrainer
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBondingTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bondingTrainerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBondingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = bondingTrainerRepository.findAll().size();
        bondingTrainer.setId(count.incrementAndGet());

        // Create the BondingTrainer
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondingTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBondingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = bondingTrainerRepository.findAll().size();
        bondingTrainer.setId(count.incrementAndGet());

        // Create the BondingTrainer
        BondingTrainerDTO bondingTrainerDTO = bondingTrainerMapper.toDto(bondingTrainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondingTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bondingTrainerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BondingTrainer in the database
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBondingTrainer() throws Exception {
        // Initialize the database
        bondingTrainerRepository.saveAndFlush(bondingTrainer);

        int databaseSizeBeforeDelete = bondingTrainerRepository.findAll().size();

        // Delete the bondingTrainer
        restBondingTrainerMockMvc
            .perform(delete(ENTITY_API_URL_ID, bondingTrainer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BondingTrainer> bondingTrainerList = bondingTrainerRepository.findAll();
        assertThat(bondingTrainerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

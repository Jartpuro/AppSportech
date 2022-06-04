package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Area;
import co.edu.sena.domain.AreaTrainer;
import co.edu.sena.domain.Trainer;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.AreaTrainerRepository;
import co.edu.sena.service.AreaTrainerService;
import co.edu.sena.service.dto.AreaTrainerDTO;
import co.edu.sena.service.mapper.AreaTrainerMapper;
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
 * Integration tests for the {@link AreaTrainerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AreaTrainerResourceIT {

    private static final State DEFAULT_AREA_TRAINER_STATE = State.ACTIVE;
    private static final State UPDATED_AREA_TRAINER_STATE = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/area-trainers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AreaTrainerRepository areaTrainerRepository;

    @Mock
    private AreaTrainerRepository areaTrainerRepositoryMock;

    @Autowired
    private AreaTrainerMapper areaTrainerMapper;

    @Mock
    private AreaTrainerService areaTrainerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaTrainerMockMvc;

    private AreaTrainer areaTrainer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaTrainer createEntity(EntityManager em) {
        AreaTrainer areaTrainer = new AreaTrainer().areaTrainerState(DEFAULT_AREA_TRAINER_STATE);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        areaTrainer.setArea(area);
        // Add required entity
        Trainer trainer;
        if (TestUtil.findAll(em, Trainer.class).isEmpty()) {
            trainer = TrainerResourceIT.createEntity(em);
            em.persist(trainer);
            em.flush();
        } else {
            trainer = TestUtil.findAll(em, Trainer.class).get(0);
        }
        areaTrainer.setTrainer(trainer);
        return areaTrainer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaTrainer createUpdatedEntity(EntityManager em) {
        AreaTrainer areaTrainer = new AreaTrainer().areaTrainerState(UPDATED_AREA_TRAINER_STATE);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createUpdatedEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        areaTrainer.setArea(area);
        // Add required entity
        Trainer trainer;
        if (TestUtil.findAll(em, Trainer.class).isEmpty()) {
            trainer = TrainerResourceIT.createUpdatedEntity(em);
            em.persist(trainer);
            em.flush();
        } else {
            trainer = TestUtil.findAll(em, Trainer.class).get(0);
        }
        areaTrainer.setTrainer(trainer);
        return areaTrainer;
    }

    @BeforeEach
    public void initTest() {
        areaTrainer = createEntity(em);
    }

    @Test
    @Transactional
    void createAreaTrainer() throws Exception {
        int databaseSizeBeforeCreate = areaTrainerRepository.findAll().size();
        // Create the AreaTrainer
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(areaTrainer);
        restAreaTrainerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeCreate + 1);
        AreaTrainer testAreaTrainer = areaTrainerList.get(areaTrainerList.size() - 1);
        assertThat(testAreaTrainer.getAreaTrainerState()).isEqualTo(DEFAULT_AREA_TRAINER_STATE);
    }

    @Test
    @Transactional
    void createAreaTrainerWithExistingId() throws Exception {
        // Create the AreaTrainer with an existing ID
        areaTrainer.setId(1L);
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(areaTrainer);

        int databaseSizeBeforeCreate = areaTrainerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaTrainerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAreaTrainerStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaTrainerRepository.findAll().size();
        // set the field null
        areaTrainer.setAreaTrainerState(null);

        // Create the AreaTrainer, which fails.
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(areaTrainer);

        restAreaTrainerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAreaTrainers() throws Exception {
        // Initialize the database
        areaTrainerRepository.saveAndFlush(areaTrainer);

        // Get all the areaTrainerList
        restAreaTrainerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaTrainer.getId().intValue())))
            .andExpect(jsonPath("$.[*].areaTrainerState").value(hasItem(DEFAULT_AREA_TRAINER_STATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaTrainersWithEagerRelationshipsIsEnabled() throws Exception {
        when(areaTrainerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaTrainerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(areaTrainerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaTrainersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(areaTrainerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaTrainerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(areaTrainerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getAreaTrainer() throws Exception {
        // Initialize the database
        areaTrainerRepository.saveAndFlush(areaTrainer);

        // Get the areaTrainer
        restAreaTrainerMockMvc
            .perform(get(ENTITY_API_URL_ID, areaTrainer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(areaTrainer.getId().intValue()))
            .andExpect(jsonPath("$.areaTrainerState").value(DEFAULT_AREA_TRAINER_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAreaTrainer() throws Exception {
        // Get the areaTrainer
        restAreaTrainerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAreaTrainer() throws Exception {
        // Initialize the database
        areaTrainerRepository.saveAndFlush(areaTrainer);

        int databaseSizeBeforeUpdate = areaTrainerRepository.findAll().size();

        // Update the areaTrainer
        AreaTrainer updatedAreaTrainer = areaTrainerRepository.findById(areaTrainer.getId()).get();
        // Disconnect from session so that the updates on updatedAreaTrainer are not directly saved in db
        em.detach(updatedAreaTrainer);
        updatedAreaTrainer.areaTrainerState(UPDATED_AREA_TRAINER_STATE);
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(updatedAreaTrainer);

        restAreaTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaTrainerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO))
            )
            .andExpect(status().isOk());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeUpdate);
        AreaTrainer testAreaTrainer = areaTrainerList.get(areaTrainerList.size() - 1);
        assertThat(testAreaTrainer.getAreaTrainerState()).isEqualTo(UPDATED_AREA_TRAINER_STATE);
    }

    @Test
    @Transactional
    void putNonExistingAreaTrainer() throws Exception {
        int databaseSizeBeforeUpdate = areaTrainerRepository.findAll().size();
        areaTrainer.setId(count.incrementAndGet());

        // Create the AreaTrainer
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(areaTrainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaTrainerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAreaTrainer() throws Exception {
        int databaseSizeBeforeUpdate = areaTrainerRepository.findAll().size();
        areaTrainer.setId(count.incrementAndGet());

        // Create the AreaTrainer
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(areaTrainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAreaTrainer() throws Exception {
        int databaseSizeBeforeUpdate = areaTrainerRepository.findAll().size();
        areaTrainer.setId(count.incrementAndGet());

        // Create the AreaTrainer
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(areaTrainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaTrainerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAreaTrainerWithPatch() throws Exception {
        // Initialize the database
        areaTrainerRepository.saveAndFlush(areaTrainer);

        int databaseSizeBeforeUpdate = areaTrainerRepository.findAll().size();

        // Update the areaTrainer using partial update
        AreaTrainer partialUpdatedAreaTrainer = new AreaTrainer();
        partialUpdatedAreaTrainer.setId(areaTrainer.getId());

        restAreaTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaTrainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAreaTrainer))
            )
            .andExpect(status().isOk());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeUpdate);
        AreaTrainer testAreaTrainer = areaTrainerList.get(areaTrainerList.size() - 1);
        assertThat(testAreaTrainer.getAreaTrainerState()).isEqualTo(DEFAULT_AREA_TRAINER_STATE);
    }

    @Test
    @Transactional
    void fullUpdateAreaTrainerWithPatch() throws Exception {
        // Initialize the database
        areaTrainerRepository.saveAndFlush(areaTrainer);

        int databaseSizeBeforeUpdate = areaTrainerRepository.findAll().size();

        // Update the areaTrainer using partial update
        AreaTrainer partialUpdatedAreaTrainer = new AreaTrainer();
        partialUpdatedAreaTrainer.setId(areaTrainer.getId());

        partialUpdatedAreaTrainer.areaTrainerState(UPDATED_AREA_TRAINER_STATE);

        restAreaTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaTrainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAreaTrainer))
            )
            .andExpect(status().isOk());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeUpdate);
        AreaTrainer testAreaTrainer = areaTrainerList.get(areaTrainerList.size() - 1);
        assertThat(testAreaTrainer.getAreaTrainerState()).isEqualTo(UPDATED_AREA_TRAINER_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingAreaTrainer() throws Exception {
        int databaseSizeBeforeUpdate = areaTrainerRepository.findAll().size();
        areaTrainer.setId(count.incrementAndGet());

        // Create the AreaTrainer
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(areaTrainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, areaTrainerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAreaTrainer() throws Exception {
        int databaseSizeBeforeUpdate = areaTrainerRepository.findAll().size();
        areaTrainer.setId(count.incrementAndGet());

        // Create the AreaTrainer
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(areaTrainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAreaTrainer() throws Exception {
        int databaseSizeBeforeUpdate = areaTrainerRepository.findAll().size();
        areaTrainer.setId(count.incrementAndGet());

        // Create the AreaTrainer
        AreaTrainerDTO areaTrainerDTO = areaTrainerMapper.toDto(areaTrainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(areaTrainerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaTrainer in the database
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAreaTrainer() throws Exception {
        // Initialize the database
        areaTrainerRepository.saveAndFlush(areaTrainer);

        int databaseSizeBeforeDelete = areaTrainerRepository.findAll().size();

        // Delete the areaTrainer
        restAreaTrainerMockMvc
            .perform(delete(ENTITY_API_URL_ID, areaTrainer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AreaTrainer> areaTrainerList = areaTrainerRepository.findAll();
        assertThat(areaTrainerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

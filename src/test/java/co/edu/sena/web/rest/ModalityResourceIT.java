package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Modality;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.ModalityRepository;
import co.edu.sena.service.dto.ModalityDTO;
import co.edu.sena.service.mapper.ModalityMapper;
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
 * Integration tests for the {@link ModalityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModalityResourceIT {

    private static final String DEFAULT_MODALITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODALITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODALITY_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_MODALITY_COLOR = "BBBBBBBBBB";

    private static final State DEFAULT_MODALITY_STATE = State.ACTIVE;
    private static final State UPDATED_MODALITY_STATE = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/modalities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ModalityRepository modalityRepository;

    @Autowired
    private ModalityMapper modalityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModalityMockMvc;

    private Modality modality;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modality createEntity(EntityManager em) {
        Modality modality = new Modality()
            .modalityName(DEFAULT_MODALITY_NAME)
            .modalityColor(DEFAULT_MODALITY_COLOR)
            .modalityState(DEFAULT_MODALITY_STATE);
        return modality;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modality createUpdatedEntity(EntityManager em) {
        Modality modality = new Modality()
            .modalityName(UPDATED_MODALITY_NAME)
            .modalityColor(UPDATED_MODALITY_COLOR)
            .modalityState(UPDATED_MODALITY_STATE);
        return modality;
    }

    @BeforeEach
    public void initTest() {
        modality = createEntity(em);
    }

    @Test
    @Transactional
    void createModality() throws Exception {
        int databaseSizeBeforeCreate = modalityRepository.findAll().size();
        // Create the Modality
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);
        restModalityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modalityDTO)))
            .andExpect(status().isCreated());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeCreate + 1);
        Modality testModality = modalityList.get(modalityList.size() - 1);
        assertThat(testModality.getModalityName()).isEqualTo(DEFAULT_MODALITY_NAME);
        assertThat(testModality.getModalityColor()).isEqualTo(DEFAULT_MODALITY_COLOR);
        assertThat(testModality.getModalityState()).isEqualTo(DEFAULT_MODALITY_STATE);
    }

    @Test
    @Transactional
    void createModalityWithExistingId() throws Exception {
        // Create the Modality with an existing ID
        modality.setId(1L);
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        int databaseSizeBeforeCreate = modalityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModalityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modalityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkModalityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = modalityRepository.findAll().size();
        // set the field null
        modality.setModalityName(null);

        // Create the Modality, which fails.
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        restModalityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modalityDTO)))
            .andExpect(status().isBadRequest());

        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModalityColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = modalityRepository.findAll().size();
        // set the field null
        modality.setModalityColor(null);

        // Create the Modality, which fails.
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        restModalityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modalityDTO)))
            .andExpect(status().isBadRequest());

        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModalityStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = modalityRepository.findAll().size();
        // set the field null
        modality.setModalityState(null);

        // Create the Modality, which fails.
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        restModalityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modalityDTO)))
            .andExpect(status().isBadRequest());

        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllModalities() throws Exception {
        // Initialize the database
        modalityRepository.saveAndFlush(modality);

        // Get all the modalityList
        restModalityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modality.getId().intValue())))
            .andExpect(jsonPath("$.[*].modalityName").value(hasItem(DEFAULT_MODALITY_NAME)))
            .andExpect(jsonPath("$.[*].modalityColor").value(hasItem(DEFAULT_MODALITY_COLOR)))
            .andExpect(jsonPath("$.[*].modalityState").value(hasItem(DEFAULT_MODALITY_STATE.toString())));
    }

    @Test
    @Transactional
    void getModality() throws Exception {
        // Initialize the database
        modalityRepository.saveAndFlush(modality);

        // Get the modality
        restModalityMockMvc
            .perform(get(ENTITY_API_URL_ID, modality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modality.getId().intValue()))
            .andExpect(jsonPath("$.modalityName").value(DEFAULT_MODALITY_NAME))
            .andExpect(jsonPath("$.modalityColor").value(DEFAULT_MODALITY_COLOR))
            .andExpect(jsonPath("$.modalityState").value(DEFAULT_MODALITY_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingModality() throws Exception {
        // Get the modality
        restModalityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewModality() throws Exception {
        // Initialize the database
        modalityRepository.saveAndFlush(modality);

        int databaseSizeBeforeUpdate = modalityRepository.findAll().size();

        // Update the modality
        Modality updatedModality = modalityRepository.findById(modality.getId()).get();
        // Disconnect from session so that the updates on updatedModality are not directly saved in db
        em.detach(updatedModality);
        updatedModality.modalityName(UPDATED_MODALITY_NAME).modalityColor(UPDATED_MODALITY_COLOR).modalityState(UPDATED_MODALITY_STATE);
        ModalityDTO modalityDTO = modalityMapper.toDto(updatedModality);

        restModalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modalityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modalityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeUpdate);
        Modality testModality = modalityList.get(modalityList.size() - 1);
        assertThat(testModality.getModalityName()).isEqualTo(UPDATED_MODALITY_NAME);
        assertThat(testModality.getModalityColor()).isEqualTo(UPDATED_MODALITY_COLOR);
        assertThat(testModality.getModalityState()).isEqualTo(UPDATED_MODALITY_STATE);
    }

    @Test
    @Transactional
    void putNonExistingModality() throws Exception {
        int databaseSizeBeforeUpdate = modalityRepository.findAll().size();
        modality.setId(count.incrementAndGet());

        // Create the Modality
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modalityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModality() throws Exception {
        int databaseSizeBeforeUpdate = modalityRepository.findAll().size();
        modality.setId(count.incrementAndGet());

        // Create the Modality
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModality() throws Exception {
        int databaseSizeBeforeUpdate = modalityRepository.findAll().size();
        modality.setId(count.incrementAndGet());

        // Create the Modality
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModalityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modalityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModalityWithPatch() throws Exception {
        // Initialize the database
        modalityRepository.saveAndFlush(modality);

        int databaseSizeBeforeUpdate = modalityRepository.findAll().size();

        // Update the modality using partial update
        Modality partialUpdatedModality = new Modality();
        partialUpdatedModality.setId(modality.getId());

        partialUpdatedModality.modalityColor(UPDATED_MODALITY_COLOR);

        restModalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModality))
            )
            .andExpect(status().isOk());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeUpdate);
        Modality testModality = modalityList.get(modalityList.size() - 1);
        assertThat(testModality.getModalityName()).isEqualTo(DEFAULT_MODALITY_NAME);
        assertThat(testModality.getModalityColor()).isEqualTo(UPDATED_MODALITY_COLOR);
        assertThat(testModality.getModalityState()).isEqualTo(DEFAULT_MODALITY_STATE);
    }

    @Test
    @Transactional
    void fullUpdateModalityWithPatch() throws Exception {
        // Initialize the database
        modalityRepository.saveAndFlush(modality);

        int databaseSizeBeforeUpdate = modalityRepository.findAll().size();

        // Update the modality using partial update
        Modality partialUpdatedModality = new Modality();
        partialUpdatedModality.setId(modality.getId());

        partialUpdatedModality
            .modalityName(UPDATED_MODALITY_NAME)
            .modalityColor(UPDATED_MODALITY_COLOR)
            .modalityState(UPDATED_MODALITY_STATE);

        restModalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModality))
            )
            .andExpect(status().isOk());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeUpdate);
        Modality testModality = modalityList.get(modalityList.size() - 1);
        assertThat(testModality.getModalityName()).isEqualTo(UPDATED_MODALITY_NAME);
        assertThat(testModality.getModalityColor()).isEqualTo(UPDATED_MODALITY_COLOR);
        assertThat(testModality.getModalityState()).isEqualTo(UPDATED_MODALITY_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingModality() throws Exception {
        int databaseSizeBeforeUpdate = modalityRepository.findAll().size();
        modality.setId(count.incrementAndGet());

        // Create the Modality
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modalityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModality() throws Exception {
        int databaseSizeBeforeUpdate = modalityRepository.findAll().size();
        modality.setId(count.incrementAndGet());

        // Create the Modality
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModality() throws Exception {
        int databaseSizeBeforeUpdate = modalityRepository.findAll().size();
        modality.setId(count.incrementAndGet());

        // Create the Modality
        ModalityDTO modalityDTO = modalityMapper.toDto(modality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModalityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(modalityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Modality in the database
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModality() throws Exception {
        // Initialize the database
        modalityRepository.saveAndFlush(modality);

        int databaseSizeBeforeDelete = modalityRepository.findAll().size();

        // Delete the modality
        restModalityMockMvc
            .perform(delete(ENTITY_API_URL_ID, modality.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Modality> modalityList = modalityRepository.findAll();
        assertThat(modalityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

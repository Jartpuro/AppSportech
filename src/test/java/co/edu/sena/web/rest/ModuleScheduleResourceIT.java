package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Module;
import co.edu.sena.domain.ModuleSchedule;
import co.edu.sena.repository.ModuleScheduleRepository;
import co.edu.sena.service.dto.ModuleScheduleDTO;
import co.edu.sena.service.mapper.ModuleScheduleMapper;
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
 * Integration tests for the {@link ModuleScheduleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModuleScheduleResourceIT {

    private static final String ENTITY_API_URL = "/api/module-schedules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ModuleScheduleRepository moduleScheduleRepository;

    @Autowired
    private ModuleScheduleMapper moduleScheduleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModuleScheduleMockMvc;

    private ModuleSchedule moduleSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModuleSchedule createEntity(EntityManager em) {
        ModuleSchedule moduleSchedule = new ModuleSchedule();
        // Add required entity
        Module module;
        if (TestUtil.findAll(em, Module.class).isEmpty()) {
            module = ModuleResourceIT.createEntity(em);
            em.persist(module);
            em.flush();
        } else {
            module = TestUtil.findAll(em, Module.class).get(0);
        }
        moduleSchedule.setModule(module);
        return moduleSchedule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModuleSchedule createUpdatedEntity(EntityManager em) {
        ModuleSchedule moduleSchedule = new ModuleSchedule();
        // Add required entity
        Module module;
        if (TestUtil.findAll(em, Module.class).isEmpty()) {
            module = ModuleResourceIT.createUpdatedEntity(em);
            em.persist(module);
            em.flush();
        } else {
            module = TestUtil.findAll(em, Module.class).get(0);
        }
        moduleSchedule.setModule(module);
        return moduleSchedule;
    }

    @BeforeEach
    public void initTest() {
        moduleSchedule = createEntity(em);
    }

    @Test
    @Transactional
    void createModuleSchedule() throws Exception {
        int databaseSizeBeforeCreate = moduleScheduleRepository.findAll().size();
        // Create the ModuleSchedule
        ModuleScheduleDTO moduleScheduleDTO = moduleScheduleMapper.toDto(moduleSchedule);
        restModuleScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moduleScheduleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        ModuleSchedule testModuleSchedule = moduleScheduleList.get(moduleScheduleList.size() - 1);
    }

    @Test
    @Transactional
    void createModuleScheduleWithExistingId() throws Exception {
        // Create the ModuleSchedule with an existing ID
        moduleSchedule.setId(1L);
        ModuleScheduleDTO moduleScheduleDTO = moduleScheduleMapper.toDto(moduleSchedule);

        int databaseSizeBeforeCreate = moduleScheduleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moduleScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllModuleSchedules() throws Exception {
        // Initialize the database
        moduleScheduleRepository.saveAndFlush(moduleSchedule);

        // Get all the moduleScheduleList
        restModuleScheduleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleSchedule.getId().intValue())));
    }

    @Test
    @Transactional
    void getModuleSchedule() throws Exception {
        // Initialize the database
        moduleScheduleRepository.saveAndFlush(moduleSchedule);

        // Get the moduleSchedule
        restModuleScheduleMockMvc
            .perform(get(ENTITY_API_URL_ID, moduleSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moduleSchedule.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingModuleSchedule() throws Exception {
        // Get the moduleSchedule
        restModuleScheduleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewModuleSchedule() throws Exception {
        // Initialize the database
        moduleScheduleRepository.saveAndFlush(moduleSchedule);

        int databaseSizeBeforeUpdate = moduleScheduleRepository.findAll().size();

        // Update the moduleSchedule
        ModuleSchedule updatedModuleSchedule = moduleScheduleRepository.findById(moduleSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedModuleSchedule are not directly saved in db
        em.detach(updatedModuleSchedule);
        ModuleScheduleDTO moduleScheduleDTO = moduleScheduleMapper.toDto(updatedModuleSchedule);

        restModuleScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moduleScheduleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moduleScheduleDTO))
            )
            .andExpect(status().isOk());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeUpdate);
        ModuleSchedule testModuleSchedule = moduleScheduleList.get(moduleScheduleList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingModuleSchedule() throws Exception {
        int databaseSizeBeforeUpdate = moduleScheduleRepository.findAll().size();
        moduleSchedule.setId(count.incrementAndGet());

        // Create the ModuleSchedule
        ModuleScheduleDTO moduleScheduleDTO = moduleScheduleMapper.toDto(moduleSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moduleScheduleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moduleScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModuleSchedule() throws Exception {
        int databaseSizeBeforeUpdate = moduleScheduleRepository.findAll().size();
        moduleSchedule.setId(count.incrementAndGet());

        // Create the ModuleSchedule
        ModuleScheduleDTO moduleScheduleDTO = moduleScheduleMapper.toDto(moduleSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuleScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moduleScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModuleSchedule() throws Exception {
        int databaseSizeBeforeUpdate = moduleScheduleRepository.findAll().size();
        moduleSchedule.setId(count.incrementAndGet());

        // Create the ModuleSchedule
        ModuleScheduleDTO moduleScheduleDTO = moduleScheduleMapper.toDto(moduleSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuleScheduleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moduleScheduleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModuleScheduleWithPatch() throws Exception {
        // Initialize the database
        moduleScheduleRepository.saveAndFlush(moduleSchedule);

        int databaseSizeBeforeUpdate = moduleScheduleRepository.findAll().size();

        // Update the moduleSchedule using partial update
        ModuleSchedule partialUpdatedModuleSchedule = new ModuleSchedule();
        partialUpdatedModuleSchedule.setId(moduleSchedule.getId());

        restModuleScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModuleSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModuleSchedule))
            )
            .andExpect(status().isOk());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeUpdate);
        ModuleSchedule testModuleSchedule = moduleScheduleList.get(moduleScheduleList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateModuleScheduleWithPatch() throws Exception {
        // Initialize the database
        moduleScheduleRepository.saveAndFlush(moduleSchedule);

        int databaseSizeBeforeUpdate = moduleScheduleRepository.findAll().size();

        // Update the moduleSchedule using partial update
        ModuleSchedule partialUpdatedModuleSchedule = new ModuleSchedule();
        partialUpdatedModuleSchedule.setId(moduleSchedule.getId());

        restModuleScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModuleSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModuleSchedule))
            )
            .andExpect(status().isOk());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeUpdate);
        ModuleSchedule testModuleSchedule = moduleScheduleList.get(moduleScheduleList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingModuleSchedule() throws Exception {
        int databaseSizeBeforeUpdate = moduleScheduleRepository.findAll().size();
        moduleSchedule.setId(count.incrementAndGet());

        // Create the ModuleSchedule
        ModuleScheduleDTO moduleScheduleDTO = moduleScheduleMapper.toDto(moduleSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moduleScheduleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moduleScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModuleSchedule() throws Exception {
        int databaseSizeBeforeUpdate = moduleScheduleRepository.findAll().size();
        moduleSchedule.setId(count.incrementAndGet());

        // Create the ModuleSchedule
        ModuleScheduleDTO moduleScheduleDTO = moduleScheduleMapper.toDto(moduleSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuleScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moduleScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModuleSchedule() throws Exception {
        int databaseSizeBeforeUpdate = moduleScheduleRepository.findAll().size();
        moduleSchedule.setId(count.incrementAndGet());

        // Create the ModuleSchedule
        ModuleScheduleDTO moduleScheduleDTO = moduleScheduleMapper.toDto(moduleSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuleScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moduleScheduleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModuleSchedule in the database
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModuleSchedule() throws Exception {
        // Initialize the database
        moduleScheduleRepository.saveAndFlush(moduleSchedule);

        int databaseSizeBeforeDelete = moduleScheduleRepository.findAll().size();

        // Delete the moduleSchedule
        restModuleScheduleMockMvc
            .perform(delete(ENTITY_API_URL_ID, moduleSchedule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModuleSchedule> moduleScheduleList = moduleScheduleRepository.findAll();
        assertThat(moduleScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

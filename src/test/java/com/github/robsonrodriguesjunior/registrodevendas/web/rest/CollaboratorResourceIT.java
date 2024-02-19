package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.robsonrodriguesjunior.registrodevendas.IntegrationTest;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorStatus;
import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorType;
import com.github.robsonrodriguesjunior.registrodevendas.repository.CollaboratorRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CollaboratorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CollaboratorResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final CollaboratorType DEFAULT_TYPE = CollaboratorType.SELLER;
    private static final CollaboratorType UPDATED_TYPE = CollaboratorType.MANAGER;

    private static final CollaboratorStatus DEFAULT_STATUS = CollaboratorStatus.ACTIVE;
    private static final CollaboratorStatus UPDATED_STATUS = CollaboratorStatus.INACTIVE;

    private static final String ENTITY_API_URL = "/api/collaborators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollaboratorMockMvc;

    private Collaborator collaborator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborator createEntity(EntityManager em) {
        Collaborator collaborator = new Collaborator().code(DEFAULT_CODE).type(DEFAULT_TYPE).status(DEFAULT_STATUS);
        return collaborator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborator createUpdatedEntity(EntityManager em) {
        Collaborator collaborator = new Collaborator().code(UPDATED_CODE).type(UPDATED_TYPE).status(UPDATED_STATUS);
        return collaborator;
    }

    @BeforeEach
    public void initTest() {
        collaborator = createEntity(em);
    }

    @Test
    @Transactional
    void createCollaborator() throws Exception {
        int databaseSizeBeforeCreate = collaboratorRepository.findAll().size();
        // Create the Collaborator
        restCollaboratorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collaborator)))
            .andExpect(status().isCreated());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeCreate + 1);
        Collaborator testCollaborator = collaboratorList.get(collaboratorList.size() - 1);
        assertThat(testCollaborator.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCollaborator.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCollaborator.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createCollaboratorWithExistingId() throws Exception {
        // Create the Collaborator with an existing ID
        collaborator.setId(1L);

        int databaseSizeBeforeCreate = collaboratorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollaboratorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collaborator)))
            .andExpect(status().isBadRequest());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaboratorRepository.findAll().size();
        // set the field null
        collaborator.setCode(null);

        // Create the Collaborator, which fails.

        restCollaboratorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collaborator)))
            .andExpect(status().isBadRequest());

        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaboratorRepository.findAll().size();
        // set the field null
        collaborator.setType(null);

        // Create the Collaborator, which fails.

        restCollaboratorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collaborator)))
            .andExpect(status().isBadRequest());

        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaboratorRepository.findAll().size();
        // set the field null
        collaborator.setStatus(null);

        // Create the Collaborator, which fails.

        restCollaboratorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collaborator)))
            .andExpect(status().isBadRequest());

        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCollaborators() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList
        restCollaboratorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaborator.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getCollaborator() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get the collaborator
        restCollaboratorMockMvc
            .perform(get(ENTITY_API_URL_ID, collaborator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collaborator.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCollaborator() throws Exception {
        // Get the collaborator
        restCollaboratorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCollaborator() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();

        // Update the collaborator
        Collaborator updatedCollaborator = collaboratorRepository.findById(collaborator.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCollaborator are not directly saved in db
        em.detach(updatedCollaborator);
        updatedCollaborator.code(UPDATED_CODE).type(UPDATED_TYPE).status(UPDATED_STATUS);

        restCollaboratorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCollaborator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCollaborator))
            )
            .andExpect(status().isOk());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
        Collaborator testCollaborator = collaboratorList.get(collaboratorList.size() - 1);
        assertThat(testCollaborator.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCollaborator.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCollaborator.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingCollaborator() throws Exception {
        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();
        collaborator.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaboratorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collaborator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollaborator() throws Exception {
        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();
        collaborator.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaboratorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollaborator() throws Exception {
        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();
        collaborator.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaboratorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collaborator)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollaboratorWithPatch() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();

        // Update the collaborator using partial update
        Collaborator partialUpdatedCollaborator = new Collaborator();
        partialUpdatedCollaborator.setId(collaborator.getId());

        restCollaboratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollaborator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborator))
            )
            .andExpect(status().isOk());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
        Collaborator testCollaborator = collaboratorList.get(collaboratorList.size() - 1);
        assertThat(testCollaborator.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCollaborator.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCollaborator.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateCollaboratorWithPatch() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();

        // Update the collaborator using partial update
        Collaborator partialUpdatedCollaborator = new Collaborator();
        partialUpdatedCollaborator.setId(collaborator.getId());

        partialUpdatedCollaborator.code(UPDATED_CODE).type(UPDATED_TYPE).status(UPDATED_STATUS);

        restCollaboratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollaborator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborator))
            )
            .andExpect(status().isOk());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
        Collaborator testCollaborator = collaboratorList.get(collaboratorList.size() - 1);
        assertThat(testCollaborator.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCollaborator.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCollaborator.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCollaborator() throws Exception {
        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();
        collaborator.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaboratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collaborator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollaborator() throws Exception {
        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();
        collaborator.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaboratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollaborator() throws Exception {
        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();
        collaborator.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaboratorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(collaborator))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollaborator() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        int databaseSizeBeforeDelete = collaboratorRepository.findAll().size();

        // Delete the collaborator
        restCollaboratorMockMvc
            .perform(delete(ENTITY_API_URL_ID, collaborator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

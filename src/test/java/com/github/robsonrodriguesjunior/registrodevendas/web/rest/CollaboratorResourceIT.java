package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.robsonrodriguesjunior.registrodevendas.IntegrationTest;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Person;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Sale;
import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostView;
import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsView;
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
    void getCollaboratorsByIdFiltering() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        Long id = collaborator.getId();

        defaultCollaboratorShouldBeFound("id.equals=" + id);
        defaultCollaboratorShouldNotBeFound("id.notEquals=" + id);

        defaultCollaboratorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCollaboratorShouldNotBeFound("id.greaterThan=" + id);

        defaultCollaboratorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCollaboratorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCollaboratorsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where code equals to DEFAULT_CODE
        defaultCollaboratorShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the collaboratorList where code equals to UPDATED_CODE
        defaultCollaboratorShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCollaboratorsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCollaboratorShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the collaboratorList where code equals to UPDATED_CODE
        defaultCollaboratorShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCollaboratorsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where code is not null
        defaultCollaboratorShouldBeFound("code.specified=true");

        // Get all the collaboratorList where code is null
        defaultCollaboratorShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllCollaboratorsByCodeContainsSomething() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where code contains DEFAULT_CODE
        defaultCollaboratorShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the collaboratorList where code contains UPDATED_CODE
        defaultCollaboratorShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCollaboratorsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where code does not contain DEFAULT_CODE
        defaultCollaboratorShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the collaboratorList where code does not contain UPDATED_CODE
        defaultCollaboratorShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCollaboratorsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where type equals to DEFAULT_TYPE
        defaultCollaboratorShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the collaboratorList where type equals to UPDATED_TYPE
        defaultCollaboratorShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCollaboratorsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCollaboratorShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the collaboratorList where type equals to UPDATED_TYPE
        defaultCollaboratorShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCollaboratorsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where type is not null
        defaultCollaboratorShouldBeFound("type.specified=true");

        // Get all the collaboratorList where type is null
        defaultCollaboratorShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllCollaboratorsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where status equals to DEFAULT_STATUS
        defaultCollaboratorShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the collaboratorList where status equals to UPDATED_STATUS
        defaultCollaboratorShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCollaboratorsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCollaboratorShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the collaboratorList where status equals to UPDATED_STATUS
        defaultCollaboratorShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCollaboratorsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList where status is not null
        defaultCollaboratorShouldBeFound("status.specified=true");

        // Get all the collaboratorList where status is null
        defaultCollaboratorShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCollaboratorsByPersonIsEqualToSomething() throws Exception {
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            collaboratorRepository.saveAndFlush(collaborator);
            person = PersonResourceIT.createEntity(em);
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        em.persist(person);
        em.flush();
        collaborator.setPerson(person);
        collaboratorRepository.saveAndFlush(collaborator);
        Long personId = person.getId();
        // Get all the collaboratorList where person equals to personId
        defaultCollaboratorShouldBeFound("personId.equals=" + personId);

        // Get all the collaboratorList where person equals to (personId + 1)
        defaultCollaboratorShouldNotBeFound("personId.equals=" + (personId + 1));
    }

    @Test
    @Transactional
    void getAllCollaboratorsBySalesIsEqualToSomething() throws Exception {
        Sale sales;
        if (TestUtil.findAll(em, Sale.class).isEmpty()) {
            collaboratorRepository.saveAndFlush(collaborator);
            sales = SaleResourceIT.createEntity(em);
        } else {
            sales = TestUtil.findAll(em, Sale.class).get(0);
        }
        em.persist(sales);
        em.flush();
        collaborator.addSales(sales);
        collaboratorRepository.saveAndFlush(collaborator);
        Long salesId = sales.getId();
        // Get all the collaboratorList where sales equals to salesId
        defaultCollaboratorShouldBeFound("salesId.equals=" + salesId);

        // Get all the collaboratorList where sales equals to (salesId + 1)
        defaultCollaboratorShouldNotBeFound("salesId.equals=" + (salesId + 1));
    }

    @Test
    @Transactional
    void getAllCollaboratorsBySellersWhoEarnedMostViewIsEqualToSomething() throws Exception {
        SellersWhoEarnedMostView sellersWhoEarnedMostView;
        if (TestUtil.findAll(em, SellersWhoEarnedMostView.class).isEmpty()) {
            collaboratorRepository.saveAndFlush(collaborator);
            sellersWhoEarnedMostView = SellersWhoEarnedMostViewResourceIT.createEntity(em);
        } else {
            sellersWhoEarnedMostView = TestUtil.findAll(em, SellersWhoEarnedMostView.class).get(0);
        }
        em.persist(sellersWhoEarnedMostView);
        em.flush();
        collaborator.setSellersWhoEarnedMostView(sellersWhoEarnedMostView);
        sellersWhoEarnedMostView.setSeller(collaborator);
        collaboratorRepository.saveAndFlush(collaborator);
        Long sellersWhoEarnedMostViewId = sellersWhoEarnedMostView.getId();
        // Get all the collaboratorList where sellersWhoEarnedMostView equals to sellersWhoEarnedMostViewId
        defaultCollaboratorShouldBeFound("sellersWhoEarnedMostViewId.equals=" + sellersWhoEarnedMostViewId);

        // Get all the collaboratorList where sellersWhoEarnedMostView equals to (sellersWhoEarnedMostViewId + 1)
        defaultCollaboratorShouldNotBeFound("sellersWhoEarnedMostViewId.equals=" + (sellersWhoEarnedMostViewId + 1));
    }

    @Test
    @Transactional
    void getAllCollaboratorsBySellersWhoSoldMostProductsViewIsEqualToSomething() throws Exception {
        SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView;
        if (TestUtil.findAll(em, SellersWhoSoldMostProductsView.class).isEmpty()) {
            collaboratorRepository.saveAndFlush(collaborator);
            sellersWhoSoldMostProductsView = SellersWhoSoldMostProductsViewResourceIT.createEntity(em);
        } else {
            sellersWhoSoldMostProductsView = TestUtil.findAll(em, SellersWhoSoldMostProductsView.class).get(0);
        }
        em.persist(sellersWhoSoldMostProductsView);
        em.flush();
        collaborator.setSellersWhoSoldMostProductsView(sellersWhoSoldMostProductsView);
        sellersWhoSoldMostProductsView.setSeller(collaborator);
        collaboratorRepository.saveAndFlush(collaborator);
        Long sellersWhoSoldMostProductsViewId = sellersWhoSoldMostProductsView.getId();
        // Get all the collaboratorList where sellersWhoSoldMostProductsView equals to sellersWhoSoldMostProductsViewId
        defaultCollaboratorShouldBeFound("sellersWhoSoldMostProductsViewId.equals=" + sellersWhoSoldMostProductsViewId);

        // Get all the collaboratorList where sellersWhoSoldMostProductsView equals to (sellersWhoSoldMostProductsViewId + 1)
        defaultCollaboratorShouldNotBeFound("sellersWhoSoldMostProductsViewId.equals=" + (sellersWhoSoldMostProductsViewId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCollaboratorShouldBeFound(String filter) throws Exception {
        restCollaboratorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaborator.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restCollaboratorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCollaboratorShouldNotBeFound(String filter) throws Exception {
        restCollaboratorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCollaboratorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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

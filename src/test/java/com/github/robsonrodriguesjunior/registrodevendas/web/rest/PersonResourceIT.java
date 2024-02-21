package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.robsonrodriguesjunior.registrodevendas.IntegrationTest;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Client;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Person;
import com.github.robsonrodriguesjunior.registrodevendas.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTHDAY = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person().firstName(DEFAULT_FIRST_NAME).secondName(DEFAULT_SECOND_NAME).birthday(DEFAULT_BIRTHDAY);
        return person;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity(EntityManager em) {
        Person person = new Person().firstName(UPDATED_FIRST_NAME).secondName(UPDATED_SECOND_NAME).birthday(UPDATED_BIRTHDAY);
        return person;
    }

    @BeforeEach
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();
        // Create the Person
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPerson.getSecondName()).isEqualTo(DEFAULT_SECOND_NAME);
        assertThat(testPerson.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
    }

    @Test
    @Transactional
    void createPersonWithExistingId() throws Exception {
        // Create the Person with an existing ID
        person.setId(1L);

        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setFirstName(null);

        // Create the Person, which fails.

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSecondNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setSecondName(null);

        // Create the Person, which fails.

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBirthdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setBirthday(null);

        // Create the Person, which fails.

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_NAME)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())));
    }

    @Test
    @Transactional
    void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.secondName").value(DEFAULT_SECOND_NAME))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()));
    }

    @Test
    @Transactional
    void getPeopleByIdFiltering() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        Long id = person.getId();

        defaultPersonShouldBeFound("id.equals=" + id);
        defaultPersonShouldNotBeFound("id.notEquals=" + id);

        defaultPersonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPeopleByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where firstName equals to DEFAULT_FIRST_NAME
        defaultPersonShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the personList where firstName equals to UPDATED_FIRST_NAME
        defaultPersonShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllPeopleByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultPersonShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the personList where firstName equals to UPDATED_FIRST_NAME
        defaultPersonShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllPeopleByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where firstName is not null
        defaultPersonShouldBeFound("firstName.specified=true");

        // Get all the personList where firstName is null
        defaultPersonShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllPeopleByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where firstName contains DEFAULT_FIRST_NAME
        defaultPersonShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the personList where firstName contains UPDATED_FIRST_NAME
        defaultPersonShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllPeopleByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where firstName does not contain DEFAULT_FIRST_NAME
        defaultPersonShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the personList where firstName does not contain UPDATED_FIRST_NAME
        defaultPersonShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllPeopleBySecondNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where secondName equals to DEFAULT_SECOND_NAME
        defaultPersonShouldBeFound("secondName.equals=" + DEFAULT_SECOND_NAME);

        // Get all the personList where secondName equals to UPDATED_SECOND_NAME
        defaultPersonShouldNotBeFound("secondName.equals=" + UPDATED_SECOND_NAME);
    }

    @Test
    @Transactional
    void getAllPeopleBySecondNameIsInShouldWork() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where secondName in DEFAULT_SECOND_NAME or UPDATED_SECOND_NAME
        defaultPersonShouldBeFound("secondName.in=" + DEFAULT_SECOND_NAME + "," + UPDATED_SECOND_NAME);

        // Get all the personList where secondName equals to UPDATED_SECOND_NAME
        defaultPersonShouldNotBeFound("secondName.in=" + UPDATED_SECOND_NAME);
    }

    @Test
    @Transactional
    void getAllPeopleBySecondNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where secondName is not null
        defaultPersonShouldBeFound("secondName.specified=true");

        // Get all the personList where secondName is null
        defaultPersonShouldNotBeFound("secondName.specified=false");
    }

    @Test
    @Transactional
    void getAllPeopleBySecondNameContainsSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where secondName contains DEFAULT_SECOND_NAME
        defaultPersonShouldBeFound("secondName.contains=" + DEFAULT_SECOND_NAME);

        // Get all the personList where secondName contains UPDATED_SECOND_NAME
        defaultPersonShouldNotBeFound("secondName.contains=" + UPDATED_SECOND_NAME);
    }

    @Test
    @Transactional
    void getAllPeopleBySecondNameNotContainsSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where secondName does not contain DEFAULT_SECOND_NAME
        defaultPersonShouldNotBeFound("secondName.doesNotContain=" + DEFAULT_SECOND_NAME);

        // Get all the personList where secondName does not contain UPDATED_SECOND_NAME
        defaultPersonShouldBeFound("secondName.doesNotContain=" + UPDATED_SECOND_NAME);
    }

    @Test
    @Transactional
    void getAllPeopleByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where birthday equals to DEFAULT_BIRTHDAY
        defaultPersonShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the personList where birthday equals to UPDATED_BIRTHDAY
        defaultPersonShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllPeopleByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultPersonShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the personList where birthday equals to UPDATED_BIRTHDAY
        defaultPersonShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllPeopleByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where birthday is not null
        defaultPersonShouldBeFound("birthday.specified=true");

        // Get all the personList where birthday is null
        defaultPersonShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    void getAllPeopleByBirthdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where birthday is greater than or equal to DEFAULT_BIRTHDAY
        defaultPersonShouldBeFound("birthday.greaterThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the personList where birthday is greater than or equal to UPDATED_BIRTHDAY
        defaultPersonShouldNotBeFound("birthday.greaterThanOrEqual=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllPeopleByBirthdayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where birthday is less than or equal to DEFAULT_BIRTHDAY
        defaultPersonShouldBeFound("birthday.lessThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the personList where birthday is less than or equal to SMALLER_BIRTHDAY
        defaultPersonShouldNotBeFound("birthday.lessThanOrEqual=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllPeopleByBirthdayIsLessThanSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where birthday is less than DEFAULT_BIRTHDAY
        defaultPersonShouldNotBeFound("birthday.lessThan=" + DEFAULT_BIRTHDAY);

        // Get all the personList where birthday is less than UPDATED_BIRTHDAY
        defaultPersonShouldBeFound("birthday.lessThan=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllPeopleByBirthdayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where birthday is greater than DEFAULT_BIRTHDAY
        defaultPersonShouldNotBeFound("birthday.greaterThan=" + DEFAULT_BIRTHDAY);

        // Get all the personList where birthday is greater than SMALLER_BIRTHDAY
        defaultPersonShouldBeFound("birthday.greaterThan=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllPeopleByClientIsEqualToSomething() throws Exception {
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            personRepository.saveAndFlush(person);
            client = ClientResourceIT.createEntity(em);
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        em.persist(client);
        em.flush();
        person.setClient(client);
        client.setPerson(person);
        personRepository.saveAndFlush(person);
        Long clientId = client.getId();
        // Get all the personList where client equals to clientId
        defaultPersonShouldBeFound("clientId.equals=" + clientId);

        // Get all the personList where client equals to (clientId + 1)
        defaultPersonShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }

    @Test
    @Transactional
    void getAllPeopleBySellerIsEqualToSomething() throws Exception {
        Collaborator seller;
        if (TestUtil.findAll(em, Collaborator.class).isEmpty()) {
            personRepository.saveAndFlush(person);
            seller = CollaboratorResourceIT.createEntity(em);
        } else {
            seller = TestUtil.findAll(em, Collaborator.class).get(0);
        }
        em.persist(seller);
        em.flush();
        person.setSeller(seller);
        seller.setPerson(person);
        personRepository.saveAndFlush(person);
        Long sellerId = seller.getId();
        // Get all the personList where seller equals to sellerId
        defaultPersonShouldBeFound("sellerId.equals=" + sellerId);

        // Get all the personList where seller equals to (sellerId + 1)
        defaultPersonShouldNotBeFound("sellerId.equals=" + (sellerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonShouldBeFound(String filter) throws Exception {
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_NAME)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())));

        // Check, that the count call also returns 1
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonShouldNotBeFound(String filter) throws Exception {
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson.firstName(UPDATED_FIRST_NAME).secondName(UPDATED_SECOND_NAME).birthday(UPDATED_BIRTHDAY);

        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerson.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testPerson.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void putNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, person.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson.firstName(UPDATED_FIRST_NAME).secondName(UPDATED_SECOND_NAME);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testPerson.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
    }

    @Test
    @Transactional
    void fullUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson.firstName(UPDATED_FIRST_NAME).secondName(UPDATED_SECOND_NAME).birthday(UPDATED_BIRTHDAY);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testPerson.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void patchNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, person.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Delete the person
        restPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, person.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

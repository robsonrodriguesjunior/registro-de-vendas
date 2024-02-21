package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import static com.github.robsonrodriguesjunior.registrodevendas.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.robsonrodriguesjunior.registrodevendas.IntegrationTest;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SellersWhoEarnedMostViewRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link SellersWhoEarnedMostViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SellersWhoEarnedMostViewResourceIT {

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALUE = new BigDecimal(1 - 1);

    private static final Long DEFAULT_POSITION = 1L;
    private static final Long UPDATED_POSITION = 2L;
    private static final Long SMALLER_POSITION = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/sellers-who-earned-most-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SellersWhoEarnedMostViewRepository sellersWhoEarnedMostViewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSellersWhoEarnedMostViewMockMvc;

    private SellersWhoEarnedMostView sellersWhoEarnedMostView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SellersWhoEarnedMostView createEntity(EntityManager em) {
        SellersWhoEarnedMostView sellersWhoEarnedMostView = new SellersWhoEarnedMostView().value(DEFAULT_VALUE).position(DEFAULT_POSITION);
        return sellersWhoEarnedMostView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SellersWhoEarnedMostView createUpdatedEntity(EntityManager em) {
        SellersWhoEarnedMostView sellersWhoEarnedMostView = new SellersWhoEarnedMostView().value(UPDATED_VALUE).position(UPDATED_POSITION);
        return sellersWhoEarnedMostView;
    }

    @BeforeEach
    public void initTest() {
        sellersWhoEarnedMostView = createEntity(em);
    }

    @Test
    @Transactional
    void createSellersWhoEarnedMostView() throws Exception {
        int databaseSizeBeforeCreate = sellersWhoEarnedMostViewRepository.findAll().size();
        // Create the SellersWhoEarnedMostView
        restSellersWhoEarnedMostViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoEarnedMostView))
            )
            .andExpect(status().isCreated());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeCreate + 1);
        SellersWhoEarnedMostView testSellersWhoEarnedMostView = sellersWhoEarnedMostViewList.get(sellersWhoEarnedMostViewList.size() - 1);
        assertThat(testSellersWhoEarnedMostView.getValue()).isEqualByComparingTo(DEFAULT_VALUE);
        assertThat(testSellersWhoEarnedMostView.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createSellersWhoEarnedMostViewWithExistingId() throws Exception {
        // Create the SellersWhoEarnedMostView with an existing ID
        sellersWhoEarnedMostView.setId(1L);

        int databaseSizeBeforeCreate = sellersWhoEarnedMostViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSellersWhoEarnedMostViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoEarnedMostView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViews() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList
        restSellersWhoEarnedMostViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sellersWhoEarnedMostView.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(sameNumber(DEFAULT_VALUE))))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.intValue())));
    }

    @Test
    @Transactional
    void getSellersWhoEarnedMostView() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get the sellersWhoEarnedMostView
        restSellersWhoEarnedMostViewMockMvc
            .perform(get(ENTITY_API_URL_ID, sellersWhoEarnedMostView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sellersWhoEarnedMostView.getId().intValue()))
            .andExpect(jsonPath("$.value").value(sameNumber(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.intValue()));
    }

    @Test
    @Transactional
    void getSellersWhoEarnedMostViewsByIdFiltering() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        Long id = sellersWhoEarnedMostView.getId();

        defaultSellersWhoEarnedMostViewShouldBeFound("id.equals=" + id);
        defaultSellersWhoEarnedMostViewShouldNotBeFound("id.notEquals=" + id);

        defaultSellersWhoEarnedMostViewShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSellersWhoEarnedMostViewShouldNotBeFound("id.greaterThan=" + id);

        defaultSellersWhoEarnedMostViewShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSellersWhoEarnedMostViewShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where value equals to DEFAULT_VALUE
        defaultSellersWhoEarnedMostViewShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the sellersWhoEarnedMostViewList where value equals to UPDATED_VALUE
        defaultSellersWhoEarnedMostViewShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultSellersWhoEarnedMostViewShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the sellersWhoEarnedMostViewList where value equals to UPDATED_VALUE
        defaultSellersWhoEarnedMostViewShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where value is not null
        defaultSellersWhoEarnedMostViewShouldBeFound("value.specified=true");

        // Get all the sellersWhoEarnedMostViewList where value is null
        defaultSellersWhoEarnedMostViewShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where value is greater than or equal to DEFAULT_VALUE
        defaultSellersWhoEarnedMostViewShouldBeFound("value.greaterThanOrEqual=" + DEFAULT_VALUE);

        // Get all the sellersWhoEarnedMostViewList where value is greater than or equal to UPDATED_VALUE
        defaultSellersWhoEarnedMostViewShouldNotBeFound("value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where value is less than or equal to DEFAULT_VALUE
        defaultSellersWhoEarnedMostViewShouldBeFound("value.lessThanOrEqual=" + DEFAULT_VALUE);

        // Get all the sellersWhoEarnedMostViewList where value is less than or equal to SMALLER_VALUE
        defaultSellersWhoEarnedMostViewShouldNotBeFound("value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where value is less than DEFAULT_VALUE
        defaultSellersWhoEarnedMostViewShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the sellersWhoEarnedMostViewList where value is less than UPDATED_VALUE
        defaultSellersWhoEarnedMostViewShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where value is greater than DEFAULT_VALUE
        defaultSellersWhoEarnedMostViewShouldNotBeFound("value.greaterThan=" + DEFAULT_VALUE);

        // Get all the sellersWhoEarnedMostViewList where value is greater than SMALLER_VALUE
        defaultSellersWhoEarnedMostViewShouldBeFound("value.greaterThan=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where position equals to DEFAULT_POSITION
        defaultSellersWhoEarnedMostViewShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the sellersWhoEarnedMostViewList where position equals to UPDATED_POSITION
        defaultSellersWhoEarnedMostViewShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultSellersWhoEarnedMostViewShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the sellersWhoEarnedMostViewList where position equals to UPDATED_POSITION
        defaultSellersWhoEarnedMostViewShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where position is not null
        defaultSellersWhoEarnedMostViewShouldBeFound("position.specified=true");

        // Get all the sellersWhoEarnedMostViewList where position is null
        defaultSellersWhoEarnedMostViewShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByPositionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where position is greater than or equal to DEFAULT_POSITION
        defaultSellersWhoEarnedMostViewShouldBeFound("position.greaterThanOrEqual=" + DEFAULT_POSITION);

        // Get all the sellersWhoEarnedMostViewList where position is greater than or equal to UPDATED_POSITION
        defaultSellersWhoEarnedMostViewShouldNotBeFound("position.greaterThanOrEqual=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByPositionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where position is less than or equal to DEFAULT_POSITION
        defaultSellersWhoEarnedMostViewShouldBeFound("position.lessThanOrEqual=" + DEFAULT_POSITION);

        // Get all the sellersWhoEarnedMostViewList where position is less than or equal to SMALLER_POSITION
        defaultSellersWhoEarnedMostViewShouldNotBeFound("position.lessThanOrEqual=" + SMALLER_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByPositionIsLessThanSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where position is less than DEFAULT_POSITION
        defaultSellersWhoEarnedMostViewShouldNotBeFound("position.lessThan=" + DEFAULT_POSITION);

        // Get all the sellersWhoEarnedMostViewList where position is less than UPDATED_POSITION
        defaultSellersWhoEarnedMostViewShouldBeFound("position.lessThan=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsByPositionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        // Get all the sellersWhoEarnedMostViewList where position is greater than DEFAULT_POSITION
        defaultSellersWhoEarnedMostViewShouldNotBeFound("position.greaterThan=" + DEFAULT_POSITION);

        // Get all the sellersWhoEarnedMostViewList where position is greater than SMALLER_POSITION
        defaultSellersWhoEarnedMostViewShouldBeFound("position.greaterThan=" + SMALLER_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoEarnedMostViewsBySellerIsEqualToSomething() throws Exception {
        Collaborator seller;
        if (TestUtil.findAll(em, Collaborator.class).isEmpty()) {
            sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);
            seller = CollaboratorResourceIT.createEntity(em);
        } else {
            seller = TestUtil.findAll(em, Collaborator.class).get(0);
        }
        em.persist(seller);
        em.flush();
        sellersWhoEarnedMostView.setSeller(seller);
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);
        Long sellerId = seller.getId();
        // Get all the sellersWhoEarnedMostViewList where seller equals to sellerId
        defaultSellersWhoEarnedMostViewShouldBeFound("sellerId.equals=" + sellerId);

        // Get all the sellersWhoEarnedMostViewList where seller equals to (sellerId + 1)
        defaultSellersWhoEarnedMostViewShouldNotBeFound("sellerId.equals=" + (sellerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSellersWhoEarnedMostViewShouldBeFound(String filter) throws Exception {
        restSellersWhoEarnedMostViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sellersWhoEarnedMostView.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(sameNumber(DEFAULT_VALUE))))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.intValue())));

        // Check, that the count call also returns 1
        restSellersWhoEarnedMostViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSellersWhoEarnedMostViewShouldNotBeFound(String filter) throws Exception {
        restSellersWhoEarnedMostViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSellersWhoEarnedMostViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSellersWhoEarnedMostView() throws Exception {
        // Get the sellersWhoEarnedMostView
        restSellersWhoEarnedMostViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSellersWhoEarnedMostView() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        int databaseSizeBeforeUpdate = sellersWhoEarnedMostViewRepository.findAll().size();

        // Update the sellersWhoEarnedMostView
        SellersWhoEarnedMostView updatedSellersWhoEarnedMostView = sellersWhoEarnedMostViewRepository
            .findById(sellersWhoEarnedMostView.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSellersWhoEarnedMostView are not directly saved in db
        em.detach(updatedSellersWhoEarnedMostView);
        updatedSellersWhoEarnedMostView.value(UPDATED_VALUE).position(UPDATED_POSITION);

        restSellersWhoEarnedMostViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSellersWhoEarnedMostView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSellersWhoEarnedMostView))
            )
            .andExpect(status().isOk());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeUpdate);
        SellersWhoEarnedMostView testSellersWhoEarnedMostView = sellersWhoEarnedMostViewList.get(sellersWhoEarnedMostViewList.size() - 1);
        assertThat(testSellersWhoEarnedMostView.getValue()).isEqualByComparingTo(UPDATED_VALUE);
        assertThat(testSellersWhoEarnedMostView.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingSellersWhoEarnedMostView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoEarnedMostViewRepository.findAll().size();
        sellersWhoEarnedMostView.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSellersWhoEarnedMostViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sellersWhoEarnedMostView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoEarnedMostView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSellersWhoEarnedMostView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoEarnedMostViewRepository.findAll().size();
        sellersWhoEarnedMostView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSellersWhoEarnedMostViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoEarnedMostView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSellersWhoEarnedMostView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoEarnedMostViewRepository.findAll().size();
        sellersWhoEarnedMostView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSellersWhoEarnedMostViewMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoEarnedMostView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSellersWhoEarnedMostViewWithPatch() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        int databaseSizeBeforeUpdate = sellersWhoEarnedMostViewRepository.findAll().size();

        // Update the sellersWhoEarnedMostView using partial update
        SellersWhoEarnedMostView partialUpdatedSellersWhoEarnedMostView = new SellersWhoEarnedMostView();
        partialUpdatedSellersWhoEarnedMostView.setId(sellersWhoEarnedMostView.getId());

        partialUpdatedSellersWhoEarnedMostView.value(UPDATED_VALUE);

        restSellersWhoEarnedMostViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSellersWhoEarnedMostView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSellersWhoEarnedMostView))
            )
            .andExpect(status().isOk());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeUpdate);
        SellersWhoEarnedMostView testSellersWhoEarnedMostView = sellersWhoEarnedMostViewList.get(sellersWhoEarnedMostViewList.size() - 1);
        assertThat(testSellersWhoEarnedMostView.getValue()).isEqualByComparingTo(UPDATED_VALUE);
        assertThat(testSellersWhoEarnedMostView.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateSellersWhoEarnedMostViewWithPatch() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        int databaseSizeBeforeUpdate = sellersWhoEarnedMostViewRepository.findAll().size();

        // Update the sellersWhoEarnedMostView using partial update
        SellersWhoEarnedMostView partialUpdatedSellersWhoEarnedMostView = new SellersWhoEarnedMostView();
        partialUpdatedSellersWhoEarnedMostView.setId(sellersWhoEarnedMostView.getId());

        partialUpdatedSellersWhoEarnedMostView.value(UPDATED_VALUE).position(UPDATED_POSITION);

        restSellersWhoEarnedMostViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSellersWhoEarnedMostView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSellersWhoEarnedMostView))
            )
            .andExpect(status().isOk());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeUpdate);
        SellersWhoEarnedMostView testSellersWhoEarnedMostView = sellersWhoEarnedMostViewList.get(sellersWhoEarnedMostViewList.size() - 1);
        assertThat(testSellersWhoEarnedMostView.getValue()).isEqualByComparingTo(UPDATED_VALUE);
        assertThat(testSellersWhoEarnedMostView.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingSellersWhoEarnedMostView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoEarnedMostViewRepository.findAll().size();
        sellersWhoEarnedMostView.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSellersWhoEarnedMostViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sellersWhoEarnedMostView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoEarnedMostView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSellersWhoEarnedMostView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoEarnedMostViewRepository.findAll().size();
        sellersWhoEarnedMostView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSellersWhoEarnedMostViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoEarnedMostView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSellersWhoEarnedMostView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoEarnedMostViewRepository.findAll().size();
        sellersWhoEarnedMostView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSellersWhoEarnedMostViewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoEarnedMostView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SellersWhoEarnedMostView in the database
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSellersWhoEarnedMostView() throws Exception {
        // Initialize the database
        sellersWhoEarnedMostViewRepository.saveAndFlush(sellersWhoEarnedMostView);

        int databaseSizeBeforeDelete = sellersWhoEarnedMostViewRepository.findAll().size();

        // Delete the sellersWhoEarnedMostView
        restSellersWhoEarnedMostViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, sellersWhoEarnedMostView.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SellersWhoEarnedMostView> sellersWhoEarnedMostViewList = sellersWhoEarnedMostViewRepository.findAll();
        assertThat(sellersWhoEarnedMostViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.robsonrodriguesjunior.registrodevendas.IntegrationTest;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SellersWhoSoldMostProductsViewRepository;
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
 * Integration tests for the {@link SellersWhoSoldMostProductsViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SellersWhoSoldMostProductsViewResourceIT {

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;
    private static final Long SMALLER_QUANTITY = 1L - 1L;

    private static final Long DEFAULT_POSITION = 1L;
    private static final Long UPDATED_POSITION = 2L;
    private static final Long SMALLER_POSITION = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/sellers-who-sold-most-products-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SellersWhoSoldMostProductsViewRepository sellersWhoSoldMostProductsViewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSellersWhoSoldMostProductsViewMockMvc;

    private SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SellersWhoSoldMostProductsView createEntity(EntityManager em) {
        SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView = new SellersWhoSoldMostProductsView()
            .quantity(DEFAULT_QUANTITY)
            .position(DEFAULT_POSITION);
        return sellersWhoSoldMostProductsView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SellersWhoSoldMostProductsView createUpdatedEntity(EntityManager em) {
        SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView = new SellersWhoSoldMostProductsView()
            .quantity(UPDATED_QUANTITY)
            .position(UPDATED_POSITION);
        return sellersWhoSoldMostProductsView;
    }

    @BeforeEach
    public void initTest() {
        sellersWhoSoldMostProductsView = createEntity(em);
    }

    @Test
    @Transactional
    void createSellersWhoSoldMostProductsView() throws Exception {
        int databaseSizeBeforeCreate = sellersWhoSoldMostProductsViewRepository.findAll().size();
        // Create the SellersWhoSoldMostProductsView
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoSoldMostProductsView))
            )
            .andExpect(status().isCreated());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeCreate + 1);
        SellersWhoSoldMostProductsView testSellersWhoSoldMostProductsView = sellersWhoSoldMostProductsViewList.get(
            sellersWhoSoldMostProductsViewList.size() - 1
        );
        assertThat(testSellersWhoSoldMostProductsView.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSellersWhoSoldMostProductsView.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createSellersWhoSoldMostProductsViewWithExistingId() throws Exception {
        // Create the SellersWhoSoldMostProductsView with an existing ID
        sellersWhoSoldMostProductsView.setId(1L);

        int databaseSizeBeforeCreate = sellersWhoSoldMostProductsViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoSoldMostProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViews() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sellersWhoSoldMostProductsView.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.intValue())));
    }

    @Test
    @Transactional
    void getSellersWhoSoldMostProductsView() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get the sellersWhoSoldMostProductsView
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(get(ENTITY_API_URL_ID, sellersWhoSoldMostProductsView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sellersWhoSoldMostProductsView.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.intValue()));
    }

    @Test
    @Transactional
    void getSellersWhoSoldMostProductsViewsByIdFiltering() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        Long id = sellersWhoSoldMostProductsView.getId();

        defaultSellersWhoSoldMostProductsViewShouldBeFound("id.equals=" + id);
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("id.notEquals=" + id);

        defaultSellersWhoSoldMostProductsViewShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("id.greaterThan=" + id);

        defaultSellersWhoSoldMostProductsViewShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where quantity equals to DEFAULT_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the sellersWhoSoldMostProductsViewList where quantity equals to UPDATED_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the sellersWhoSoldMostProductsViewList where quantity equals to UPDATED_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where quantity is not null
        defaultSellersWhoSoldMostProductsViewShouldBeFound("quantity.specified=true");

        // Get all the sellersWhoSoldMostProductsViewList where quantity is null
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the sellersWhoSoldMostProductsViewList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the sellersWhoSoldMostProductsViewList where quantity is less than or equal to SMALLER_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where quantity is less than DEFAULT_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the sellersWhoSoldMostProductsViewList where quantity is less than UPDATED_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where quantity is greater than DEFAULT_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the sellersWhoSoldMostProductsViewList where quantity is greater than SMALLER_QUANTITY
        defaultSellersWhoSoldMostProductsViewShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where position equals to DEFAULT_POSITION
        defaultSellersWhoSoldMostProductsViewShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the sellersWhoSoldMostProductsViewList where position equals to UPDATED_POSITION
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultSellersWhoSoldMostProductsViewShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the sellersWhoSoldMostProductsViewList where position equals to UPDATED_POSITION
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where position is not null
        defaultSellersWhoSoldMostProductsViewShouldBeFound("position.specified=true");

        // Get all the sellersWhoSoldMostProductsViewList where position is null
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByPositionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where position is greater than or equal to DEFAULT_POSITION
        defaultSellersWhoSoldMostProductsViewShouldBeFound("position.greaterThanOrEqual=" + DEFAULT_POSITION);

        // Get all the sellersWhoSoldMostProductsViewList where position is greater than or equal to UPDATED_POSITION
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("position.greaterThanOrEqual=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByPositionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where position is less than or equal to DEFAULT_POSITION
        defaultSellersWhoSoldMostProductsViewShouldBeFound("position.lessThanOrEqual=" + DEFAULT_POSITION);

        // Get all the sellersWhoSoldMostProductsViewList where position is less than or equal to SMALLER_POSITION
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("position.lessThanOrEqual=" + SMALLER_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByPositionIsLessThanSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where position is less than DEFAULT_POSITION
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("position.lessThan=" + DEFAULT_POSITION);

        // Get all the sellersWhoSoldMostProductsViewList where position is less than UPDATED_POSITION
        defaultSellersWhoSoldMostProductsViewShouldBeFound("position.lessThan=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsByPositionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        // Get all the sellersWhoSoldMostProductsViewList where position is greater than DEFAULT_POSITION
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("position.greaterThan=" + DEFAULT_POSITION);

        // Get all the sellersWhoSoldMostProductsViewList where position is greater than SMALLER_POSITION
        defaultSellersWhoSoldMostProductsViewShouldBeFound("position.greaterThan=" + SMALLER_POSITION);
    }

    @Test
    @Transactional
    void getAllSellersWhoSoldMostProductsViewsBySellerIsEqualToSomething() throws Exception {
        Collaborator seller;
        if (TestUtil.findAll(em, Collaborator.class).isEmpty()) {
            sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);
            seller = CollaboratorResourceIT.createEntity(em);
        } else {
            seller = TestUtil.findAll(em, Collaborator.class).get(0);
        }
        em.persist(seller);
        em.flush();
        sellersWhoSoldMostProductsView.setSeller(seller);
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);
        Long sellerId = seller.getId();
        // Get all the sellersWhoSoldMostProductsViewList where seller equals to sellerId
        defaultSellersWhoSoldMostProductsViewShouldBeFound("sellerId.equals=" + sellerId);

        // Get all the sellersWhoSoldMostProductsViewList where seller equals to (sellerId + 1)
        defaultSellersWhoSoldMostProductsViewShouldNotBeFound("sellerId.equals=" + (sellerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSellersWhoSoldMostProductsViewShouldBeFound(String filter) throws Exception {
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sellersWhoSoldMostProductsView.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.intValue())));

        // Check, that the count call also returns 1
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSellersWhoSoldMostProductsViewShouldNotBeFound(String filter) throws Exception {
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSellersWhoSoldMostProductsView() throws Exception {
        // Get the sellersWhoSoldMostProductsView
        restSellersWhoSoldMostProductsViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSellersWhoSoldMostProductsView() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        int databaseSizeBeforeUpdate = sellersWhoSoldMostProductsViewRepository.findAll().size();

        // Update the sellersWhoSoldMostProductsView
        SellersWhoSoldMostProductsView updatedSellersWhoSoldMostProductsView = sellersWhoSoldMostProductsViewRepository
            .findById(sellersWhoSoldMostProductsView.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSellersWhoSoldMostProductsView are not directly saved in db
        em.detach(updatedSellersWhoSoldMostProductsView);
        updatedSellersWhoSoldMostProductsView.quantity(UPDATED_QUANTITY).position(UPDATED_POSITION);

        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSellersWhoSoldMostProductsView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSellersWhoSoldMostProductsView))
            )
            .andExpect(status().isOk());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeUpdate);
        SellersWhoSoldMostProductsView testSellersWhoSoldMostProductsView = sellersWhoSoldMostProductsViewList.get(
            sellersWhoSoldMostProductsViewList.size() - 1
        );
        assertThat(testSellersWhoSoldMostProductsView.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSellersWhoSoldMostProductsView.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingSellersWhoSoldMostProductsView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoSoldMostProductsViewRepository.findAll().size();
        sellersWhoSoldMostProductsView.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sellersWhoSoldMostProductsView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoSoldMostProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSellersWhoSoldMostProductsView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoSoldMostProductsViewRepository.findAll().size();
        sellersWhoSoldMostProductsView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoSoldMostProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSellersWhoSoldMostProductsView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoSoldMostProductsViewRepository.findAll().size();
        sellersWhoSoldMostProductsView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoSoldMostProductsView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSellersWhoSoldMostProductsViewWithPatch() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        int databaseSizeBeforeUpdate = sellersWhoSoldMostProductsViewRepository.findAll().size();

        // Update the sellersWhoSoldMostProductsView using partial update
        SellersWhoSoldMostProductsView partialUpdatedSellersWhoSoldMostProductsView = new SellersWhoSoldMostProductsView();
        partialUpdatedSellersWhoSoldMostProductsView.setId(sellersWhoSoldMostProductsView.getId());

        partialUpdatedSellersWhoSoldMostProductsView.quantity(UPDATED_QUANTITY).position(UPDATED_POSITION);

        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSellersWhoSoldMostProductsView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSellersWhoSoldMostProductsView))
            )
            .andExpect(status().isOk());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeUpdate);
        SellersWhoSoldMostProductsView testSellersWhoSoldMostProductsView = sellersWhoSoldMostProductsViewList.get(
            sellersWhoSoldMostProductsViewList.size() - 1
        );
        assertThat(testSellersWhoSoldMostProductsView.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSellersWhoSoldMostProductsView.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateSellersWhoSoldMostProductsViewWithPatch() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        int databaseSizeBeforeUpdate = sellersWhoSoldMostProductsViewRepository.findAll().size();

        // Update the sellersWhoSoldMostProductsView using partial update
        SellersWhoSoldMostProductsView partialUpdatedSellersWhoSoldMostProductsView = new SellersWhoSoldMostProductsView();
        partialUpdatedSellersWhoSoldMostProductsView.setId(sellersWhoSoldMostProductsView.getId());

        partialUpdatedSellersWhoSoldMostProductsView.quantity(UPDATED_QUANTITY).position(UPDATED_POSITION);

        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSellersWhoSoldMostProductsView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSellersWhoSoldMostProductsView))
            )
            .andExpect(status().isOk());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeUpdate);
        SellersWhoSoldMostProductsView testSellersWhoSoldMostProductsView = sellersWhoSoldMostProductsViewList.get(
            sellersWhoSoldMostProductsViewList.size() - 1
        );
        assertThat(testSellersWhoSoldMostProductsView.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSellersWhoSoldMostProductsView.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingSellersWhoSoldMostProductsView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoSoldMostProductsViewRepository.findAll().size();
        sellersWhoSoldMostProductsView.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sellersWhoSoldMostProductsView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoSoldMostProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSellersWhoSoldMostProductsView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoSoldMostProductsViewRepository.findAll().size();
        sellersWhoSoldMostProductsView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoSoldMostProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSellersWhoSoldMostProductsView() throws Exception {
        int databaseSizeBeforeUpdate = sellersWhoSoldMostProductsViewRepository.findAll().size();
        sellersWhoSoldMostProductsView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sellersWhoSoldMostProductsView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SellersWhoSoldMostProductsView in the database
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSellersWhoSoldMostProductsView() throws Exception {
        // Initialize the database
        sellersWhoSoldMostProductsViewRepository.saveAndFlush(sellersWhoSoldMostProductsView);

        int databaseSizeBeforeDelete = sellersWhoSoldMostProductsViewRepository.findAll().size();

        // Delete the sellersWhoSoldMostProductsView
        restSellersWhoSoldMostProductsViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, sellersWhoSoldMostProductsView.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsViewList = sellersWhoSoldMostProductsViewRepository.findAll();
        assertThat(sellersWhoSoldMostProductsViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

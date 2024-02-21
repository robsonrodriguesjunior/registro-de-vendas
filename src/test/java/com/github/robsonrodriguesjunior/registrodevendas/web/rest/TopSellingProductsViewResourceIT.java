package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.robsonrodriguesjunior.registrodevendas.IntegrationTest;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Product;
import com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.TopSellingProductsViewRepository;
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
 * Integration tests for the {@link TopSellingProductsViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TopSellingProductsViewResourceIT {

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;
    private static final Long SMALLER_QUANTITY = 1L - 1L;

    private static final Long DEFAULT_POSITION = 1L;
    private static final Long UPDATED_POSITION = 2L;
    private static final Long SMALLER_POSITION = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/top-selling-products-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TopSellingProductsViewRepository topSellingProductsViewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopSellingProductsViewMockMvc;

    private TopSellingProductsView topSellingProductsView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopSellingProductsView createEntity(EntityManager em) {
        TopSellingProductsView topSellingProductsView = new TopSellingProductsView().quantity(DEFAULT_QUANTITY).position(DEFAULT_POSITION);
        return topSellingProductsView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopSellingProductsView createUpdatedEntity(EntityManager em) {
        TopSellingProductsView topSellingProductsView = new TopSellingProductsView().quantity(UPDATED_QUANTITY).position(UPDATED_POSITION);
        return topSellingProductsView;
    }

    @BeforeEach
    public void initTest() {
        topSellingProductsView = createEntity(em);
    }

    @Test
    @Transactional
    void createTopSellingProductsView() throws Exception {
        int databaseSizeBeforeCreate = topSellingProductsViewRepository.findAll().size();
        // Create the TopSellingProductsView
        restTopSellingProductsViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(topSellingProductsView))
            )
            .andExpect(status().isCreated());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeCreate + 1);
        TopSellingProductsView testTopSellingProductsView = topSellingProductsViewList.get(topSellingProductsViewList.size() - 1);
        assertThat(testTopSellingProductsView.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTopSellingProductsView.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createTopSellingProductsViewWithExistingId() throws Exception {
        // Create the TopSellingProductsView with an existing ID
        topSellingProductsView.setId(1L);

        int databaseSizeBeforeCreate = topSellingProductsViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopSellingProductsViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(topSellingProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViews() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList
        restTopSellingProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topSellingProductsView.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.intValue())));
    }

    @Test
    @Transactional
    void getTopSellingProductsView() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get the topSellingProductsView
        restTopSellingProductsViewMockMvc
            .perform(get(ENTITY_API_URL_ID, topSellingProductsView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topSellingProductsView.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.intValue()));
    }

    @Test
    @Transactional
    void getTopSellingProductsViewsByIdFiltering() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        Long id = topSellingProductsView.getId();

        defaultTopSellingProductsViewShouldBeFound("id.equals=" + id);
        defaultTopSellingProductsViewShouldNotBeFound("id.notEquals=" + id);

        defaultTopSellingProductsViewShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTopSellingProductsViewShouldNotBeFound("id.greaterThan=" + id);

        defaultTopSellingProductsViewShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTopSellingProductsViewShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where quantity equals to DEFAULT_QUANTITY
        defaultTopSellingProductsViewShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the topSellingProductsViewList where quantity equals to UPDATED_QUANTITY
        defaultTopSellingProductsViewShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultTopSellingProductsViewShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the topSellingProductsViewList where quantity equals to UPDATED_QUANTITY
        defaultTopSellingProductsViewShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where quantity is not null
        defaultTopSellingProductsViewShouldBeFound("quantity.specified=true");

        // Get all the topSellingProductsViewList where quantity is null
        defaultTopSellingProductsViewShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultTopSellingProductsViewShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the topSellingProductsViewList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultTopSellingProductsViewShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultTopSellingProductsViewShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the topSellingProductsViewList where quantity is less than or equal to SMALLER_QUANTITY
        defaultTopSellingProductsViewShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where quantity is less than DEFAULT_QUANTITY
        defaultTopSellingProductsViewShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the topSellingProductsViewList where quantity is less than UPDATED_QUANTITY
        defaultTopSellingProductsViewShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where quantity is greater than DEFAULT_QUANTITY
        defaultTopSellingProductsViewShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the topSellingProductsViewList where quantity is greater than SMALLER_QUANTITY
        defaultTopSellingProductsViewShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where position equals to DEFAULT_POSITION
        defaultTopSellingProductsViewShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the topSellingProductsViewList where position equals to UPDATED_POSITION
        defaultTopSellingProductsViewShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultTopSellingProductsViewShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the topSellingProductsViewList where position equals to UPDATED_POSITION
        defaultTopSellingProductsViewShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where position is not null
        defaultTopSellingProductsViewShouldBeFound("position.specified=true");

        // Get all the topSellingProductsViewList where position is null
        defaultTopSellingProductsViewShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByPositionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where position is greater than or equal to DEFAULT_POSITION
        defaultTopSellingProductsViewShouldBeFound("position.greaterThanOrEqual=" + DEFAULT_POSITION);

        // Get all the topSellingProductsViewList where position is greater than or equal to UPDATED_POSITION
        defaultTopSellingProductsViewShouldNotBeFound("position.greaterThanOrEqual=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByPositionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where position is less than or equal to DEFAULT_POSITION
        defaultTopSellingProductsViewShouldBeFound("position.lessThanOrEqual=" + DEFAULT_POSITION);

        // Get all the topSellingProductsViewList where position is less than or equal to SMALLER_POSITION
        defaultTopSellingProductsViewShouldNotBeFound("position.lessThanOrEqual=" + SMALLER_POSITION);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByPositionIsLessThanSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where position is less than DEFAULT_POSITION
        defaultTopSellingProductsViewShouldNotBeFound("position.lessThan=" + DEFAULT_POSITION);

        // Get all the topSellingProductsViewList where position is less than UPDATED_POSITION
        defaultTopSellingProductsViewShouldBeFound("position.lessThan=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByPositionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        // Get all the topSellingProductsViewList where position is greater than DEFAULT_POSITION
        defaultTopSellingProductsViewShouldNotBeFound("position.greaterThan=" + DEFAULT_POSITION);

        // Get all the topSellingProductsViewList where position is greater than SMALLER_POSITION
        defaultTopSellingProductsViewShouldBeFound("position.greaterThan=" + SMALLER_POSITION);
    }

    @Test
    @Transactional
    void getAllTopSellingProductsViewsByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);
            product = ProductResourceIT.createEntity(em);
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        topSellingProductsView.setProduct(product);
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);
        Long productId = product.getId();
        // Get all the topSellingProductsViewList where product equals to productId
        defaultTopSellingProductsViewShouldBeFound("productId.equals=" + productId);

        // Get all the topSellingProductsViewList where product equals to (productId + 1)
        defaultTopSellingProductsViewShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTopSellingProductsViewShouldBeFound(String filter) throws Exception {
        restTopSellingProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topSellingProductsView.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.intValue())));

        // Check, that the count call also returns 1
        restTopSellingProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTopSellingProductsViewShouldNotBeFound(String filter) throws Exception {
        restTopSellingProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTopSellingProductsViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTopSellingProductsView() throws Exception {
        // Get the topSellingProductsView
        restTopSellingProductsViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTopSellingProductsView() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        int databaseSizeBeforeUpdate = topSellingProductsViewRepository.findAll().size();

        // Update the topSellingProductsView
        TopSellingProductsView updatedTopSellingProductsView = topSellingProductsViewRepository
            .findById(topSellingProductsView.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTopSellingProductsView are not directly saved in db
        em.detach(updatedTopSellingProductsView);
        updatedTopSellingProductsView.quantity(UPDATED_QUANTITY).position(UPDATED_POSITION);

        restTopSellingProductsViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTopSellingProductsView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTopSellingProductsView))
            )
            .andExpect(status().isOk());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeUpdate);
        TopSellingProductsView testTopSellingProductsView = topSellingProductsViewList.get(topSellingProductsViewList.size() - 1);
        assertThat(testTopSellingProductsView.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTopSellingProductsView.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingTopSellingProductsView() throws Exception {
        int databaseSizeBeforeUpdate = topSellingProductsViewRepository.findAll().size();
        topSellingProductsView.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopSellingProductsViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, topSellingProductsView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(topSellingProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTopSellingProductsView() throws Exception {
        int databaseSizeBeforeUpdate = topSellingProductsViewRepository.findAll().size();
        topSellingProductsView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopSellingProductsViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(topSellingProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTopSellingProductsView() throws Exception {
        int databaseSizeBeforeUpdate = topSellingProductsViewRepository.findAll().size();
        topSellingProductsView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopSellingProductsViewMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(topSellingProductsView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTopSellingProductsViewWithPatch() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        int databaseSizeBeforeUpdate = topSellingProductsViewRepository.findAll().size();

        // Update the topSellingProductsView using partial update
        TopSellingProductsView partialUpdatedTopSellingProductsView = new TopSellingProductsView();
        partialUpdatedTopSellingProductsView.setId(topSellingProductsView.getId());

        partialUpdatedTopSellingProductsView.position(UPDATED_POSITION);

        restTopSellingProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTopSellingProductsView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTopSellingProductsView))
            )
            .andExpect(status().isOk());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeUpdate);
        TopSellingProductsView testTopSellingProductsView = topSellingProductsViewList.get(topSellingProductsViewList.size() - 1);
        assertThat(testTopSellingProductsView.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTopSellingProductsView.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateTopSellingProductsViewWithPatch() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        int databaseSizeBeforeUpdate = topSellingProductsViewRepository.findAll().size();

        // Update the topSellingProductsView using partial update
        TopSellingProductsView partialUpdatedTopSellingProductsView = new TopSellingProductsView();
        partialUpdatedTopSellingProductsView.setId(topSellingProductsView.getId());

        partialUpdatedTopSellingProductsView.quantity(UPDATED_QUANTITY).position(UPDATED_POSITION);

        restTopSellingProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTopSellingProductsView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTopSellingProductsView))
            )
            .andExpect(status().isOk());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeUpdate);
        TopSellingProductsView testTopSellingProductsView = topSellingProductsViewList.get(topSellingProductsViewList.size() - 1);
        assertThat(testTopSellingProductsView.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTopSellingProductsView.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingTopSellingProductsView() throws Exception {
        int databaseSizeBeforeUpdate = topSellingProductsViewRepository.findAll().size();
        topSellingProductsView.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopSellingProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, topSellingProductsView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(topSellingProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTopSellingProductsView() throws Exception {
        int databaseSizeBeforeUpdate = topSellingProductsViewRepository.findAll().size();
        topSellingProductsView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopSellingProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(topSellingProductsView))
            )
            .andExpect(status().isBadRequest());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTopSellingProductsView() throws Exception {
        int databaseSizeBeforeUpdate = topSellingProductsViewRepository.findAll().size();
        topSellingProductsView.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopSellingProductsViewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(topSellingProductsView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TopSellingProductsView in the database
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTopSellingProductsView() throws Exception {
        // Initialize the database
        topSellingProductsViewRepository.saveAndFlush(topSellingProductsView);

        int databaseSizeBeforeDelete = topSellingProductsViewRepository.findAll().size();

        // Delete the topSellingProductsView
        restTopSellingProductsViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, topSellingProductsView.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopSellingProductsView> topSellingProductsViewList = topSellingProductsViewRepository.findAll();
        assertThat(topSellingProductsViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

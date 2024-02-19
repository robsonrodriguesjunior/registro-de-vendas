package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.robsonrodriguesjunior.registrodevendas.IntegrationTest;
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

    private static final Long DEFAULT_POSITION = 1L;
    private static final Long UPDATED_POSITION = 2L;

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

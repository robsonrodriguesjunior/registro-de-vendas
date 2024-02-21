package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.TopSellingProductsViewRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsView}.
 */
@Service
@Transactional
public class TopSellingProductsViewService {

    private final Logger log = LoggerFactory.getLogger(TopSellingProductsViewService.class);

    private final TopSellingProductsViewRepository topSellingProductsViewRepository;

    public TopSellingProductsViewService(TopSellingProductsViewRepository topSellingProductsViewRepository) {
        this.topSellingProductsViewRepository = topSellingProductsViewRepository;
    }

    /**
     * Save a topSellingProductsView.
     *
     * @param topSellingProductsView the entity to save.
     * @return the persisted entity.
     */
    public TopSellingProductsView save(TopSellingProductsView topSellingProductsView) {
        log.debug("Request to save TopSellingProductsView : {}", topSellingProductsView);
        return topSellingProductsViewRepository.save(topSellingProductsView);
    }

    /**
     * Update a topSellingProductsView.
     *
     * @param topSellingProductsView the entity to save.
     * @return the persisted entity.
     */
    public TopSellingProductsView update(TopSellingProductsView topSellingProductsView) {
        log.debug("Request to update TopSellingProductsView : {}", topSellingProductsView);
        return topSellingProductsViewRepository.save(topSellingProductsView);
    }

    /**
     * Partially update a topSellingProductsView.
     *
     * @param topSellingProductsView the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TopSellingProductsView> partialUpdate(TopSellingProductsView topSellingProductsView) {
        log.debug("Request to partially update TopSellingProductsView : {}", topSellingProductsView);

        return topSellingProductsViewRepository
            .findById(topSellingProductsView.getId())
            .map(existingTopSellingProductsView -> {
                if (topSellingProductsView.getQuantity() != null) {
                    existingTopSellingProductsView.setQuantity(topSellingProductsView.getQuantity());
                }
                if (topSellingProductsView.getPosition() != null) {
                    existingTopSellingProductsView.setPosition(topSellingProductsView.getPosition());
                }

                return existingTopSellingProductsView;
            })
            .map(topSellingProductsViewRepository::save);
    }

    /**
     * Get all the topSellingProductsViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TopSellingProductsView> findAll(Pageable pageable) {
        log.debug("Request to get all TopSellingProductsViews");
        return topSellingProductsViewRepository.findAll(pageable);
    }

    /**
     * Get one topSellingProductsView by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TopSellingProductsView> findOne(Long id) {
        log.debug("Request to get TopSellingProductsView : {}", id);
        return topSellingProductsViewRepository.findById(id);
    }

    /**
     * Delete the topSellingProductsView by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TopSellingProductsView : {}", id);
        topSellingProductsViewRepository.deleteById(id);
    }
}

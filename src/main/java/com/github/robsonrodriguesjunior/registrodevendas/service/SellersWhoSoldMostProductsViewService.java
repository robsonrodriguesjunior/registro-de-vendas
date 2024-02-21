package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SellersWhoSoldMostProductsViewRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsView}.
 */
@Service
@Transactional
public class SellersWhoSoldMostProductsViewService {

    private final Logger log = LoggerFactory.getLogger(SellersWhoSoldMostProductsViewService.class);

    private final SellersWhoSoldMostProductsViewRepository sellersWhoSoldMostProductsViewRepository;

    public SellersWhoSoldMostProductsViewService(SellersWhoSoldMostProductsViewRepository sellersWhoSoldMostProductsViewRepository) {
        this.sellersWhoSoldMostProductsViewRepository = sellersWhoSoldMostProductsViewRepository;
    }

    /**
     * Save a sellersWhoSoldMostProductsView.
     *
     * @param sellersWhoSoldMostProductsView the entity to save.
     * @return the persisted entity.
     */
    public SellersWhoSoldMostProductsView save(SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView) {
        log.debug("Request to save SellersWhoSoldMostProductsView : {}", sellersWhoSoldMostProductsView);
        return sellersWhoSoldMostProductsViewRepository.save(sellersWhoSoldMostProductsView);
    }

    /**
     * Update a sellersWhoSoldMostProductsView.
     *
     * @param sellersWhoSoldMostProductsView the entity to save.
     * @return the persisted entity.
     */
    public SellersWhoSoldMostProductsView update(SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView) {
        log.debug("Request to update SellersWhoSoldMostProductsView : {}", sellersWhoSoldMostProductsView);
        return sellersWhoSoldMostProductsViewRepository.save(sellersWhoSoldMostProductsView);
    }

    /**
     * Partially update a sellersWhoSoldMostProductsView.
     *
     * @param sellersWhoSoldMostProductsView the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SellersWhoSoldMostProductsView> partialUpdate(SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView) {
        log.debug("Request to partially update SellersWhoSoldMostProductsView : {}", sellersWhoSoldMostProductsView);

        return sellersWhoSoldMostProductsViewRepository
            .findById(sellersWhoSoldMostProductsView.getId())
            .map(existingSellersWhoSoldMostProductsView -> {
                if (sellersWhoSoldMostProductsView.getQuantity() != null) {
                    existingSellersWhoSoldMostProductsView.setQuantity(sellersWhoSoldMostProductsView.getQuantity());
                }
                if (sellersWhoSoldMostProductsView.getPosition() != null) {
                    existingSellersWhoSoldMostProductsView.setPosition(sellersWhoSoldMostProductsView.getPosition());
                }

                return existingSellersWhoSoldMostProductsView;
            })
            .map(sellersWhoSoldMostProductsViewRepository::save);
    }

    /**
     * Get all the sellersWhoSoldMostProductsViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SellersWhoSoldMostProductsView> findAll(Pageable pageable) {
        log.debug("Request to get all SellersWhoSoldMostProductsViews");
        return sellersWhoSoldMostProductsViewRepository.findAll(pageable);
    }

    /**
     * Get one sellersWhoSoldMostProductsView by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SellersWhoSoldMostProductsView> findOne(Long id) {
        log.debug("Request to get SellersWhoSoldMostProductsView : {}", id);
        return sellersWhoSoldMostProductsViewRepository.findById(id);
    }

    /**
     * Delete the sellersWhoSoldMostProductsView by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SellersWhoSoldMostProductsView : {}", id);
        sellersWhoSoldMostProductsViewRepository.deleteById(id);
    }
}

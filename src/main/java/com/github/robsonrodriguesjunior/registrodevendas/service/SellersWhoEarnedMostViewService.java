package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SellersWhoEarnedMostViewRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostView}.
 */
@Service
@Transactional
public class SellersWhoEarnedMostViewService {

    private final Logger log = LoggerFactory.getLogger(SellersWhoEarnedMostViewService.class);

    private final SellersWhoEarnedMostViewRepository sellersWhoEarnedMostViewRepository;

    public SellersWhoEarnedMostViewService(SellersWhoEarnedMostViewRepository sellersWhoEarnedMostViewRepository) {
        this.sellersWhoEarnedMostViewRepository = sellersWhoEarnedMostViewRepository;
    }

    /**
     * Save a sellersWhoEarnedMostView.
     *
     * @param sellersWhoEarnedMostView the entity to save.
     * @return the persisted entity.
     */
    public SellersWhoEarnedMostView save(SellersWhoEarnedMostView sellersWhoEarnedMostView) {
        log.debug("Request to save SellersWhoEarnedMostView : {}", sellersWhoEarnedMostView);
        return sellersWhoEarnedMostViewRepository.save(sellersWhoEarnedMostView);
    }

    /**
     * Update a sellersWhoEarnedMostView.
     *
     * @param sellersWhoEarnedMostView the entity to save.
     * @return the persisted entity.
     */
    public SellersWhoEarnedMostView update(SellersWhoEarnedMostView sellersWhoEarnedMostView) {
        log.debug("Request to update SellersWhoEarnedMostView : {}", sellersWhoEarnedMostView);
        return sellersWhoEarnedMostViewRepository.save(sellersWhoEarnedMostView);
    }

    /**
     * Partially update a sellersWhoEarnedMostView.
     *
     * @param sellersWhoEarnedMostView the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SellersWhoEarnedMostView> partialUpdate(SellersWhoEarnedMostView sellersWhoEarnedMostView) {
        log.debug("Request to partially update SellersWhoEarnedMostView : {}", sellersWhoEarnedMostView);

        return sellersWhoEarnedMostViewRepository
            .findById(sellersWhoEarnedMostView.getId())
            .map(existingSellersWhoEarnedMostView -> {
                if (sellersWhoEarnedMostView.getValue() != null) {
                    existingSellersWhoEarnedMostView.setValue(sellersWhoEarnedMostView.getValue());
                }
                if (sellersWhoEarnedMostView.getPosition() != null) {
                    existingSellersWhoEarnedMostView.setPosition(sellersWhoEarnedMostView.getPosition());
                }

                return existingSellersWhoEarnedMostView;
            })
            .map(sellersWhoEarnedMostViewRepository::save);
    }

    /**
     * Get all the sellersWhoEarnedMostViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SellersWhoEarnedMostView> findAll(Pageable pageable) {
        log.debug("Request to get all SellersWhoEarnedMostViews");
        return sellersWhoEarnedMostViewRepository.findAll(pageable);
    }

    /**
     * Get one sellersWhoEarnedMostView by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SellersWhoEarnedMostView> findOne(Long id) {
        log.debug("Request to get SellersWhoEarnedMostView : {}", id);
        return sellersWhoEarnedMostViewRepository.findById(id);
    }

    /**
     * Delete the sellersWhoEarnedMostView by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SellersWhoEarnedMostView : {}", id);
        sellersWhoEarnedMostViewRepository.deleteById(id);
    }
}

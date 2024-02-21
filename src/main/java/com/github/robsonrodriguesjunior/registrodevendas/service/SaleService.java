package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Sale;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SaleRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Sale}.
 */
@Service
@Transactional
public class SaleService {

    private final Logger log = LoggerFactory.getLogger(SaleService.class);

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    /**
     * Save a sale.
     *
     * @param sale the entity to save.
     * @return the persisted entity.
     */
    public Sale save(Sale sale) {
        log.debug("Request to save Sale : {}", sale);
        return saleRepository.save(sale);
    }

    /**
     * Update a sale.
     *
     * @param sale the entity to save.
     * @return the persisted entity.
     */
    public Sale update(Sale sale) {
        log.debug("Request to update Sale : {}", sale);
        return saleRepository.save(sale);
    }

    /**
     * Partially update a sale.
     *
     * @param sale the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Sale> partialUpdate(Sale sale) {
        log.debug("Request to partially update Sale : {}", sale);

        return saleRepository
            .findById(sale.getId())
            .map(existingSale -> {
                if (sale.getDate() != null) {
                    existingSale.setDate(sale.getDate());
                }

                return existingSale;
            })
            .map(saleRepository::save);
    }

    /**
     * Get all the sales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Sale> findAll(Pageable pageable) {
        log.debug("Request to get all Sales");
        return saleRepository.findAll(pageable);
    }

    /**
     * Get one sale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Sale> findOne(Long id) {
        log.debug("Request to get Sale : {}", id);
        return saleRepository.findById(id);
    }

    /**
     * Delete the sale by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sale : {}", id);
        saleRepository.deleteById(id);
    }
}

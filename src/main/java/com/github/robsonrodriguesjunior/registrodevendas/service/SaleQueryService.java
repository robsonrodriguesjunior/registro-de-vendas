package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.*; // for static metamodels
import com.github.robsonrodriguesjunior.registrodevendas.domain.Sale;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SaleRepository;
import com.github.robsonrodriguesjunior.registrodevendas.service.criteria.SaleCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Sale} entities in the database.
 * The main input is a {@link SaleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Sale} or a {@link Page} of {@link Sale} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SaleQueryService extends QueryService<Sale> {

    private final Logger log = LoggerFactory.getLogger(SaleQueryService.class);

    private final SaleRepository saleRepository;

    public SaleQueryService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    /**
     * Return a {@link List} of {@link Sale} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Sale> findByCriteria(SaleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Sale> specification = createSpecification(criteria);
        return saleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Sale} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Sale> findByCriteria(SaleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Sale> specification = createSpecification(criteria);
        return saleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SaleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Sale> specification = createSpecification(criteria);
        return saleRepository.count(specification);
    }

    /**
     * Function to convert {@link SaleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Sale> createSpecification(SaleCriteria criteria) {
        Specification<Sale> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Sale_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Sale_.date));
            }
            if (criteria.getClientId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClientId(), root -> root.join(Sale_.client, JoinType.LEFT).get(Client_.id))
                    );
            }
            if (criteria.getSellerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSellerId(), root -> root.join(Sale_.seller, JoinType.LEFT).get(Collaborator_.id))
                    );
            }
            if (criteria.getProductsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProductsId(), root -> root.join(Sale_.products, JoinType.LEFT).get(Product_.id))
                    );
            }
        }
        return specification;
    }
}

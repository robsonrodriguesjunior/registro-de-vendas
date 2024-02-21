package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.*; // for static metamodels
import com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.TopSellingProductsViewRepository;
import com.github.robsonrodriguesjunior.registrodevendas.service.criteria.TopSellingProductsViewCriteria;
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
 * Service for executing complex queries for {@link TopSellingProductsView} entities in the database.
 * The main input is a {@link TopSellingProductsViewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TopSellingProductsView} or a {@link Page} of {@link TopSellingProductsView} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TopSellingProductsViewQueryService extends QueryService<TopSellingProductsView> {

    private final Logger log = LoggerFactory.getLogger(TopSellingProductsViewQueryService.class);

    private final TopSellingProductsViewRepository topSellingProductsViewRepository;

    public TopSellingProductsViewQueryService(TopSellingProductsViewRepository topSellingProductsViewRepository) {
        this.topSellingProductsViewRepository = topSellingProductsViewRepository;
    }

    /**
     * Return a {@link List} of {@link TopSellingProductsView} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TopSellingProductsView> findByCriteria(TopSellingProductsViewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TopSellingProductsView> specification = createSpecification(criteria);
        return topSellingProductsViewRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TopSellingProductsView} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TopSellingProductsView> findByCriteria(TopSellingProductsViewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TopSellingProductsView> specification = createSpecification(criteria);
        return topSellingProductsViewRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TopSellingProductsViewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TopSellingProductsView> specification = createSpecification(criteria);
        return topSellingProductsViewRepository.count(specification);
    }

    /**
     * Function to convert {@link TopSellingProductsViewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TopSellingProductsView> createSpecification(TopSellingProductsViewCriteria criteria) {
        Specification<TopSellingProductsView> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TopSellingProductsView_.id));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), TopSellingProductsView_.quantity));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPosition(), TopSellingProductsView_.position));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(TopSellingProductsView_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.*; // for static metamodels
import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SellersWhoSoldMostProductsViewRepository;
import com.github.robsonrodriguesjunior.registrodevendas.service.criteria.SellersWhoSoldMostProductsViewCriteria;
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
 * Service for executing complex queries for {@link SellersWhoSoldMostProductsView} entities in the database.
 * The main input is a {@link SellersWhoSoldMostProductsViewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SellersWhoSoldMostProductsView} or a {@link Page} of {@link SellersWhoSoldMostProductsView} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SellersWhoSoldMostProductsViewQueryService extends QueryService<SellersWhoSoldMostProductsView> {

    private final Logger log = LoggerFactory.getLogger(SellersWhoSoldMostProductsViewQueryService.class);

    private final SellersWhoSoldMostProductsViewRepository sellersWhoSoldMostProductsViewRepository;

    public SellersWhoSoldMostProductsViewQueryService(SellersWhoSoldMostProductsViewRepository sellersWhoSoldMostProductsViewRepository) {
        this.sellersWhoSoldMostProductsViewRepository = sellersWhoSoldMostProductsViewRepository;
    }

    /**
     * Return a {@link List} of {@link SellersWhoSoldMostProductsView} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SellersWhoSoldMostProductsView> findByCriteria(SellersWhoSoldMostProductsViewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SellersWhoSoldMostProductsView> specification = createSpecification(criteria);
        return sellersWhoSoldMostProductsViewRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SellersWhoSoldMostProductsView} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SellersWhoSoldMostProductsView> findByCriteria(SellersWhoSoldMostProductsViewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SellersWhoSoldMostProductsView> specification = createSpecification(criteria);
        return sellersWhoSoldMostProductsViewRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SellersWhoSoldMostProductsViewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SellersWhoSoldMostProductsView> specification = createSpecification(criteria);
        return sellersWhoSoldMostProductsViewRepository.count(specification);
    }

    /**
     * Function to convert {@link SellersWhoSoldMostProductsViewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SellersWhoSoldMostProductsView> createSpecification(SellersWhoSoldMostProductsViewCriteria criteria) {
        Specification<SellersWhoSoldMostProductsView> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SellersWhoSoldMostProductsView_.id));
            }
            if (criteria.getQuantity() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQuantity(), SellersWhoSoldMostProductsView_.quantity));
            }
            if (criteria.getPosition() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPosition(), SellersWhoSoldMostProductsView_.position));
            }
            if (criteria.getSellerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSellerId(),
                            root -> root.join(SellersWhoSoldMostProductsView_.seller, JoinType.LEFT).get(Collaborator_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

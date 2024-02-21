package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.*; // for static metamodels
import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SellersWhoEarnedMostViewRepository;
import com.github.robsonrodriguesjunior.registrodevendas.service.criteria.SellersWhoEarnedMostViewCriteria;
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
 * Service for executing complex queries for {@link SellersWhoEarnedMostView} entities in the database.
 * The main input is a {@link SellersWhoEarnedMostViewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SellersWhoEarnedMostView} or a {@link Page} of {@link SellersWhoEarnedMostView} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SellersWhoEarnedMostViewQueryService extends QueryService<SellersWhoEarnedMostView> {

    private final Logger log = LoggerFactory.getLogger(SellersWhoEarnedMostViewQueryService.class);

    private final SellersWhoEarnedMostViewRepository sellersWhoEarnedMostViewRepository;

    public SellersWhoEarnedMostViewQueryService(SellersWhoEarnedMostViewRepository sellersWhoEarnedMostViewRepository) {
        this.sellersWhoEarnedMostViewRepository = sellersWhoEarnedMostViewRepository;
    }

    /**
     * Return a {@link List} of {@link SellersWhoEarnedMostView} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SellersWhoEarnedMostView> findByCriteria(SellersWhoEarnedMostViewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SellersWhoEarnedMostView> specification = createSpecification(criteria);
        return sellersWhoEarnedMostViewRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SellersWhoEarnedMostView} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SellersWhoEarnedMostView> findByCriteria(SellersWhoEarnedMostViewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SellersWhoEarnedMostView> specification = createSpecification(criteria);
        return sellersWhoEarnedMostViewRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SellersWhoEarnedMostViewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SellersWhoEarnedMostView> specification = createSpecification(criteria);
        return sellersWhoEarnedMostViewRepository.count(specification);
    }

    /**
     * Function to convert {@link SellersWhoEarnedMostViewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SellersWhoEarnedMostView> createSpecification(SellersWhoEarnedMostViewCriteria criteria) {
        Specification<SellersWhoEarnedMostView> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SellersWhoEarnedMostView_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), SellersWhoEarnedMostView_.value));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPosition(), SellersWhoEarnedMostView_.position));
            }
            if (criteria.getSellerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSellerId(),
                            root -> root.join(SellersWhoEarnedMostView_.seller, JoinType.LEFT).get(Collaborator_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

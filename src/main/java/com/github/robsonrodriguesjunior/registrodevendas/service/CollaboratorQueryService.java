package com.github.robsonrodriguesjunior.registrodevendas.service;

// for static metamodels
import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator_;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Person_;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Sale_;
import com.github.robsonrodriguesjunior.registrodevendas.repository.CollaboratorRepository;
import com.github.robsonrodriguesjunior.registrodevendas.service.criteria.CollaboratorCriteria;
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
 * Service for executing complex queries for {@link Collaborator} entities in
 * the database.
 * The main input is a {@link CollaboratorCriteria} which gets converted to
 * {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Collaborator} or a {@link Page} of
 * {@link Collaborator} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CollaboratorQueryService extends QueryService<Collaborator> {

    private final Logger log = LoggerFactory.getLogger(CollaboratorQueryService.class);

    private final CollaboratorRepository collaboratorRepository;

    public CollaboratorQueryService(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    /**
     * Return a {@link List} of {@link Collaborator} which matches the criteria from
     * the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Collaborator> findByCriteria(CollaboratorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Collaborator> specification = createSpecification(criteria);
        return collaboratorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Collaborator} which matches the criteria from
     * the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Collaborator> findByCriteria(CollaboratorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Collaborator> specification = createSpecification(criteria);
        return collaboratorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CollaboratorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Collaborator> specification = createSpecification(criteria);
        return collaboratorRepository.count(specification);
    }

    /**
     * Function to convert {@link CollaboratorCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Collaborator> createSpecification(CollaboratorCriteria criteria) {
        Specification<Collaborator> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Collaborator_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Collaborator_.code));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Collaborator_.type));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Collaborator_.status));
            }
            if (criteria.getPersonId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPersonId(), root -> root.join(Collaborator_.person, JoinType.LEFT).get(Person_.id))
                    );
            }
            if (criteria.getSalesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSalesId(), root -> root.join(Collaborator_.sales, JoinType.LEFT).get(Sale_.id))
                    );
            }
        }
        return specification;
    }
}

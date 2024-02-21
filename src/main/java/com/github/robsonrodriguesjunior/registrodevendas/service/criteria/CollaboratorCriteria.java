package com.github.robsonrodriguesjunior.registrodevendas.service.criteria;

import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorStatus;
import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator} entity. This class is used
 * in {@link com.github.robsonrodriguesjunior.registrodevendas.web.rest.CollaboratorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /collaborators?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CollaboratorCriteria implements Serializable, Criteria {

    /**
     * Class for filtering CollaboratorType
     */
    public static class CollaboratorTypeFilter extends Filter<CollaboratorType> {

        public CollaboratorTypeFilter() {}

        public CollaboratorTypeFilter(CollaboratorTypeFilter filter) {
            super(filter);
        }

        @Override
        public CollaboratorTypeFilter copy() {
            return new CollaboratorTypeFilter(this);
        }
    }

    /**
     * Class for filtering CollaboratorStatus
     */
    public static class CollaboratorStatusFilter extends Filter<CollaboratorStatus> {

        public CollaboratorStatusFilter() {}

        public CollaboratorStatusFilter(CollaboratorStatusFilter filter) {
            super(filter);
        }

        @Override
        public CollaboratorStatusFilter copy() {
            return new CollaboratorStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private CollaboratorTypeFilter type;

    private CollaboratorStatusFilter status;

    private LongFilter personId;

    private LongFilter salesId;

    private LongFilter sellersWhoEarnedMostViewId;

    private LongFilter sellersWhoSoldMostProductsViewId;

    private Boolean distinct;

    public CollaboratorCriteria() {}

    public CollaboratorCriteria(CollaboratorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
        this.salesId = other.salesId == null ? null : other.salesId.copy();
        this.sellersWhoEarnedMostViewId = other.sellersWhoEarnedMostViewId == null ? null : other.sellersWhoEarnedMostViewId.copy();
        this.sellersWhoSoldMostProductsViewId =
            other.sellersWhoSoldMostProductsViewId == null ? null : other.sellersWhoSoldMostProductsViewId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CollaboratorCriteria copy() {
        return new CollaboratorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public CollaboratorTypeFilter getType() {
        return type;
    }

    public CollaboratorTypeFilter type() {
        if (type == null) {
            type = new CollaboratorTypeFilter();
        }
        return type;
    }

    public void setType(CollaboratorTypeFilter type) {
        this.type = type;
    }

    public CollaboratorStatusFilter getStatus() {
        return status;
    }

    public CollaboratorStatusFilter status() {
        if (status == null) {
            status = new CollaboratorStatusFilter();
        }
        return status;
    }

    public void setStatus(CollaboratorStatusFilter status) {
        this.status = status;
    }

    public LongFilter getPersonId() {
        return personId;
    }

    public LongFilter personId() {
        if (personId == null) {
            personId = new LongFilter();
        }
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }

    public LongFilter getSalesId() {
        return salesId;
    }

    public LongFilter salesId() {
        if (salesId == null) {
            salesId = new LongFilter();
        }
        return salesId;
    }

    public void setSalesId(LongFilter salesId) {
        this.salesId = salesId;
    }

    public LongFilter getSellersWhoEarnedMostViewId() {
        return sellersWhoEarnedMostViewId;
    }

    public LongFilter sellersWhoEarnedMostViewId() {
        if (sellersWhoEarnedMostViewId == null) {
            sellersWhoEarnedMostViewId = new LongFilter();
        }
        return sellersWhoEarnedMostViewId;
    }

    public void setSellersWhoEarnedMostViewId(LongFilter sellersWhoEarnedMostViewId) {
        this.sellersWhoEarnedMostViewId = sellersWhoEarnedMostViewId;
    }

    public LongFilter getSellersWhoSoldMostProductsViewId() {
        return sellersWhoSoldMostProductsViewId;
    }

    public LongFilter sellersWhoSoldMostProductsViewId() {
        if (sellersWhoSoldMostProductsViewId == null) {
            sellersWhoSoldMostProductsViewId = new LongFilter();
        }
        return sellersWhoSoldMostProductsViewId;
    }

    public void setSellersWhoSoldMostProductsViewId(LongFilter sellersWhoSoldMostProductsViewId) {
        this.sellersWhoSoldMostProductsViewId = sellersWhoSoldMostProductsViewId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CollaboratorCriteria that = (CollaboratorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(type, that.type) &&
            Objects.equals(status, that.status) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(salesId, that.salesId) &&
            Objects.equals(sellersWhoEarnedMostViewId, that.sellersWhoEarnedMostViewId) &&
            Objects.equals(sellersWhoSoldMostProductsViewId, that.sellersWhoSoldMostProductsViewId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            type,
            status,
            personId,
            salesId,
            sellersWhoEarnedMostViewId,
            sellersWhoSoldMostProductsViewId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollaboratorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (personId != null ? "personId=" + personId + ", " : "") +
            (salesId != null ? "salesId=" + salesId + ", " : "") +
            (sellersWhoEarnedMostViewId != null ? "sellersWhoEarnedMostViewId=" + sellersWhoEarnedMostViewId + ", " : "") +
            (sellersWhoSoldMostProductsViewId != null ? "sellersWhoSoldMostProductsViewId=" + sellersWhoSoldMostProductsViewId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

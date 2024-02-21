package com.github.robsonrodriguesjunior.registrodevendas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsView} entity. This class is used
 * in {@link com.github.robsonrodriguesjunior.registrodevendas.web.rest.SellersWhoSoldMostProductsViewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sellers-who-sold-most-products-views?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SellersWhoSoldMostProductsViewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter quantity;

    private LongFilter position;

    private LongFilter sellerId;

    private Boolean distinct;

    public SellersWhoSoldMostProductsViewCriteria() {}

    public SellersWhoSoldMostProductsViewCriteria(SellersWhoSoldMostProductsViewCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.sellerId = other.sellerId == null ? null : other.sellerId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SellersWhoSoldMostProductsViewCriteria copy() {
        return new SellersWhoSoldMostProductsViewCriteria(this);
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

    public LongFilter getQuantity() {
        return quantity;
    }

    public LongFilter quantity() {
        if (quantity == null) {
            quantity = new LongFilter();
        }
        return quantity;
    }

    public void setQuantity(LongFilter quantity) {
        this.quantity = quantity;
    }

    public LongFilter getPosition() {
        return position;
    }

    public LongFilter position() {
        if (position == null) {
            position = new LongFilter();
        }
        return position;
    }

    public void setPosition(LongFilter position) {
        this.position = position;
    }

    public LongFilter getSellerId() {
        return sellerId;
    }

    public LongFilter sellerId() {
        if (sellerId == null) {
            sellerId = new LongFilter();
        }
        return sellerId;
    }

    public void setSellerId(LongFilter sellerId) {
        this.sellerId = sellerId;
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
        final SellersWhoSoldMostProductsViewCriteria that = (SellersWhoSoldMostProductsViewCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(position, that.position) &&
            Objects.equals(sellerId, that.sellerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, position, sellerId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SellersWhoSoldMostProductsViewCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (position != null ? "position=" + position + ", " : "") +
            (sellerId != null ? "sellerId=" + sellerId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

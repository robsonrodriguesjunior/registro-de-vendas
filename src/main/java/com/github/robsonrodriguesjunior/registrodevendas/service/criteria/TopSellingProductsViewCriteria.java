package com.github.robsonrodriguesjunior.registrodevendas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsView} entity. This class is used
 * in {@link com.github.robsonrodriguesjunior.registrodevendas.web.rest.TopSellingProductsViewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /top-selling-products-views?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TopSellingProductsViewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter quantity;

    private LongFilter position;

    private LongFilter productId;

    private Boolean distinct;

    public TopSellingProductsViewCriteria() {}

    public TopSellingProductsViewCriteria(TopSellingProductsViewCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TopSellingProductsViewCriteria copy() {
        return new TopSellingProductsViewCriteria(this);
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

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
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
        final TopSellingProductsViewCriteria that = (TopSellingProductsViewCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(position, that.position) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, position, productId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopSellingProductsViewCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (position != null ? "position=" + position + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

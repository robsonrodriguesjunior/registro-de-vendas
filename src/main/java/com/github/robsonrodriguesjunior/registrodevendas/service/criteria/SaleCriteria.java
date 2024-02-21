package com.github.robsonrodriguesjunior.registrodevendas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Sale} entity. This class is used
 * in {@link com.github.robsonrodriguesjunior.registrodevendas.web.rest.SaleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sales?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter date;

    private LongFilter clientId;

    private LongFilter sellerId;

    private LongFilter productsId;

    private Boolean distinct;

    public SaleCriteria() {}

    public SaleCriteria(SaleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.sellerId = other.sellerId == null ? null : other.sellerId.copy();
        this.productsId = other.productsId == null ? null : other.productsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SaleCriteria copy() {
        return new SaleCriteria(this);
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

    public InstantFilter getDate() {
        return date;
    }

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public LongFilter clientId() {
        if (clientId == null) {
            clientId = new LongFilter();
        }
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
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

    public LongFilter getProductsId() {
        return productsId;
    }

    public LongFilter productsId() {
        if (productsId == null) {
            productsId = new LongFilter();
        }
        return productsId;
    }

    public void setProductsId(LongFilter productsId) {
        this.productsId = productsId;
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
        final SaleCriteria that = (SaleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(sellerId, that.sellerId) &&
            Objects.equals(productsId, that.productsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, clientId, sellerId, productsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (clientId != null ? "clientId=" + clientId + ", " : "") +
            (sellerId != null ? "sellerId=" + sellerId + ", " : "") +
            (productsId != null ? "productsId=" + productsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

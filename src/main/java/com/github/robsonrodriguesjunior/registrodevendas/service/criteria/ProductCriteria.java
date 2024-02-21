package com.github.robsonrodriguesjunior.registrodevendas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Product} entity. This class is used
 * in {@link com.github.robsonrodriguesjunior.registrodevendas.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private LongFilter categoryId;

    private LongFilter salesId;

    private LongFilter topSellingProductsViewId;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.salesId = other.salesId == null ? null : other.salesId.copy();
        this.topSellingProductsViewId = other.topSellingProductsViewId == null ? null : other.topSellingProductsViewId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            categoryId = new LongFilter();
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
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

    public LongFilter getTopSellingProductsViewId() {
        return topSellingProductsViewId;
    }

    public LongFilter topSellingProductsViewId() {
        if (topSellingProductsViewId == null) {
            topSellingProductsViewId = new LongFilter();
        }
        return topSellingProductsViewId;
    }

    public void setTopSellingProductsViewId(LongFilter topSellingProductsViewId) {
        this.topSellingProductsViewId = topSellingProductsViewId;
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
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(salesId, that.salesId) &&
            Objects.equals(topSellingProductsViewId, that.topSellingProductsViewId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, categoryId, salesId, topSellingProductsViewId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (salesId != null ? "salesId=" + salesId + ", " : "") +
            (topSellingProductsViewId != null ? "topSellingProductsViewId=" + topSellingProductsViewId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

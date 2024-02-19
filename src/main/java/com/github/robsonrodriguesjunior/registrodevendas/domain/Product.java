package com.github.robsonrodriguesjunior.registrodevendas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "code", length = 255, nullable = false)
    private String code;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_product__sales",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "sales_id")
    )
    @JsonIgnoreProperties(value = { "client", "seller", "products" }, allowSetters = true)
    private Set<Sale> sales = new HashSet<>();

    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "product")
    private TopSellingProductsView topSellingProductsView;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Product code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    public Product sales(Set<Sale> sales) {
        this.setSales(sales);
        return this;
    }

    public Product addSales(Sale sale) {
        this.sales.add(sale);
        return this;
    }

    public Product removeSales(Sale sale) {
        this.sales.remove(sale);
        return this;
    }

    public TopSellingProductsView getTopSellingProductsView() {
        return this.topSellingProductsView;
    }

    public void setTopSellingProductsView(TopSellingProductsView topSellingProductsView) {
        if (this.topSellingProductsView != null) {
            this.topSellingProductsView.setProduct(null);
        }
        if (topSellingProductsView != null) {
            topSellingProductsView.setProduct(this);
        }
        this.topSellingProductsView = topSellingProductsView;
    }

    public Product topSellingProductsView(TopSellingProductsView topSellingProductsView) {
        this.setTopSellingProductsView(topSellingProductsView);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}

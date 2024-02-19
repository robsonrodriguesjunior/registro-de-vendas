package com.github.robsonrodriguesjunior.registrodevendas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sale.
 */
@Entity
@Table(name = "sale")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "person", "buys" }, allowSetters = true)
    private Client client;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "person", "sales", "sellersWhoEarnedMostView", "sellersWhoSoldMostProductsView" }, allowSetters = true)
    private Collaborator seller;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "sales")
    @JsonIgnoreProperties(value = { "category", "sales", "topSellingProductsView" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Sale date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Sale client(Client client) {
        this.setClient(client);
        return this;
    }

    public Collaborator getSeller() {
        return this.seller;
    }

    public void setSeller(Collaborator collaborator) {
        this.seller = collaborator;
    }

    public Sale seller(Collaborator collaborator) {
        this.setSeller(collaborator);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.removeSales(this));
        }
        if (products != null) {
            products.forEach(i -> i.addSales(this));
        }
        this.products = products;
    }

    public Sale products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Sale addProducts(Product product) {
        this.products.add(product);
        product.getSales().add(this);
        return this;
    }

    public Sale removeProducts(Product product) {
        this.products.remove(product);
        product.getSales().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }
        return getId() != null && getId().equals(((Sale) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sale{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}

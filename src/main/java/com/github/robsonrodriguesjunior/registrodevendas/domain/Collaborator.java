package com.github.robsonrodriguesjunior.registrodevendas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorStatus;
import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Collaborator.
 */
@Entity
@Table(name = "collaborator")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Collaborator implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CollaboratorType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CollaboratorStatus status;

    @JsonIgnoreProperties(value = { "client", "seller" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Person person;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
    @JsonIgnoreProperties(value = { "client", "seller", "products" }, allowSetters = true)
    private Set<Sale> sales = new HashSet<>();

    @JsonIgnoreProperties(value = { "seller" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "seller")
    private SellersWhoEarnedMostView sellersWhoEarnedMostView;

    @JsonIgnoreProperties(value = { "seller" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "seller")
    private SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Collaborator id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Collaborator code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CollaboratorType getType() {
        return this.type;
    }

    public Collaborator type(CollaboratorType type) {
        this.setType(type);
        return this;
    }

    public void setType(CollaboratorType type) {
        this.type = type;
    }

    public CollaboratorStatus getStatus() {
        return this.status;
    }

    public Collaborator status(CollaboratorStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CollaboratorStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Collaborator person(Person person) {
        this.setPerson(person);
        return this;
    }

    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        if (this.sales != null) {
            this.sales.forEach(i -> i.setSeller(null));
        }
        if (sales != null) {
            sales.forEach(i -> i.setSeller(this));
        }
        this.sales = sales;
    }

    public Collaborator sales(Set<Sale> sales) {
        this.setSales(sales);
        return this;
    }

    public Collaborator addSales(Sale sale) {
        this.sales.add(sale);
        sale.setSeller(this);
        return this;
    }

    public Collaborator removeSales(Sale sale) {
        this.sales.remove(sale);
        sale.setSeller(null);
        return this;
    }

    public SellersWhoEarnedMostView getSellersWhoEarnedMostView() {
        return this.sellersWhoEarnedMostView;
    }

    public void setSellersWhoEarnedMostView(SellersWhoEarnedMostView sellersWhoEarnedMostView) {
        if (this.sellersWhoEarnedMostView != null) {
            this.sellersWhoEarnedMostView.setSeller(null);
        }
        if (sellersWhoEarnedMostView != null) {
            sellersWhoEarnedMostView.setSeller(this);
        }
        this.sellersWhoEarnedMostView = sellersWhoEarnedMostView;
    }

    public Collaborator sellersWhoEarnedMostView(SellersWhoEarnedMostView sellersWhoEarnedMostView) {
        this.setSellersWhoEarnedMostView(sellersWhoEarnedMostView);
        return this;
    }

    public SellersWhoSoldMostProductsView getSellersWhoSoldMostProductsView() {
        return this.sellersWhoSoldMostProductsView;
    }

    public void setSellersWhoSoldMostProductsView(SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView) {
        if (this.sellersWhoSoldMostProductsView != null) {
            this.sellersWhoSoldMostProductsView.setSeller(null);
        }
        if (sellersWhoSoldMostProductsView != null) {
            sellersWhoSoldMostProductsView.setSeller(this);
        }
        this.sellersWhoSoldMostProductsView = sellersWhoSoldMostProductsView;
    }

    public Collaborator sellersWhoSoldMostProductsView(SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView) {
        this.setSellersWhoSoldMostProductsView(sellersWhoSoldMostProductsView);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collaborator)) {
            return false;
        }
        return getId() != null && getId().equals(((Collaborator) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collaborator{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

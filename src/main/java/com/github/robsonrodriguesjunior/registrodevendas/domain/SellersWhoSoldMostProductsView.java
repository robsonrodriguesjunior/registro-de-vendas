package com.github.robsonrodriguesjunior.registrodevendas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A SellersWhoSoldMostProductsView.
 */
@Entity
@Table(name = "sellers_who_sold_most_products_view")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SellersWhoSoldMostProductsView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "position")
    private Long position;

    @JsonIgnoreProperties(value = { "person", "sales", "sellersWhoEarnedMostView", "sellersWhoSoldMostProductsView" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Collaborator seller;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SellersWhoSoldMostProductsView id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public SellersWhoSoldMostProductsView quantity(Long quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPosition() {
        return this.position;
    }

    public SellersWhoSoldMostProductsView position(Long position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Collaborator getSeller() {
        return this.seller;
    }

    public void setSeller(Collaborator collaborator) {
        this.seller = collaborator;
    }

    public SellersWhoSoldMostProductsView seller(Collaborator collaborator) {
        this.setSeller(collaborator);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SellersWhoSoldMostProductsView)) {
            return false;
        }
        return getId() != null && getId().equals(((SellersWhoSoldMostProductsView) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SellersWhoSoldMostProductsView{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", position=" + getPosition() +
            "}";
    }
}

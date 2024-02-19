package com.github.robsonrodriguesjunior.registrodevendas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A SellersWhoEarnedMostView.
 */
@Entity
@Table(name = "sellers_who_earned_most_view")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SellersWhoEarnedMostView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "value", precision = 21, scale = 2)
    private BigDecimal value;

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

    public SellersWhoEarnedMostView id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public SellersWhoEarnedMostView value(BigDecimal value) {
        this.setValue(value);
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getPosition() {
        return this.position;
    }

    public SellersWhoEarnedMostView position(Long position) {
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

    public SellersWhoEarnedMostView seller(Collaborator collaborator) {
        this.setSeller(collaborator);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SellersWhoEarnedMostView)) {
            return false;
        }
        return getId() != null && getId().equals(((SellersWhoEarnedMostView) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SellersWhoEarnedMostView{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", position=" + getPosition() +
            "}";
    }
}

package com.github.robsonrodriguesjunior.registrodevendas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "second_name", length = 255, nullable = false)
    private String secondName;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @JsonIgnoreProperties(value = { "person", "buys" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
    private Client client;

    @JsonIgnoreProperties(value = { "person", "sales", "sellersWhoEarnedMostView", "sellersWhoSoldMostProductsView" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
    private Collaborator seller;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Person firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public Person secondName(String secondName) {
        this.setSecondName(secondName);
        return this;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Person birthday(LocalDate birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        if (this.client != null) {
            this.client.setPerson(null);
        }
        if (client != null) {
            client.setPerson(this);
        }
        this.client = client;
    }

    public Person client(Client client) {
        this.setClient(client);
        return this;
    }

    public Collaborator getSeller() {
        return this.seller;
    }

    public void setSeller(Collaborator collaborator) {
        if (this.seller != null) {
            this.seller.setPerson(null);
        }
        if (collaborator != null) {
            collaborator.setPerson(this);
        }
        this.seller = collaborator;
    }

    public Person seller(Collaborator collaborator) {
        this.setSeller(collaborator);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return getId() != null && getId().equals(((Person) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", birthday='" + getBirthday() + "'" +
            "}";
    }
}

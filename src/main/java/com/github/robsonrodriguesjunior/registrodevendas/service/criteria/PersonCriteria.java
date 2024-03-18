package com.github.robsonrodriguesjunior.registrodevendas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the
 * {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Person}
 * entity. This class is used
 * in
 * {@link com.github.robsonrodriguesjunior.registrodevendas.web.rest.PersonResource}
 * to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /people?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class PersonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter secondName;

    private LocalDateFilter birthday;

    private StringFilter cpf;

    private LongFilter clientId;

    private LongFilter sellerId;

    private Boolean distinct;

    public PersonCriteria() {}

    public PersonCriteria(PersonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.secondName = other.secondName == null ? null : other.secondName.copy();
        this.birthday = other.birthday == null ? null : other.birthday.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.sellerId = other.sellerId == null ? null : other.sellerId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PersonCriteria copy() {
        return new PersonCriteria(this);
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getSecondName() {
        return secondName;
    }

    public StringFilter secondName() {
        if (secondName == null) {
            secondName = new StringFilter();
        }
        return secondName;
    }

    public void setSecondName(StringFilter secondName) {
        this.secondName = secondName;
    }

    public LocalDateFilter getBirthday() {
        return birthday;
    }

    public LocalDateFilter birthday() {
        if (birthday == null) {
            birthday = new LocalDateFilter();
        }
        return birthday;
    }

    public void setBirthday(LocalDateFilter birthday) {
        this.birthday = birthday;
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
        final PersonCriteria that = (PersonCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(secondName, that.secondName) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(sellerId, that.sellerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, birthday, clientId, sellerId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (secondName != null ? "secondName=" + secondName + ", " : "") +
                (birthday != null ? "birthday=" + birthday + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (sellerId != null ? "sellerId=" + sellerId + ", " : "") +
                (distinct != null ? "distinct=" + distinct + ", " : "") +
                "}";
    }
}

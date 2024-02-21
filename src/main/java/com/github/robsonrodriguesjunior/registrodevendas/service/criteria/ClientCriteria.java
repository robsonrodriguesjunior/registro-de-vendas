package com.github.robsonrodriguesjunior.registrodevendas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Client} entity. This class is used
 * in {@link com.github.robsonrodriguesjunior.registrodevendas.web.rest.ClientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private LongFilter personId;

    private LongFilter buysId;

    private Boolean distinct;

    public ClientCriteria() {}

    public ClientCriteria(ClientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
        this.buysId = other.buysId == null ? null : other.buysId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClientCriteria copy() {
        return new ClientCriteria(this);
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

    public LongFilter getPersonId() {
        return personId;
    }

    public LongFilter personId() {
        if (personId == null) {
            personId = new LongFilter();
        }
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }

    public LongFilter getBuysId() {
        return buysId;
    }

    public LongFilter buysId() {
        if (buysId == null) {
            buysId = new LongFilter();
        }
        return buysId;
    }

    public void setBuysId(LongFilter buysId) {
        this.buysId = buysId;
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
        final ClientCriteria that = (ClientCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(buysId, that.buysId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, personId, buysId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (personId != null ? "personId=" + personId + ", " : "") +
            (buysId != null ? "buysId=" + buysId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

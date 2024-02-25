package com.github.robsonrodriguesjunior.registrodevendas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class Client implements Serializable {

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

    @JsonIgnoreProperties(value = { "client", "seller" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Person person;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @JsonIgnoreProperties(value = { "client", "seller", "products" }, allowSetters = true)
    private Set<Sale> buys = new HashSet<>();
}

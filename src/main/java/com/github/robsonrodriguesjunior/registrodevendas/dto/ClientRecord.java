package com.github.robsonrodriguesjunior.registrodevendas.dto;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Client;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Person;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Optional;
import org.hibernate.validator.constraints.br.CPF;

public record ClientRecord(
    Long id,
    @NotNull @NotBlank @Size(max = 255) String code,
    @NotNull @NotBlank @Size(max = 255) String firstName,
    @NotNull @NotBlank @Size(max = 255) String secondName,
    @Nonnull LocalDate birthday,
    @NotNull @NotBlank @CPF String cpf
) {
    public static ClientRecord of(@Nonnull Client client) {
        final var id = client.getId();
        final var code = client.getCode();
        final var firstName = Optional.ofNullable(client.getPerson()).map(Person::getFirstName).orElse("");
        final var secondName = Optional.ofNullable(client.getPerson()).map(Person::getSecondName).orElse("");
        final var birthday = Optional.ofNullable(client.getPerson()).map(Person::getBirthday).orElse(LocalDate.now());
        final var cpf = Optional.ofNullable(client.getPerson()).map(Person::getCpf).orElse("kspskps");

        return new ClientRecord(id, code, firstName, secondName, birthday, cpf);
    }
}

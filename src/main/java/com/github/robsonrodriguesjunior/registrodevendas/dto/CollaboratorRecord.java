package com.github.robsonrodriguesjunior.registrodevendas.dto;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Person;
import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorStatus;
import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorType;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Optional;
import org.hibernate.validator.constraints.br.CPF;

public record CollaboratorRecord(
    Long id,
    @NotNull @NotBlank @Size(max = 255) String code,
    @NotNull CollaboratorType type,
    @NotNull CollaboratorStatus status,
    @NotNull @NotBlank @Size(max = 255) String firstName,
    @NotNull @NotBlank @Size(max = 255) String secondName,
    @NotNull LocalDate birthday,
    @NotNull @NotBlank @CPF String cpf
) {
    public static CollaboratorRecord of(@Nonnull Collaborator collaborator) {
        final var id = collaborator.getId();
        final var code = collaborator.getCode();
        final var type = collaborator.getType();
        final var status = collaborator.getStatus();
        final var firstName = Optional.ofNullable(collaborator.getPerson()).map(Person::getFirstName).orElse(null);
        final var secondName = Optional.ofNullable(collaborator.getPerson()).map(Person::getSecondName).orElse(null);
        final var birthday = Optional.ofNullable(collaborator.getPerson()).map(Person::getBirthday).orElse(null);
        final var cpf = Optional.ofNullable(collaborator.getPerson()).map(Person::getCpf).orElse(null);

        return new CollaboratorRecord(id, code, type, status, firstName, secondName, birthday, cpf);
    }
}

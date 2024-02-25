package com.github.robsonrodriguesjunior.registrodevendas.dto;

import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorStatus;
import com.github.robsonrodriguesjunior.registrodevendas.domain.enumeration.CollaboratorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CollaboratorRecord(
    Long id,
    @NotNull @NotBlank @Size(max = 255) String code,
    @NotNull CollaboratorType type,
    @NotNull CollaboratorStatus status,
    @NotNull @NotBlank @Size(max = 255) String firstName,
    @NotNull @NotBlank @Size(max = 255) String secondName,
    @NotNull LocalDate birthday
) {}

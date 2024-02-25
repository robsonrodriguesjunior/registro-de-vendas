package com.github.robsonrodriguesjunior.registrodevendas.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ClientRecord(
    Long id,
    @NotNull @NotBlank @Size(max = 255) String code,
    @NotNull @NotBlank @Size(max = 255) String firstName,
    @NotNull @NotBlank @Size(max = 255) String secondName,
    @Nonnull LocalDate birthday
) {}

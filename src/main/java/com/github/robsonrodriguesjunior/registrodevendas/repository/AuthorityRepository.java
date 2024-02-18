package com.github.robsonrodriguesjunior.registrodevendas.repository;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}

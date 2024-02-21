package com.github.robsonrodriguesjunior.registrodevendas.repository;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Collaborator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long>, JpaSpecificationExecutor<Collaborator> {}

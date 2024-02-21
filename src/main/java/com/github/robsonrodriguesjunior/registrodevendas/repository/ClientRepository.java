package com.github.robsonrodriguesjunior.registrodevendas.repository;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Client;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {}

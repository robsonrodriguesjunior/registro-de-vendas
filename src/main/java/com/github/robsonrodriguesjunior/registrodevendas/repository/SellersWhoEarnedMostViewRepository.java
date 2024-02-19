package com.github.robsonrodriguesjunior.registrodevendas.repository;

import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SellersWhoEarnedMostView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SellersWhoEarnedMostViewRepository extends JpaRepository<SellersWhoEarnedMostView, Long> {}

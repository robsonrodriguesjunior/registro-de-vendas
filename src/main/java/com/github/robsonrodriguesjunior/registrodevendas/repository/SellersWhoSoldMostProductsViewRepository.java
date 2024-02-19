package com.github.robsonrodriguesjunior.registrodevendas.repository;

import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SellersWhoSoldMostProductsView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SellersWhoSoldMostProductsViewRepository extends JpaRepository<SellersWhoSoldMostProductsView, Long> {}

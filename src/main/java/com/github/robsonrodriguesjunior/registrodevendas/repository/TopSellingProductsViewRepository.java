package com.github.robsonrodriguesjunior.registrodevendas.repository;

import com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TopSellingProductsView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopSellingProductsViewRepository
    extends JpaRepository<TopSellingProductsView, Long>, JpaSpecificationExecutor<TopSellingProductsView> {}

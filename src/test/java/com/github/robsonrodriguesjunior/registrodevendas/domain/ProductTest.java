package com.github.robsonrodriguesjunior.registrodevendas.domain;

import static com.github.robsonrodriguesjunior.registrodevendas.domain.CategoryTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.ProductTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.SaleTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsViewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.robsonrodriguesjunior.registrodevendas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void categoryTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        product.setCategory(categoryBack);
        assertThat(product.getCategory()).isEqualTo(categoryBack);

        product.category(null);
        assertThat(product.getCategory()).isNull();
    }

    @Test
    void salesTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Sale saleBack = getSaleRandomSampleGenerator();

        product.addSales(saleBack);
        assertThat(product.getSales()).containsOnly(saleBack);

        product.removeSales(saleBack);
        assertThat(product.getSales()).doesNotContain(saleBack);

        product.sales(new HashSet<>(Set.of(saleBack)));
        assertThat(product.getSales()).containsOnly(saleBack);

        product.setSales(new HashSet<>());
        assertThat(product.getSales()).doesNotContain(saleBack);
    }

    @Test
    void topSellingProductsViewTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        TopSellingProductsView topSellingProductsViewBack = getTopSellingProductsViewRandomSampleGenerator();

        product.setTopSellingProductsView(topSellingProductsViewBack);
        assertThat(product.getTopSellingProductsView()).isEqualTo(topSellingProductsViewBack);
        assertThat(topSellingProductsViewBack.getProduct()).isEqualTo(product);

        product.topSellingProductsView(null);
        assertThat(product.getTopSellingProductsView()).isNull();
        assertThat(topSellingProductsViewBack.getProduct()).isNull();
    }
}

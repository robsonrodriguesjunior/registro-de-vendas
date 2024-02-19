package com.github.robsonrodriguesjunior.registrodevendas.domain;

import static com.github.robsonrodriguesjunior.registrodevendas.domain.ProductTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsViewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.robsonrodriguesjunior.registrodevendas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TopSellingProductsViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopSellingProductsView.class);
        TopSellingProductsView topSellingProductsView1 = getTopSellingProductsViewSample1();
        TopSellingProductsView topSellingProductsView2 = new TopSellingProductsView();
        assertThat(topSellingProductsView1).isNotEqualTo(topSellingProductsView2);

        topSellingProductsView2.setId(topSellingProductsView1.getId());
        assertThat(topSellingProductsView1).isEqualTo(topSellingProductsView2);

        topSellingProductsView2 = getTopSellingProductsViewSample2();
        assertThat(topSellingProductsView1).isNotEqualTo(topSellingProductsView2);
    }

    @Test
    void productTest() throws Exception {
        TopSellingProductsView topSellingProductsView = getTopSellingProductsViewRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        topSellingProductsView.setProduct(productBack);
        assertThat(topSellingProductsView.getProduct()).isEqualTo(productBack);

        topSellingProductsView.product(null);
        assertThat(topSellingProductsView.getProduct()).isNull();
    }
}

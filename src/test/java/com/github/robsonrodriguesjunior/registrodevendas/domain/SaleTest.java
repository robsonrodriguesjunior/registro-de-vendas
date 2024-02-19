package com.github.robsonrodriguesjunior.registrodevendas.domain;

import static com.github.robsonrodriguesjunior.registrodevendas.domain.ClientTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.CollaboratorTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.ProductTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.SaleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.robsonrodriguesjunior.registrodevendas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SaleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sale.class);
        Sale sale1 = getSaleSample1();
        Sale sale2 = new Sale();
        assertThat(sale1).isNotEqualTo(sale2);

        sale2.setId(sale1.getId());
        assertThat(sale1).isEqualTo(sale2);

        sale2 = getSaleSample2();
        assertThat(sale1).isNotEqualTo(sale2);
    }

    @Test
    void clientTest() throws Exception {
        Sale sale = getSaleRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        sale.setClient(clientBack);
        assertThat(sale.getClient()).isEqualTo(clientBack);

        sale.client(null);
        assertThat(sale.getClient()).isNull();
    }

    @Test
    void sellerTest() throws Exception {
        Sale sale = getSaleRandomSampleGenerator();
        Collaborator collaboratorBack = getCollaboratorRandomSampleGenerator();

        sale.setSeller(collaboratorBack);
        assertThat(sale.getSeller()).isEqualTo(collaboratorBack);

        sale.seller(null);
        assertThat(sale.getSeller()).isNull();
    }

    @Test
    void productsTest() throws Exception {
        Sale sale = getSaleRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        sale.addProducts(productBack);
        assertThat(sale.getProducts()).containsOnly(productBack);
        assertThat(productBack.getSales()).containsOnly(sale);

        sale.removeProducts(productBack);
        assertThat(sale.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getSales()).doesNotContain(sale);

        sale.products(new HashSet<>(Set.of(productBack)));
        assertThat(sale.getProducts()).containsOnly(productBack);
        assertThat(productBack.getSales()).containsOnly(sale);

        sale.setProducts(new HashSet<>());
        assertThat(sale.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getSales()).doesNotContain(sale);
    }
}

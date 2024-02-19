package com.github.robsonrodriguesjunior.registrodevendas.domain;

import static com.github.robsonrodriguesjunior.registrodevendas.domain.CollaboratorTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.PersonTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.SaleTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostViewTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsViewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.robsonrodriguesjunior.registrodevendas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CollaboratorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collaborator.class);
        Collaborator collaborator1 = getCollaboratorSample1();
        Collaborator collaborator2 = new Collaborator();
        assertThat(collaborator1).isNotEqualTo(collaborator2);

        collaborator2.setId(collaborator1.getId());
        assertThat(collaborator1).isEqualTo(collaborator2);

        collaborator2 = getCollaboratorSample2();
        assertThat(collaborator1).isNotEqualTo(collaborator2);
    }

    @Test
    void personTest() throws Exception {
        Collaborator collaborator = getCollaboratorRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        collaborator.setPerson(personBack);
        assertThat(collaborator.getPerson()).isEqualTo(personBack);

        collaborator.person(null);
        assertThat(collaborator.getPerson()).isNull();
    }

    @Test
    void salesTest() throws Exception {
        Collaborator collaborator = getCollaboratorRandomSampleGenerator();
        Sale saleBack = getSaleRandomSampleGenerator();

        collaborator.addSales(saleBack);
        assertThat(collaborator.getSales()).containsOnly(saleBack);
        assertThat(saleBack.getSeller()).isEqualTo(collaborator);

        collaborator.removeSales(saleBack);
        assertThat(collaborator.getSales()).doesNotContain(saleBack);
        assertThat(saleBack.getSeller()).isNull();

        collaborator.sales(new HashSet<>(Set.of(saleBack)));
        assertThat(collaborator.getSales()).containsOnly(saleBack);
        assertThat(saleBack.getSeller()).isEqualTo(collaborator);

        collaborator.setSales(new HashSet<>());
        assertThat(collaborator.getSales()).doesNotContain(saleBack);
        assertThat(saleBack.getSeller()).isNull();
    }

    @Test
    void sellersWhoEarnedMostViewTest() throws Exception {
        Collaborator collaborator = getCollaboratorRandomSampleGenerator();
        SellersWhoEarnedMostView sellersWhoEarnedMostViewBack = getSellersWhoEarnedMostViewRandomSampleGenerator();

        collaborator.setSellersWhoEarnedMostView(sellersWhoEarnedMostViewBack);
        assertThat(collaborator.getSellersWhoEarnedMostView()).isEqualTo(sellersWhoEarnedMostViewBack);
        assertThat(sellersWhoEarnedMostViewBack.getSeller()).isEqualTo(collaborator);

        collaborator.sellersWhoEarnedMostView(null);
        assertThat(collaborator.getSellersWhoEarnedMostView()).isNull();
        assertThat(sellersWhoEarnedMostViewBack.getSeller()).isNull();
    }

    @Test
    void sellersWhoSoldMostProductsViewTest() throws Exception {
        Collaborator collaborator = getCollaboratorRandomSampleGenerator();
        SellersWhoSoldMostProductsView sellersWhoSoldMostProductsViewBack = getSellersWhoSoldMostProductsViewRandomSampleGenerator();

        collaborator.setSellersWhoSoldMostProductsView(sellersWhoSoldMostProductsViewBack);
        assertThat(collaborator.getSellersWhoSoldMostProductsView()).isEqualTo(sellersWhoSoldMostProductsViewBack);
        assertThat(sellersWhoSoldMostProductsViewBack.getSeller()).isEqualTo(collaborator);

        collaborator.sellersWhoSoldMostProductsView(null);
        assertThat(collaborator.getSellersWhoSoldMostProductsView()).isNull();
        assertThat(sellersWhoSoldMostProductsViewBack.getSeller()).isNull();
    }
}

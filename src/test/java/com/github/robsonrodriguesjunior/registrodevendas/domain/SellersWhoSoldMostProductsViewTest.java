package com.github.robsonrodriguesjunior.registrodevendas.domain;

import static com.github.robsonrodriguesjunior.registrodevendas.domain.CollaboratorTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsViewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.robsonrodriguesjunior.registrodevendas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SellersWhoSoldMostProductsViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SellersWhoSoldMostProductsView.class);
        SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView1 = getSellersWhoSoldMostProductsViewSample1();
        SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView2 = new SellersWhoSoldMostProductsView();
        assertThat(sellersWhoSoldMostProductsView1).isNotEqualTo(sellersWhoSoldMostProductsView2);

        sellersWhoSoldMostProductsView2.setId(sellersWhoSoldMostProductsView1.getId());
        assertThat(sellersWhoSoldMostProductsView1).isEqualTo(sellersWhoSoldMostProductsView2);

        sellersWhoSoldMostProductsView2 = getSellersWhoSoldMostProductsViewSample2();
        assertThat(sellersWhoSoldMostProductsView1).isNotEqualTo(sellersWhoSoldMostProductsView2);
    }

    @Test
    void sellerTest() throws Exception {
        SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView = getSellersWhoSoldMostProductsViewRandomSampleGenerator();
        Collaborator collaboratorBack = getCollaboratorRandomSampleGenerator();

        sellersWhoSoldMostProductsView.setSeller(collaboratorBack);
        assertThat(sellersWhoSoldMostProductsView.getSeller()).isEqualTo(collaboratorBack);

        sellersWhoSoldMostProductsView.seller(null);
        assertThat(sellersWhoSoldMostProductsView.getSeller()).isNull();
    }
}

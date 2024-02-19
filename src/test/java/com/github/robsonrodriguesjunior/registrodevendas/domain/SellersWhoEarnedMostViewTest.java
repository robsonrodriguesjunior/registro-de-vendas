package com.github.robsonrodriguesjunior.registrodevendas.domain;

import static com.github.robsonrodriguesjunior.registrodevendas.domain.CollaboratorTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostViewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.robsonrodriguesjunior.registrodevendas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SellersWhoEarnedMostViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SellersWhoEarnedMostView.class);
        SellersWhoEarnedMostView sellersWhoEarnedMostView1 = getSellersWhoEarnedMostViewSample1();
        SellersWhoEarnedMostView sellersWhoEarnedMostView2 = new SellersWhoEarnedMostView();
        assertThat(sellersWhoEarnedMostView1).isNotEqualTo(sellersWhoEarnedMostView2);

        sellersWhoEarnedMostView2.setId(sellersWhoEarnedMostView1.getId());
        assertThat(sellersWhoEarnedMostView1).isEqualTo(sellersWhoEarnedMostView2);

        sellersWhoEarnedMostView2 = getSellersWhoEarnedMostViewSample2();
        assertThat(sellersWhoEarnedMostView1).isNotEqualTo(sellersWhoEarnedMostView2);
    }

    @Test
    void sellerTest() throws Exception {
        SellersWhoEarnedMostView sellersWhoEarnedMostView = getSellersWhoEarnedMostViewRandomSampleGenerator();
        Collaborator collaboratorBack = getCollaboratorRandomSampleGenerator();

        sellersWhoEarnedMostView.setSeller(collaboratorBack);
        assertThat(sellersWhoEarnedMostView.getSeller()).isEqualTo(collaboratorBack);

        sellersWhoEarnedMostView.seller(null);
        assertThat(sellersWhoEarnedMostView.getSeller()).isNull();
    }
}

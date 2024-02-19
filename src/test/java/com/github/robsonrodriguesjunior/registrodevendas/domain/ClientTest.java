package com.github.robsonrodriguesjunior.registrodevendas.domain;

import static com.github.robsonrodriguesjunior.registrodevendas.domain.ClientTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.PersonTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.SaleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.robsonrodriguesjunior.registrodevendas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void personTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        client.setPerson(personBack);
        assertThat(client.getPerson()).isEqualTo(personBack);

        client.person(null);
        assertThat(client.getPerson()).isNull();
    }

    @Test
    void buysTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Sale saleBack = getSaleRandomSampleGenerator();

        client.addBuys(saleBack);
        assertThat(client.getBuys()).containsOnly(saleBack);
        assertThat(saleBack.getClient()).isEqualTo(client);

        client.removeBuys(saleBack);
        assertThat(client.getBuys()).doesNotContain(saleBack);
        assertThat(saleBack.getClient()).isNull();

        client.buys(new HashSet<>(Set.of(saleBack)));
        assertThat(client.getBuys()).containsOnly(saleBack);
        assertThat(saleBack.getClient()).isEqualTo(client);

        client.setBuys(new HashSet<>());
        assertThat(client.getBuys()).doesNotContain(saleBack);
        assertThat(saleBack.getClient()).isNull();
    }
}

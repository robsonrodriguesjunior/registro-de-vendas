package com.github.robsonrodriguesjunior.registrodevendas.domain;

import static com.github.robsonrodriguesjunior.registrodevendas.domain.ClientTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.CollaboratorTestSamples.*;
import static com.github.robsonrodriguesjunior.registrodevendas.domain.PersonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.robsonrodriguesjunior.registrodevendas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Person.class);
        Person person1 = getPersonSample1();
        Person person2 = new Person();
        assertThat(person1).isNotEqualTo(person2);

        person2.setId(person1.getId());
        assertThat(person1).isEqualTo(person2);

        person2 = getPersonSample2();
        assertThat(person1).isNotEqualTo(person2);
    }

    @Test
    void clientTest() throws Exception {
        Person person = getPersonRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        person.setClient(clientBack);
        assertThat(person.getClient()).isEqualTo(clientBack);
        assertThat(clientBack.getPerson()).isEqualTo(person);

        person.client(null);
        assertThat(person.getClient()).isNull();
        assertThat(clientBack.getPerson()).isNull();
    }

    @Test
    void sellerTest() throws Exception {
        Person person = getPersonRandomSampleGenerator();
        Collaborator collaboratorBack = getCollaboratorRandomSampleGenerator();

        person.setSeller(collaboratorBack);
        assertThat(person.getSeller()).isEqualTo(collaboratorBack);
        assertThat(collaboratorBack.getPerson()).isEqualTo(person);

        person.seller(null);
        assertThat(person.getSeller()).isNull();
        assertThat(collaboratorBack.getPerson()).isNull();
    }
}

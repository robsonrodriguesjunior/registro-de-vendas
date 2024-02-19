package com.github.robsonrodriguesjunior.registrodevendas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CollaboratorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Collaborator getCollaboratorSample1() {
        return new Collaborator().id(1L).code("code1");
    }

    public static Collaborator getCollaboratorSample2() {
        return new Collaborator().id(2L).code("code2");
    }

    public static Collaborator getCollaboratorRandomSampleGenerator() {
        return new Collaborator().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString());
    }
}

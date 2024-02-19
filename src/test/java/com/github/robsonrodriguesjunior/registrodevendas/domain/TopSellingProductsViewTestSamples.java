package com.github.robsonrodriguesjunior.registrodevendas.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TopSellingProductsViewTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TopSellingProductsView getTopSellingProductsViewSample1() {
        return new TopSellingProductsView().id(1L).quantity(1L).position(1L);
    }

    public static TopSellingProductsView getTopSellingProductsViewSample2() {
        return new TopSellingProductsView().id(2L).quantity(2L).position(2L);
    }

    public static TopSellingProductsView getTopSellingProductsViewRandomSampleGenerator() {
        return new TopSellingProductsView()
            .id(longCount.incrementAndGet())
            .quantity(longCount.incrementAndGet())
            .position(longCount.incrementAndGet());
    }
}

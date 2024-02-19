package com.github.robsonrodriguesjunior.registrodevendas.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SellersWhoSoldMostProductsViewTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SellersWhoSoldMostProductsView getSellersWhoSoldMostProductsViewSample1() {
        return new SellersWhoSoldMostProductsView().id(1L).quantity(1L).position(1L);
    }

    public static SellersWhoSoldMostProductsView getSellersWhoSoldMostProductsViewSample2() {
        return new SellersWhoSoldMostProductsView().id(2L).quantity(2L).position(2L);
    }

    public static SellersWhoSoldMostProductsView getSellersWhoSoldMostProductsViewRandomSampleGenerator() {
        return new SellersWhoSoldMostProductsView()
            .id(longCount.incrementAndGet())
            .quantity(longCount.incrementAndGet())
            .position(longCount.incrementAndGet());
    }
}

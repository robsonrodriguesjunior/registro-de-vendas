package com.github.robsonrodriguesjunior.registrodevendas.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SellersWhoEarnedMostViewTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SellersWhoEarnedMostView getSellersWhoEarnedMostViewSample1() {
        return new SellersWhoEarnedMostView().id(1L).position(1L);
    }

    public static SellersWhoEarnedMostView getSellersWhoEarnedMostViewSample2() {
        return new SellersWhoEarnedMostView().id(2L).position(2L);
    }

    public static SellersWhoEarnedMostView getSellersWhoEarnedMostViewRandomSampleGenerator() {
        return new SellersWhoEarnedMostView().id(longCount.incrementAndGet()).position(longCount.incrementAndGet());
    }
}

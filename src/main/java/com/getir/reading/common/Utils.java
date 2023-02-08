package com.getir.reading.common;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static long generateOrderNumber() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextLong(10_000_000L, 100_000_000L);
    }
}

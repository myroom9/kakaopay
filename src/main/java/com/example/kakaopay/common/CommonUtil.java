package com.example.kakaopay.common;

import java.util.Random;

public class CommonUtil {
    private static final int ASC_ZERO = 48;
    private static final int ASC_NINE = 57;

    public static String getRandomStringWithLength(int length) {
        Random random = new Random();
        return random.ints(ASC_ZERO, ASC_NINE + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

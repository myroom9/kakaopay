package com.example.kakaopay.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeUtil {
    public static LocalDateTime of(LocalDate date) {
        return date.atTime(0, 0);
    }

    public static LocalDateTime nextDayOf(LocalDate date) {
        return date.plusDays(1).atTime(0, 0);
    }
}

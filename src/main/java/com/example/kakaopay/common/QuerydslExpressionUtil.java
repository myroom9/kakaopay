package com.example.kakaopay.common;

import com.querydsl.core.types.dsl.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class QuerydslExpressionUtil {

    /**
     * 테스트%
     *
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression startsWith(StringPath path, String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return path.startsWith(value);
    }

    public static BooleanExpression contains(StringPath path, String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return path.contains(value);
    }

    /**
     * =
     *
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression eq(StringPath path, String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return path.eq(value);
    }

    public static BooleanExpression eq(BooleanPath path, Boolean value) {
        if (value == null) {
            return null;
        }
        return path.eq(value);
    }

    public static <T extends Number & Comparable<?>> BooleanExpression eq(NumberPath<T> path, T value) {
        if (value == null) {
            return null;
        }
        return path.eq(value);
    }

    public static <T extends Enum<T>> BooleanExpression eq(EnumPath<T> path, T value) {
        if (value == null) {
            return null;
        }
        return path.eq(value);
    }

    /**
     * >=
     *
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression goe(DatePath<LocalDate> path, LocalDate value) {
        if (value == null) {
            return null;
        }
        return path.goe(value);
    }

    /**
     * >=
     *
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression goe(DateTimePath<LocalDateTime> path, LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return path.goe(value);
    }

    /**
     * >=
     *
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression goe(NumberPath<Byte> path, Byte value) {
        if (value == null) {
            return null;
        }
        return path.goe(value);
    }

    /**
     * >=
     *
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression goe(NumberPath<Integer> path, Integer value) {
        if (value == null) {
            return null;
        }
        return path.goe(value);
    }

    /**
     * <=
     *
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression loe(DatePath<LocalDate> path, LocalDate value) {
        if (value == null) {
            return null;
        }
        return path.loe(value);
    }

    /**
     * <=
     *
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression loe(DateTimePath<LocalDateTime> path, LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return path.loe(value);
    }

    /**
     * <=
     *
     * for 카드순서
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression loe(NumberPath<Byte> path, Byte value) {
        if (value == null) {
            return null;
        }
        return path.loe(value);
    }

    public static BooleanExpression loe(NumberPath<Integer> path, Integer value) {
        if (value == null) {
            return null;
        }
        return path.loe(value);
    }

    /**
     * <
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression lt(NumberPath<Integer> path, Integer value) {
        if (value == null) {
            return null;
        }
        return path.lt(value);
    }

    /**
     * <
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression lt(DateTimePath<LocalDateTime> path, LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return path.lt(value);
    }

    /**
     * < (localDate)
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression lt(DatePath<LocalDate> path, LocalDate value) {
        if (value == null) {
            return null;
        }
        return path.lt(value);
    }
}

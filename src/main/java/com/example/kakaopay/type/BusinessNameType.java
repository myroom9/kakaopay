package com.example.kakaopay.type;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.exception.cumtom.BusinessException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BusinessNameType {
    FOOD("푸드"),
    COSMETIC("화장품"),
    RESTAURANT("식당"),
    ;

    private final String name;

    BusinessNameType(String name) {
        this.name = name;
    }

    public static BusinessNameType stringToEnum(String name) {
        return Arrays.stream(values())
                .filter(o -> o.toString().equals(name))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BUSINESS_TYPE));
    }
}

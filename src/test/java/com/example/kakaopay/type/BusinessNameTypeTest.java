package com.example.kakaopay.type;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.exception.cumtom.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessNameTypeTest {
    @Test
    @DisplayName("[성공] 비지니스 string으로 enum type 찾기")
    void stringToEnumSuccessTest() {
        BusinessNameType food = BusinessNameType.stringToEnum("FOOD");
        Assertions.assertThat(food).isEqualTo(BusinessNameType.FOOD);
    }

    @Test
    @DisplayName("[예외] 비지니스 string으로 enum type 못찾은 경우")
    void stringToEnumExceptionTest() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            BusinessNameType.stringToEnum("TEST");
        });

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_BUSINESS_TYPE);
    }
}
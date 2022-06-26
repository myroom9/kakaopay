package com.example.kakaopay.exception.cumtom;

import com.example.kakaopay.common.ErrorCode;
import lombok.Getter;

public class BusinessException extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;

    public BusinessException() {
        this(ErrorCode.INTERNAL_EXCEPTION);
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, Throwable e) {
        super(errorCode.getMessage(), e);
        this.errorCode = errorCode;
    }
}

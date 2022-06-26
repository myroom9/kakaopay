package com.example.kakaopay.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private int status;
    private String message;
    private String code;
    private String data;
    private String requestId;
    private LocalDateTime requestAt;
    private LocalDateTime responseAt;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.requestId = RequestContextUtil.getRequestId();
        this.requestAt = RequestContextUtil.getRequestDate();
        this.responseAt = LocalDateTime.now();
        this.data = null;
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = message;
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }
}

package com.example.kakaopay.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiResponse<T> {
    @Schema(description = "http status 코드")
    private int status;
    @Schema(description = "응답 코드")
    private String code;
    @Schema(description = "응답 메세지")
    private String message;
    @Schema(description = "응답 데이터")
    private T data;
    @Schema(description = "요청 UUID")
    private String requestId;
    @Schema(description = "요청 시간")
    private LocalDateTime requestAt;
    @Schema(description = "응답 시간")
    private LocalDateTime responseAt;

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>defaultBuilder().build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>defaultBuilder()
                .data(data)
                .build();
    }

    private static <T> ApiResponseBuilder<T> defaultBuilder() {
        return ApiResponse.<T>builder()
                .status(200)
                .code("20000000")
                .message("success")
                .data(null)
                .requestId(RequestContextUtil.getRequestId())
                .requestAt(RequestContextUtil.getRequestDate())
                .responseAt(LocalDateTime.now());

    }
}

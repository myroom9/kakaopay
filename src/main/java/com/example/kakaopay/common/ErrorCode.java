package com.example.kakaopay.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_EXCEPTION(500, "50000000", "서비스 내부에서 알 수 없는 에러가 발생하였습니다."),
    NOT_SUPPORT_API(404, "40400000", "지원하지 않는 API입니다."),
    NOT_CORRECT_VALUE(400, "40000000", "올바르지 않은 값입니다."),

    // MEMBER 0XXXX
    NOT_FOUND_MEMBER(400, "40000000", "회원 조회에 실패하였습니다."),

    // BUSINESS TYPE 1XXXX
    NOT_FOUND_BUSINESS_TYPE(400, "40010000", "비즈니스 타입을 찾을 수 없습니다."),

    // MERCHANT 2XXXX
    NOT_FOUND_MERCHANT(400, "40020000", "가맹점을 찾을 수 없습니다."),

    // BARCODE 3XXXX
    NOT_FOUND_BARCODE(400, "40030000", "바코드 정보를 찾을 수 없습니다."),

    // POINT 4XXXX
    NOT_FOUND_POINT(400, "40040000", "사용가능한 포인트 금액이 존재하지 않습니다."),
    NOT_ENOUGH_POINT(400, "40040001", "포인트가 부족합니다."),
    ;

    private final int status;
    private final String message;
    private final String code;

    ErrorCode(int status, String code, String message) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}

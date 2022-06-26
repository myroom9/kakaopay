package com.example.kakaopay.type;

import lombok.Getter;

@Getter
public enum PointTransactionType {
    SAVE("적립"),
    USE("사용"),
    ;

    private final String description;

    PointTransactionType(String description) {
        this.description = description;
    }

}

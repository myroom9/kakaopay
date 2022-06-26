package com.example.kakaopay.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveResponse {
    @Schema(description = "가입된 회원 아이디", required = true)
    private String id;
    @Schema(description = "가입된 회원 이름", required = true)
    private String name;
    @Schema(description = "가입일자", required = true)
    private LocalDateTime createdAt;
}

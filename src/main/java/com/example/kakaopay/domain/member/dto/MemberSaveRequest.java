package com.example.kakaopay.domain.member.dto;

import com.example.kakaopay.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequest {

    @NotBlank
    @Size(max = 10)
    @Schema(description = "회원 아이디", required = true)
    private String id;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "회원 이름", required = true)
    private String name;
}

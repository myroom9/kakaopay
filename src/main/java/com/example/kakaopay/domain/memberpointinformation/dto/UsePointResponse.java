package com.example.kakaopay.domain.memberpointinformation.dto;

import com.example.kakaopay.domain.memberpointinformation.MemberPointInformation;
import com.example.kakaopay.type.BusinessNameType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsePointResponse {
    @Schema(description = "회원 아이디", required = true)
    private String memberId;
    @Schema(description = "가맹점 아이디", required = true)
    private Long merchantId;
    @Schema(description = "가맹점명", required = true)
    private String merchantName;
    @Schema(description = "가맹점 업종", required = true)
    private BusinessNameType businessNameType;
    @Schema(description = "사용 포인트", required = true)
    private BigDecimal usedPoint;
    @Schema(description = "남은 포인트", required = true)
    private BigDecimal remainPoint;
    @Schema(description = "사용시간", required = true)
    private LocalDateTime usedAt;

    public static UsePointResponse fromEntity(MemberPointInformation request, BigDecimal requestUsePoint) {
        BusinessNameType businessNameType = BusinessNameType.stringToEnum(request.getBusinessType().getName());
        return UsePointResponse.builder()
                .memberId(request.getMember().getId())
                .merchantId(request.getMerchant().getId())
                .merchantName(request.getMerchant().getName())
                .businessNameType(businessNameType)
                .usedPoint(requestUsePoint)
                .remainPoint(request.getAmount())
                .usedAt(request.getCreatedAt())
                .build();
    }
}

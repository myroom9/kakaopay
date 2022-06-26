package com.example.kakaopay.domain.memberpointinformation.dto;

import com.example.kakaopay.type.PointTransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointRequest {
    @Schema(description = "가맹점 일련번호", required = true)
    @NotNull
    private Long merchantId;
    @Schema(description = "바코드 번호", required = true)
    @NotBlank @Size(min = 10, max = 10)
    private String barcode;
    @Schema(description = "포인트 사용 타입", required = true)
    @NotNull
    private PointTransactionType transactionType;
    @Schema(description = "포인트 금액", required = true)
    @NotNull
    private BigDecimal point;
}

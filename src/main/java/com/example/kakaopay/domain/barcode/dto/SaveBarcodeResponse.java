package com.example.kakaopay.domain.barcode.dto;

import com.example.kakaopay.domain.barcode.Barcode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveBarcodeResponse {
    @Schema(description = "바코드 생성 회원 아이디", required = true)
    private String memberId;
    @Schema(description = "바코드 번호", required = true)
    private String barcode;

    public static SaveBarcodeResponse fromEntity(String memberId, Barcode barcode) {
        return SaveBarcodeResponse.builder()
                .memberId(memberId)
                .barcode(barcode.getBarcode())
                .build();
    }
}

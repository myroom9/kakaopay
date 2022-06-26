package com.example.kakaopay.domain.pointtransactionhistory.dto;

import com.example.kakaopay.domain.barcode.Barcode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointTransactionSearchRequest {
    private String barcode;
    private String memberId;
    private LocalDate startDate;
    private LocalDate endDate;

    public static PointTransactionSearchRequest fromEntity(Barcode barcode, LocalDate startDate, LocalDate endDate) {
        return PointTransactionSearchRequest.builder()
                .barcode(barcode.getBarcode())
                .memberId(barcode.getMember().getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}

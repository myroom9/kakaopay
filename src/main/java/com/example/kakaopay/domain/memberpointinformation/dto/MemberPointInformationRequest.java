package com.example.kakaopay.domain.memberpointinformation.dto;

import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.merchant.Merchant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPointInformationRequest {
    private Merchant merchant;
    private Barcode barcode;
    private Member member;
    private BigDecimal point;

    public static MemberPointInformationRequest fromEntity(Merchant merchant, Barcode barcode, BigDecimal point) {
        return MemberPointInformationRequest.builder()
                .merchant(merchant)
                .barcode(barcode)
                .member(barcode.getMember())
                .point(point)
                .build();
    }
}

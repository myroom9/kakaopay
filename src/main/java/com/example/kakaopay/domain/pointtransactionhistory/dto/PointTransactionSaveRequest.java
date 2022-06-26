package com.example.kakaopay.domain.pointtransactionhistory.dto;

import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.type.PointTransactionType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointTransactionSaveRequest {
    private Member member;
    private Merchant merchant;
    private BusinessType businessType;
    private BigDecimal amount;
    private PointTransactionType pointTransactionType;

    public static PointTransactionSaveRequest fromEntity(Member member,
                                                         Merchant merchant,
                                                         BigDecimal point,
                                                         PointTransactionType pointTransactionType) {
        return PointTransactionSaveRequest.builder()
                .member(member)
                .merchant(merchant)
                .businessType(merchant.getBusinessType())
                .amount(point)
                .pointTransactionType(pointTransactionType)
                .build();
    }
}

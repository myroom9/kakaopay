package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.barcode.BarcodeService;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.memberpointinformation.dto.MemberPointInformationRequest;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.domain.merchant.MerchantService;
import com.example.kakaopay.domain.pointtransactionhistory.PointTransactionHistoryService;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSaveRequest;
import com.example.kakaopay.type.PointTransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class MemberPointInformationSaveFacade extends MemberPointInformationAbstract {

    private final PointTransactionHistoryService pointTransactionService;
    private final MemberPointInformationService memberPointInformationService;

    public MemberPointInformationSaveFacade(MerchantService merchantService, BarcodeService barcodeService, PointTransactionHistoryService pointTransactionService, MemberPointInformationService memberPointInformationService) {
        super(merchantService, barcodeService);
        this.pointTransactionService = pointTransactionService;
        this.memberPointInformationService = memberPointInformationService;
    }

    @Override
    public MemberPointInformation handlePoint(Merchant merchant, Barcode barcode, BigDecimal point) {
        MemberPointInformationRequest saveRequest = MemberPointInformationRequest.fromEntity(merchant, barcode, point);
        return memberPointInformationService.save(saveRequest);
    }

    @Override
    public void savePointTransactionHistory(Member member, Merchant merchant, BigDecimal point) {
        PointTransactionSaveRequest pointTransactionSaveRequest =
                PointTransactionSaveRequest.fromEntity(member, merchant, point, PointTransactionType.SAVE);
        pointTransactionService.saveTransaction(pointTransactionSaveRequest);
    }
}

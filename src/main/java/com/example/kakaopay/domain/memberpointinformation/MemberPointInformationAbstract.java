package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.barcode.BarcodeService;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.memberpointinformation.dto.PointRequest;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.domain.merchant.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class MemberPointInformationAbstract {

    private final MerchantService merchantService;
    private final BarcodeService barcodeService;

    public abstract MemberPointInformation handlePoint(Merchant merchant, Barcode barcode, BigDecimal point);

    public abstract void savePointTransactionHistory(Member member, Merchant merchant, BigDecimal point);

    public MemberPointInformation doProcessPoint(PointRequest request) {
        // 가맹점 조회
        Merchant merchant = getMerchantById(request);
        // 바코드 조회
        Barcode barcode = getBarcodeByBarcodeNumber(request);
        // 회원 조회
        Member member = getMemberFromBarcode(barcode);

        // 포인트 처리
        MemberPointInformation response = handlePoint(merchant, barcode, request.getPoint());

        // 포인트 히스토리 처리
        savePointTransactionHistory(member, merchant, request.getPoint());

        return response;
    }

    private Member getMemberFromBarcode(Barcode barcode) {
        return barcode.getMember();
    }

    private Barcode getBarcodeByBarcodeNumber(PointRequest request) {
        return barcodeService.findByBarcodeNumber(request.getBarcode());
    }

    private Merchant getMerchantById(PointRequest request) {
        return merchantService.findMerchantById(request.getMerchantId());
    }
}

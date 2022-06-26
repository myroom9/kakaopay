package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.barcode.BarcodeService;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.memberpointinformation.dto.MemberPointInformationRequest;
import com.example.kakaopay.domain.memberpointinformation.dto.PointRequest;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.domain.merchant.MerchantService;
import com.example.kakaopay.domain.pointtransactionhistory.PointTransactionHistoryService;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSaveRequest;
import com.example.kakaopay.type.PointTransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberPointInformationFacade {
    private final MerchantService merchantService;
    private final BarcodeService barcodeService;
    private final MemberPointInformationService memberPointInformationService;
    private final PointTransactionHistoryService pointTransactionService;

    public MemberPointInformation savePoint(PointRequest request) {
        // 가맹점 조회
        Merchant merchant = merchantService.findMerchantById(request.getMerchantId());
        // 바코드 조회
        Barcode barcode = barcodeService.findByBarcodeNumber(request.getBarcode());
        Member member = barcode.getMember();

        // 포인트 저장
        MemberPointInformationRequest saveRequest = MemberPointInformationRequest.fromEntity(merchant, barcode, request.getPoint());
        MemberPointInformation savePoint = memberPointInformationService.save(saveRequest);

        // 포인트 로그 저장 (포인트 저장은 실패해도 rollback하지 않는다 tx 별개처리)
        PointTransactionSaveRequest pointTransactionSaveRequest =
                PointTransactionSaveRequest.fromEntity(member, merchant, request.getPoint(), PointTransactionType.SAVE);
        pointTransactionService.saveTransaction(pointTransactionSaveRequest);

        return savePoint;
    }

    public MemberPointInformation usePoint(PointRequest request) {
        // 가맹점 조회
        Merchant merchant = merchantService.findMerchantById(request.getMerchantId());
        // 바코드 조회
        Barcode barcode = barcodeService.findByBarcodeNumber(request.getBarcode());
        Member member = barcode.getMember();

        // 포인트 사용
        MemberPointInformationRequest useRequest = MemberPointInformationRequest.fromEntity(merchant, barcode, request.getPoint());
        MemberPointInformation usePoint = memberPointInformationService.use(useRequest);

        // 포인트 로그 저장
        // 포인트 로그 저장 (포인트 저장은 실패해도 rollback하지 않는다 tx 별개처리)
        PointTransactionSaveRequest pointTransactionSaveRequest =
                PointTransactionSaveRequest.fromEntity(member, merchant, request.getPoint(), PointTransactionType.USE);

        return usePoint;
    }
}

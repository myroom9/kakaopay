package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.barcode.BarcodeRepository;
import com.example.kakaopay.domain.barcode.BarcodeService;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.businesstype.BusinessTypeRepository;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.member.MemberRepository;
import com.example.kakaopay.domain.memberpointinformation.dto.PointRequest;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.domain.merchant.MerchantRepository;
import com.example.kakaopay.domain.merchant.MerchantService;
import com.example.kakaopay.domain.pointtransactionhistory.PointTransactionHistoryService;
import com.example.kakaopay.exception.cumtom.BusinessException;
import com.example.kakaopay.type.BusinessNameType;
import com.example.kakaopay.type.PointTransactionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberPointInformationFacadeTest {
    @Autowired
    MerchantService merchantService;
    @Autowired
    BarcodeService barcodeService;
    @Autowired
    MemberPointInformationService memberPointInformationService;
    @Autowired
    PointTransactionHistoryService pointTransactionService;
    @Autowired
    MemberPointInformationFacade memberPointInformationFacade;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BarcodeRepository barcodeRepository;
    @Autowired
    BusinessTypeRepository businessTypeRepository;
    @Autowired
    MerchantRepository merchantRepository;

    @Test
    @DisplayName("[성공] 포인트 적립 성공 테스트")
    void savePointSuccessTest() {
        Member member = new Member("whahn", "kakao");
        memberRepository.save(member);

        String newBarcode = "0000000000";
        Barcode barcode = new Barcode(newBarcode, member);
        barcodeRepository.save(barcode);

        BusinessType businessType = new BusinessType(1L, BusinessNameType.FOOD.getName());
        businessTypeRepository.save(businessType);

        Merchant merchant = new Merchant(businessType, "whahn-merchant");
        merchantRepository.save(merchant);

        PointRequest clientRequest = PointRequest.builder()
                .transactionType(PointTransactionType.SAVE)
                .merchantId(merchant.getId())
                .barcode("0000000000")
                .point(BigDecimal.valueOf(100))
                .build();

        MemberPointInformation memberPointInformation = memberPointInformationFacade.savePoint(clientRequest);
        Assertions.assertThat(memberPointInformation.getAmount()).isEqualTo(BigDecimal.valueOf(100));
        Assertions.assertThat(memberPointInformation.getMember().getId()).isEqualTo(member.getId());
        Assertions.assertThat(memberPointInformation.getMerchant().getId()).isEqualTo(clientRequest.getMerchantId());
    }

    @Test
    @DisplayName("[예외] 가맹점 없음 예외 테스트")
    void savePointIsNotExistMerchantTest() {
        PointRequest clientRequest = PointRequest.builder()
                .merchantId(0L)
                .build();

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            memberPointInformationFacade.savePoint(clientRequest);
        });

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_MERCHANT);
    }

    @Test
    @DisplayName("[예외] 바코드 없음 예외 테스트")
    void savePointIsNotExistBarcodeTest() {
        BusinessType businessType = new BusinessType(1L, BusinessNameType.FOOD.getName());
        businessTypeRepository.save(businessType);

        Merchant merchant = new Merchant(businessType, "whahn-merchant");
        Merchant SavedMerchant = merchantRepository.save(merchant);

        PointRequest clientRequest = PointRequest.builder()
                .merchantId(SavedMerchant.getId())
                .barcode("testbarcode")
                .build();

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            memberPointInformationFacade.savePoint(clientRequest);
        });

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_BARCODE);
    }
}
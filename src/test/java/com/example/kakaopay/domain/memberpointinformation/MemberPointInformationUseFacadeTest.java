package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.barcode.BarcodeRepository;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.businesstype.BusinessTypeRepository;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.member.MemberRepository;
import com.example.kakaopay.domain.memberpointinformation.dto.PointRequest;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.domain.merchant.MerchantRepository;
import com.example.kakaopay.exception.cumtom.BusinessException;
import com.example.kakaopay.type.BusinessNameType;
import com.example.kakaopay.type.PointTransactionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberPointInformationUseFacadeTest {
    @Autowired
    MemberPointInformationUseFacade memberPointInformationUseFacade;

    @Autowired
    MemberPointInformationSaveFacade memberPointInformationSaveFacade;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BarcodeRepository barcodeRepository;
    @Autowired
    BusinessTypeRepository businessTypeRepository;
    @Autowired
    MerchantRepository merchantRepository;

    Member member;
    Barcode barcode;
    BusinessType businessType;
    Merchant merchant;

    private void initData() {
        member = new Member("whahn", "kakao");
        memberRepository.save(member);

        String newBarcode = "0000000000";
        barcode = new Barcode(newBarcode, member);
        barcodeRepository.save(barcode);

        businessType = new BusinessType(1L, BusinessNameType.FOOD.getName());
        businessTypeRepository.save(businessType);

        merchant = new Merchant(businessType, "whahn-merchant");
        merchantRepository.save(merchant);
    }

    @Test
    @Order(0)
    @DisplayName("[에외] 포인트 적립 없는데 사용한 케이스 테스트")
    void doProcessPointNotFoundPointExceptionTest() {
        initData();

        PointRequest clientRequest = PointRequest.builder()
                .transactionType(PointTransactionType.USE)
                .merchantId(merchant.getId())
                .barcode("0000000000")
                .point(BigDecimal.valueOf(100))
                .build();

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            memberPointInformationUseFacade.handlePoint(merchant, barcode, clientRequest.getPoint());
        });
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_POINT);
    }

    @Test
    @DisplayName("[예외] 포인트 부족 케이스 테스트")
    void doProcessPointNotEnoughExceptionTest() {
        initData();

        // 적립
        PointRequest clientSaveRequest = PointRequest.builder()
                .transactionType(PointTransactionType.USE)
                .merchantId(merchant.getId())
                .barcode("0000000000")
                .point(BigDecimal.valueOf(50))
                .build();
        memberPointInformationSaveFacade.handlePoint(merchant, barcode, clientSaveRequest.getPoint());

        // 사용
        PointRequest clientUseRequest = PointRequest.builder()
                .transactionType(PointTransactionType.USE)
                .merchantId(merchant.getId())
                .barcode("0000000000")
                .point(BigDecimal.valueOf(100))
                .build();

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            memberPointInformationUseFacade.handlePoint(merchant, barcode, clientUseRequest.getPoint());
        });
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_ENOUGH_POINT);
    }

    @Test
    @DisplayName("[성공] 포인트 사용 케이스 테스트")
    void doProcessPointSuccessTest() {
        initData();

        // 적립
        PointRequest clientSaveRequest = PointRequest.builder()
                .transactionType(PointTransactionType.USE)
                .merchantId(merchant.getId())
                .barcode("0000000000")
                .point(BigDecimal.valueOf(100))
                .build();
        memberPointInformationSaveFacade.handlePoint(merchant, barcode, clientSaveRequest.getPoint());

        // 사용
        PointRequest clientUseRequest = PointRequest.builder()
                .transactionType(PointTransactionType.USE)
                .merchantId(merchant.getId())
                .barcode("0000000000")
                .point(BigDecimal.valueOf(100))
                .build();

        MemberPointInformation result = memberPointInformationUseFacade.handlePoint(merchant, barcode, clientUseRequest.getPoint());
        Assertions.assertThat(result.getAmount()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("[예외] 가맹점 없음 예외 테스트")
    void savePointIsNotExistMerchantTest() {
        PointRequest clientRequest = PointRequest.builder()
                .merchantId(0L)
                .build();

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            memberPointInformationUseFacade.doProcessPoint(clientRequest);
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
            memberPointInformationUseFacade.doProcessPoint(clientRequest);
        });

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_BARCODE);
    }
}
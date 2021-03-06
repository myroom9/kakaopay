package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.memberpointinformation.dto.MemberPointInformationRequest;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.exception.cumtom.BusinessException;
import com.example.kakaopay.type.BusinessNameType;
import com.example.kakaopay.common.CommonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MemberPointInformationServiceTest {

    @Mock
    MemberPointInformationRepository memberPointInformationRepository;

    @InjectMocks
    MemberPointInformationService memberPointInformationService;

    Member member;
    Barcode barcode;
    BusinessType businessType;
    Merchant merchant;

    public Member getMockMember() {
        return new Member("whahn", "name");
    }

    public Barcode getBarcode(Member member, String barcode) {
        return new Barcode(barcode, member);
    }

    public Merchant getMerchant(BusinessType businessType, String merchantName) {
        return new Merchant(businessType, merchantName);
    }

    @BeforeEach
    void init() {
        member = getMockMember();
        barcode = getBarcode(member, CommonUtil.getRandomStringWithLength(10));
        businessType = new BusinessType(1L, BusinessNameType.FOOD.getName());
        merchant = getMerchant(businessType, "whahn-merchant");
    }

    @Test
    @DisplayName("[??????] ????????? ?????? ?????? ?????????")
    void saveSuccessTest() {
        MemberPointInformationRequest saveData = MemberPointInformationRequest.builder()
                .barcode(barcode)
                .member(member)
                .merchant(merchant)
                .point(BigDecimal.valueOf(100))
                .build();

        MemberPointInformation mockResponse = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(500));

        Mockito.when(memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(
                                                            ArgumentMatchers.any(Member.class),
                                                            ArgumentMatchers.any(Merchant.class),
                                                            ArgumentMatchers.any(BusinessType.class)))
                .thenReturn(mockResponse);

        MemberPointInformation result = memberPointInformationService.save(saveData);
        Assertions.assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(600));
    }

    @Test
    @DisplayName("[??????] ????????? ?????? ?????? ?????????")
    void useSuccessTest() {
        MemberPointInformationRequest useData = MemberPointInformationRequest.builder()
                .barcode(barcode)
                .member(member)
                .merchant(merchant)
                .point(BigDecimal.valueOf(100))
                .build();

        MemberPointInformation mockResponse = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(500));

        Mockito.when(memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(
                        ArgumentMatchers.any(Member.class),
                        ArgumentMatchers.any(Merchant.class),
                        ArgumentMatchers.any(BusinessType.class)))
                .thenReturn(mockResponse);

        MemberPointInformation result = memberPointInformationService.use(useData);
        Assertions.assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(400));
    }

    @Test
    @DisplayName("[??????] ????????? ?????? ?????????")
    void useExceptionNotEnoughPointTest() {
        MemberPointInformationRequest useData = MemberPointInformationRequest.builder()
                .barcode(barcode)
                .member(member)
                .merchant(merchant)
                .point(BigDecimal.valueOf(600))
                .build();

        MemberPointInformation mockResponse = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(500));

        Mockito.when(memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(
                        ArgumentMatchers.any(Member.class),
                        ArgumentMatchers.any(Merchant.class),
                        ArgumentMatchers.any(BusinessType.class)))
                .thenReturn(mockResponse);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            memberPointInformationService.use(useData);
        });
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_ENOUGH_POINT);
    }

    @Test
    @DisplayName("[??????] ????????? ?????? ?????? ????????? (???????????? ?????? ???????????? ?????? ??????)")
    void useExceptionNotExistSavedPointTest() {
        MemberPointInformationRequest useData = MemberPointInformationRequest.builder()
                .barcode(barcode)
                .member(member)
                .merchant(merchant)
                .point(BigDecimal.valueOf(600))
                .build();

        Mockito.when(memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(
                        ArgumentMatchers.any(Member.class),
                        ArgumentMatchers.any(Merchant.class),
                        ArgumentMatchers.any(BusinessType.class)))
                .thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            memberPointInformationService.use(useData);
        });
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_POINT);
    }
}
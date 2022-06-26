package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.memberpointinformation.dto.PointRequest;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.type.BusinessNameType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(
        value = { MemberPointInformationController.class }
)
class MemberPointInformationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MemberPointInformationSaveFacade memberPointInformationSaveFacade;
    @MockBean
    private MemberPointInformationUseFacade memberPointInformationUseFacade;

    public Member getMockMember() {
        return new Member("whahn", "name");
    }

    public Merchant getMerchant(BusinessType businessType, String merchantName) {
        return new Merchant(businessType, merchantName);
    }

    @Test
    @DisplayName("[성공] 포인트 적립 성공 테스트")
    void savePointSuccessTest() throws Exception {
        Member member = getMockMember();
        BusinessType businessType = new BusinessType(1L, BusinessNameType.FOOD.toString());
        Merchant merchant = getMerchant(businessType, "whahn-merchant");
        MemberPointInformation result = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(100));

        Mockito.when(memberPointInformationSaveFacade.doProcessPoint(ArgumentMatchers.any(PointRequest.class)))
                .thenReturn(result);

        mockMvc.perform(
                        post("/api/v1/save-point")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .param("merchantId", "1")
                                .param("barcode", "0000000000")
                                .param("transactionType", "SAVE")
                                .param("point", "100")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.memberId").value("whahn"))
                .andExpect(jsonPath("$.data.merchantName").value("whahn-merchant"))
                .andExpect(jsonPath("$.data.businessNameType").value(BusinessNameType.FOOD.toString()))
                .andExpect(jsonPath("$.data.savedPoint").value(BigDecimal.valueOf(100)))
                .andExpect(jsonPath("$.data.remainPoint").value(BigDecimal.valueOf(100)))
        ;
    }

    @Test
    @DisplayName("[예외] 포인트 적립 유효성 검증 예외 테스트 - merchantId 없는 경우")
    void savePointExceptionTest() throws Exception {
        mockMvc.perform(
                        post("/api/v1/save-point")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .param("barcode", "0000000000")
                                .param("transactionType", "SAVE")
                                .param("point", "100")
                )
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("[예외] 포인트 적립 유효성 검증 예외 테스트 - 바코드 10자리 아닌 경우")
    void savePointExceptionTest2() throws Exception {
        mockMvc.perform(
                        post("/api/v1/save-point")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .param("merchantId", "1")
                                .param("barcode", "000000000")
                                .param("transactionType", "SAVE")
                                .param("point", "100")
                )
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("[성공] 포인트 사용 성공 테스트")
    void usePointSuccessTest() throws Exception {
        Member member = getMockMember();
        BusinessType businessType = new BusinessType(1L, BusinessNameType.FOOD.toString());
        Merchant merchant = getMerchant(businessType, "whahn-merchant");
        MemberPointInformation result = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(100));

        Mockito.when(memberPointInformationUseFacade.doProcessPoint(ArgumentMatchers.any(PointRequest.class)))
                .thenReturn(result);

        mockMvc.perform(
                        post("/api/v1/use-point")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .param("merchantId", "1")
                                .param("barcode", "0000000000")
                                .param("transactionType", "USE")
                                .param("point", "100")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.memberId").value("whahn"))
                .andExpect(jsonPath("$.data.merchantName").value("whahn-merchant"))
                .andExpect(jsonPath("$.data.businessNameType").value(BusinessNameType.FOOD.toString()))
                .andExpect(jsonPath("$.data.usedPoint").value(BigDecimal.valueOf(100)))
                .andExpect(jsonPath("$.data.remainPoint").value(BigDecimal.valueOf(100)))
        ;
    }

    @Test
    @DisplayName("[예외] 포인트 적립 유효성 검증 예외 테스트 - merchantId 없는 경우")
    void usePointExceptionTest() throws Exception {
        mockMvc.perform(
                        post("/api/v1/use-point")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .param("barcode", "0000000000")
                                .param("transactionType", "SAVE")
                                .param("point", "100")
                )
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("[예외] 포인트 적립 유효성 검증 예외 테스트 - 바코드 10자리 아닌 경우")
    void usePointExceptionTest2() throws Exception {
        mockMvc.perform(
                        post("/api/v1/use-point")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .param("merchantId", "1")
                                .param("barcode", "000000000")
                                .param("transactionType", "SAVE")
                                .param("point", "100")
                )
                .andExpect(status().isBadRequest())
        ;
    }
}
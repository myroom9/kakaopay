package com.example.kakaopay.domain.pointtransactionhistory;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.barcode.BarcodeService;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.member.MemberController;
import com.example.kakaopay.domain.member.dto.MemberSaveRequest;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchRequest;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchResponse;
import com.example.kakaopay.exception.cumtom.BusinessException;
import com.example.kakaopay.type.BusinessNameType;
import com.example.kakaopay.type.PointTransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(
        value = { PointTransactionHistoryController.class }
)
class PointTransactionHistoryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BarcodeService barcodeService;
    @MockBean
    PointTransactionHistoryService pointTransactionHistoryService;

    public Member getMockMember() {
        Member tempMember = new Member("whahn", "안원휘");
        Barcode barcode1 = new Barcode("0000000000", tempMember);
        return new Member("whahn", "안원휘", barcode1);
    }

    public Merchant getMerchant(BusinessType businessType, String merchantName) {
        return new Merchant(businessType, merchantName);
    }

    private List<PointTransactionHistory> initData(Member member, Merchant merchant, BusinessType businessType, int size) {
        ArrayList<PointTransactionHistory> data = new ArrayList<>();
        for(int i=0; i<size; i++) {
            int point = getRandomInt();
            data.add(new PointTransactionHistory(member, merchant, businessType, BigDecimal.valueOf(point), PointTransactionType.SAVE));
            data.add(new PointTransactionHistory(member, merchant, businessType, BigDecimal.valueOf(point), PointTransactionType.USE));
        }

        return data;
    }

    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(10000000);
    }

    @Test
    @DisplayName("[성공] 거래내역 조회 있음 테스트")
    void getPointListByBarcodeSuccessTest() throws Exception {
        Member member = getMockMember();
        BusinessType businessType = new BusinessType(1L, BusinessNameType.FOOD.toString());
        Merchant merchant = getMerchant(businessType, "whahn-merchant");

        List<PointTransactionSearchResponse.PointHistoryInformation> data = initData(member, merchant, businessType, 10).stream().map(PointTransactionSearchResponse.PointHistoryInformation::new).collect(Collectors.toList());
        PointTransactionSearchResponse.PageInformation pageInformation = PointTransactionSearchResponse.PageInformation.fromEntity(0, 0, 0);
        PointTransactionSearchResponse.StaticInformation staticInformation = PointTransactionSearchResponse.StaticInformation.fromEntity(data.size());

        PointTransactionSearchResponse response = PointTransactionSearchResponse.builder()
                .pointHistoryInformation(data)
                .staticInformation(staticInformation)
                .pageInformation(pageInformation)
                .build();

        Mockito.when(barcodeService.findByBarcodeNumber("0000000000"))
                .thenReturn(member.getBarcode());

        Mockito.when(pointTransactionHistoryService.getPointList(ArgumentMatchers.any(PointTransactionSearchRequest.class), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/point-history")
                                .param("barcode", "0000000000")
                                .param("startDate", "2022-06-26")
                                .param("endDate", "2022-06-26")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.staticInformation.totalCount").value(data.size()))
                .andExpect(jsonPath("$.data.pageInformation.currentPage").value(0))
                .andExpect(jsonPath("$.data.pageInformation.nextPage").value(0))
                .andExpect(jsonPath("$.data.pageInformation.lastPage").value(0))
                .andExpect(jsonPath("$.data.pointHistoryInformation").exists())
        ;
    }

    @Test
    @DisplayName("[예외] 없는 바코드 포인트 내역 조회 테스트")
    void getPointListByBarcodeExceptionTest() throws Exception {
        Mockito.when(barcodeService.findByBarcodeNumber("0000000000"))
                .thenThrow(new BusinessException(ErrorCode.NOT_FOUND_BARCODE));

        mockMvc.perform(
                        get("/api/v1/point-history")
                                .param("startDate", "2022-06-26")
                                .param("endDate", "2022-06-26")
                )
                .andExpect(status().isBadRequest())
        ;
    }
}
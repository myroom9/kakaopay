package com.example.kakaopay.domain.pointtransactionhistory;

import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSaveRequest;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchRequest;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchResponse;
import com.example.kakaopay.type.BusinessNameType;
import com.example.kakaopay.type.PointTransactionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PointTransactionHistoryServiceTest {
    @Mock
    PointTransactionRepository pointTransactionRepository;

    @InjectMocks
    PointTransactionHistoryService pointTransactionHistoryService;

    Member member;
    Barcode barcode;
    BusinessType businessType;
    Merchant merchant;

    public Member getMockMember() {
        Member tempMember = new Member("whahn", "안원휘");
        Barcode barcode1 = new Barcode("0000000000", tempMember);
        return new Member("whahn", "안원휘", barcode1);
    }

    public Merchant getMerchant(BusinessType businessType, String merchantName) {
        return new Merchant(businessType, merchantName);
    }

    @Test
    @DisplayName("[성공] 포인트 내역 저장")
    void saveTransactionTest() {
        member = getMockMember();
        BusinessType businessType = new BusinessType(1L, BusinessNameType.FOOD.toString());
        merchant = getMerchant(businessType, "whahn-merchant");

        PointTransactionSaveRequest request = PointTransactionSaveRequest.builder()
                .merchant(merchant)
                .member(member)
                .businessType(businessType)
                .amount(BigDecimal.valueOf(100))
                .pointTransactionType(PointTransactionType.SAVE)
                .build();

        pointTransactionHistoryService.saveTransaction(request);
    }

    @Test
    @DisplayName("[성공] 포인트 내역 조회")
    void getPointListSuccessList() {
        member = getMockMember();
        BusinessType businessType = new BusinessType(1L, BusinessNameType.FOOD.toString());
        merchant = getMerchant(businessType, "whahn-merchant");

        List<PointTransactionHistory> dataList = initData(member, merchant, businessType, 10);
        Page<PointTransactionHistory> content = new PageImpl<>(dataList, getPageable(), dataList.size());

        Mockito.when(pointTransactionRepository.findPointHistoriesWithPaging(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(content);

        PointTransactionSearchRequest request = new PointTransactionSearchRequest();

        PointTransactionSearchResponse result = pointTransactionHistoryService.getPointList(request, getPageable());
        Assertions.assertThat(result.getStaticInformation().getTotalCount()).isEqualTo(20);
        Assertions.assertThat(result.getPageInformation().getCurrentPage()).isEqualTo(1);
        Assertions.assertThat(result.getPageInformation().getNextPage()).isEqualTo(1);
        Assertions.assertThat(result.getPageInformation().getLastPage()).isEqualTo(1);
        Assertions.assertThat(result.getPointHistoryInformation().get(0).getBarcode()).isEqualTo("0000000000");
    }

    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(10000000);
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

    private Pageable getPageable() {
        return new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }
}
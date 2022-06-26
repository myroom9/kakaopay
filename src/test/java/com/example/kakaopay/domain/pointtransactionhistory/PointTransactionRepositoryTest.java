package com.example.kakaopay.domain.pointtransactionhistory;

import com.example.kakaopay.common.PageResponse;
import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.barcode.BarcodeRepository;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.businesstype.BusinessTypeRepository;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.member.MemberRepository;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.domain.merchant.MerchantRepository;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchRequest;
import com.example.kakaopay.type.BusinessNameType;
import com.example.kakaopay.type.PointTransactionType;
import com.example.kakaopay.common.CommonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointTransactionRepositoryTest {
    @Autowired
    PointTransactionRepository pointTransactionRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BarcodeRepository barcodeRepository;
    @Autowired
    BusinessTypeRepository businessTypeRepository;
    @Autowired
    MerchantRepository merchantRepository;

    private Member member;
    private Barcode barcode;
    private BusinessType businessType;
    private Merchant merchant;

    @BeforeEach
    void init() {
        member = new Member("whahn", "kakao");
        memberRepository.save(member);

        String newBarcode = CommonUtil.getRandomStringWithLength(10);
        barcode = new Barcode(newBarcode, member);
        barcodeRepository.save(barcode);

        businessType = new BusinessType(1L, BusinessNameType.FOOD.getName());
        businessTypeRepository.save(businessType);

        merchant = new Merchant(businessType, "whahn-merchant");
        merchantRepository.save(merchant);
    }

    private void initBulkData(int dataSize) {
        ArrayList<PointTransactionHistory> data = new ArrayList<>();
        for(int i = 0; i < dataSize; i++) {
            int point = getRandomInt();
            data.add(new PointTransactionHistory(member, merchant, businessType, BigDecimal.valueOf(point), PointTransactionType.SAVE));
            data.add(new PointTransactionHistory(member, merchant, businessType, BigDecimal.valueOf(point), PointTransactionType.USE));
        }
        pointTransactionRepository.saveAllAndFlush(data);
    }

    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(10000000);
    }

    @Test
    @DisplayName("[성공] 포인트 내역 조회 성공")
    void findPointHistoriesWithPagingSuccessTest() {
        initBulkData(20);

        PointTransactionSearchRequest clientRequest = PointTransactionSearchRequest.builder()
                .memberId("whahn")
                .barcode(barcode.getBarcode())
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();

        Pageable pageable = Pageable.ofSize(10);

        Page<PointTransactionHistory> result = pointTransactionRepository.findPointHistoriesWithPaging(clientRequest, pageable);
        PageResponse<PointTransactionHistory> finalResult = PageResponse.of(result);
        Assertions.assertThat(finalResult.getDocuments().size()).isEqualTo(10);
        Assertions.assertThat(finalResult.getMeta().getTotalPageCount()).isGreaterThan(4);
    }

    @Test
    @DisplayName("[성공] 히스토리 없는 조회 성공")
    void findPointHistoriesWithPagingSuccessTest2() {
        PointTransactionSearchRequest clientRequest = PointTransactionSearchRequest.builder()
                .memberId("------")
                .barcode(barcode.getBarcode())
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();

        Pageable pageable = Pageable.ofSize(10);

        Page<PointTransactionHistory> result = pointTransactionRepository.findPointHistoriesWithPaging(clientRequest, pageable);
        PageResponse<PointTransactionHistory> finalResult = PageResponse.of(result);
        Assertions.assertThat(finalResult.getDocuments().size()).isEqualTo(0);
        Assertions.assertThat(finalResult.getMeta().getTotalPageCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("[예외] 가입되지 않은 회원 아이디로 포인트 조회")
    void findPointHistoriesWithPagingExceptionTest() {
        initBulkData(20);

        PointTransactionSearchRequest clientRequest = PointTransactionSearchRequest.builder()
                .memberId("whahn2")
                .barcode(barcode.getBarcode())
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();

        Pageable pageable = Pageable.ofSize(10);

        Page<PointTransactionHistory> result = pointTransactionRepository.findPointHistoriesWithPaging(clientRequest, pageable);
        PageResponse<PointTransactionHistory> finalResult = PageResponse.of(result);
        Assertions.assertThat(finalResult.getDocuments().size()).isEqualTo(0);
        Assertions.assertThat(finalResult.getMeta().getTotalPageCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("[예외] 생성되지 않은 바코드로 포인트 조회")
    void findPointHistoriesWithPagingExceptionTest2() {
        initBulkData(20);

        PointTransactionSearchRequest clientRequest = PointTransactionSearchRequest.builder()
                .memberId("whahn")
                .barcode("0000000000")
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();

        Pageable pageable = Pageable.ofSize(10);

        Page<PointTransactionHistory> result = pointTransactionRepository.findPointHistoriesWithPaging(clientRequest, pageable);
        PageResponse<PointTransactionHistory> finalResult = PageResponse.of(result);
        Assertions.assertThat(finalResult.getDocuments().size()).isEqualTo(0);
        Assertions.assertThat(finalResult.getMeta().getTotalPageCount()).isEqualTo(0);
    }
}
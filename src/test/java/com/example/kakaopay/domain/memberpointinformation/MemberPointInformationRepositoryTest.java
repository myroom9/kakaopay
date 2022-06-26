package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.barcode.BarcodeRepository;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.businesstype.BusinessTypeRepository;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.member.MemberRepository;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.domain.merchant.MerchantRepository;
import com.example.kakaopay.type.BusinessNameType;
import com.example.kakaopay.common.CommonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberPointInformationRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    MemberPointInformationRepository memberPointInformationRepository;
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

    @Test
    @DisplayName("[성공] 포인트 조회 테스트 (포인트 없는 경우)")
    void findByMemberAndMerchantAndBusinessTypeSuccessTest() {
        MemberPointInformation result = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, businessType);
        Assertions.assertThat(result).isNull();
    }

    @Test
    @DisplayName("[성공] 저장된 포인트가 없는 경우의 포인트 저장 테스트")
    void saveSuccessTestWithoutSavedPoint() {
        MemberPointInformation saveData = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(1000));

        MemberPointInformation result = memberPointInformationRepository.save(saveData);
        Assertions.assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(1000));
        Assertions.assertThat(result.getVersion()).isEqualTo(0);
    }

    @Test
    @DisplayName("[예외] 저장된 포인트가 있는 경우의 포인트 저장 테스트")
    void saveSuccessTestWithSavedPoint() {
        // 최초 적립
        MemberPointInformation saveData = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(1000));
        entityManager.persist(saveData);
        entityManager.flush();

        // 2차 적립
        MemberPointInformation findCurrentPoint = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, businessType);
        findCurrentPoint.addPoint(BigDecimal.valueOf(2000));
        entityManager.persist(findCurrentPoint);
        entityManager.flush();

        MemberPointInformation finalResult = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, businessType);
        Assertions.assertThat(saveData.getId()).isEqualTo(finalResult.getId());
        Assertions.assertThat(finalResult.getAmount()).isEqualTo(BigDecimal.valueOf(3000));
        Assertions.assertThat(finalResult.getVersion()).isEqualTo(1);
    }

    @Test
    @DisplayName("[예외] 2명의 회원 포인트 저장 테스트")
    void saveSuccessTestWithTwoMember() {
        // 최초 적립
        MemberPointInformation saveData = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(1000));
        entityManager.persist(saveData);
        entityManager.flush();

        // 2차 적립
        Member member = new Member("whahn2", "no2");
        memberRepository.save(member);
        MemberPointInformation saveDataAnotherMember = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(1000));
        entityManager.persist(saveDataAnotherMember);
        entityManager.flush();

        MemberPointInformation firstResult = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(this.member, merchant, businessType);
        MemberPointInformation secondResult = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, businessType);
        Assertions.assertThat(firstResult.getId()).isNotEqualTo(secondResult.getId());
    }

    @Test
    @DisplayName("[예외] 다른 가맹점 포인트 저장 테스트")
    void saveSuccessTestWithTwoMerchant() {
        // A 가맹점
        MemberPointInformation saveData = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(1000));
        entityManager.persist(saveData);
        entityManager.flush();

        // B 가맹점
        Merchant merchant = new Merchant(businessType, "whahn-merchant-2");
        merchantRepository.save(merchant);


        MemberPointInformation saveDataAnotherMember = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(1000));
        entityManager.persist(saveDataAnotherMember);
        entityManager.flush();

        MemberPointInformation firstResult = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(this.member, this.merchant, businessType);
        MemberPointInformation secondResult = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, businessType);
        Assertions.assertThat(firstResult.getId()).isNotEqualTo(secondResult.getId());
    }

    @Test
    @DisplayName("[예외] 다른 업종 가맹점의 포인트 저장 테스트")
    void saveSuccessTestWithTwoBusinessType() {
        // A 가맹점
        MemberPointInformation saveData = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(1000));
        entityManager.persist(saveData);
        entityManager.flush();

        // B 가맹점
        BusinessType businessType = new BusinessType(2L, BusinessNameType.COSMETIC.getName());
        businessTypeRepository.save(businessType);
        merchant.changeBusinessType(businessType);

        MemberPointInformation saveDataAnotherMember = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(1000));
        entityManager.persist(saveDataAnotherMember);
        entityManager.flush();

        MemberPointInformation firstResult = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, this.businessType);
        MemberPointInformation secondResult = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, businessType);
        Assertions.assertThat(firstResult.getId()).isNotEqualTo(secondResult.getId());
    }

    @Test
    @DisplayName("[예외] 동시 수정 테스트 (version 동작 테스트)")
    void versionWorkingTest() throws InterruptedException {
        MemberPointInformation saveData = new MemberPointInformation(member, merchant, businessType, BigDecimal.valueOf(1000));
        entityManager.persist(saveData);
        entityManager.flush();
        entityManager.clear();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        MemberPointInformation member1 = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, businessType);
        executorService.execute(() -> {
            member1.addPoint(BigDecimal.valueOf(100));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test = 1");
        });

        MemberPointInformation member2 = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, businessType);
        executorService.execute(() -> {
            member2.addPoint(BigDecimal.valueOf(100));
            System.out.println("test = 2");
        });

        System.out.println("5 = " + 2);
        MemberPointInformation result = memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(member, merchant, businessType);
        System.out.println("result.getAmount() = " + result.getAmount());
    }
}
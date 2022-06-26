package com.example.kakaopay.domain.barcode;

import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.member.MemberRepository;
import com.example.kakaopay.common.CommonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BarcodeRepositoryTest {
    @Autowired
    BarcodeRepository barcodeRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("[성공] 요청 회원 아이디에 등록된 바코드 번호 조회")
    void findByBarcodeAndMemberSuccessTest() {
        Member member = new Member("whahn", "kakao");
        memberRepository.save(member);

        String newBarcode = CommonUtil.getRandomStringWithLength(10);
        Barcode barcode = new Barcode(newBarcode, member);
        barcodeRepository.save(barcode);

        Barcode confirmBarcode = barcodeRepository.findByBarcodeAndMember(barcode.getBarcode(), member);

        Assertions.assertThat(barcode.getBarcode()).isEqualTo(confirmBarcode.getBarcode());
        Assertions.assertThat(member.getId()).isEqualTo(confirmBarcode.getMember().getId());
    }
}
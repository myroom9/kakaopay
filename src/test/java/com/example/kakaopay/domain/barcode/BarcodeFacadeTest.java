package com.example.kakaopay.domain.barcode;

import com.example.kakaopay.domain.barcode.dto.SaveBarcodeResponse;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.member.MemberService;
import com.example.kakaopay.domain.member.dto.MemberSaveRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class BarcodeFacadeTest {

    @Autowired
    BarcodeFacade barcodeFacade;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("[성공] 바코드 생성 테스트")
    void createBarcodeByMemberIdSuccessTest() {
        // member 추가
        MemberSaveRequest memberRequest = MemberSaveRequest.builder()
                .id("whahn")
                .name("안원휘")
                .build();
        Member member = memberService.save(memberRequest);

        SaveBarcodeResponse result = barcodeFacade.createBarcodeByMemberId(member.getId());
        Assertions.assertThat(result.getMemberId()).isEqualTo("whahn");
        Assertions.assertThat(result.getBarcode()).isNotBlank();
        Assertions.assertThat(result.getBarcode()).hasSize(10);
    }

    @Test
    @DisplayName("[예외] 이미 바코드 발급이 완료된 회원의 신규 바코드 요청 예외 테스트")
    void createBarcodeByMemberIdExceptionTest() {
        // member 추가
        MemberSaveRequest memberRequest = MemberSaveRequest.builder()
                .id("whahn")
                .name("안원휘")
                .build();
        Member member = memberService.save(memberRequest);

        SaveBarcodeResponse firstResult = barcodeFacade.createBarcodeByMemberId(member.getId());
        SaveBarcodeResponse secondResult = barcodeFacade.createBarcodeByMemberId(member.getId());

        Assertions.assertThat(firstResult.getBarcode()).hasSize(10);
        Assertions.assertThat(firstResult.getBarcode()).isEqualTo(secondResult.getBarcode());
    }

}
package com.example.kakaopay.domain.member;

import com.example.kakaopay.domain.member.dto.MemberSaveRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("[성공] 회원 생성 성공 테스트")
    void saveSuccessTest() {
        MemberSaveRequest memberRequest = MemberSaveRequest.builder()
                .id("whahn")
                .name("안원휘")
                .build();

        Member memberResponse = new Member("whahn", "안원휘");

        Mockito.when(memberRepository.save(ArgumentMatchers.any(Member.class)))
                .thenReturn(memberResponse);

        Member result = memberService.save(memberRequest);
        Assertions.assertThat(result.getId()).isEqualTo("whahn");
        Assertions.assertThat(result.getName()).isEqualTo("안원휘");
    }
}
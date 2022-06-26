package com.example.kakaopay.domain.member;

import com.example.kakaopay.domain.barcode.BarcodeController;
import com.example.kakaopay.domain.barcode.BarcodeFacade;
import com.example.kakaopay.domain.member.dto.MemberSaveRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(
        value = { MemberController.class }
)
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("[성공] 회원 생성 성공 테스트")
    void createBarcodeSuccessTest() throws Exception {
        Member member = new Member("whahn", "안원휘");

        Mockito.when(memberService.save(ArgumentMatchers.any(MemberSaveRequest.class)))
                .thenReturn(member);

        mockMvc.perform(
                        post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "whahn")
                        .param("name", "안원휘")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.id").value("whahn"))
                .andExpect(jsonPath("$.data.name").value("안원휘"))
        ;
    }
}
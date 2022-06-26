package com.example.kakaopay.domain.barcode;

import com.example.kakaopay.domain.barcode.dto.SaveBarcodeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        value = { BarcodeController.class }
)
class BarcodeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BarcodeFacade barcodeFacade;

    @Test
    @DisplayName("[성공] 바코드 생성 컨트롤러 호출 테스트")
    void createBarcodeSuccessTest() throws Exception {
        SaveBarcodeResponse response = SaveBarcodeResponse.builder()
                .memberId("whahn")
                .barcode("0000000000")
                .build();

        Mockito.when(barcodeFacade.createBarcodeByMemberId("whahn"))
                        .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/barcodes/members/whahn")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.memberId").value("whahn"))
                .andExpect(jsonPath("$.data.barcode").value("0000000000"));
    }
}
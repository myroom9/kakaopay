package com.example.kakaopay.domain.barcode;

import com.example.kakaopay.common.ApiResponse;
import com.example.kakaopay.domain.barcode.dto.SaveBarcodeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/barcodes")
@Tag(name = "바코드 관련 API", description = "바코드관련 API LIST")
public class BarcodeController {
    private final BarcodeFacade barcodeFacade;

    @Operation(summary = "바코드 생성")
    @PostMapping("/members/{memberId}")
    public ApiResponse<SaveBarcodeResponse> createBarcode(@PathVariable("memberId") String memberId) {
        SaveBarcodeResponse response = barcodeFacade.createBarcodeByMemberId(memberId);
        return ApiResponse.success(response);
    }
}

package com.example.kakaopay.domain.merchant;

import com.example.kakaopay.common.ApiResponse;
import com.example.kakaopay.common.ModelMapperUtil;
import com.example.kakaopay.domain.merchant.dto.MerchantSaveRequest;
import com.example.kakaopay.domain.merchant.dto.MerchantSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchants")
@Tag(name = "가맹점 관련 API", description = "가맹점 관련 API LIST")
public class MerchantController {

    private final MerchantService merchantService;

    @Operation(summary = "가맹점 등록")
    @PostMapping
    public ApiResponse<MerchantSaveResponse> MerchantSaveResponse(MerchantSaveRequest request) {
        Merchant merchant = merchantService.save(request);
        MerchantSaveResponse response = MerchantSaveResponse.fromEntity(merchant);
        return ApiResponse.success(response);
    }
}

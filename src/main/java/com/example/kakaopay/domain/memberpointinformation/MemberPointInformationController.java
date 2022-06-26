package com.example.kakaopay.domain.memberpointinformation;


import com.example.kakaopay.common.ApiResponse;
import com.example.kakaopay.domain.memberpointinformation.dto.PointRequest;
import com.example.kakaopay.domain.memberpointinformation.dto.SavePointResponse;
import com.example.kakaopay.domain.memberpointinformation.dto.UsePointResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "멤버십 적립/사용 API", description = "멤버십 적립/사용 API LIST")
public class MemberPointInformationController {

    private final MemberPointInformationSaveFacade memberPointInformationSaveFacade;
    private final MemberPointInformationUseFacade memberPointInformationUseFacade;

    @Operation(summary = "멤버십 적립")
    @PostMapping("/save-point")
    public ApiResponse<SavePointResponse> savePoint(@Valid PointRequest request) {
        MemberPointInformation response = memberPointInformationSaveFacade.doProcessPoint(request);
        SavePointResponse finalResponse = SavePointResponse.fromEntity(response, request.getPoint());

        return ApiResponse.success(finalResponse);
    }

    @Operation(summary = "멤버십 적립")
    @PostMapping("/use-point")
    public ApiResponse<UsePointResponse> usePoint(@Valid PointRequest request) {
        MemberPointInformation response = memberPointInformationUseFacade.doProcessPoint(request);
        UsePointResponse finalResponse = UsePointResponse.fromEntity(response, request.getPoint());

        return ApiResponse.success(finalResponse);
    }
}

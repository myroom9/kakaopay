package com.example.kakaopay.domain.member;

import com.example.kakaopay.common.ApiResponse;
import com.example.kakaopay.common.ModelMapperUtil;
import com.example.kakaopay.domain.barcode.BarcodeFacade;
import com.example.kakaopay.domain.barcode.dto.SaveBarcodeResponse;
import com.example.kakaopay.domain.member.dto.MemberSaveRequest;
import com.example.kakaopay.domain.member.dto.MemberSaveResponse;
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
@RequestMapping("/api/v1/members")
@Tag(name = "멤버십 회원 API", description = "멤버십 회원 API LIST")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "멤버십 회원 가입")
    @PostMapping
    public ApiResponse<MemberSaveResponse> createBarcode(MemberSaveRequest request) {
        Member member = memberService.save(request);
        MemberSaveResponse response = ModelMapperUtil.map(member, MemberSaveResponse.class);
        return ApiResponse.success(response);
    }
}

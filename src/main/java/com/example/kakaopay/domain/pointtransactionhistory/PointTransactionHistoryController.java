package com.example.kakaopay.domain.pointtransactionhistory;

import com.example.kakaopay.common.ApiResponse;
import com.example.kakaopay.common.ModelMapperUtil;
import com.example.kakaopay.common.PageResponse;
import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.barcode.BarcodeService;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.member.dto.MemberSaveRequest;
import com.example.kakaopay.domain.member.dto.MemberSaveResponse;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchRequest;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/point-history")
@Tag(name = "포인트 내역 조회 API", description = "포인트 내역 조회 API LIST")
public class PointTransactionHistoryController {

    private final PointTransactionHistoryService pointTransactionHistoryService;
    private final BarcodeService barcodeService;

    @Operation(summary = "포인트 내역 조회 - by 바코드")
    @GetMapping
    public ApiResponse<PointTransactionSearchResponse> getPointListByBarcode(@RequestParam("barcode") String barcode,
                                                                             @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                             @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                                             @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Barcode barcodeObject = barcodeService.findByBarcodeNumber(barcode);
        PointTransactionSearchRequest request = PointTransactionSearchRequest.fromEntity(barcodeObject, startDate, endDate);

        PointTransactionSearchResponse dataList = pointTransactionHistoryService.getPointList(request, pageable);
        return ApiResponse.success(dataList);
    }
}

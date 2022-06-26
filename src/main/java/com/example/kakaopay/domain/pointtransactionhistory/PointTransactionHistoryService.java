package com.example.kakaopay.domain.pointtransactionhistory;

import com.example.kakaopay.common.PageResponse;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSaveRequest;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchRequest;
import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointTransactionHistoryService {

    private final PointTransactionRepository pointTransactionRepository;

    public void saveTransaction(PointTransactionSaveRequest request) {
        PointTransactionHistory pointTransactionHistory = new PointTransactionHistory(
                request.getMember(),
                request.getMerchant(),
                request.getBusinessType(),
                request.getAmount(),
                request.getPointTransactionType());
        pointTransactionRepository.save(pointTransactionHistory);
    }

    /**
     * 포인트 조회
     */
    public PointTransactionSearchResponse getPointList(PointTransactionSearchRequest request, Pageable pageable) {
        Page<PointTransactionHistory> pointListWithPage = pointTransactionRepository.findPointHistoriesWithPaging(request, pageable);
        PageResponse<PointTransactionHistory> pageResponse = PageResponse.of(pointListWithPage);

        List<PointHistoryInformation> list = pageResponse.getDocuments().stream().map(PointHistoryInformation::new).collect(Collectors.toList());
        PageInformation pageInformation = getPageInformation(pageable, pageResponse);
        StaticInformation staticInformation = StaticInformation.fromEntity(pageResponse.getMeta().getTotalCount());

        return builder()
                .pointHistoryInformation(list)
                .pageInformation(pageInformation)
                .staticInformation(staticInformation)
                .build();
    }

    private PageInformation getPageInformation(Pageable pageable, PageResponse<PointTransactionHistory> pageResponse) {
        int lastPage = pageResponse.getMeta().getTotalPageCount();
        int currentPage = 0;
        int nextPage = 0;
        if (lastPage != 0) {
            currentPage = pageable.getPageNumber() + 1;
            nextPage = (currentPage == lastPage) ? currentPage : currentPage + 1;
        }

        return PageInformation.fromEntity(currentPage, nextPage, lastPage);
    }
}

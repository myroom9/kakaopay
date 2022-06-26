package com.example.kakaopay.domain.pointtransactionhistory.dto;

import com.example.kakaopay.domain.pointtransactionhistory.PointTransactionHistory;
import com.example.kakaopay.type.PointTransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PointTransactionSearchResponse {
    private StaticInformation staticInformation;
    private PageInformation pageInformation;
    private List<PointHistoryInformation> pointHistoryInformation;

    @Data
    @Builder
    public static class StaticInformation {
        private Long totalCount;

        public static StaticInformation fromEntity(long totalCount) {
            return StaticInformation.builder()
                    .totalCount(totalCount)
                    .build();
        }
    }

    @Data
    @Builder
    public static class PageInformation {
        private int currentPage;
        private int nextPage;
        private int lastPage;

        public static PageInformation fromEntity(int currentPage, int nextPage, int lastPage) {
            return PageInformation.builder()
                    .currentPage(currentPage)
                    .nextPage(nextPage)
                    .lastPage(lastPage)
                    .build();
        }
    }

    @Data
    public static class PointHistoryInformation {
        private Long merchantId;
        private String merchantName;
        private String barcode;
        private String memberId;
        private BigDecimal point;
        private PointTransactionType transactionType;
        private LocalDateTime approvedAt;

        public PointHistoryInformation(PointTransactionHistory pointTransactionHistory) {
            merchantId = pointTransactionHistory.getMerchant().getId();
            merchantName = pointTransactionHistory.getMerchant().getName();
            barcode = pointTransactionHistory.getMember().getBarcode().getBarcode();
            memberId = pointTransactionHistory.getMember().getId();
            point = pointTransactionHistory.getAmount();
            transactionType = pointTransactionHistory.getPointTransactionType();
            approvedAt = pointTransactionHistory.getCreatedAt();
        }
    }
}

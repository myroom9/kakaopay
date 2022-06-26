package com.example.kakaopay.common;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageResponse<T> {

    @Schema(description = "페이지 정보")
    private Meta meta;
    @Schema(description = "리스트")
    private List<T> documents;

    @Schema(description = "페이지 메타 정보")
    @Data
    @AllArgsConstructor
    public static class Meta {

        @Schema(description = "총 항목 수")
        private long totalCount;
        @Schema(description = "페이지 수")
        private int totalPageCount;
    }

    public static <T> PageResponse<T> of(Page<T> page) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.meta = new Meta(page.getTotalElements(), page.getTotalPages());
        pageResponse.documents = page.getContent();
        return pageResponse;
    }
}

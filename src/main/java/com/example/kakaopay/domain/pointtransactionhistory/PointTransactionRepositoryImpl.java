package com.example.kakaopay.domain.pointtransactionhistory;

import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchRequest;
import com.example.kakaopay.common.LocalDateTimeUtil;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;

import static com.example.kakaopay.common.QuerydslExpressionUtil.*;
import static com.example.kakaopay.domain.pointtransactionhistory.QPointTransactionHistory.pointTransactionHistory;

public class PointTransactionRepositoryImpl extends QuerydslRepositorySupport implements PointTransactionRepositoryCustom {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     */
    public PointTransactionRepositoryImpl() {
        super(PointTransactionHistory.class);
    }

    @Override
    public Page<PointTransactionHistory> findPointHistoriesWithPaging(PointTransactionSearchRequest request, Pageable pageable) {
        JPQLQuery<PointTransactionHistory> basicContentQuery = getBasicQuery(request);

        // 추가 검색 조건 만드려면 아래 이어 추가

        JPQLQuery<PointTransactionHistory> finalContentQuery = basicContentQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<PointTransactionHistory> content = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, finalContentQuery).fetch();

        // count query 분리
        JPQLQuery<PointTransactionHistory> countQuery = getCountQuery(request);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private JPQLQuery<PointTransactionHistory> getBasicQuery(PointTransactionSearchRequest request) {
        return from(pointTransactionHistory)
                .innerJoin(pointTransactionHistory.member).fetchJoin()
                .innerJoin(pointTransactionHistory.merchant).fetchJoin()
                .innerJoin(pointTransactionHistory.businessType).fetchJoin()
                .where(
                        eq(pointTransactionHistory.member.id, request.getMemberId()),
                        eq(pointTransactionHistory.member.barcode.barcode, request.getBarcode()),
                        goe(pointTransactionHistory.createdAt, LocalDateTimeUtil.of(request.getStartDate())),
                        lt(pointTransactionHistory.createdAt, LocalDateTimeUtil.nextDayOf(request.getEndDate()))
                );
    }

    private JPQLQuery<PointTransactionHistory> getCountQuery(PointTransactionSearchRequest request) {
        return from(pointTransactionHistory)
                .where(
                        eq(pointTransactionHistory.member.id, request.getMemberId()),
                        goe(pointTransactionHistory.createdAt, LocalDateTimeUtil.of(request.getStartDate())),
                        lt(pointTransactionHistory.createdAt, LocalDateTimeUtil.nextDayOf(request.getEndDate()))
                );
    }
}

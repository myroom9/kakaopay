package com.example.kakaopay.domain.pointtransactionhistory;

import com.example.kakaopay.domain.pointtransactionhistory.dto.PointTransactionSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointTransactionRepositoryCustom {
    public Page<PointTransactionHistory> findPointHistoriesWithPaging(PointTransactionSearchRequest request,
                                                                      Pageable pageable);
}

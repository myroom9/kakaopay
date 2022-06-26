package com.example.kakaopay.domain.pointtransactionhistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransactionHistory, Long>, PointTransactionRepositoryCustom {
}

package com.example.kakaopay.domain.pointtransactionhistory;

import com.example.kakaopay.common.BaseEntity;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.type.PointTransactionType;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Where(clause = "deleted_at is null")
@Table(name = "point_transaction_histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointTransactionHistory extends BaseEntity {

    public PointTransactionHistory(Member member, Merchant merchant, BusinessType businessType, BigDecimal amount, PointTransactionType pointTransactionType) {
        this.member = member;
        this.merchant = merchant;
        this.businessType = businessType;
        this.amount = amount;
        this.pointTransactionType = pointTransactionType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(1) UNSIGNED")
    private Long id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id", updatable = false, nullable = false)
    private Member member;


    @ManyToOne(targetEntity = Merchant.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "merchants_id", updatable = false, nullable = false)
    private Merchant merchant;

    @ManyToOne(targetEntity = BusinessType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "business_type_id", updatable = false, nullable = false)
    private BusinessType businessType;

    @Column(columnDefinition = "DECIMAL(20,2)", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_type", length = 10, nullable = false, columnDefinition = "ENUM('SAVE', 'USE')")
    @Enumerated(EnumType.STRING)
    private PointTransactionType pointTransactionType;
}

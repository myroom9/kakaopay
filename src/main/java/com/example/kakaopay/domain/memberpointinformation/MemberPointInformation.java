package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.common.BaseEntity;
import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.exception.cumtom.BusinessException;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Where(clause = "deleted_at is null")
@Table(name = "member_point_information")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPointInformation extends BaseEntity {

    public MemberPointInformation(Member member, Merchant merchant, BusinessType businessType, BigDecimal amount) {
        this.member = member;
        this.merchant = merchant;
        this.businessType = businessType;
        this.amount = amount;
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

    @Version
    private Integer version;

    @Override
    public String toString() {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    public void addPoint(BigDecimal point) {
        this.amount = this.amount.add(point);
    }

    public void subPoint(BigDecimal point) {
        this.amount = this.amount.subtract(point);
        if (this.amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ErrorCode.NOT_ENOUGH_POINT);
        }
    }
}

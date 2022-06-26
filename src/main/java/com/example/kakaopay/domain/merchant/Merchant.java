package com.example.kakaopay.domain.merchant;

import com.example.kakaopay.common.BaseEntity;
import com.example.kakaopay.domain.businesstype.BusinessType;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Where(clause = "deleted_at is null")
@Table(name = "merchants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Merchant extends BaseEntity {

    public Merchant(BusinessType businessType, String name) {
        this.businessType = businessType;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @ManyToOne(targetEntity = BusinessType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "business_type_id")
    private BusinessType businessType;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String name;

    public void changeBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }
}

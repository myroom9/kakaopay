package com.example.kakaopay.domain.barcode;

import com.example.kakaopay.common.BaseEntity;
import com.example.kakaopay.domain.member.Member;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "barcodes")
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Barcode extends BaseEntity {

    public Barcode(String barcode, Member member) {
        this.barcode = barcode;
        this.member = member;
    }

    @Id
    @Column(name = "id", length = 10, columnDefinition = "VARCHAR(10)")
    private String barcode;

    @OneToOne
    @JoinColumn(name = "members_id")
    private Member member;

    @Override
    public String toString() {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}

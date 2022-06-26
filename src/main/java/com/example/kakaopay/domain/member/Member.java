package com.example.kakaopay.domain.member;

import com.example.kakaopay.common.BaseEntity;
import com.example.kakaopay.common.ModelMapperUtil;
import com.example.kakaopay.domain.barcode.Barcode;
import com.example.kakaopay.domain.member.dto.MemberSaveRequest;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Where(clause = "deleted_at is null")
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "id")
public class Member extends BaseEntity {

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Member(String id, String name, Barcode barcode) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
    }

    @Id
    @Column(length = 9, columnDefinition = "VARCHAR(9)")
    private String id;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @OneToOne(mappedBy = "member")
    private Barcode barcode;

    public void addMember(MemberSaveRequest request) {
        this.id = request.getId();
        this.name = request.getName();
    }

    @Override
    public String toString() {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}

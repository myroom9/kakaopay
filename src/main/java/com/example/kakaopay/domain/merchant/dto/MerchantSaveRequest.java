package com.example.kakaopay.domain.merchant.dto;

import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.type.BusinessNameType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantSaveRequest {
    private String name;
    private BusinessNameType businessNameType;
}

package com.example.kakaopay.domain.merchant.dto;

import com.example.kakaopay.common.ModelMapperUtil;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.merchant.Merchant;
import com.example.kakaopay.type.BusinessNameType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantSaveResponse {
    private Long id;
    private String name;
    private BusinessNameType businessNameType;
    private LocalDateTime createdAt;

    public static MerchantSaveResponse fromEntity(Merchant merchant) {
        MerchantSaveResponse response = ModelMapperUtil.map(merchant, MerchantSaveResponse.class);
        response.setBusinessNameType(BusinessNameType.stringToEnum(merchant.getBusinessType().getName()));
        return response;

    }
}

package com.example.kakaopay.domain.merchant;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.businesstype.BusinessTypeRepository;
import com.example.kakaopay.domain.merchant.dto.MerchantSaveRequest;
import com.example.kakaopay.exception.cumtom.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final BusinessTypeRepository businessTypeRepository;

    /**
     * 가맹점 생성
     */
    public Merchant save(MerchantSaveRequest request) {
        BusinessType businessType = businessTypeRepository.findByName(request.getBusinessNameType().toString());
        Merchant merchant = new Merchant(businessType, request.getName());
        return merchantRepository.save(merchant);
    }

    /**
     * 가맹점 조회
     */
    public Merchant findMerchantById(Long id) {
        return merchantRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MERCHANT));
    }
}

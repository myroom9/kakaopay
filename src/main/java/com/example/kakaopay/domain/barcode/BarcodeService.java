package com.example.kakaopay.domain.barcode;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.exception.cumtom.BusinessException;
import com.example.kakaopay.common.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarcodeService {
    private final BarcodeRepository barcodeRepository;

    /**
     * barcode 중복 확인
     */
    public boolean isExistBarcodeByBarcode(String barcode) {
        return barcodeRepository.findById(barcode).isPresent();
    }

    /**
     * barcode 중복시 재생성
     */
    public String keepCreatingBarcodeTillNotExist(String newBarcode) {
        boolean isDuplicateBarcode = isExistBarcodeByBarcode(newBarcode);

        while(isDuplicateBarcode) {
            newBarcode = CommonUtil.getRandomStringWithLength(10);
            isDuplicateBarcode = isExistBarcodeByBarcode(newBarcode);
        }

        return newBarcode;
    }

    /**
     * barcode 조회 by 회원
     */
    public Barcode findByMember(Member member) {
        return barcodeRepository.findByMember(member);
    }

    /**
     * barcode 조회 by 바코드 번호
     */
    public Barcode findByBarcodeNumber(String barcode) {
        return barcodeRepository.findById(barcode)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BARCODE));
    }

    /**
     * barcode 저장
     */
    public Barcode save(Barcode barcode) {
        return barcodeRepository.save(barcode);
    }
}

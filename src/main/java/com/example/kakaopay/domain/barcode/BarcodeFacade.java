package com.example.kakaopay.domain.barcode;

import com.example.kakaopay.domain.barcode.dto.SaveBarcodeResponse;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.member.MemberService;
import com.example.kakaopay.common.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BarcodeFacade {
    private final MemberService memberService;
    private final BarcodeService barcodeService;

    /**
     * 바코드 생성
     * # 이미 발급된 id의 요청이면 기존 멤버십 바코드를 반환한다
     */
    @Transactional
    public SaveBarcodeResponse createBarcodeByMemberId(String memberId) {
        // 요청 회원 정보 확인
        Member member = memberService.findByMemberId(memberId);
        Barcode findBarcode = barcodeService.findByMember(member);

        // 이미 바코드를 가지고 있는 회원일 경우
        if(findBarcode != null) {
            return SaveBarcodeResponse.fromEntity(memberId, findBarcode);
        }

        // 바코드 생성 및 바코드 중복 확인 (중복 안될 떄까지 재생성)
        String newBarcode = CommonUtil.getRandomStringWithLength(10);
        String finalBarcode = barcodeService.keepCreatingBarcodeTillNotExist(newBarcode);

        Barcode saveBarcode = new Barcode(finalBarcode, member);

        // 바코드 저장
        barcodeService.save(saveBarcode);

        return SaveBarcodeResponse.fromEntity(memberId, saveBarcode);
    }
}

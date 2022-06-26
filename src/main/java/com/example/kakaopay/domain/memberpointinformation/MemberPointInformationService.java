package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.domain.memberpointinformation.dto.MemberPointInformationRequest;
import com.example.kakaopay.exception.cumtom.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointInformationService {
    private final MemberPointInformationRepository memberPointInformationRepository;

    @Transactional
    public MemberPointInformation save(MemberPointInformationRequest request) {
        // 포인트 조회
        MemberPointInformation memberPoint = getMemberPointInformation(request).orElse(null);

        // 포인트 저장 시작
        // 조회시 존재하지 않는다면 최초 포인트 적립임 (더티체킹 불가)
        if (ObjectUtils.isEmpty(memberPoint)) {
            // 복합키로 동시성 방어
            MemberPointInformation saveData = new MemberPointInformation(request.getMember(), request.getMerchant(), request.getMerchant().getBusinessType(), request.getPoint());
            return memberPointInformationRepository.save(saveData);
        }

        memberPoint.addPoint(request.getPoint());

        return memberPoint;
    }

    @Transactional
    public MemberPointInformation use(MemberPointInformationRequest request) {
        // 포인트 조회
        MemberPointInformation memberPoint = getMemberPointInformation(request)
                                                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POINT));
        // 포인트 사용
        memberPoint.subPoint(request.getPoint());

        return memberPoint;
    }

    private Optional<MemberPointInformation> getMemberPointInformation(MemberPointInformationRequest request) {
        return Optional.ofNullable(memberPointInformationRepository.findByMemberAndMerchantAndBusinessType(
                request.getMember(),
                request.getMerchant(),
                request.getMerchant().getBusinessType()));
    }
}

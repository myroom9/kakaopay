package com.example.kakaopay.domain.member;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.common.ModelMapperUtil;
import com.example.kakaopay.domain.member.dto.MemberSaveRequest;
import com.example.kakaopay.exception.cumtom.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 생성
     */
    public Member save(MemberSaveRequest request) {
        Member member = new Member();
        member.addMember(request);
        return memberRepository.save(member);
    }

    public Member findByMemberId(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }
}

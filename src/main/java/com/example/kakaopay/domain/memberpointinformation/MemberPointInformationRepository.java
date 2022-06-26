package com.example.kakaopay.domain.memberpointinformation;

import com.example.kakaopay.domain.businesstype.BusinessType;
import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.domain.merchant.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPointInformationRepository extends JpaRepository<MemberPointInformation, Long> {

    MemberPointInformation findByMemberAndMerchantAndBusinessType(Member member,
                                                                  Merchant merchant,
                                                                  BusinessType businessType);
}

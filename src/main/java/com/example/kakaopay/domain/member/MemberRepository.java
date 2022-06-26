package com.example.kakaopay.domain.member;

import com.example.kakaopay.domain.barcode.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

}

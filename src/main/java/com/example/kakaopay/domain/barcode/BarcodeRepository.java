package com.example.kakaopay.domain.barcode;

import com.example.kakaopay.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarcodeRepository extends JpaRepository<Barcode, String> {
    Barcode findByBarcodeAndMember(String barcode, Member member);
    Barcode findByMember(Member member);
}

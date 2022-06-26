package com.example.kakaopay.domain.businesstype;

import com.example.kakaopay.type.BusinessNameType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessTypeRepository extends JpaRepository<BusinessType, Long> {
    BusinessType findByName(String name);
}

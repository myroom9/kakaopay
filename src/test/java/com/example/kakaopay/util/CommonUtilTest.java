package com.example.kakaopay.util;

import com.example.kakaopay.common.CommonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonUtilTest {
    @Test
    @DisplayName("바코드 번호 생성 테스트")
    void randomStringTest() {
        String randomString = CommonUtil.getRandomStringWithLength(10);
        String compareRandomString = CommonUtil.getRandomStringWithLength(10);
        String lengthTest = CommonUtil.getRandomStringWithLength(11);

        Assertions.assertThat(randomString).hasSize(10);
        Assertions.assertThat(randomString).isNotEqualTo(compareRandomString);

        Assertions.assertThat(lengthTest).hasSize(11);
    }
}
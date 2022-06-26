package com.example.kakaopay.domain.barcode;

import com.example.kakaopay.domain.member.Member;
import com.example.kakaopay.common.CommonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BarcodeServiceTest {

    @Mock
    BarcodeRepository barcodeRepository;
    @InjectMocks
    BarcodeService barcodeService;

    public Member getMockMember() {
        return  new Member("whahn", "name");
    }

    public Optional<Barcode> getOptionalBarcode(Member member, String barcode) {
        return Optional.of(new Barcode(barcode, member));
    }

    public Barcode getBarcode(Member member, String barcode) {
        return new Barcode(barcode, member);
    }

    @Test
    @DisplayName("[성공] 바코드 중복 없음 테스트")
    void keepCreatingBarcodeTillNotExistNotDuplicateTest() {
        String newBarcode = CommonUtil.getRandomStringWithLength(10);

        Mockito.when(barcodeRepository.findById(newBarcode))
                        .thenReturn(Optional.empty());

        String barcode = barcodeService.keepCreatingBarcodeTillNotExist(newBarcode);
        Assertions.assertThat(barcode).isEqualTo(newBarcode);
    }

    @Test
    @DisplayName("[예외] 바코드 중복 있음 테스트")
    void keepCreatingBarcodeTillNotExistDuplicateTest() {
        String newBarcode = CommonUtil.getRandomStringWithLength(10);

        Mockito.when(barcodeRepository.findById(newBarcode))
                .thenReturn(Optional.of(new Barcode()));

        String barcode = barcodeService.keepCreatingBarcodeTillNotExist(newBarcode);
        Assertions.assertThat(barcode).isNotEqualTo(newBarcode);
    }


    @Test
    @DisplayName("[성공] barcode 중복 데이터가 있는 경우")
    void isExistBarcodeByBarcodeSuccessTest() {
        String barcode = CommonUtil.getRandomStringWithLength(10);

        Member member = getMockMember();

        Optional<Barcode> responseBarcode = getOptionalBarcode(member, barcode);

        Mockito.when(barcodeRepository.findById(barcode))
                .thenReturn(responseBarcode);

        boolean result = barcodeService.isExistBarcodeByBarcode(barcode);
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[성공] barcode 중복 데이터가 없는 경우")
    void isNotExistBarcodeByBarcodeSuccessTest() {
        String barcode = CommonUtil.getRandomStringWithLength(10);

        Mockito.when(barcodeRepository.findById(barcode))
                .thenReturn(Optional.empty());

        boolean result = barcodeService.isExistBarcodeByBarcode(barcode);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[성공] 회원 정보로 바코드 조회 결과 있는 경우")
    void findByMemberExistTest() {
        String barcode = CommonUtil.getRandomStringWithLength(10);
        Member member = getMockMember();
        Barcode responseBarcode = getBarcode(member, barcode);

        Mockito.when(barcodeRepository.findByMember(member))
                .thenReturn(responseBarcode);

        Barcode resultBarcode = barcodeService.findByMember(member);
        Assertions.assertThat(resultBarcode.getBarcode()).isEqualTo(barcode);
        Assertions.assertThat(resultBarcode.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("[성공] 회원 정보로 바코드 조회 결과 없는 경우")
    void findByMemberNotExistTest() {
        Member member = getMockMember();

        Mockito.when(barcodeRepository.findByMember(member))
                .thenReturn(null);

        Barcode resultBarcode = barcodeService.findByMember(member);
        Assertions.assertThat(resultBarcode).isNull();
    }
}
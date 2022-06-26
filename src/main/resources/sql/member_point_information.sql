CREATE TABLE IF NOT EXISTS `kakaopay`.`member_point_information` (
                                                       `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '회원 포인트 정보 테이블 일련번호',
                                                       `members_id` VARCHAR(9) NOT NULL COMMENT '회원 아이디',
                                                       `merchants_id` BIGINT UNSIGNED NOT NULL COMMENT '가맹점 테이블 일련번호',
                                                       `business_type_id` INT NOT NULL COMMENT '가맹점 업종 타입 일련번호',
                                                       `amount` DECIMAL(20,2) NOT NULL DEFAULT 0.00 COMMENT '회원의 현재 남은 포인트 금액',
                                                       `version` INT NOT NULL DEFAULT 0 COMMENT '테이블 낙관적락 version 관리 컬럼',
                                                       `created_at` TIMESTAMP NOT NULL COMMENT '데이터 생성시간',
                                                       `updated_at` TIMESTAMP NULL COMMENT '데이터 수정시간',
                                                       `deleted_at` TIMESTAMP NULL COMMENT '데이터 삭제시간')
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '회원 포인트 정보 테이블';


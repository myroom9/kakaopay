CREATE TABLE IF NOT EXISTS `kakaopay`.`barcodes` (
                                       `id` VARCHAR(10) NOT NULL COMMENT '멤버쉽 바코드 (숫자형 10자리)',
                                       `members_id` VARCHAR(9) NOT NULL COMMENT '회원 아이디',
                                       `created_at` TIMESTAMP NOT NULL COMMENT '멤버쉽 바코드 생성시간',
                                       `updated_at` TIMESTAMP NULL COMMENT '멤버쉽 바코드 수정시간',
                                       `deleted_at` TIMESTAMP NULL COMMENT '멤버쉽 바코드 삭제시간',
                                       PRIMARY KEY (`id`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '멤버쉽 바코드 테이블';

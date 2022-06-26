CREATE TABLE IF NOT EXISTS `kakaopay`.`business_type` (
                                            `id` int(11) auto_increment NOT NULL COMMENT '업종 종류 테이블 일련번호',
                                            `name` VARCHAR(30) NOT NULL COMMENT '업종명',
                                            `created_at` TIMESTAMP NOT NULL COMMENT '업종 생성시간',
                                            `updated_at` TIMESTAMP NULL COMMENT '업종 수정시간',
                                            `deleted_at` TIMESTAMP NULL COMMENT '업종 삭제시간',
                                            PRIMARY KEY (`id`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '가맹점 업종 종류 테이블';

CREATE TABLE IF NOT EXISTS `kakaopay`.`merchants` (
                                        `id` BIGINT UNSIGNED auto_increment NOT NULL COMMENT '가맹점 테이블 일련번호',
                                        `business_type_id` INT NOT NULL COMMENT '가맹점 업종 테이블 일련번호',
                                        `name` VARCHAR(100) NOT NULL COMMENT '가맹점명',
                                        `created_at` TIMESTAMP NOT NULL COMMENT '가맹점 생성시간',
                                        `updated_at` TIMESTAMP NULL COMMENT '가맹점 수정시간',
                                        `deleted_at` TIMESTAMP NULL COMMENT '가맹점 삭제시간',
                                        PRIMARY KEY (`id`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '가맹점 테이블';

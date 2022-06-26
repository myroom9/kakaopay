CREATE TABLE IF NOT EXISTS `kakaopay`.`members` (
                                      `id` VARCHAR(9) NOT NULL COMMENT '회원 아이디 9자리',
                                      `name` VARCHAR(50) NOT NULL COMMENT '회원명',
                                      `created_at` TIMESTAMP NULL COMMENT '회원 생성 시간',
                                      `updated_at` TIMESTAMP NULL COMMENT '회원 수정 시간',
                                      `deleted_at` TIMESTAMP NULL COMMENT '회원 삭제 시간',
                                      PRIMARY KEY (`id`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '회원 테이블';
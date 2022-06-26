CREATE TABLE IF NOT EXISTS `kakaopay`.`point_transaction_histories` (
                                                          `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '포인트 적립/사용 내역 테이블 일련번호',
                                                          `members_id` VARCHAR(9) NOT NULL COMMENT '회원테이블 일련번호',
                                                          `merchants_id` BIGINT UNSIGNED NOT NULL COMMENT '가맹점 테이블 일련번호',
                                                          `business_type_id` INT NOT NULL COMMENT '가맹점 업종 타입 일련번호',
                                                          `amount` DECIMAL(20,2) NOT NULL DEFAULT 0.00 COMMENT '포인트 금액',
                                                          `transaction_type` ENUM('SAVE', 'USE') NOT NULL COMMENT '포인트 트랜잭션 타입 / SAVE=적립, USE=사용',
                                                          `created_at` TIMESTAMP NOT NULL COMMENT '포인트 적립/사용 시간',
                                                          `updated_at` TIMESTAMP NULL COMMENT '포인트 테이블 수정 시간',
                                                          `deleted_at` TIMESTAMP NULL COMMENT '포인트 데이터 삭제 시간')
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = '포인트 적립/사용 내역 테이블';

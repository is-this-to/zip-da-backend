-- 1. 회원 (사용자)
CREATE TABLE `user` (
    `user_id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '유저아이디',
    `email`         VARCHAR(100) NOT NULL COMMENT '이메일',
    `password`      VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `name`          VARCHAR(50) NOT NULL COMMENT '성명',
    `nick`          VARCHAR(50) NOT NULL COMMENT '닉네임',
    `phone`         VARCHAR(20) COMMENT '연락처',
    `role`          VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '권한',
    `refresh_token` VARCHAR(255) COMMENT '리프래시토큰',
    `created_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
    `updated_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
    `deleted_at`    DATETIME COMMENT '삭제일',

    PRIMARY KEY (`user_id`),
    CONSTRAINT `UK_user_email` UNIQUE (`email`),
    CONSTRAINT `UK_user_nick` UNIQUE (`nick`)
);

-- 2. 관리자
CREATE TABLE `admin` (
     `admin_id`     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '관리자아이디',
     `password`     VARCHAR(255) NOT NULL COMMENT '비밀번호',
     `name`         VARCHAR(50) NOT NULL COMMENT '성명',
     `created_at`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
     `updated_at`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
     `deleted_at`   DATETIME COMMENT '삭제일',

    PRIMARY KEY (`admin_id`)
);

-- 3. 중개사 추가 정보
CREATE TABLE `agent` (
     `agent_id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '중개사추가정보아이디',
     `user_id`          BIGINT UNSIGNED NOT NULL COMMENT '유저아이디',
     `license_no`       VARCHAR(50) COMMENT '공인중개사자격번호',
     `business_no`      VARCHAR(50) COMMENT '사업자등록번호',
     `office_name`      VARCHAR(100) COMMENT '중개사무소명',
     `approved_status`  VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '관리자승인상태',
     `created_at`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
     `updated_at`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
     `deleted_at`       DATETIME COMMENT '삭제일',

     PRIMARY KEY (`agent_id`)
);

-- 4. 매물
CREATE TABLE `property` (
    `property_id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '매물아이디',
    `user_id`           BIGINT UNSIGNED NOT NULL COMMENT '유저아이디',
    `description`       VARCHAR(2000) COMMENT '매물설명',
    `property_type`     VARCHAR(30) COMMENT '매물유형',
    `apartment_id`      BIGINT UNSIGNED COMMENT '매물의 아파트',
    `transaction_type`  VARCHAR(20) COMMENT '거래유형',
    `price`             BIGINT UNSIGNED COMMENT '매매가격',
    `deposit`           BIGINT UNSIGNED COMMENT '보증금',
    `monthly_rent`      BIGINT UNSIGNED COMMENT '월세',
    `maintenance_fee`   BIGINT UNSIGNED COMMENT '관리비',
    `region_id`         VARCHAR(100) COMMENT '지역아이디',
    `detail_address`    VARCHAR(200) COMMENT '상세주소',
    `area_m2`           DECIMAL(6,2) COMMENT '전용면적',
    `room_count`        INT COMMENT '방개수',
    `bathroom_count`    INT COMMENT '욕실개수',
    `floor`             INT COMMENT '층수',
    `move_in_date`      DATE COMMENT '입주가능일',
    `source_type`       VARCHAR(20) COMMENT '출처유형',
    `status`            VARCHAR(20) COMMENT '상태',
    `created_at`        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
    `updated_at`        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
    `deleted_at`        DATETIME COMMENT '삭제일',

    PRIMARY KEY (`property_id`)
);

-- 5. 중개사 이미지
CREATE TABLE `agent_image` (
   `image_id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '이미지아이디',
   `user_id`    BIGINT UNSIGNED NOT NULL COMMENT '유저아이디',
   `image_url`  VARCHAR(255) COMMENT '이미지경로',
   `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
   `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
   `deleted_at` DATETIME COMMENT '삭제일',

   PRIMARY KEY (`image_id`)
);

-- 6. 매물 이미지
CREATE TABLE `property_image` (
  `image_id`      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '이미지아이디',
  `property_id`   BIGINT UNSIGNED NOT NULL COMMENT '매물아이디',
  `image_url`     VARCHAR(255) COMMENT '이미지경로',
  `is_thumbnail`  BOOLEAN COMMENT '대표이미지여부',
  `sort_order`    INT COMMENT '정렬순서',
  `created_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
  `updated_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `deleted_at`    DATETIME COMMENT '삭제일',

  PRIMARY KEY (`image_id`)
);

-- 7. 관심 매물 (찜)
CREATE TABLE `bookmark` (
    `bookmark_id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '관심매물아이디',
    `user_id`       BIGINT UNSIGNED NOT NULL COMMENT '유저아이디',
    `property_id`   BIGINT UNSIGNED NOT NULL COMMENT '매물아이디',
    `created_at`    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',

    PRIMARY KEY (`bookmark_id`)
);

-- 8. 검색 조건 저장
CREATE TABLE `saved_search` (
    `search_id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '검색조건저장아이디',
    `user_id`           BIGINT UNSIGNED COMMENT '유저아이디',
    `region_id`         VARCHAR(100) COMMENT '지역',
    `property_type`     VARCHAR(30) COMMENT '매물유형',
    `transaction_type`  VARCHAR(20) COMMENT '거래유형',
    `min_price`         BIGINT UNSIGNED COMMENT '최소가격',
    `max_price`         BIGINT UNSIGNED COMMENT '최대가격',
    `min_deposit`       BIGINT UNSIGNED COMMENT '최소보증금',
    `max_deposit`       BIGINT UNSIGNED COMMENT '최대보증금',
    `min_monthly_rent`  BIGINT UNSIGNED COMMENT '최소월세',
    `max_monthly_rent`  BIGINT UNSIGNED COMMENT '최대월세',
    `created_at`        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',

    PRIMARY KEY (`search_id`)
);

-- 9. 채팅방
CREATE TABLE `chat_room` (
     `chat_room_id`     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '채팅방아이디',
     `property_id`      BIGINT UNSIGNED NOT NULL COMMENT '매물아이디',
     `user_id`          BIGINT UNSIGNED NOT NULL COMMENT '유저아이디',
     `agent_id`         BIGINT UNSIGNED NOT NULL COMMENT '중개사아이디',
     `created_at`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
     `updated_at`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
     `deleted_at`       DATETIME COMMENT '삭제일',

     PRIMARY KEY (`chat_room_id`)
);

-- 10. 채팅 메시지
CREATE TABLE `chat_message` (
    `message_id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '채팅메시지아이디',
    `chat_room_id`  BIGINT UNSIGNED NOT NULL COMMENT '채팅방아이디',
    `sender_id`     BIGINT UNSIGNED NOT NULL COMMENT '보낸이아이디',
    `message`       VARCHAR(2000) COMMENT '메시지내용',
    `is_read`       BOOLEAN COMMENT '읽음여부',
    `created_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
    `updated_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
    `deleted_at`    DATETIME COMMENT '삭제일',

    PRIMARY KEY (`message_id`)
);

-- 11. 신고 내역
CREATE TABLE `report` (
  `report_id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '신고내역아이디',
  `property_id` BIGINT UNSIGNED NOT NULL COMMENT '매물아이디',
  `user_id`     BIGINT UNSIGNED NOT NULL COMMENT '유저아이디',
  `reason`      VARCHAR(255) COMMENT '신고사유',
  `status`      VARCHAR(20) COMMENT '상태',
  `created_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
  `updated_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `deleted_at`  DATETIME COMMENT '삭제일',

  PRIMARY KEY (`report_id`)
);

-- 12. 지역
CREATE TABLE `region` (
  `region_id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '지역아이디',
  `sido`        VARCHAR(100) COMMENT '시도',
  `sigungu`     VARCHAR(100) COMMENT '시군구',
  `dong`        VARCHAR(100) COMMENT '동읍면리',

  PRIMARY KEY (`region_id`)
);

-- 13. 검색조건저장 옵션
CREATE TABLE `saved_search_option` (
   `search_id`      BIGINT UNSIGNED NOT NULL COMMENT '검색조건저장아이디',
   `option_id`      BIGINT UNSIGNED NOT NULL COMMENT '옵션아이디',
   `option_value`   VARCHAR(20) COMMENT '옵션값'
);

-- 14. 매물 옵션
CREATE TABLE `property_option` (
   `property_id`    BIGINT UNSIGNED NOT NULL COMMENT '매물아이디',
   `option_id`      BIGINT UNSIGNED NOT NULL COMMENT '옵션아이디',
   `option_value`   VARCHAR(20) COMMENT '옵션값'
);

-- 15. 옵션
CREATE TABLE `option` (
  `option_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '옵션아이디',
  `option_name` VARCHAR(20) COMMENT '옵션명',

  PRIMARY KEY (`option_id`)
);
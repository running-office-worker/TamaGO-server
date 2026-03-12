-- 테이블 리네이밍
ALTER TABLE t_received_letters RENAME TO t_user_letters;

-- PK 컬럼 리네이밍
ALTER TABLE t_user_letters CHANGE received_letter_id user_letter_id BIGINT AUTO_INCREMENT NOT NULL;

-- read_status → letter_status 컬럼 변경 (SCHEDULED 추가)
ALTER TABLE t_user_letters CHANGE read_status letter_status ENUM('SCHEDULED', 'UNREAD', 'READ') DEFAULT 'UNREAD';

-- scheduled_at 컬럼 추가
ALTER TABLE t_user_letters ADD COLUMN scheduled_at DATETIME(6) NULL AFTER letter_status;

-- 스케줄러 조회용 인덱스
CREATE INDEX idx_user_letters_status_scheduled ON t_user_letters (letter_status, scheduled_at);

-- LetterSchedule 테이블 삭제
DROP TABLE IF EXISTS t_letter_schedules;

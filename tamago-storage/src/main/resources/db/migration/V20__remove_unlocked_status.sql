-- 기존 UNLOCKED 레코드를 OWNED로 전환 (혹시 있을 경우 대비)
UPDATE t_owned_monsters SET status = 'OWNED' WHERE status = 'UNLOCKED';
-- ENUM에서 UNLOCKED 제거
ALTER TABLE t_owned_monsters MODIFY COLUMN status ENUM('OWNED') NULL;

-- t_monster_unlock_policy의 rule_type ENUM에 KILOMETER 추가 후 TOTAL_KM 데이터 변환
ALTER TABLE t_monster_unlock_policy MODIFY COLUMN rule_type ENUM('TOTAL_KM', 'KILOMETER') NULL;
UPDATE t_monster_unlock_policy SET rule_type = 'KILOMETER' WHERE rule_type = 'TOTAL_KM';
ALTER TABLE t_monster_unlock_policy MODIFY COLUMN rule_type ENUM('KILOMETER') NULL;

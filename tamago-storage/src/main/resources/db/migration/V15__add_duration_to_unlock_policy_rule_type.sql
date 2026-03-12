-- t_monster_evolution_policy: KILOMETER → MONSTER_KILOMETER 변경
ALTER TABLE t_monster_evolution_policy MODIFY COLUMN rule_type ENUM('KILOMETER', 'MONSTER_KILOMETER') NULL;
UPDATE t_monster_evolution_policy SET rule_type = 'MONSTER_KILOMETER' WHERE rule_type = 'KILOMETER';
ALTER TABLE t_monster_evolution_policy MODIFY COLUMN rule_type ENUM('MONSTER_KILOMETER') NULL;

-- t_monster_unlock_policy: KILOMETER → TOTAL_KILOMETER 변경 + TOTAL_DURATION 추가
ALTER TABLE t_monster_unlock_policy MODIFY COLUMN rule_type ENUM('KILOMETER', 'TOTAL_KILOMETER', 'TOTAL_DURATION') NULL;
UPDATE t_monster_unlock_policy SET rule_type = 'TOTAL_KILOMETER' WHERE rule_type = 'KILOMETER';
ALTER TABLE t_monster_unlock_policy MODIFY COLUMN rule_type ENUM('TOTAL_KILOMETER', 'TOTAL_DURATION') NULL;

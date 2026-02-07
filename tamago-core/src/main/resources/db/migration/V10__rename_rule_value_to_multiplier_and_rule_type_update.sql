ALTER TABLE t_monster_evolution_policy CHANGE COLUMN rule_value multiplier INT;

UPDATE t_monster_evolution_policy SET rule_type = 'KILOMETER' WHERE rule_type = 'TOTAL_KM';

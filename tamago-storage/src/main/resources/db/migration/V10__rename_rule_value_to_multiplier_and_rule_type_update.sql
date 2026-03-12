ALTER TABLE t_monster_evolution_policy CHANGE COLUMN rule_value multiplier INT;

ALTER TABLE t_monster_evolution_policy MODIFY COLUMN rule_type ENUM('TOTAL_KM', 'KILOMETER') NULL;
UPDATE t_monster_evolution_policy SET rule_type = 'KILOMETER' WHERE rule_type = 'TOTAL_KM';

ALTER TABLE t_owned_monsters CHANGE COLUMN earned_xp having_xp INT;

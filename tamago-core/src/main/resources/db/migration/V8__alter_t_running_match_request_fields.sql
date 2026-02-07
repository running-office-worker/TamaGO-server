ALTER TABLE t_running CHANGE COLUMN kilometre distance DOUBLE;
ALTER TABLE t_running ADD COLUMN cadence INT AFTER pace;
ALTER TABLE t_running CHANGE COLUMN kcal calories INT;
ALTER TABLE t_running DROP COLUMN time;
ALTER TABLE t_running ADD COLUMN elevation_gain DOUBLE AFTER distance;
ALTER TABLE t_running ADD COLUMN heartbeat INT AFTER elevation_gain;
ALTER TABLE t_running ADD COLUMN owned_monster_id BIGINT NOT NULL AFTER user_id;

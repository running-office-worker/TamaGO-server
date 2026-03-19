ALTER TABLE t_monsters
    ADD COLUMN monster_type VARCHAR(20) NULL DEFAULT NULL AFTER nickname;

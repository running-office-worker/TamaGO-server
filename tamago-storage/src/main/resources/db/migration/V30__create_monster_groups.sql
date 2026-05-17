CREATE TABLE IF NOT EXISTS t_monster_groups
(
    monster_group_id BIGINT AUTO_INCREMENT NOT NULL,
    code             VARCHAR(50) NOT NULL,
    name             VARCHAR(255) NULL,
    created_at       datetime(6) NULL,
    updated_at       datetime(6) NULL,
    deleted_at       datetime(6) NULL,
    CONSTRAINT pk_t_monster_groups PRIMARY KEY (monster_group_id),
    CONSTRAINT uk_t_monster_groups_code UNIQUE (code)
);

ALTER TABLE t_monsters
    ADD COLUMN monster_group_id BIGINT NULL AFTER monster_id,
    ADD COLUMN evolution_stage INT NULL AFTER monster_group_id;

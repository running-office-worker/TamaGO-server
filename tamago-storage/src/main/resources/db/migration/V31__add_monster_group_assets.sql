DELETE FROM t_monster_assets
WHERE asset_type = 'B_PNG';

ALTER TABLE t_monster_assets
    MODIFY COLUMN asset_type ENUM ('M_LOTTIE', 'M_SVG', 'S_SVG', 'S_PNG', 'M_PNG', 'LBG_PNG', 'RBG_PNG') NULL;

CREATE TABLE IF NOT EXISTS t_monster_group_assets
(
    monster_group_asset_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at             datetime(6) NULL,
    updated_at             datetime(6) NULL,
    deleted_at             datetime(6) NULL,
    monster_group_id       BIGINT NOT NULL,
    asset_key              VARCHAR(255) NULL,
    asset_name             VARCHAR(255) NULL,
    asset_type             ENUM ('LBG_PNG', 'RBG_PNG') NULL,
    CONSTRAINT pk_t_monster_group_assets PRIMARY KEY (monster_group_asset_id)
);

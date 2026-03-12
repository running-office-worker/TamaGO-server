-- ==============================================
-- t_monsters: nickname, evolution_xp 컬럼 추가
-- ==============================================
ALTER TABLE t_monsters ADD COLUMN nickname VARCHAR(255) NULL;
ALTER TABLE t_monsters ADD COLUMN evolution_xp INT NULL;

-- ==============================================
-- t_owned_monsters: earned_xp, status 컬럼 추가
-- ==============================================
ALTER TABLE t_owned_monsters ADD COLUMN earned_xp INT NULL;
ALTER TABLE t_owned_monsters ADD COLUMN status ENUM('UNLOCKED', 'OWNED') NULL;

-- ==============================================
-- t_letters: mood 컬럼 삭제
-- ==============================================
ALTER TABLE t_letters DROP COLUMN mood;

-- ==============================================
-- t_monster_unlock_policy (신규)
-- ==============================================
CREATE TABLE IF NOT EXISTS t_monster_unlock_policy
(
    monster_unlock_policy_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at               datetime(6) NULL,
    updated_at               datetime(6) NULL,
    deleted_at               datetime(6) NULL,
    monster_id               BIGINT       NOT NULL,
    rule_type                ENUM ('TOTAL_KM') NULL,
    rule_value               INT NULL,
    description              VARCHAR(255) NULL,
    CONSTRAINT pk_t_monster_unlock_policy PRIMARY KEY (monster_unlock_policy_id)
);

ALTER TABLE t_monster_unlock_policy
    ADD CONSTRAINT FK_monster_unlock_policy_monster
        FOREIGN KEY (monster_id) REFERENCES t_monsters (monster_id);

-- ==============================================
-- t_monster_evolution_policy (신규)
-- ==============================================
CREATE TABLE IF NOT EXISTS t_monster_evolution_policy
(
    monster_evolution_policy_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at                 datetime(6) NULL,
    updated_at                 datetime(6) NULL,
    deleted_at                 datetime(6) NULL,
    monster_id                 BIGINT NOT NULL,
    rule_type                  ENUM ('TOTAL_KM') NULL,
    rule_value                 INT NULL,
    CONSTRAINT pk_t_monster_evolution_policy PRIMARY KEY (monster_evolution_policy_id)
);

ALTER TABLE t_monster_evolution_policy
    ADD CONSTRAINT FK_monster_evolution_policy_monster
        FOREIGN KEY (monster_id) REFERENCES t_monsters (monster_id);

-- ==============================================
-- t_monster_assets (신규)
-- ==============================================
CREATE TABLE IF NOT EXISTS t_monster_assets
(
    monster_asset_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime(6) NULL,
    updated_at       datetime(6) NULL,
    deleted_at       datetime(6) NULL,
    monster_id       BIGINT NOT NULL,
    asset_key        VARCHAR(255) NULL,
    asset_type       ENUM ('PNG', 'GIF', 'LOTTIE') NULL,
    CONSTRAINT pk_t_monster_assets PRIMARY KEY (monster_asset_id)
);

-- ==============================================
-- t_running_routes (신규)
-- ==============================================
CREATE TABLE IF NOT EXISTS t_running_routes
(
    running_route_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime(6) NULL,
    updated_at       datetime(6) NULL,
    deleted_at       datetime(6) NULL,
    running_id       BIGINT NOT NULL,
    route            LINESTRING NULL,
    start_point      POINT NULL,
    end_point        POINT NULL,
    CONSTRAINT pk_t_running_routes PRIMARY KEY (running_route_id)
);

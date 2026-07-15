-- ==============================================
-- 몬스터 시드 데이터 (3개 진화 체인 + 진화 정책 + 해금 정책)
-- Repeatable migration: 내용 변경 시 자동 재실행
-- ==============================================

-- 기존 시드 데이터 정리
DELETE FROM t_monster_assets WHERE monster_id IN (SELECT monster_id FROM t_monsters WHERE nickname IN ('거북이', '파이리', '팽귄'));
DELETE FROM t_monster_group_assets WHERE monster_group_id IN (
    SELECT monster_group_id FROM t_monster_groups WHERE code IN ('TURTLE', 'FIRE', 'SNOW')
);
DELETE FROM t_monster_evolution_policy WHERE monster_id IN (SELECT monster_id FROM t_monsters WHERE nickname IN ('거북이', '파이리', '팽귄'));
DELETE FROM t_monster_unlock_policy WHERE monster_id IN (SELECT monster_id FROM t_monsters WHERE nickname IN ('거북이', '파이리', '팽귄'));
DELETE FROM t_monsters WHERE nickname IN ('거북이', '파이리', '팽귄');
DELETE FROM t_monster_groups WHERE code IN ('TURTLE', 'FIRE', 'PENGUIN', 'SNOW');

-- ==============================================
-- 1. 거북이 진화 체인 (기본 몬스터, 해금 조건 없음)
-- 진화: 1km → 3km → 10km
-- ==============================================

INSERT INTO t_monster_groups (code, name, created_at, updated_at)
VALUES ('TURTLE', '거북이', NOW(), NOW());
SET @turtle_group = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, monster_type, evolution_xp, created_at, updated_at)
VALUES (@turtle_group, 1, '거북이', 'DEFAULT', 10, NOW(), NOW());
SET @turtle_1 = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@turtle_group, 2, '거북이', 30, NOW(), NOW());
SET @turtle_2 = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@turtle_group, 3, '거북이', 100, NOW(), NOW());
SET @turtle_3 = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@turtle_group, 4, '거북이', NULL, NOW(), NOW());
SET @turtle_4 = LAST_INSERT_ID();

-- 거북이 진화 체인 연결
UPDATE t_monsters SET next_monster_id = @turtle_2 WHERE monster_id = @turtle_1;
UPDATE t_monsters SET previous_monster_id = @turtle_1, next_monster_id = @turtle_3 WHERE monster_id = @turtle_2;
UPDATE t_monsters SET previous_monster_id = @turtle_2, next_monster_id = @turtle_4 WHERE monster_id = @turtle_3;
UPDATE t_monsters SET previous_monster_id = @turtle_3 WHERE monster_id = @turtle_4;

-- 거북이 진화 정책 (MONSTER_KILOMETER, multiplier = km당 XP)
INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (@turtle_1, 'MONSTER_KILOMETER', 10, NOW(), NOW());

INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (@turtle_2, 'MONSTER_KILOMETER', 10, NOW(), NOW());

INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (@turtle_3, 'MONSTER_KILOMETER', 10, NOW(), NOW());

-- ==============================================
-- 2. 파이리 진화 체인 (해금 조건: 누적 1시간 러닝)
-- 진화: 5km → 15km → 30km
-- ==============================================

INSERT INTO t_monster_groups (code, name, created_at, updated_at)
VALUES ('FIRE', '파이리', NOW(), NOW());
SET @fire_group = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@fire_group, 1, '파이리', 50, NOW(), NOW());
SET @fire_1 = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@fire_group, 2, '파이리', 150, NOW(), NOW());
SET @fire_2 = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@fire_group, 3, '파이리', 300, NOW(), NOW());
SET @fire_3 = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@fire_group, 4, '파이리', NULL, NOW(), NOW());
SET @fire_4 = LAST_INSERT_ID();

-- 파이리 진화 체인 연결
UPDATE t_monsters SET next_monster_id = @fire_2 WHERE monster_id = @fire_1;
UPDATE t_monsters SET previous_monster_id = @fire_1, next_monster_id = @fire_3 WHERE monster_id = @fire_2;
UPDATE t_monsters SET previous_monster_id = @fire_2, next_monster_id = @fire_4 WHERE monster_id = @fire_3;
UPDATE t_monsters SET previous_monster_id = @fire_3 WHERE monster_id = @fire_4;

-- 파이리 진화 정책 (MONSTER_KILOMETER, multiplier = km당 XP)
INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (@fire_1, 'MONSTER_KILOMETER', 10, NOW(), NOW());

INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (@fire_2, 'MONSTER_KILOMETER', 10, NOW(), NOW());

INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (@fire_3, 'MONSTER_KILOMETER', 10, NOW(), NOW());

-- 파이리 해금 정책 (누적 60분 러닝)
INSERT INTO t_monster_unlock_policy (monster_id, rule_type, rule_value, description, created_at, updated_at)
VALUES (@fire_1, 'TOTAL_DURATION', 60, '누적 1시간 러닝 달성 시 해금', NOW(), NOW());

-- ==============================================
-- 3. 팽귄 진화 체인 (해금 조건: 누적 30km 러닝)
-- 진화: 10km → 25km → 50km
-- ==============================================

INSERT INTO t_monster_groups (code, name, created_at, updated_at)
VALUES ('SNOW', '팽귄', NOW(), NOW());
SET @penguin_group = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@penguin_group, 1, '팽귄', 100, NOW(), NOW());
SET @penguin_1 = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@penguin_group, 2, '팽귄', 250, NOW(), NOW());
SET @penguin_2 = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@penguin_group, 3, '팽귄', 500, NOW(), NOW());
SET @penguin_3 = LAST_INSERT_ID();

INSERT INTO t_monsters (monster_group_id, evolution_stage, nickname, evolution_xp, created_at, updated_at)
VALUES (@penguin_group, 4, '팽귄', NULL, NOW(), NOW());
SET @penguin_4 = LAST_INSERT_ID();

-- 팽귄 진화 체인 연결
UPDATE t_monsters SET next_monster_id = @penguin_2 WHERE monster_id = @penguin_1;
UPDATE t_monsters SET previous_monster_id = @penguin_1, next_monster_id = @penguin_3 WHERE monster_id = @penguin_2;
UPDATE t_monsters SET previous_monster_id = @penguin_2, next_monster_id = @penguin_4 WHERE monster_id = @penguin_3;
UPDATE t_monsters SET previous_monster_id = @penguin_3 WHERE monster_id = @penguin_4;

-- 팽귄 진화 정책 (MONSTER_KILOMETER, multiplier = km당 XP)
INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (@penguin_1, 'MONSTER_KILOMETER', 10, NOW(), NOW());

INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (@penguin_2, 'MONSTER_KILOMETER', 10, NOW(), NOW());

INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (@penguin_3, 'MONSTER_KILOMETER', 10, NOW(), NOW());

-- 팽귄 해금 정책 (누적 30km 러닝)
INSERT INTO t_monster_unlock_policy (monster_id, rule_type, rule_value, description, created_at, updated_at)
VALUES (@penguin_1, 'TOTAL_KILOMETER', 30, '누적 30km 러닝 달성 시 해금', NOW(), NOW());

-- ==============================================
-- 4. 몬스터 자산
-- S3 key: dev/monster/{monster_group_code}/{evolution_stage}/{file_name}
-- ==============================================

INSERT INTO t_monster_assets (monster_id, asset_key, asset_name, asset_type, created_at, updated_at)
VALUES
    (@turtle_1, 'dev/monster/turtle/1/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@turtle_1, 'dev/monster/turtle/1/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),
    (@turtle_2, 'dev/monster/turtle/2/M_LOTTIE.lottie', 'M_LOTTIE.lottie', 'M_LOTTIE', NOW(), NOW()),
    (@turtle_2, 'dev/monster/turtle/2/M_GIF.gif', 'M_GIF.gif', 'M_GIF', NOW(), NOW()),
    (@turtle_2, 'dev/monster/turtle/2/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@turtle_2, 'dev/monster/turtle/2/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),
    (@turtle_3, 'dev/monster/turtle/3/M_LOTTIE.lottie', 'M_LOTTIE.lottie', 'M_LOTTIE', NOW(), NOW()),
    (@turtle_3, 'dev/monster/turtle/3/M_GIF.gif', 'M_GIF.gif', 'M_GIF', NOW(), NOW()),
    (@turtle_3, 'dev/monster/turtle/3/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@turtle_3, 'dev/monster/turtle/3/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),
    (@turtle_4, 'dev/monster/turtle/4/M_LOTTIE.lottie', 'M_LOTTIE.lottie', 'M_LOTTIE', NOW(), NOW()),
    (@turtle_4, 'dev/monster/turtle/4/M_GIF.gif', 'M_GIF.gif', 'M_GIF', NOW(), NOW()),
    (@turtle_4, 'dev/monster/turtle/4/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@turtle_4, 'dev/monster/turtle/4/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),

    (@fire_1, 'dev/monster/fire/1/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@fire_1, 'dev/monster/fire/1/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),
    (@fire_2, 'dev/monster/fire/2/M_LOTTIE.lottie', 'M_LOTTIE.lottie', 'M_LOTTIE', NOW(), NOW()),
    (@fire_2, 'dev/monster/fire/2/M_GIF.gif', 'M_GIF.gif', 'M_GIF', NOW(), NOW()),
    (@fire_2, 'dev/monster/fire/2/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@fire_2, 'dev/monster/fire/2/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),
    (@fire_3, 'dev/monster/fire/3/M_LOTTIE.lottie', 'M_LOTTIE.lottie', 'M_LOTTIE', NOW(), NOW()),
    (@fire_3, 'dev/monster/fire/3/M_GIF.gif', 'M_GIF.gif', 'M_GIF', NOW(), NOW()),
    (@fire_3, 'dev/monster/fire/3/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@fire_3, 'dev/monster/fire/3/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),
    (@fire_4, 'dev/monster/fire/4/M_LOTTIE.lottie', 'M_LOTTIE.lottie', 'M_LOTTIE', NOW(), NOW()),
    (@fire_4, 'dev/monster/fire/4/M_GIF.gif', 'M_GIF.gif', 'M_GIF', NOW(), NOW()),
    (@fire_4, 'dev/monster/fire/4/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@fire_4, 'dev/monster/fire/4/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),

    (@penguin_1, 'dev/monster/snow/1/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@penguin_1, 'dev/monster/snow/1/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),
    (@penguin_2, 'dev/monster/snow/2/M_LOTTIE.lottie', 'M_LOTTIE.lottie', 'M_LOTTIE', NOW(), NOW()),
    (@penguin_2, 'dev/monster/snow/2/M_GIF.gif', 'M_GIF.gif', 'M_GIF', NOW(), NOW()),
    (@penguin_2, 'dev/monster/snow/2/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@penguin_2, 'dev/monster/snow/2/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),
    (@penguin_3, 'dev/monster/snow/3/M_LOTTIE.lottie', 'M_LOTTIE.lottie', 'M_LOTTIE', NOW(), NOW()),
    (@penguin_3, 'dev/monster/snow/3/M_GIF.gif', 'M_GIF.gif', 'M_GIF', NOW(), NOW()),
    (@penguin_3, 'dev/monster/snow/3/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@penguin_3, 'dev/monster/snow/3/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW()),
    (@penguin_4, 'dev/monster/snow/4/M_LOTTIE.lottie', 'M_LOTTIE.lottie', 'M_LOTTIE', NOW(), NOW()),
    (@penguin_4, 'dev/monster/snow/4/M_GIF.gif', 'M_GIF.gif', 'M_GIF', NOW(), NOW()),
    (@penguin_4, 'dev/monster/snow/4/M_PNG.png', 'M_PNG.png', 'M_PNG', NOW(), NOW()),
    (@penguin_4, 'dev/monster/snow/4/S_PNG.png', 'S_PNG.png', 'S_PNG', NOW(), NOW());

-- ==============================================
-- 5. 몬스터 그룹 배경 자산
-- S3 key: dev/monster/{monster_group_code}/bg/{file_name}
-- ==============================================

INSERT INTO t_monster_group_assets (monster_group_id, asset_key, asset_name, asset_type, metadata, created_at, updated_at)
VALUES
    (@turtle_group, 'dev/monster/turtle/bg/LBG_PNG_1.png', 'LBG_PNG_1.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#59774C', 'startHour', 6, 'endHour', 9), NOW(), NOW()),
    (@turtle_group, 'dev/monster/turtle/bg/LBG_PNG_2.png', 'LBG_PNG_2.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#96A84F', 'startHour', 9, 'endHour', 12), NOW(), NOW()),
    (@turtle_group, 'dev/monster/turtle/bg/LBG_PNG_3.png', 'LBG_PNG_3.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#7BA563', 'startHour', 12, 'endHour', 17), NOW(), NOW()),
    (@turtle_group, 'dev/monster/turtle/bg/LBG_PNG_4.png', 'LBG_PNG_4.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#908440', 'startHour', 17, 'endHour', 20), NOW(), NOW()),
    (@turtle_group, 'dev/monster/turtle/bg/LBG_PNG_5.png', 'LBG_PNG_5.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#273D57', 'startHour', 20, 'endHour', 6), NOW(), NOW()),
    (@turtle_group, 'dev/monster/turtle/bg/RBG_PNG_1.png', 'RBG_PNG_1.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#59774C', 'startHour', 6, 'endHour', 9), NOW(), NOW()),
    (@turtle_group, 'dev/monster/turtle/bg/RBG_PNG_2.png', 'RBG_PNG_2.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#96A84F', 'startHour', 9, 'endHour', 12), NOW(), NOW()),
    (@turtle_group, 'dev/monster/turtle/bg/RBG_PNG_3.png', 'RBG_PNG_3.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#7BA563', 'startHour', 12, 'endHour', 17), NOW(), NOW()),
    (@turtle_group, 'dev/monster/turtle/bg/RBG_PNG_4.png', 'RBG_PNG_4.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#908440', 'startHour', 17, 'endHour', 20), NOW(), NOW()),
    (@turtle_group, 'dev/monster/turtle/bg/RBG_PNG_5.png', 'RBG_PNG_5.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#273D57', 'startHour', 20, 'endHour', 6), NOW(), NOW()),

    (@fire_group, 'dev/monster/fire/bg/LBG_PNG_1.png', 'LBG_PNG_1.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#6B6A6F', 'startHour', 9, 'endHour', 16), NOW(), NOW()),
    (@fire_group, 'dev/monster/fire/bg/LBG_PNG_2.png', 'LBG_PNG_2.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#070C1F', 'startHour', 23, 'endHour', 3), NOW(), NOW()),
    (@fire_group, 'dev/monster/fire/bg/LBG_PNG_3.png', 'LBG_PNG_3.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#171929', 'startHour', 16, 'endHour', 20), NOW(), NOW()),
    (@fire_group, 'dev/monster/fire/bg/LBG_PNG_4.png', 'LBG_PNG_4.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#202434', 'startHour', 20, 'endHour', 23), NOW(), NOW()),
    (@fire_group, 'dev/monster/fire/bg/LBG_PNG_5.png', 'LBG_PNG_5.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#000000', 'startHour', 3, 'endHour', 9), NOW(), NOW()),
    (@fire_group, 'dev/monster/fire/bg/RBG_PNG_1.png', 'RBG_PNG_1.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#6B6A6F', 'startHour', 9, 'endHour', 16), NOW(), NOW()),
    (@fire_group, 'dev/monster/fire/bg/RBG_PNG_2.png', 'RBG_PNG_2.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#070C1F', 'startHour', 23, 'endHour', 3), NOW(), NOW()),
    (@fire_group, 'dev/monster/fire/bg/RBG_PNG_3.png', 'RBG_PNG_3.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#171929', 'startHour', 16, 'endHour', 20), NOW(), NOW()),
    (@fire_group, 'dev/monster/fire/bg/RBG_PNG_4.png', 'RBG_PNG_4.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#202434', 'startHour', 20, 'endHour', 23), NOW(), NOW()),
    (@fire_group, 'dev/monster/fire/bg/RBG_PNG_5.png', 'RBG_PNG_5.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#000000', 'startHour', 3, 'endHour', 9), NOW(), NOW()),

    (@penguin_group, 'dev/monster/snow/bg/LBG_PNG_1.png', 'LBG_PNG_1.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#DAE8F9', 'startHour', 8, 'endHour', 12), NOW(), NOW()),
    (@penguin_group, 'dev/monster/snow/bg/LBG_PNG_2.png', 'LBG_PNG_2.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#ACB8EC', 'startHour', 12, 'endHour', 16), NOW(), NOW()),
    (@penguin_group, 'dev/monster/snow/bg/LBG_PNG_3.png', 'LBG_PNG_3.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#9BA6E9', 'startHour', 16, 'endHour', 21), NOW(), NOW()),
    (@penguin_group, 'dev/monster/snow/bg/LBG_PNG_4.png', 'LBG_PNG_4.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#061851', 'startHour', 21, 'endHour', 3), NOW(), NOW()),
    (@penguin_group, 'dev/monster/snow/bg/LBG_PNG_5.png', 'LBG_PNG_5.png', 'LBG_PNG', JSON_OBJECT('backgroundColor', '#7789E0', 'startHour', 3, 'endHour', 8), NOW(), NOW()),
    (@penguin_group, 'dev/monster/snow/bg/RBG_PNG_1.png', 'RBG_PNG_1.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#DAE8F9', 'startHour', 8, 'endHour', 12), NOW(), NOW()),
    (@penguin_group, 'dev/monster/snow/bg/RBG_PNG_2.png', 'RBG_PNG_2.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#ACB8EC', 'startHour', 12, 'endHour', 16), NOW(), NOW()),
    (@penguin_group, 'dev/monster/snow/bg/RBG_PNG_3.png', 'RBG_PNG_3.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#9BA6E9', 'startHour', 16, 'endHour', 21), NOW(), NOW()),
    (@penguin_group, 'dev/monster/snow/bg/RBG_PNG_4.png', 'RBG_PNG_4.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#061851', 'startHour', 21, 'endHour', 3), NOW(), NOW()),
    (@penguin_group, 'dev/monster/snow/bg/RBG_PNG_5.png', 'RBG_PNG_5.png', 'RBG_PNG', JSON_OBJECT('backgroundColor', '#7789E0', 'startHour', 3, 'endHour', 8), NOW(), NOW());

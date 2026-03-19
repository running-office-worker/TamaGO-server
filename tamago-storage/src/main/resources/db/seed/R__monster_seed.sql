-- ==============================================
-- 몬스터 시드 데이터 (3개 진화 체인 + 진화 정책 + 해금 정책)
-- Repeatable migration: 내용 변경 시 자동 재실행
-- ==============================================

-- 기존 시드 데이터 정리
DELETE FROM t_monster_evolution_policy WHERE monster_id IN (SELECT monster_id FROM t_monsters WHERE nickname IN ('거북이', '파이리', '팽귄'));
DELETE FROM t_monster_unlock_policy WHERE monster_id IN (SELECT monster_id FROM t_monsters WHERE nickname IN ('거북이', '파이리', '팽귄'));
DELETE FROM t_monsters WHERE nickname IN ('거북이', '파이리', '팽귄');

-- ==============================================
-- 1. 거북이 진화 체인 (기본 몬스터, 해금 조건 없음)
-- 진화: 1km → 3km → 10km
-- ==============================================

INSERT INTO t_monsters (nickname, monster_type, evolution_xp, created_at, updated_at)
VALUES ('거북이', 'DEFAULT', 10, NOW(), NOW());
SET @turtle_1 = LAST_INSERT_ID();

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('거북이', 30, NOW(), NOW());
SET @turtle_2 = LAST_INSERT_ID();

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('거북이', 100, NOW(), NOW());
SET @turtle_3 = LAST_INSERT_ID();

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('거북이', NULL, NOW(), NOW());
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

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('파이리', 50, NOW(), NOW());
SET @fire_1 = LAST_INSERT_ID();

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('파이리', 150, NOW(), NOW());
SET @fire_2 = LAST_INSERT_ID();

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('파이리', 300, NOW(), NOW());
SET @fire_3 = LAST_INSERT_ID();

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('파이리', NULL, NOW(), NOW());
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

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('팽귄', 100, NOW(), NOW());
SET @penguin_1 = LAST_INSERT_ID();

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('팽귄', 250, NOW(), NOW());
SET @penguin_2 = LAST_INSERT_ID();

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('팽귄', 500, NOW(), NOW());
SET @penguin_3 = LAST_INSERT_ID();

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('팽귄', NULL, NOW(), NOW());
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

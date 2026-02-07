-- ==============================================
-- Monster 시드 데이터 (4단계 진화 체인 1세트)
-- Repeatable migration: 내용 변경 시 자동 재실행
-- ==============================================

DELETE FROM t_monster_evolution_policy WHERE monster_id IN (1, 2, 3, 4);
DELETE FROM t_monsters WHERE monster_id IN (1, 2, 3, 4);

-- 1단계: 알 → 2단계로 진화
INSERT INTO t_monsters (monster_id, previous_monster_id, next_monster_id, nickname, evolution_xp, created_at, updated_at)
VALUES (1, NULL, 2, '타마알', 100, NOW(), NOW());

-- 2단계: 아기 → 3단계로 진화
INSERT INTO t_monsters (monster_id, previous_monster_id, next_monster_id, nickname, evolution_xp, created_at, updated_at)
VALUES (2, 1, 3, '타마베이비', 300, NOW(), NOW());

-- 3단계: 성장기 → 4단계로 진화
INSERT INTO t_monsters (monster_id, previous_monster_id, next_monster_id, nickname, evolution_xp, created_at, updated_at)
VALUES (3, 2, 4, '타마러너', 700, NOW(), NOW());

-- 4단계: 최종 진화 (next 없음)
INSERT INTO t_monsters (monster_id, previous_monster_id, next_monster_id, nickname, evolution_xp, created_at, updated_at)
VALUES (4, 3, NULL, '타마히어로', NULL, NOW(), NOW());

-- ==============================================
-- Monster 진화 정책 (KILOMETER 기준)
-- ==============================================

-- 1단계 → 2단계: 누적 10km 달성 시 진화
INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (1, 'KILOMETER', 10, NOW(), NOW());

-- 2단계 → 3단계: 누적 50km 달성 시 진화
INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (2, 'KILOMETER', 50, NOW(), NOW());

-- 3단계 → 4단계: 누적 150km 달성 시 진화
INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES (3, 'KILOMETER', 150, NOW(), NOW());

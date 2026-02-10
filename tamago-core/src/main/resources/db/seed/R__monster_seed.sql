-- ==============================================
-- 몬스터 시드 데이터 (몬스터 정의 + 진화 정책)
-- Repeatable migration: 내용 변경 시 자동 재실행
-- ==============================================

-- 기존 시드 데이터 정리
DELETE FROM t_monster_evolution_policy WHERE monster_id IN (SELECT monster_id FROM t_monsters WHERE nickname IN ('타마알', '타마베이비', '타마러너', '타마히어로'));
DELETE FROM t_monsters WHERE nickname IN ('타마알', '타마베이비', '타마러너', '타마히어로');

-- ==============================================
-- 몬스터 (4단계 진화 체인)
-- ==============================================

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('타마알', 100, NOW(), NOW());

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('타마베이비', 300, NOW(), NOW());

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('타마러너', 700, NOW(), NOW());

INSERT INTO t_monsters (nickname, evolution_xp, created_at, updated_at)
VALUES ('타마히어로', NULL, NOW(), NOW());

-- 진화 체인 연결
UPDATE t_monsters SET next_monster_id = (SELECT m.monster_id FROM (SELECT monster_id FROM t_monsters WHERE nickname = '타마베이비') m) WHERE nickname = '타마알';
UPDATE t_monsters SET previous_monster_id = (SELECT m.monster_id FROM (SELECT monster_id FROM t_monsters WHERE nickname = '타마알') m), next_monster_id = (SELECT m.monster_id FROM (SELECT monster_id FROM t_monsters WHERE nickname = '타마러너') m) WHERE nickname = '타마베이비';
UPDATE t_monsters SET previous_monster_id = (SELECT m.monster_id FROM (SELECT monster_id FROM t_monsters WHERE nickname = '타마베이비') m), next_monster_id = (SELECT m.monster_id FROM (SELECT monster_id FROM t_monsters WHERE nickname = '타마히어로') m) WHERE nickname = '타마러너';
UPDATE t_monsters SET previous_monster_id = (SELECT m.monster_id FROM (SELECT monster_id FROM t_monsters WHERE nickname = '타마러너') m) WHERE nickname = '타마히어로';

-- ==============================================
-- 몬스터 진화 정책 (KILOMETER 기준, multiplier = km당 XP 배율)
-- ==============================================

INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES ((SELECT monster_id FROM t_monsters WHERE nickname = '타마알'), 'KILOMETER', 10, NOW(), NOW());

INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES ((SELECT monster_id FROM t_monsters WHERE nickname = '타마베이비'), 'KILOMETER', 50, NOW(), NOW());

INSERT INTO t_monster_evolution_policy (monster_id, rule_type, multiplier, created_at, updated_at)
VALUES ((SELECT monster_id FROM t_monsters WHERE nickname = '타마러너'), 'KILOMETER', 150, NOW(), NOW());

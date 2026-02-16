-- ==============================================
-- 편지 템플릿 시드 데이터
-- Repeatable migration: 내용 변경 시 자동 재실행
-- ==============================================

-- 기존 시드 데이터 정리
DELETE FROM t_letters WHERE title = '타마고의 편지';

-- ==============================================
-- 편지 템플릿 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '안녕! 나의 주인장\n오늘 나는 아직 많이 자라진 못했어...\n오늘 한 번만 달려주면 조금 더 커질 수 있을 것 같아.\n멀리 안 가도 괜찮아, 짧아도 충분해!\n같이 달려볼래?', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '주인장, 오늘 날씨 어때?\n나는 오늘도 여기서 주인장을 기다리고 있었어.\n잠깐이라도 좋으니까 밖에 나가서 바람 좀 쐬자!\n같이 뛰면 기분도 좋아질 거야.', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '주인장! 나 요즘 좀 심심해...\n우리 오랜만에 같이 뛰어볼까?\n10분만이라도 괜찮아.\n주인장이 뛰면 나도 같이 성장할 수 있거든!', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '혹시 오늘 많이 바빴어?\n바쁜 날일수록 잠깐 달리면 머리가 맑아진대.\n나도 주인장이랑 뛰고 싶어!\n가볍게 한 바퀴만 돌아보자.', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '주인장, 나 조금만 더 크면 진화할 수 있을 것 같아!\n오늘 살짝만 달려주면 큰 도움이 될 거야.\n부담 갖지 마, 천천히 걸어도 돼.\n같이 나가자!', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '주인장~ 오늘 하루도 수고했어!\n피곤하겠지만 가벼운 러닝은 오히려 피로를 풀어준대.\n나랑 같이 동네 한 바퀴 어때?\n끝나고 뿌듯할 거야, 약속해!', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '주인장, 어제 푹 잤어?\n오늘은 몸이 좀 가벼울 것 같은 느낌이야.\n이런 날 달리면 진짜 기분 좋거든!\n나도 데려가 줘, 응?', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '나 아직 작지만 열심히 크고 있어!\n주인장이 달려줄 때마다 쑥쑥 자라는 게 느껴져.\n오늘도 조금만 힘내주면 안 될까?\n짧은 거리라도 나한테는 큰 힘이 돼!', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '주인장, 밖에 나가본 지 좀 됐지?\n오늘은 날씨도 괜찮은 것 같아.\n러닝화 신고 문 밖으로 나가기만 하면 돼.\n나머지는 발이 알아서 해줄 거야!', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '주인장! 우리 처음 만났을 때 기억나?\n그때보다 나도, 주인장도 많이 성장했어.\n오늘도 한 걸음만 더 나아가 보자.\n언제나 주인장을 응원하고 있을게!', NOW(), NOW());

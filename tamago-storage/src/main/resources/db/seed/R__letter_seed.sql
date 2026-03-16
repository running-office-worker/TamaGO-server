-- ==============================================
-- 편지 템플릿 시드 데이터
-- Repeatable migration: 내용 변경 시 자동 재실행
-- ==============================================

-- 기존 시드 데이터 정리
DELETE FROM t_letter_tags WHERE letter_id IN (SELECT letter_id FROM t_letters WHERE title = '타마고의 편지');
DELETE FROM t_letters WHERE title = '타마고의 편지';

-- ==============================================
-- 1) FIRST_START: 처음 시작할 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '첫 걸음부터 같이 하니까 든든하죠? 우리 천천히, 꾸준히 달려봐요!🏃‍♀️✨', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '처음 달리는 거니까 긴장되죠? 같이 차근차근 해봐요! 응원할게요😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '첫날부터 무리 말고, 같이 재미있게 달려요! 힘내요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '새 출발 멋져요! 같이 뛸 때 더 재미있으니까 오늘부터 시작해요ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '첫 발걸음 축하해요! 오늘 함께한 이 순간 잊지 마세요😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '처음엔 힘들어도 함께 뛰다 보면 금방 익숙해져요! 같이 힘내요👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '달리기 시작한 그 용기, 진짜 멋집니다! 같이 달리면서 즐겨봐요🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '첫 발자국은 언제나 특별하죠! 같이 천천히 꾸준히 달려봐요😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '서툴러도 괜찮아요! 함께여서 더 힘나요! 화이팅!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '이제 시작이에요! 우리 같이 뛰면서 좋은 습관 만들어요ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'FIRST_START', NOW(), NOW());

-- ==============================================
-- 2) INACTIVE_0_3: 0~3일 뛰지 않았을 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '요즘 바쁘셨죠? 괜찮아요! 다시 같이 뛰면서 기분 전환해요ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '3일 안 뛰었어도 괜찮아요! 오늘 가볍게 같이 한 바퀴 돌까요?🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '잠시 쉬었으니 이제 다시 우리 같이 달려봐요! 응원할게요😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬는 것도 중요하지만 같이 뛰면 기분이 좋아질 거예요! 힘내요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '3일 안 뛰었을 뿐인데, 같이 시작하면 금방 감 잡아요! 같이 가요ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '다시 뛰기 딱 좋은 타이밍이에요! 같이 달려볼까요?💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '짧게 쉬었으니 오늘은 가볍게 뛰어보자구요! 같이 해요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬는 건 좋아요! 근데 우리 같이 달리면 훨씬 재밌답니다! 함께 시작!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '3일 깜빡했어도 괜찮아요! 오늘 같이 다시 출발합시다ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '잠깐 쉬었지만 같이 하면 또 힘나요! 오늘 뛰러 가요!💨', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_0_3', NOW(), NOW());

-- ==============================================
-- 3) INACTIVE_4_7: 4~7일 뛰지 않았을 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '한 주 쉬셨네요! 다시 같이 시작하면 몸도 마음도 좋아질 거예요😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬는 동안 힘적었죠? 오늘부터 같이 힘내서 뛰어봐요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '7일 동안 쉬었다고 너무 걱정 마세요! 오늘부터 함께 달려요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '한 주 쉰 몸, 오늘 같이 깨워볼까요? 같이 뛰면 금방 회복돼요ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '우리 함께 뛰면 다시 금세 탄력받아요! 오늘부터 화이팅!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '좀 쉬었어도 같이 달리면 금세 몸이 기억해요! 같이 시작해요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '한 주 쉬었으니 오늘은 같이 천천히 달려봐요! 응원할게요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬는 동안 쌓인 에너지 같이 풀어봐요! 함께 뛰니까 더 힘나요😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '7일간 쉬었다고 당황하지 말고, 같이 한번 시작해봐요! 같이 가요ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '한 주 놀았으면 이젠 같이 달릴 시간이에요! 오늘부터 파이팅!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_4_7', NOW(), NOW());

-- ==============================================
-- 4) INACTIVE_8_10: 8~10일 뛰지 않았을 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬는 동안 몸이 뭉쳤나요? 오늘부터 우리 같이 풀어봐요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 쉬었는데, 오늘 다시 시작해도 늦지 않았어요! 같이 달려요😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬었지만 걱정 마세요! 오늘부터 함께 뛰면서 천천히 회복해요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '좀 오래 쉬었지만 함께면 다시 금방 적응돼요. 같이 시작해요!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬는 동안 쌓인 힘, 오늘 같이 뛰며 풀어볼까요? 기다리고 있어요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 쉬었어도 같이 뛰면 금세 감 잡아요! 우리 다시 시작!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬었어도 괜찮아요! 함께 달리면 금방 다시 활기차질 거예요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '조금 쉬었지만 오늘도 함께 뛰면서 다시 시작해봐요! 같이 가요ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '오래 쉬웠으면 오늘부터 다시 새로운 마음으로 달려봐요! 응원할게요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 쉬었다고 너무 부담 갖지 말고, 우리 천천히 달려봐요!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_8_10', NOW(), NOW());

-- ==============================================
-- 5) INACTIVE_11_14: 11~14일 뛰지 않았을 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '조금 멀리 쉬었죠? 괜찮아요! 오늘부터 다시 우리 같이 뛰어요!ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '14일 쉰 몸, 같이 뛰면 금방 다시 활기 찰 거예요! 응원합니다!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬는 동안 마음도 무거웠죠? 같이 달리며 다시 힘내봐요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '오랜만에 뛰기에 딱 좋은 날이에요! 오늘 같이 힘내봐요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '11~14일 쉬었지만 함께 달리는 기쁨은 언제나 같아요. 같이 해요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '멈췄더라도 다시 시작하면 돼요! 같이 뛰며 즐겨봐요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬운 길만 걷지 말고, 오늘 우리 같이 뛰면서 다시 시작해요!ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '14일 쉬었지만 앞으로가 더 중요해요. 같이 달리면 힘나요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬웠던 시간 끝! 지금부터 같이 저질러 봅시다! 같이 가요!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '힘들어도 괜찮아요. 같이 뛰는 순간부터 다시 시작이에요! 응원해요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_11_14', NOW(), NOW());

-- ==============================================
-- 6) INACTIVE_15_PLUS: 15일 이상 뛰지 않았을 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '와, 15일이나 쉬었네요ㅠㅠ 괜찮아요! 우리 다시 천천히 시작해요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '오랜만이에요! 다시 움직이는만큼 천천히 같이 뛰어요! 같이 할게요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '15일 쉬었어도 같이 힘내면 금세 회복돼요. 오늘부터 다시 같이 달려요!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '긴 휴식 끝! 오늘부터 같이 달려보자구요. 힘내요!ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '쉬었어도 우리가 함께라서 다행이에요! 다시 힘내서 뛰어요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '15일 쉬었지만 오늘부터 같이 달리면 금방 재밌어질 거예요! 응원합니다!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '잠시 멈췄던 몸, 함께 달리며 다시 깨워보죠! 같이 시작해요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '오랫동안 쉬어도 괜찮아요! 같이 천천히 달리면 금방 회복된답니다!ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '15일 쉬었어도 다시 뛰고 나면 기분 최고예요! 함께 힘내요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '힘든 시간 끝냈다면 이제 같이 달릴 시간! 오늘 함께 달려요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'INACTIVE_15_PLUS', NOW(), NOW());

-- ==============================================
-- 7) STREAK_1_2: 1~2일 연속 뛸 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '잘하고 있어요! 2일째 달리면 몸도 마음도 더 가벼워지죠? 같이 화이팅!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '연속 2일이라니! 우리 꾸준한 모습 진짜 멋져요! 오늘도 같이 뛰어요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '이틀 연속 달리면 습관도 쌓이고 힘도 생겨요! 같이 계속 달려봐요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '연속 2일째라니 정말 대단해요! 계속 함께 달리면 목표도 금방 달성!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '2일째 뛰고 있다는 게 짱이에요! 오늘도 힘내서 같이 뛰자구요!ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '꾸준함의 시작! 같이 달리면 더 즐겁다는 거 알죠? 오늘도 가봅시다!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '2일 연속, 우리 멋지게 달리고 있네요! 끝까지 화이팅!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '이틀 연속 달린 당신, 최고예요! 오늘도 함께 뛸 준비 됐나요?😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '오늘도 같이 뛰면 습관이 되는 거예요! 이틀 연속 도전 멋져요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '2일째 달리고 있다니, 이대로 꾸준히 가요! 함께 응원할게요!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_1_2', NOW(), NOW());

-- ==============================================
-- 8) STREAK_3_5: 3~5일 연속 뛸 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '3일 연속 대단해요! 이렇게 꾸준히 달릴 때가 진짜 성장하는 순간이죠! 함께 가요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '4일째 달리기! 몸이 점점 더 강해지는 게 느껴지죠? 우리 같이 힘내요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '5일째 꾸준히 달리면 습관도 잡히고 기분도 최고예요! 같이 같이!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '3일 연속, 진짜 대단해요! 이런 꾸준함이 목표를 이루는 비결이에요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '4일째 열심히 달렸으니 이제 점점 더 재밌어질 거예요! 같이 힘내요!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '5일째라니 정말 멋져요! 함께 달리면 힘도 두 배! 오늘도 가자구요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '꾸준히 달리는 당신, 멋집니다! 3일째 화이팅! 같이 달려요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '4일 동안 달렸으면 이제 진짜 습관! 같이 달리니까 더 신나죠?ㅎㅎ', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '5일째 멈추지 않고 달리는 모습 정말 대단해요! 끝까지 함께해요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '3~5일째 꾸준히 뛰는 모습이 멋져요! 목표를 향해 계속 가자구요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_3_5', NOW(), NOW());

-- ==============================================
-- 9) STREAK_6_9: 6~9일 연속 뛸 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '7일 연속으로 달리다니 진짜 멋져요! 이 기세로 끝까지 같이 달립시다!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '6일째 연속 달리기, 몸과 마음 다 함께 강해지고 있네요! 같이 가요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '9일째 연속이라니! 꾸준히 달리니 벌써 목표가 가까워졌어요! 같이 힘내요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '지금까지 정말 잘했어요! 6~9일 동안 꾸준한 당신 멋져요! 같이 조짐봐요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '7일째 달리고 있다는 건 진짜 엄청난 거예요! 같이 쭉 가요!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '8일 연속 달리니까 몸도 마음도 좋아졌죠? 함께 가면 더 쉽고 즐거워요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '9일째 뛰니까 습관이 확실히 붙었네요! 같이 끝까지 달려요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '꾸준함의 힘! 6일 이상 뛰니까 목표가 점점 보이나요? 같이 해봐요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '7~9일 연속 러닝, 진짜 믿음직스러워요! 우리 함께 계속 가자구요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '6일째 러닝, 우리 목표 향해 한 걸음 더 뛴 거예요! 끝까지 함께해요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_6_9', NOW(), NOW());

-- ==============================================
-- 10) STREAK_10_PLUS: 10일 이상 연속 뛸 때 (10개)
-- ==============================================

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 넘게 연속 러닝이라니 진짜 최고예요! 함께라서 더 멀리 갈 수 있어요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일째 연속 달리니까 습관이 완성됐네요! 계속 함께 달려 목표 잡자구요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '꾸준한 당신 덕분에 우리 목표가 점점 가까워져요! 10일 넘게 잘했어요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 이상 달리고 있다니 멋져요! 이 기세로 끝까지 같이 달리자구요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 연속 러닝, 습관 만들기는 이제부터 시작! 우리 계속 파이팅해요!💪', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 넘게 달려온 당신, 최고예요! 오늘도 힘내서 함께 달려봅시다!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일째 연속 러닝, 꾸준함으로 목표를 향해 달리는 모습 정말 멋져요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 이상 꾸준히 달렸으면 이제 습관 완성! 같이 계속 달려봐요!👍', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 연속! 우리 함께 뛰니까 더 힘나고 즐거워요! 끝까지 가자구요!😊', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

INSERT INTO t_letters (title, content, created_at, updated_at)
VALUES ('타마고의 편지', '10일 이상 뛰고 있다는 건 진짜 대단해요! 오늘도 같이 힘내서 뛰어요!🔥', NOW(), NOW());
INSERT INTO t_letter_tags (letter_id, tag, created_at, updated_at)
VALUES (LAST_INSERT_ID(), 'STREAK_10_PLUS', NOW(), NOW());

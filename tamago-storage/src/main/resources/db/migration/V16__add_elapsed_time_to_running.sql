-- t_running에 elapsed_time(초 단위) 컬럼 추가
ALTER TABLE t_running ADD COLUMN elapsed_time INT NULL AFTER heartbeat;

-- 기존 데이터: started_at, finished_at 기반으로 elapsed_time 채우기
UPDATE t_running SET elapsed_time = TIMESTAMPDIFF(SECOND, started_at, finished_at)
WHERE started_at IS NOT NULL AND finished_at IS NOT NULL;

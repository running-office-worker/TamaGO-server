CREATE TABLE t_running_plans (
    running_plan_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    goal_distance DOUBLE NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) NULL,
    PRIMARY KEY (running_plan_id)
);

ALTER TABLE t_running ADD COLUMN running_plan_id BIGINT NOT NULL;

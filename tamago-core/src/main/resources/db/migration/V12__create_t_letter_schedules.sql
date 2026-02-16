CREATE TABLE IF NOT EXISTS t_letter_schedules (
    letter_schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id            BIGINT      NOT NULL,
    next_delivery_at   datetime(6) NOT NULL,
    is_active          BIT         NOT NULL DEFAULT 1,
    created_at         datetime(6) NULL,
    updated_at         datetime(6) NULL,
    deleted_at         datetime(6) NULL
);

CREATE UNIQUE INDEX uk_letter_schedules_user_id ON t_letter_schedules (user_id);
CREATE INDEX idx_letter_schedules_active_delivery ON t_letter_schedules (is_active, next_delivery_at);

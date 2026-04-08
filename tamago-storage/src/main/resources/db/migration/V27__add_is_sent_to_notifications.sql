ALTER TABLE t_notifications DROP COLUMN is_read;
ALTER TABLE t_notifications DROP COLUMN scheduled_at;
ALTER TABLE t_notifications ADD COLUMN status ENUM('PENDING', 'SENT', 'READ') NOT NULL DEFAULT 'PENDING';

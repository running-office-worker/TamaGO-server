ALTER TABLE t_users
DROP
COLUMN total_kilo;

ALTER TABLE t_users
    ADD total_kilo DOUBLE NULL;

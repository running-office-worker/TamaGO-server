-- V1에서 생성된 FK 제거 (베이스라인 환경에서 FK가 없을 수 있으므로 조건부 드롭)
SET @fk = (SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS WHERE CONSTRAINT_SCHEMA = DATABASE() AND TABLE_NAME = 't_monsters' AND CONSTRAINT_NAME = 'FKh0bjivbxisexq3nblihda15jb' AND CONSTRAINT_TYPE = 'FOREIGN KEY');
SET @sql = IF(@fk > 0, 'ALTER TABLE t_monsters DROP FOREIGN KEY FKh0bjivbxisexq3nblihda15jb', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @fk = (SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS WHERE CONSTRAINT_SCHEMA = DATABASE() AND TABLE_NAME = 't_user_auth' AND CONSTRAINT_NAME = 'FKdesjd2qt6rq1159ct3ojf02c' AND CONSTRAINT_TYPE = 'FOREIGN KEY');
SET @sql = IF(@fk > 0, 'ALTER TABLE t_user_auth DROP FOREIGN KEY FKdesjd2qt6rq1159ct3ojf02c', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @fk = (SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS WHERE CONSTRAINT_SCHEMA = DATABASE() AND TABLE_NAME = 't_owned_monsters' AND CONSTRAINT_NAME = 'FKqetmjhl5k7s5r9jfvsftilyhm' AND CONSTRAINT_TYPE = 'FOREIGN KEY');
SET @sql = IF(@fk > 0, 'ALTER TABLE t_owned_monsters DROP FOREIGN KEY FKqetmjhl5k7s5r9jfvsftilyhm', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- V4에서 생성된 FK 제거
SET @fk = (SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS WHERE CONSTRAINT_SCHEMA = DATABASE() AND TABLE_NAME = 't_monster_unlock_policy' AND CONSTRAINT_NAME = 'FK_monster_unlock_policy_monster' AND CONSTRAINT_TYPE = 'FOREIGN KEY');
SET @sql = IF(@fk > 0, 'ALTER TABLE t_monster_unlock_policy DROP FOREIGN KEY FK_monster_unlock_policy_monster', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @fk = (SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS WHERE CONSTRAINT_SCHEMA = DATABASE() AND TABLE_NAME = 't_monster_evolution_policy' AND CONSTRAINT_NAME = 'FK_monster_evolution_policy_monster' AND CONSTRAINT_TYPE = 'FOREIGN KEY');
SET @sql = IF(@fk > 0, 'ALTER TABLE t_monster_evolution_policy DROP FOREIGN KEY FK_monster_evolution_policy_monster', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

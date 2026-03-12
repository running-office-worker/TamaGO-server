DROP PROCEDURE IF EXISTS drop_index_if_exists;

CREATE PROCEDURE drop_index_if_exists(IN tbl VARCHAR(64), IN idx VARCHAR(64))
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.statistics
        WHERE table_schema = DATABASE() AND table_name = tbl AND index_name = idx
    ) THEN
        SET @stmt = CONCAT('DROP INDEX `', idx, '` ON `', tbl, '`');
        PREPARE s FROM @stmt;
        EXECUTE s;
        DEALLOCATE PREPARE s;
    END IF;
END;

CALL drop_index_if_exists('t_received_letters', 'FK_received_letters_letter_id');
CALL drop_index_if_exists('t_monster_evolution_policy', 'FK_monster_evolution_policy_monster');
CALL drop_index_if_exists('t_monster_unlock_policy', 'FK_monster_unlock_policy_monster');
CALL drop_index_if_exists('t_monsters', 'FKh0bjivbxisexq3nblihda15jb');
CALL drop_index_if_exists('t_owned_monsters', 'FKqetmjhl5k7s5r9jfvsftilyhm');
CALL drop_index_if_exists('t_user_auth', 'FKdesjd2qt6rq1159ct3ojf02c');

DROP PROCEDURE IF EXISTS drop_index_if_exists;

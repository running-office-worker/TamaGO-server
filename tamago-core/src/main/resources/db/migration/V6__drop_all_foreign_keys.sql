-- V1에서 생성된 FK 제거
ALTER TABLE t_monsters DROP FOREIGN KEY FKh0bjivbxisexq3nblihda15jb;
ALTER TABLE t_user_auth DROP FOREIGN KEY FKdesjd2qt6rq1159ct3ojf02c;
ALTER TABLE t_owned_monsters DROP FOREIGN KEY FKqetmjhl5k7s5r9jfvsftilyhm;

-- V4에서 생성된 FK 제거
ALTER TABLE t_monster_unlock_policy DROP FOREIGN KEY FK_monster_unlock_policy_monster;
ALTER TABLE t_monster_evolution_policy DROP FOREIGN KEY FK_monster_evolution_policy_monster;

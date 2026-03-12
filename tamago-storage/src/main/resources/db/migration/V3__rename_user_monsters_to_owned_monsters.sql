ALTER TABLE t_user_monsters RENAME TO t_owned_monsters;

ALTER TABLE t_owned_monsters RENAME COLUMN user_monster_id TO owned_monster_id;

ALTER TABLE t_monster_assets
    MODIFY COLUMN asset_type ENUM ('M_LOTTIE', 'M_SVG', 'S_SVG', 'S_PNG', 'M_PNG', 'LBG_PNG', 'RBG_PNG', 'M_GIF') NULL;

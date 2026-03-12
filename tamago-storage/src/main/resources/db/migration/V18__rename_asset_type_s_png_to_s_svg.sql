-- Migrate existing S_PNG data to S_SVG before altering enum
UPDATE t_monster_assets
SET asset_type = 'S_SVG'
WHERE asset_type = 'S_PNG';

-- Update asset_type enum to replace S_PNG with S_SVG
ALTER TABLE t_monster_assets
    MODIFY COLUMN asset_type ENUM ('PNG', 'GIF', 'M_LOTTIE', 'M_SVG', 'S_SVG') NULL;

-- Migrate existing LOTTIE data to M_LOTTIE before altering enum
UPDATE t_monster_assets
SET asset_type = 'M_LOTTIE'
WHERE asset_type = 'LOTTIE';

-- Update asset_type enum to include new types and remove old LOTTIE
ALTER TABLE t_monster_assets
    MODIFY COLUMN asset_type ENUM ('PNG', 'GIF', 'M_LOTTIE', 'M_SVG', 'S_PNG') NULL;

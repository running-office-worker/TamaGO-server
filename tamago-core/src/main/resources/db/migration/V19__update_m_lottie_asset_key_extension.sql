-- Update asset_key extension from .json to .lottie for existing M_LOTTIE assets
UPDATE t_monster_assets
SET asset_key  = CONCAT(SUBSTRING(asset_key, 1, CHAR_LENGTH(asset_key) - 4), 'lottie'),
    asset_name = CONCAT(SUBSTRING(asset_name, 1, CHAR_LENGTH(asset_name) - 4), 'lottie')
WHERE asset_type = 'M_LOTTIE'
  AND asset_key LIKE '%.json';

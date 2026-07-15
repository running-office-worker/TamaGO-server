UPDATE t_monster_group_assets
SET metadata =
        JSON_SET(
            COALESCE(metadata, JSON_OBJECT()),
            '$.startHour',
            CASE
                WHEN RIGHT(asset_name, 5) = '1.png' THEN 4
                WHEN RIGHT(asset_name, 5) = '2.png' THEN 8
                WHEN RIGHT(asset_name, 5) = '3.png' THEN 13
                WHEN RIGHT(asset_name, 5) = '4.png' THEN 17
                WHEN RIGHT(asset_name, 5) = '5.png' THEN 20
                ELSE JSON_EXTRACT(metadata, '$.startHour')
            END,
            '$.endHour',
            CASE
                WHEN RIGHT(asset_name, 5) = '1.png' THEN 8
                WHEN RIGHT(asset_name, 5) = '2.png' THEN 13
                WHEN RIGHT(asset_name, 5) = '3.png' THEN 17
                WHEN RIGHT(asset_name, 5) = '4.png' THEN 20
                WHEN RIGHT(asset_name, 5) = '5.png' THEN 4
                ELSE JSON_EXTRACT(metadata, '$.endHour')
            END
        )
WHERE asset_type IN ('LBG_PNG', 'RBG_PNG')
  AND asset_name IN (
      'LBG_PNG_1.png',
      'LBG_PNG_2.png',
      'LBG_PNG_3.png',
      'LBG_PNG_4.png',
      'LBG_PNG_5.png',
      'RBG_PNG_1.png',
      'RBG_PNG_2.png',
      'RBG_PNG_3.png',
      'RBG_PNG_4.png',
      'RBG_PNG_5.png'
  );

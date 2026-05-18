UPDATE t_monster_group_assets mga
    JOIN t_monster_groups mg ON mg.monster_group_id = mga.monster_group_id
SET mga.metadata =
        CASE
            WHEN mg.code = 'TURTLE' AND RIGHT(mga.asset_name, 5) = '1.png' THEN JSON_OBJECT('backgroundColor', '#59774C')
            WHEN mg.code = 'TURTLE' AND RIGHT(mga.asset_name, 5) = '2.png' THEN JSON_OBJECT('backgroundColor', '#96A84F')
            WHEN mg.code = 'TURTLE' AND RIGHT(mga.asset_name, 5) = '3.png' THEN JSON_OBJECT('backgroundColor', '#7BA563')
            WHEN mg.code = 'TURTLE' AND RIGHT(mga.asset_name, 5) = '4.png' THEN JSON_OBJECT('backgroundColor', '#908440')
            WHEN mg.code = 'TURTLE' AND RIGHT(mga.asset_name, 5) = '5.png' THEN JSON_OBJECT('backgroundColor', '#273D57')
            WHEN mg.code = 'FIRE' AND RIGHT(mga.asset_name, 5) = '1.png' THEN JSON_OBJECT('backgroundColor', '#6B6A6F')
            WHEN mg.code = 'FIRE' AND RIGHT(mga.asset_name, 5) = '2.png' THEN JSON_OBJECT('backgroundColor', '#070C1F')
            WHEN mg.code = 'FIRE' AND RIGHT(mga.asset_name, 5) = '3.png' THEN JSON_OBJECT('backgroundColor', '#171929')
            WHEN mg.code = 'FIRE' AND RIGHT(mga.asset_name, 5) = '4.png' THEN JSON_OBJECT('backgroundColor', '#202434')
            WHEN mg.code = 'FIRE' AND RIGHT(mga.asset_name, 5) = '5.png' THEN JSON_OBJECT('backgroundColor', '#000000')
            WHEN mg.code = 'SNOW' AND RIGHT(mga.asset_name, 5) = '1.png' THEN JSON_OBJECT('backgroundColor', '#DAE8F9')
            WHEN mg.code = 'SNOW' AND RIGHT(mga.asset_name, 5) = '2.png' THEN JSON_OBJECT('backgroundColor', '#ACB8EC')
            WHEN mg.code = 'SNOW' AND RIGHT(mga.asset_name, 5) = '3.png' THEN JSON_OBJECT('backgroundColor', '#9BA6E9')
            WHEN mg.code = 'SNOW' AND RIGHT(mga.asset_name, 5) = '4.png' THEN JSON_OBJECT('backgroundColor', '#061851')
            WHEN mg.code = 'SNOW' AND RIGHT(mga.asset_name, 5) = '5.png' THEN JSON_OBJECT('backgroundColor', '#7789E0')
            ELSE mga.metadata
        END
WHERE mg.code IN ('TURTLE', 'FIRE', 'SNOW')
  AND mga.asset_type IN ('LBG_PNG', 'RBG_PNG');

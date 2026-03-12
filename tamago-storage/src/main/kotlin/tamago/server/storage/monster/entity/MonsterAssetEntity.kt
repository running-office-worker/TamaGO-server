package tamago.server.storage.monster.entity

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.storage.support.BaseTimeEntity

@Entity
@Table(name = "t_monster_assets")
class MonsterAssetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monster_asset_id")
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "monster_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val monster: MonsterEntity,
    @Column(name = "asset_key")
    val assetKey: String? = null,
    @Column(name = "asset_name")
    val assetName: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type")
    val assetType: AssetType? = null,
) : BaseTimeEntity()

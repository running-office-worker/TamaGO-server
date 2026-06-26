package tamago.server.storage.monster.converter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import tamago.server.core.monster.domain.vo.AssetMetadata
import tamago.server.core.monster.domain.vo.BackgroundAssetMetadata

@Converter
class MonsterGroupAssetMetadataConverter : AttributeConverter<AssetMetadata, String> {
    override fun convertToDatabaseColumn(attribute: AssetMetadata?): String? =
        when (attribute) {
            null -> null
            is BackgroundAssetMetadata -> objectMapper.writeValueAsString(attribute)
        }

    override fun convertToEntityAttribute(dbData: String?): AssetMetadata? =
        dbData?.let { objectMapper.readValue<BackgroundAssetMetadata>(it) }

    companion object {
        private val objectMapper = jacksonObjectMapper()
    }
}

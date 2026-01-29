package tamago.server.core.letter.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.letter.infrastructure.entity.ReceivedLetterEntity

interface ReceivedLetterJpaRepository : JpaRepository<ReceivedLetterEntity, Long> {
    fun findByUserId(userId: Long): List<ReceivedLetterEntity>
    fun findByUserIdAndLetterId(userId: Long, letterId: Long): ReceivedLetterEntity?
    fun findTopByUserIdOrderByCreatedAtDesc(userId: Long): ReceivedLetterEntity?
}

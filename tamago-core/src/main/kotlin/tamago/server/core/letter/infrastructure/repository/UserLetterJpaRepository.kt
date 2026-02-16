package tamago.server.core.letter.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.infrastructure.entity.UserLetterEntity
import java.time.LocalDateTime

interface UserLetterJpaRepository : JpaRepository<UserLetterEntity, Long> {
    fun findByUserId(userId: Long): List<UserLetterEntity>
    fun findByUserIdAndLetterId(userId: Long, letterId: Long): UserLetterEntity?
    fun findTopByUserIdOrderByCreatedAtDesc(userId: Long): UserLetterEntity?
    fun findAllByLetterStatusAndScheduledAtLessThanEqual(
        letterStatus: LetterStatus,
        now: LocalDateTime,
    ): List<UserLetterEntity>
    fun findByUserIdAndLetterStatus(userId: Long, letterStatus: LetterStatus): UserLetterEntity?
}

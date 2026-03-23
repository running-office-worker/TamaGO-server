package tamago.server.storage.letter.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.storage.letter.entity.UserLetterEntity
import java.time.LocalDateTime

interface UserLetterJpaRepository :
    JpaRepository<UserLetterEntity, Long>,
    KotlinJdslJpqlExecutor {
    fun findByUserId(userId: Long): List<UserLetterEntity>

    fun findByUserIdAndLetterId(
        userId: Long,
        letterId: Long,
    ): UserLetterEntity?

    fun findTopByUserIdOrderByCreatedAtDesc(userId: Long): UserLetterEntity?

    fun findFirstByUserIdAndLetterStatusOrderByCreatedAtDesc(
        userId: Long,
        letterStatus: LetterStatus,
    ): UserLetterEntity?

    fun findAllByLetterStatusAndScheduledAtLessThanEqual(
        letterStatus: LetterStatus,
        scheduledAt: LocalDateTime,
    ): List<UserLetterEntity>

    fun findAllByUserIdAndDeletedAtIsNotNull(userId: Long): List<UserLetterEntity>
}

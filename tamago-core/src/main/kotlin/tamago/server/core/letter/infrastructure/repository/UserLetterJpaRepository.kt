package tamago.server.core.letter.infrastructure.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.infrastructure.entity.UserLetterEntity
import java.time.LocalDateTime

interface UserLetterJpaRepository : JpaRepository<UserLetterEntity, Long>, KotlinJdslJpqlExecutor {
    fun findByUserId(userId: Long): List<UserLetterEntity>
    fun findByUserIdAndLetterId(userId: Long, letterId: Long): UserLetterEntity?
    fun findTopByUserIdOrderByCreatedAtDesc(userId: Long): UserLetterEntity?
    fun findByUserIdAndLetterStatus(userId: Long, letterStatus: LetterStatus): UserLetterEntity?
}

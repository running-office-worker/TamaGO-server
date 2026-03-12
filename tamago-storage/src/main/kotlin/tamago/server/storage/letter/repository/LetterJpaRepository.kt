package tamago.server.storage.letter.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.letter.entity.LetterEntity

interface LetterJpaRepository :
    JpaRepository<LetterEntity, Long>,
    KotlinJdslJpqlExecutor {
    fun findAllByDeletedAtIsNull(): List<LetterEntity>
}

package tamago.server.core.letter.infrastructure.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.letter.infrastructure.entity.LetterEntity

interface LetterJpaRepository : JpaRepository<LetterEntity, Long>, KotlinJdslJpqlExecutor

package tamago.server.core.letter.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.letter.infrastructure.entity.LetterTagEntity

interface LetterTagJpaRepository : JpaRepository<LetterTagEntity, Long>

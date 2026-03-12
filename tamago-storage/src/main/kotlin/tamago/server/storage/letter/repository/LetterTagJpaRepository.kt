package tamago.server.storage.letter.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.letter.entity.LetterTagEntity

interface LetterTagJpaRepository : JpaRepository<LetterTagEntity, Long>

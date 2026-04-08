package tamago.server.storage.notification.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.notification.entity.NotificationEntity

interface NotificationJpaRepository :
    JpaRepository<NotificationEntity, Long>,
    KotlinJdslJpqlExecutor

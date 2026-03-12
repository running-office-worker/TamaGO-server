package tamago.server.notification

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import tamago.server.core.notification.domain.port.outbound.FcmSendPort

private val logger = KotlinLogging.logger {}

@Component
class FcmSendAdapter(
    private val firebaseCloudMessageSender: FirebaseCloudMessageSender,
) : FcmSendPort {
    override fun send(
        tokens: List<String>,
        title: String?,
        body: String,
        destination: String?,
    ) {
        val requests =
            tokens.map { token ->
                FcmSendRequest(
                    fcmToken = token,
                    title = title ?: "",
                    body = body,
                    destination = destination,
                )
            }
        firebaseCloudMessageSender.sendAsync(requests)
        logger.info { "FCM 발송 요청 완료 - tokenCount: ${tokens.size}" }
    }
}

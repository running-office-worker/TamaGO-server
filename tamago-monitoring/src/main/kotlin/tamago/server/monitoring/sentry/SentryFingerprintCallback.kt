package tamago.server.monitoring.sentry

import io.sentry.Hint
import io.sentry.SentryEvent
import io.sentry.SentryOptions
import org.springframework.stereotype.Component
import tamago.server.core.common.exception.BusinessException

@Component
class SentryFingerprintCallback : SentryOptions.BeforeSendCallback {
    override fun execute(
        event: SentryEvent,
        hint: Hint,
    ): SentryEvent {
        val exception = event.throwable ?: return event

        val fingerprint = resolveFingerprint(exception)
        if (fingerprint != null) {
            event.fingerprints = fingerprint
        }

        return event
    }

    private fun resolveFingerprint(exception: Throwable): List<String>? =
        when (exception) {
            is BusinessException ->
                listOf(
                    "BusinessException",
                    exception.getCode().getCode(),
                )

            else -> null
        }
}

package tamago.server.core.notification.domain.port.outbound

interface FcmSendPort {
    fun send(
        tokens: List<String>,
        title: String?,
        body: String,
        destination: String?,
    )
}

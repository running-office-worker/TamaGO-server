package tamago.server.core.notification

interface NotificationCommandUseCase {
    fun sendLetterArrivalNotification(tokens: List<String>)
}

package allegro_hotel_room_reservations.users.notification

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import java.util.UUID

class NotificationPublisher(
        @Autowired private var template: RabbitTemplate
) {
    fun publishNotification(message: String) {
        val notification = Notification(message, UUID.randomUUID().toString(), LocalDateTime.now())
        template.convertAndSend(NotificationConfiguration.NOTIFICATION_EXCHANGE, NotificationConfiguration.NOTIFICATION_ROUTING_KEY, notification)

    }
}

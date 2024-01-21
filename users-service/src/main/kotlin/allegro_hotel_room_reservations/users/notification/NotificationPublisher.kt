package allegro_hotel_room_reservations.users.notification

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID

@RestController
class NotificationPublisher(
        @Autowired
        private var template: RabbitTemplate

) {
    @PostMapping("/api/users/notify")
    fun publishNotification(@RequestBody notification: Notification): String {
        notification.id = UUID.randomUUID().toString()
        notification.date = LocalDateTime.now()
        template.convertAndSend(MQConfig.NOTIFICATION_EXCHANGE, MQConfig.NOTIFICATION_ROUTING_KEY, notification)

        return "Message published: ${notification.message}"
    }
}

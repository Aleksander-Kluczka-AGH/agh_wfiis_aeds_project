package allegro_hotel_room_reservations.clients.notification

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NotificationSender {
    @Autowired
    val rabbitTemplate: RabbitTemplate? = null

    fun sendNotification(message: String) {
        rabbitTemplate!!.convertAndSend("notification_exchange", "notification_routing_key", message)
    }
}

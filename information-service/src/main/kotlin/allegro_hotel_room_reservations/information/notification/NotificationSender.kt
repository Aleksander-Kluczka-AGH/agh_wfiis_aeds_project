package allegro_hotel_room_reservations.information.notification

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID


@Component
class NotificationSender {
    @Autowired
    private val rabbitTemplate: AmqpTemplate? = null

    @Value("\${notification.exchange}")
    private val exchange: String? = null

    @Value("\${notification.routing-key}")
    private val routingKey: String? = null

    fun sendNotification(message: String) {
        val notification = Notification(message, UUID.randomUUID().toString(), LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        rabbitTemplate!!.convertAndSend(exchange, routingKey, notification)
    }
}

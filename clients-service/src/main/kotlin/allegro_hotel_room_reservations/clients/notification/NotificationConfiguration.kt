package allegro_hotel_room_reservations.clients.notification

import allegro_hotel_room_reservations.clients.notification.NotificationPublisher
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NotificationConfiguration {

    @Bean
    fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun notificationPublisher(template: RabbitTemplate): NotificationPublisher {
        return NotificationPublisher(template)
    }

    companion object {
        const val NOTIFICATION_QUEUE: String = "notification_queue"
        const val NOTIFICATION_EXCHANGE: String = "notification_exchange"
        const val NOTIFICATION_ROUTING_KEY: String = "notification_routing_key"
    }
}

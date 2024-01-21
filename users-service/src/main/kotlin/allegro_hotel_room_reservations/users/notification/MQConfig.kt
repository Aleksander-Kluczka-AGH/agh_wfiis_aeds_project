package allegro_hotel_room_reservations.users.notification

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MQConfig {

    @Bean
    fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    companion object {
        const val NOTIFICATION_QUEUE: String = "notification_queue"
        const val NOTIFICATION_EXCHANGE: String = "notification_exchange"
        const val NOTIFICATION_ROUTING_KEY: String = "notification_routing_key"
    }
}

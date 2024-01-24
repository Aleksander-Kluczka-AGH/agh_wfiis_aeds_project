package allegro_hotel_room_reservations.clients

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ClientsServiceApp

fun main(args: Array<String>) {
    createSpringApplication().run(*args)
}

fun createSpringApplication() = SpringApplication(ClientsServiceApp::class.java)

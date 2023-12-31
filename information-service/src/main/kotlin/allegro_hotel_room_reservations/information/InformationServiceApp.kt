package allegro_hotel_room_reservations.information

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class InformationServiceApp

fun main(args: Array<String>) {
    createSpringApplication().run(*args)
}

fun createSpringApplication() = SpringApplication(InformationServiceApp::class.java)
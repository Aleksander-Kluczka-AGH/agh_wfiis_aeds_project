package allegro_hotel_room_reservations.reservations

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ReservationsServiceApp

fun main(args: Array<String>) {
    createSpringApplication().run(*args)
}

fun createSpringApplication() = SpringApplication(ReservationsServiceApp::class.java)
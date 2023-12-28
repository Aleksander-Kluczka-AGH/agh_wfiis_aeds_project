package allegro_hotel_room_reservations.users

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class UserServiceApp

fun main(args: Array<String>) {
    createSpringApplication().run(*args)
}

fun createSpringApplication() = SpringApplication(UserServiceApp::class.java)
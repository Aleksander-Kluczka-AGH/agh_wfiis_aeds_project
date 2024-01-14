package allegro_hotel_room_reservations.users.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    var role: String,
    var name: String,
    var surname: String,
    var email: String
) {
    constructor() : this(0, "unknown", "unknown", "unknown", "unknown")
}

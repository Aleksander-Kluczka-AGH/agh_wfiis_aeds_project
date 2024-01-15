package allegro_hotel_room_reservations.reservations.domain.model
import java.time.LocalDate
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val userId: Long?,
    val roomId: Long?,
    var startDate: LocalDate?,
    var endDate: LocalDate?
) {
    constructor() : this(0, null, null, null, null)

}


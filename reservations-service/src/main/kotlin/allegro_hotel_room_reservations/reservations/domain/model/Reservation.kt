package allegro_hotel_room_reservations.reservations.domain.model
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name= "reservation")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var clientId: Long?,
    var roomId: Long?,
    var startDate: LocalDate?,
    var endDate: LocalDate?
) {
    constructor() : this(0, null, null, null, null)

}


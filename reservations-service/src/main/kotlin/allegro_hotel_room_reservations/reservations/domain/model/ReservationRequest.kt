package allegro_hotel_room_reservations.reservations.domain.model

import java.time.LocalDate

data class ReservationRequest(
    val clientId: Long,
    val roomId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate
)

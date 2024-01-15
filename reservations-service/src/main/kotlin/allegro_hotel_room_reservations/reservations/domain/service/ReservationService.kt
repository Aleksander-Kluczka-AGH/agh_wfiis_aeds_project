package allegro_hotel_room_reservations.reservations.domain.service

import allegro_hotel_room_reservations.reservations.domain.model.Reservation
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRequest
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class ReservationService {

    private val reservations = mutableListOf<Reservation>()
    private val reservationIdCounter = AtomicLong(1)

    fun makeReservation(reservationRequest: ReservationRequest): Reservation {
        val reservation = Reservation(
            id = reservationIdCounter.getAndIncrement(),
            userId = reservationRequest.userId,
            roomId = reservationRequest.roomId,
            startDate = reservationRequest.startDate,
            endDate = reservationRequest.endDate
        )
        reservations.add(reservation)
        return reservation
    }

    fun getReservation(reservationId: Long): Reservation? {
        return reservations.find { it.id == reservationId }
    }

    fun updateReservation(reservationId: Long, updatedReservation: ReservationRequest): Reservation? {
        val existingReservation = getReservation(reservationId)
        if (existingReservation != null) {
            // Perform update logic based on your requirements
            existingReservation.startDate = updatedReservation.startDate
            existingReservation.endDate = updatedReservation.endDate
        }
        return existingReservation
    }

    fun cancelReservation(reservationId: Long): Boolean {
        val reservation = getReservation(reservationId)
        return if (reservation != null) {
            reservations.remove(reservation)
            true
        } else {
            false
        }
    }

    fun getUserReservations(userId: Long): List<Reservation> {
        return reservations.filter { it.userId == userId }
    }
}

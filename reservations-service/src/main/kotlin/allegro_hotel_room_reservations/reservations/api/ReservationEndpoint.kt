package allegro_hotel_room_reservations.reservations.api

import allegro_hotel_room_reservations.reservations.domain.model.Reservation
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRequest
import allegro_hotel_room_reservations.reservations.domain.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reservations")
class ReservationController @Autowired constructor(private val reservationService: ReservationService) {

    // POST /api/reservations
    @PostMapping
    fun makeReservation(@RequestBody reservationRequest: ReservationRequest): ResponseEntity<Reservation> {
        val reservation = reservationService.makeReservation(reservationRequest)
        return ResponseEntity(reservation, HttpStatus.CREATED)
    }

    // GET /api/reservations/{reservationId}
    @GetMapping("/{reservationId}")
    fun getReservation(@PathVariable reservationId: Long): ResponseEntity<Reservation> {
        val reservation = reservationService.getReservation(reservationId)
        return if (reservation != null) {
            ResponseEntity(reservation, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // PUT /api/reservations/{reservationId}
    @PutMapping("/{reservationId}")
    fun updateReservation(
        @PathVariable reservationId: Long,
        @RequestBody updatedReservation: ReservationRequest
    ): ResponseEntity<Reservation> {
        val reservation = reservationService.updateReservation(reservationId, updatedReservation)
        return if (reservation != null) {
            ResponseEntity(reservation, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // DELETE /api/reservations/{reservationId}
    @DeleteMapping("/{reservationId}")
    fun cancelReservation(@PathVariable reservationId: Long): ResponseEntity<Void> {
        val canceled = reservationService.cancelReservation(reservationId)
        return if (canceled) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // GET /api/reservations/user/{userId}
    @GetMapping("/user/{userId}")
    fun getUserReservations(@PathVariable userId: Long): ResponseEntity<List<Reservation>> {
        val userReservations = reservationService.getUserReservations(userId)
        return ResponseEntity(userReservations, HttpStatus.OK)
    }
}

package allegro_hotel_room_reservations.reservations.api

import RequestMaker
import allegro_hotel_room_reservations.reservations.domain.model.Reservation
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRepository
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/reservations")
class ReservationController @Autowired constructor(private val reservationRepository: ReservationRepository?) {

    @PostMapping
    fun makeReservation(@RequestBody reservationRequest: ReservationRequest, requestMaker: RequestMaker): ResponseEntity<Reservation> {

        val hasConflict = reservationRepository!!.hasConflict(
            roomId = reservationRequest.roomId,
            startDate = reservationRequest.startDate,
            endDate = reservationRequest.endDate,
            excludeReservationId = -1
        )

        if (hasConflict) {
            return ResponseEntity(HttpStatus.CONFLICT)
        }

        val reservation = Reservation(
            id = 0,
            clientId = reservationRequest.clientId,
            roomId = reservationRequest.roomId,
            startDate = reservationRequest.startDate,
            endDate = reservationRequest.endDate
        )

        val clientCheckResponse = requestMaker.getClientCheckResponse(reservationRequest.clientId)?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val roomCheckResponse = requestMaker.getRoomCheckResponse(reservationRequest.roomId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)


        return ResponseEntity(reservationRepository.save(reservation), HttpStatus.CREATED)
    }

    // GET /api/reservations/{reservationId}
    @GetMapping("/{reservationId}")
    fun getReservation(@PathVariable reservationId: Long): ResponseEntity<Reservation> {
        val reservation = reservationRepository!!.findById(reservationId).orElse(null)
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
        @RequestBody updatedReservation: ReservationRequest, requestMaker: RequestMaker
    ): ResponseEntity<Reservation> {

        val existingReservation = reservationRepository!!.findById(reservationId)

        return if (existingReservation.isPresent) {
            val reservation = existingReservation.get()

            val hasConflict = reservationRepository.hasConflict(
                roomId = updatedReservation.roomId,
                startDate = updatedReservation.startDate,
                endDate = updatedReservation.endDate,
                excludeReservationId = reservationId
            )

            if (hasConflict) {
                return ResponseEntity(HttpStatus.CONFLICT)
            }

            requestMaker.getClientCheckResponse(updatedReservation.clientId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

            reservation.apply {
                clientId = updatedReservation.clientId
                roomId = updatedReservation.roomId
                startDate = updatedReservation.startDate
                endDate = updatedReservation.endDate
            }
            ResponseEntity(reservationRepository.save(reservation), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // DELETE /api/reservations/{reservationId}
    @DeleteMapping("/{reservationId}")
    fun cancelReservation(@PathVariable reservationId: Long): ResponseEntity<Void> {
        return if (reservationRepository!!.existsById(reservationId)) {
            reservationRepository.deleteById(reservationId)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

//    // GET /api/reservations/client/{clientId}
    @GetMapping("/client/{clientId}")
    fun getClientReservations(@PathVariable clientId: Long): ResponseEntity<List<Reservation>> {
        val clientReservations = reservationRepository!!.findByClientId(clientId)
        return ResponseEntity(clientReservations, HttpStatus.OK)
    }

    // GET /api/reservations
    @GetMapping
    fun getAllReservations(): ResponseEntity<MutableIterable<Reservation>> {
        val allReservations = reservationRepository!!.findAll()
        return ResponseEntity(allReservations, HttpStatus.OK)
    }



}

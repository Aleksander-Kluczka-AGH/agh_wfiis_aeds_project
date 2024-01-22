package allegro_hotel_room_reservations.reservations.api

import allegro_hotel_room_reservations.reservations.domain.model.Reservation
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRepository
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@RestController
@RequestMapping("/api/reservations")
class ReservationController @Autowired constructor(private val reservationRepository: ReservationRepository) {

    // POST /api/reservations
    @PostMapping
    suspend fun makeReservation(@RequestBody reservationRequest: ReservationRequest): ResponseEntity<Reservation> {
        val reservation = Reservation(
            id = 0,
            clientId = reservationRequest.clientId,
            roomId = reservationRequest.roomId,
            startDate = reservationRequest.startDate,
            endDate = reservationRequest.endDate
        )

        val savedReservation = reservationRepository.save(reservation)

        val roomUpdateUrl = "http://localhost:8102/api/rooms/${reservationRequest.roomId}"
        val updatedRoom = Room(id = reservationRequest.roomId, vacant = false)

        // Create a WebClient instance
        val webClient = WebClient.builder().build()

        // Make the PUT request using WebClient
        val response = webClient.put()
            .uri(roomUpdateUrl)
            .bodyValue(updatedRoom)
            .retrieve()
            .toEntity(Room::class.java)
            .awaitBodyOrNull()

        // Check if the room update was successful
        return if (response?.statusCode == HttpStatus.OK) {
            ResponseEntity(savedReservation, HttpStatus.CREATED)
        } else {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    }

    // GET /api/reservations/{reservationId}
    @GetMapping("/{reservationId}")
    fun getReservation(@PathVariable reservationId: Long): ResponseEntity<Reservation> {
        val reservation = reservationRepository.findById(reservationId).orElse(null)
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
        val existingReservation = reservationRepository.findById(reservationId)
        return if (existingReservation.isPresent) {
            val reservation = existingReservation.get()
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
        return if (reservationRepository.existsById(reservationId)) {
            reservationRepository.deleteById(reservationId)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

//    // GET /api/reservations/user/{userId}
    @GetMapping("/user/{clientId}")
    fun getUserReservations(@PathVariable clientId: Long): ResponseEntity<List<Reservation>> {
        val userReservations = reservationRepository.findByClientId(clientId)
        return ResponseEntity(userReservations, HttpStatus.OK)
    }

    // GET /api/reservations
    @GetMapping
    fun getAllReservations(): ResponseEntity<MutableIterable<Reservation>> {
        val allReservations = reservationRepository.findAll()
        return ResponseEntity(allReservations, HttpStatus.OK)
    }
}

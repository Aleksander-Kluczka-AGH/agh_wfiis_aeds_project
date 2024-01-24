package allegro_hotel_room_reservations.reservations.api

import allegro_hotel_room_reservations.reservations.domain.model.Reservation
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRepository
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json



@RestController
@RequestMapping("/api/reservations")
class ReservationController @Autowired constructor(private val reservationRepository: ReservationRepository) {

    @PostMapping
    fun makeReservation(@RequestBody reservationRequest: ReservationRequest): ResponseEntity<Reservation> {

        val hasConflict = reservationRepository.hasConflict(
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

        val userCheckUrl = "http://users-service:8080/api/users/${reservationRequest.clientId}"
        try{
            val userCheckResponse = WebClient.create().get()
                .uri(userCheckUrl)
                .retrieve()
                .bodyToMono(String::class.java)
                .cast(String::class.java)
                .block()

            println("userCheckResponse: $userCheckResponse")
        }
        catch (e: Exception) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }



        val roomCheckUrl = "http://information-service:8080/api/rooms/${reservationRequest.roomId}"
        try{
        val roomCheckResponse = WebClient.create().get()
            .uri(roomCheckUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .cast(String::class.java)
            .block()

        println("roomCheckResponse: $roomCheckResponse")
        }
        catch (e: Exception) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        return ResponseEntity(reservationRepository.save(reservation), HttpStatus.CREATED)
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

            val hasConflict = reservationRepository.hasConflict(
                roomId = updatedReservation.roomId,
                startDate = updatedReservation.startDate,
                endDate = updatedReservation.endDate,
                excludeReservationId = reservationId
            )

            if (hasConflict) {
                return ResponseEntity(HttpStatus.CONFLICT)
            }

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

//    // GET /api/reservations/user/{clientId}
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

@Serializable
data class RoomId(
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("date") val date: String
)
@Serializable
data class RoomInfo(
    @SerialName("id") val id: RoomId,
    @SerialName("roomNumber") var roomNumber: String,
    @SerialName("roomType") var roomType: String,
    @SerialName("vacant") var vacant: Boolean
)
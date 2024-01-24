package reservations

import allegro_hotel_room_reservations.reservations.api.ReservationController
import allegro_hotel_room_reservations.reservations.domain.model.Reservation
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRepository
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate

// ... (imports)

class ReservationControllerTest {

    private val reservationRepository: ReservationRepository = mockk()
    private val controller = ReservationController(reservationRepository)

    @Test
    fun `Make reservation with no conflict should return CREATED`() {
        // given
        val reservationRequest = ReservationRequest(
            clientId = 1,
            roomId = 101,
            startDate = LocalDate.of(2024, 1, 15),
            endDate = LocalDate.of(2024, 1, 20),
        )

        val reservationSlot = slot<Reservation>()

        every {
            reservationRepository.hasConflict(
                roomId = reservationRequest.roomId,
                startDate = reservationRequest.startDate,
                endDate = reservationRequest.endDate,
                excludeReservationId = -1
            )
        } returns false

        every { reservationRepository.save(capture(reservationSlot)) } answers {
            reservationSlot.captured
        }

        // when
        val response: ResponseEntity<Reservation> = controller.makeReservation(reservationRequest)

        // then
        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun `Make reservation with conflict should return CONFLICT`() {
        // given
        val reservationRequest = ReservationRequest(
            clientId = 1,
            roomId = 101,
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 1, 5)
        )

        every {
            reservationRepository.hasConflict(
                roomId = reservationRequest.roomId,
                startDate = reservationRequest.startDate,
                endDate = reservationRequest.endDate,
                excludeReservationId = -1
            )
        } returns true

        // when
        val response: ResponseEntity<Reservation> = controller.makeReservation(reservationRequest)

        // then
        assertEquals(HttpStatus.CONFLICT, response.statusCode)
    }

    @Test
    fun `Get reservation by ID should return OK when reservation exists`() {
        // given
        val reservationId = 1L
        val reservation = Reservation(id = reservationId, clientId = 1, roomId = 101, startDate = LocalDate.of(2024, 1, 1), endDate = LocalDate.of(2024, 1, 5))

        every { reservationRepository.findById(reservationId) } returns java.util.Optional.of(reservation)

        // when
        val response: ResponseEntity<Reservation> = controller.getReservation(reservationId)

        // then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(reservation, response.body)
    }

    @Test
    fun `Get reservation by ID should return NOT_FOUND when reservation does not exist`() {
        // given
        val reservationId = 1L

        every { reservationRepository.findById(reservationId) } returns java.util.Optional.empty()

        // when
        val response: ResponseEntity<Reservation> = controller.getReservation(reservationId)

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    // Add similar tests for updateReservation, cancelReservation, getUserReservations, and getAllReservations

}

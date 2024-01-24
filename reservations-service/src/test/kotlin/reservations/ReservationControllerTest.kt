package reservations

import RequestMaker
import allegro_hotel_room_reservations.reservations.api.ReservationController
import allegro_hotel_room_reservations.reservations.domain.model.Reservation
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRepository
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRequest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate


class ReservationControllerTest {

    private val reservationRepository: ReservationRepository = mockk()
    private val controller = ReservationController(reservationRepository)
    private val requestMaker: RequestMaker = mockk()

    private val existingReservation = Reservation(
        id = 1,
        clientId = 1,
        roomId = 101,
        startDate = LocalDate.of(2024, 1, 1),
        endDate = LocalDate.of(2024, 1, 5)
    )

    @Test
    fun `Make reservation with no conflict should return CREATED`() {
        // given
        val reservationRequest = ReservationRequest(
            clientId = 1,
            roomId = 101,
            startDate = LocalDate.of(2024, 1, 15),
            endDate = LocalDate.of(2024, 1, 20),
        )


        every {
            reservationRepository.hasConflict(
                roomId = reservationRequest.roomId,
                startDate = reservationRequest.startDate,
                endDate = reservationRequest.endDate,
                excludeReservationId = -1
            )
        } returns false

        val reservation = Reservation(
            id = 0,
            clientId = 1,
            roomId = 101,
            startDate = LocalDate.of(2024, 1, 15),
            endDate = LocalDate.of(2024, 1, 20),
        )

        every { reservationRepository.save(reservation) } returns reservation

        every { requestMaker.getClientCheckResponse(reservationRequest.clientId) } returns "Client exists"
        every { requestMaker.getRoomCheckResponse(reservationRequest.roomId) } returns "Room exists"

        val controller = ReservationController(reservationRepository)

        // when
        val response: ResponseEntity<Reservation> = controller.makeReservation(reservationRequest, requestMaker)

        // then
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(reservation, response.body)
    }

    @Test
    fun `Make reservation with conflict should return CONFLICT`() {

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
        val response: ResponseEntity<Reservation> = controller.makeReservation(reservationRequest, requestMaker)

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

    @Test
    fun `Update reservation with no conflict should return OK`() {
        // given
        val reservationId = 1L
        val updatedReservationRequest = ReservationRequest(
            clientId = 2,
            roomId = 102,
            startDate = LocalDate.of(2024, 2, 1),
            endDate = LocalDate.of(2024, 2, 5)
        )

        every { reservationRepository.findById(reservationId) } returns java.util.Optional.of(existingReservation)
        every {
            reservationRepository.hasConflict(
                roomId = updatedReservationRequest.roomId,
                startDate = updatedReservationRequest.startDate,
                endDate = updatedReservationRequest.endDate,
                excludeReservationId = reservationId
            )
        } returns false

        val updatedReservation = existingReservation.copy(
            clientId = updatedReservationRequest.clientId,
            roomId = updatedReservationRequest.roomId,
            startDate = updatedReservationRequest.startDate,
            endDate = updatedReservationRequest.endDate
        )

        every { reservationRepository.save(updatedReservation) } returns updatedReservation
        every { reservationRepository.save(updatedReservation) } returns updatedReservation
        every { requestMaker.getClientCheckResponse(updatedReservation.clientId!!) } returns "Client exists"

        // when
        val response: ResponseEntity<Reservation> = controller.updateReservation(reservationId, updatedReservationRequest, requestMaker)

        // then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(updatedReservation, response.body)
    }

    @Test
    fun `Update reservation with conflict should return CONFLICT`() {
        // given
        val reservationId = 1L
        val updatedReservationRequest = ReservationRequest(
            clientId = 2,
            roomId = 102,
            startDate = LocalDate.of(2024, 2, 1),
            endDate = LocalDate.of(2024, 2, 5)
        )

        every { reservationRepository.findById(reservationId) } returns java.util.Optional.of(existingReservation)
        every {
            reservationRepository.hasConflict(
                roomId = updatedReservationRequest.roomId,
                startDate = updatedReservationRequest.startDate,
                endDate = updatedReservationRequest.endDate,
                excludeReservationId = reservationId
            )
        } returns true

        // when
        val response: ResponseEntity<Reservation> = controller.updateReservation(reservationId, updatedReservationRequest, requestMaker)

        // then
        assertEquals(HttpStatus.CONFLICT, response.statusCode)
    }

    @Test
    fun `Cancel reservation should return NO_CONTENT`() {
        // given
        val reservationId = 1L

        every { reservationRepository.existsById(reservationId) } returns true
        every { reservationRepository.deleteById(reservationId) } returns Unit

        // when
        val response: ResponseEntity<Void> = controller.cancelReservation(reservationId)

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun `Cancel non-existing reservation should return NOT_FOUND`() {
        // given
        val reservationId = 1L

        every { reservationRepository.existsById(reservationId) } returns false

        // when
        val response: ResponseEntity<Void> = controller.cancelReservation(reservationId)

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }


}

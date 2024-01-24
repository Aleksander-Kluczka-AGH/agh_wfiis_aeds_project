package reservations

import allegro_hotel_room_reservations.reservations.api.ReservationController
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRepository
import allegro_hotel_room_reservations.reservations.domain.model.ReservationRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDate

@SpringBootTest(classes = [ReservationController::class])
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationServiceTest {

    @MockBean
    private lateinit var reservationRepositoryMock: ReservationRepository
    private lateinit var controller : ReservationController

    @Test
    fun `Failed reservation no repository`() {
        controller = ReservationController(null)


        Assertions.assertThrows(NullPointerException::class.java) {
            controller.makeReservation(
                ReservationRequest(
                    clientId = 1,
                    roomId = 101,
                    startDate = LocalDate.of(2024,1,15),
                    endDate = LocalDate.of(2024,1,20)
                ))
        }

    }
}

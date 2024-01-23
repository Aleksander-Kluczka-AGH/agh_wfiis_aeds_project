package allegro_hotel_room_reservations.users

import allegro_hotel_room_reservations.users.api.ClientController
import allegro_hotel_room_reservations.users.domain.Client
import allegro_hotel_room_reservations.users.domain.ClientRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceAppTest {

    @MockBean
    private lateinit var clientRepositoryMock: ClientRepository
    private lateinit var sut : ClientController

    @Test
    fun `Client created successfully`() {
        sut = ClientController(clientRepositoryMock)

        val response = sut.createClient(Client(5, "one", "two", "three", "four"))

        assert(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `Client not created when invalid repository`() {
        sut = ClientController(null)

        Assertions.assertThrows(NullPointerException::class.java) {
            sut.createClient(Client(5, "1", "2", "3", "4"))
        }
    }
}

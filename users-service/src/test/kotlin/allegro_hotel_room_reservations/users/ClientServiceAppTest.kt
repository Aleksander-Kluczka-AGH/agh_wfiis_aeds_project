package allegro_hotel_room_reservations.users

import allegro_hotel_room_reservations.users.api.ClientController
import allegro_hotel_room_reservations.users.domain.Client
import allegro_hotel_room_reservations.users.domain.ClientRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceAppTest {

    @Mock
    private lateinit var clientRepositoryMock: ClientRepository
    @InjectMocks
    private lateinit var sut : ClientController

    @Test
    fun `Client cannot be found in repository`() {
        sut = ClientController(clientRepositoryMock)

        val response = sut.getUserById(15)

        assert(response.statusCode.is4xxClientError)
    }

    @Test
    fun `Client cannot be found when repository is null`() {
        sut = ClientController(null)

        Assertions.assertThrows(NullPointerException::class.java) {
            sut.getUserById(15)
        }
    }

    @Test
    fun `Client is retrieved correctly`() {
        val originalClient: Optional<Client> = Optional.of(Client(1, "one", "two", "three", "four"))
        sut = ClientController(clientRepositoryMock)
        `when`(clientRepositoryMock.findById(1)).thenReturn(originalClient)

        val response = sut.getUserById(1)

        assert(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `Non-existent client cannot be updated`() {
        sut = ClientController(clientRepositoryMock)

        val response = sut.updateUserData(1, Client(1, "one", "two", "three", "four"))

        assert(response.statusCode.is4xxClientError)
    }

    @Test
    fun `Client cannot be updated when repository is null`() {
        sut = ClientController(null)

        Assertions.assertThrows(NullPointerException::class.java) {
            sut.updateUserData(1, Client(1, "one", "two", "three", "four"))
        }
    }

    @Test
    fun `Client is updated correctly`() {
        val originalClient: Optional<Client> = Optional.of(Client(1, "one", "two", "three", "four"))
        sut = ClientController(clientRepositoryMock)
        `when`(clientRepositoryMock.findById(1)).thenReturn(originalClient)

        val response = sut.updateUserData(1, Client(1, "1", "2", "3", "4"))

        assert(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `Non-existent client cannot be deleted`() {
        sut = ClientController(clientRepositoryMock)

        val response = sut.deleteUser(1)

        assert(response.statusCode.is4xxClientError)
    }

    @Test
    fun `Client cannot be deleted when repository is null`() {
        sut = ClientController(null)

        Assertions.assertThrows(NullPointerException::class.java) {
            sut.deleteUser(1)
        }
    }

    @Test
    fun `Client is deleted correctly`() {
        val originalClient: Optional<Client> = Optional.of(Client(1, "one", "two", "three", "four"))
        sut = ClientController(clientRepositoryMock)
        `when`(clientRepositoryMock.findById(1)).thenReturn(originalClient)

        val response = sut.deleteUser(1)

        assert(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `Client created successfully`() {
        sut = ClientController(clientRepositoryMock)

        val response = sut.createClient(Client(5, "one", "two", "three", "four"))

        assert(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `Client not created when repository is null`() {
        sut = ClientController(null)

        Assertions.assertThrows(NullPointerException::class.java) {
            sut.createClient(Client(5, "1", "2", "3", "4"))
        }
    }
}

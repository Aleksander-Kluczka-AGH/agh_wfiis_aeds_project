package allegro_hotel_room_reservations.clients

import allegro_hotel_room_reservations.clients.api.ClientController
import allegro_hotel_room_reservations.clients.domain.Client
import allegro_hotel_room_reservations.clients.domain.ClientRepository
import allegro_hotel_room_reservations.clients.notification.NotificationSender
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
class ClientsServiceAppTest {
    @Mock
    private lateinit var clientRepositoryMock: ClientRepository
    @Mock
    private lateinit var notificationSenderMock: NotificationSender
    @InjectMocks
    private lateinit var sut : ClientController

    @Test
    fun `Client cannot be found in repository`() {
        sut = ClientController(clientRepositoryMock, notificationSenderMock)

        val response = sut.getClientById(15)

        assert(response.statusCode.is4xxClientError)
    }

    @Test
    fun `Client cannot be found when repository is null`() {
        sut = ClientController(null, notificationSenderMock)

        Assertions.assertThrows(NullPointerException::class.java) {
            sut.getClientById(15)
        }
    }

    @Test
    fun `Client is retrieved correctly`() {
        val originalClient: Optional<Client> = Optional.of(Client(1, "one", "two", "three", "four"))
        sut = ClientController(clientRepositoryMock, notificationSenderMock)
        `when`(clientRepositoryMock.findById(1)).thenReturn(originalClient)

        val response = sut.getClientById(1)

        assert(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `Non-existent client cannot be updated`() {
        sut = ClientController(clientRepositoryMock, notificationSenderMock)

        val response = sut.updateClientData(1, Client(1, "one", "two", "three", "four"))

        assert(response.statusCode.is4xxClientError)
    }

    @Test
    fun `Client cannot be updated when repository is null`() {
        sut = ClientController(null, notificationSenderMock)

        Assertions.assertThrows(NullPointerException::class.java) {
            sut.updateClientData(1, Client(1, "one", "two", "three", "four"))
        }
    }

    @Test
    fun `Client is updated correctly`() {
        val originalClient: Optional<Client> = Optional.of(Client(1, "one", "two", "three", "four"))
        sut = ClientController(clientRepositoryMock, notificationSenderMock)

        `when`(clientRepositoryMock.findById(1)).thenReturn(originalClient)

        val response = sut.updateClientData(1, Client(1, "1", "2", "3", "4"))

        assert(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `Non-existent client cannot be deleted`() {
        sut = ClientController(clientRepositoryMock, notificationSenderMock)

        val response = sut.deleteClient(1)

        assert(response.statusCode.is4xxClientError)
    }

    @Test
    fun `Client cannot be deleted when repository is null`() {
        sut = ClientController(null, notificationSenderMock)

        Assertions.assertThrows(NullPointerException::class.java) {
            sut.deleteClient(1)
        }
    }

    @Test
    fun `Client is deleted correctly`() {
        val originalClient: Optional<Client> = Optional.of(Client(1, "one", "two", "three", "four"))
        sut = ClientController(clientRepositoryMock, notificationSenderMock)
        `when`(clientRepositoryMock.findById(1)).thenReturn(originalClient)

        val response = sut.deleteClient(1)

        assert(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `Client created successfully`() {
        sut = ClientController(clientRepositoryMock, notificationSenderMock)

        val response = sut.createClient(Client(5, "one", "two", "three", "four"))

        assert(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun `Client not created when repository is null`() {
        sut = ClientController(null, notificationSenderMock)

        Assertions.assertThrows(NullPointerException::class.java) {
            sut.createClient(Client(5, "1", "2", "3", "4"))
        }
    }
}

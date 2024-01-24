package allegro_hotel_room_reservations.users.api

import allegro_hotel_room_reservations.users.domain.Client
import allegro_hotel_room_reservations.users.domain.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
//import org.springframework.jmx.export.notification.NotificationPublisher
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/users")
class ClientController
@Autowired constructor(
    private var clientRepository: ClientRepository?,
) {
    @GetMapping("/{clientId}")
    fun getClientById(@PathVariable clientId: Int): ResponseEntity<out Any> {
        val client: Client? = clientRepository!!.findById(clientId).orElse(null)
        return if (client != null) {
            ResponseEntity.ok(client)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
    }

    @PutMapping("/{clientId}")
    fun updateClientData(@PathVariable clientId: Int, @RequestBody updatedClient: Client): ResponseEntity<String> {
        val existingUser = clientRepository!!.findById(clientId)

        if (existingUser.isPresent) {
            val clientToUpdate = existingUser.get()

            clientToUpdate.name = updatedClient.name
            clientToUpdate.surname = updatedClient.surname
            clientToUpdate.email = updatedClient.email
            clientToUpdate.role = updatedClient.role

            clientRepository!!.save(clientToUpdate)
//            notificationPublisher.publishNotification("Client $clientId has been updated.")
            return ResponseEntity.ok("Client updated successfully")
        } else {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{clientId}")
    fun deleteClient(@PathVariable clientId: Int): ResponseEntity<String> {
        val existingUser = clientRepository!!.findById(clientId)

        if (existingUser.isPresent) {
            clientRepository!!.deleteById(clientId)
//            notificationPublisher.publishNotification("Client $clientId has been deleted.")
            return ResponseEntity.ok("User deleted successfully")
        } else {
            return ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createClient(@RequestBody client: Client): ResponseEntity<Client> {
        val savedClient = clientRepository!!.save(client)
        return ResponseEntity(savedClient, HttpStatus.CREATED)
    }

}

package allegro_hotel_room_reservations.users.api

import allegro_hotel_room_reservations.users.domain.Client
import allegro_hotel_room_reservations.users.domain.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/users")
class ClientController{
    private var clientRepository: ClientRepository? = null

    @Autowired
    fun constructor(clientRepository: ClientRepository) {
        this.clientRepository = clientRepository
    }

    @GetMapping("/{clientId}")
    fun getUserById(@PathVariable clientId: Int): ResponseEntity<out Any> {
        val client: Client? = clientRepository!!.findById(clientId).orElse(null)
        return if (client != null) {
            ResponseEntity.ok(client)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/{clientId}")
    fun updateUserData(@PathVariable clientId: Int, @RequestBody updatedClient: Client): ResponseEntity<String> {
        val existingUser = clientRepository!!.findById(clientId)

        if (existingUser.isPresent) {
            val clientToUpdate = existingUser.get()

            clientToUpdate.name = updatedClient.name
            clientToUpdate.surname = updatedClient.surname
            clientToUpdate.email = updatedClient.email
            clientToUpdate.role = updatedClient.role

            clientRepository!!.save(clientToUpdate)
            return ResponseEntity.ok("User updated successfully")
        } else {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{clientId}")
    fun deleteUser(@PathVariable clientId: Int): ResponseEntity<String> {
        val existingUser = clientRepository!!.findById(clientId)

        if (existingUser.isPresent) {
            clientRepository!!.deleteById(clientId)
            return ResponseEntity.ok("User deleted successfully")
        } else {
            return ResponseEntity.notFound().build()
        }
    }
}

package allegro_hotel_room_reservations.information.api

import allegro_hotel_room_reservations.information.domain.model.Room
import allegro_hotel_room_reservations.information.domain.service.RoomInformationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rooms")
class RoomController @Autowired constructor(private val roomInformationService: RoomInformationService) {

    // GET /api/rooms
    @GetMapping
    fun getAllRooms(): ResponseEntity<List<Room>> {
        val rooms = roomInformationService.getAllRooms()
        return ResponseEntity(rooms, HttpStatus.OK)
    }

    // GET /api/rooms/{roomNumber}
    @GetMapping("/{roomNumber}")
    fun getRoomByNumber(@PathVariable roomNumber: String): ResponseEntity<Room> {
        val room = roomInformationService.getRoomByNumber(roomNumber)
        return if (room != null) {
            ResponseEntity(room, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // PUT /api/rooms/{roomNumber}
    @PutMapping("/{roomNumber}")
    fun updateRoom(@PathVariable roomNumber: String, @RequestBody updatedRoom: Room): ResponseEntity<Room> {
        val room = roomInformationService.updateRoom(roomNumber, updatedRoom)
        return if (room != null) {
            ResponseEntity(room, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // POST /api/rooms
    @PostMapping
    fun addRoom(@RequestBody newRoom: Room): ResponseEntity<Room> {
        val room = roomInformationService.addRoom(newRoom)
        return ResponseEntity(room, HttpStatus.CREATED)
    }

    // DELETE /api/rooms/{roomNumber}
    @DeleteMapping("/{roomNumber}")
    fun deleteRoom(@PathVariable roomNumber: String): ResponseEntity<Void> {
        val deleted = roomInformationService.deleteRoom(roomNumber)
        return if (deleted) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}

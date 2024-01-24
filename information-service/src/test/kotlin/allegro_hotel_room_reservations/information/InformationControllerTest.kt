
import allegro_hotel_room_reservations.information.api.RoomController
import allegro_hotel_room_reservations.information.domain.model.Room
import allegro_hotel_room_reservations.information.domain.model.RoomInformationRepository
import io.mockk.every
import io.mockk.mockk
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class RoomControllerTest {

    private val roomInformationRepository: RoomInformationRepository = mockk()

    private val controller = RoomController(roomInformationRepository)


    @Test
    fun `Get all rooms should return a list of rooms`() {
        val rooms = listOf(Room(ObjectId(), "101", "Standard"))
        every { roomInformationRepository.findAll() } returns rooms

        val response: ResponseEntity<List<Room>> = controller.getAllRooms()

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == rooms)
    }

    @Test
    fun `Get room by number should return the room`() {
        val roomNumber = "101"
        val room = Room(ObjectId(), roomNumber, "Standard")
        every { roomInformationRepository.findByRoomNumber(roomNumber) } returns room

        val response: ResponseEntity<Room> = controller.getRoomByNumber(roomNumber)

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == room)
    }

    @Test
    fun `Get room by non-existing number should return NOT_FOUND`() {
        val roomNumber = "102"
        every { roomInformationRepository.findByRoomNumber(roomNumber) } returns null

        val response: ResponseEntity<Room> = controller.getRoomByNumber(roomNumber)

        assert(response.statusCode == HttpStatus.NOT_FOUND)
    }

    @Test
    fun `Update existing room should return the updated room`() {
        val roomNumber = "101"
        val existingRoom = Room(ObjectId(), roomNumber, "Standard")
        val updatedRoom = Room(existingRoom.id, roomNumber, "Deluxe")
        every { roomInformationRepository.findByRoomNumber(roomNumber) } returns existingRoom
        every { roomInformationRepository.save(updatedRoom) } returns updatedRoom

        val response: ResponseEntity<Room> = controller.updateRoom(roomNumber, updatedRoom)

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == updatedRoom)
    }

    @Test
    fun `Update non-existing room should return NOT_FOUND`() {
        val roomNumber = "102"
        val updatedRoom = Room(ObjectId(), roomNumber, "Deluxe")
        every { roomInformationRepository.findByRoomNumber(roomNumber) } returns null

        val response: ResponseEntity<Room> = controller.updateRoom(roomNumber, updatedRoom)

        assert(response.statusCode == HttpStatus.NOT_FOUND)
    }

    @Test
    fun `Add new room should return CREATED`() {
        val newRoom = Room(null, "103", "Standard")
        val savedRoom = Room(ObjectId(), newRoom.roomNumber, newRoom.roomType)
        every { roomInformationRepository.save(newRoom) } returns savedRoom

        val response: ResponseEntity<Room> = controller.addRoom(newRoom)

        assert(response.statusCode == HttpStatus.CREATED)
        assert(response.body == savedRoom)
    }

    @Test
    fun `Delete existing room should return NO_CONTENT`() {
        val roomNumber = "101"
        val existingRoom = Room(ObjectId(), roomNumber, "Standard")
        every { roomInformationRepository.findByRoomNumber(roomNumber) } returns existingRoom
        every { roomInformationRepository.deleteById(existingRoom.id!!) } returns Unit

        val response: ResponseEntity<Void> = controller.deleteRoom(roomNumber)

        assert(response.statusCode == HttpStatus.NO_CONTENT)
    }

    @Test
    fun `Delete non-existing room should return NOT_FOUND`() {
        val roomNumber = "102"
        every { roomInformationRepository.findByRoomNumber(roomNumber) } returns null

        val response: ResponseEntity<Void> = controller.deleteRoom(roomNumber)

        assert(response.statusCode == HttpStatus.NOT_FOUND)
    }
}

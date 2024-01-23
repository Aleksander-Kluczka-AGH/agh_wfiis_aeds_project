package allegro_hotel_room_reservations.information.domain.service

import allegro_hotel_room_reservations.information.domain.model.Room
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoomInformationService @Autowired constructor(private val roomInformationRepository: RoomInformationRepository) {

    fun getAllRooms(): List<Room> = roomInformationRepository.findAll()

    fun getRoomByNumber(roomNumber: String): Room? =
        roomInformationRepository.findByRoomNumber(roomNumber)

    fun updateRoom(roomNumber: String, updatedRoom: Room): Room? {
        val existingRoom = roomInformationRepository.findByRoomNumber(roomNumber)
        if (existingRoom != null) {
            updatedRoom.id = existingRoom.id
            return roomInformationRepository.save(updatedRoom)
        }
        return null
    }

    fun addRoom(newRoom: Room): Room = roomInformationRepository.save(newRoom)

    fun deleteRoom(roomNumber: String): Boolean {
        val existingRoom = roomInformationRepository.findByRoomNumber(roomNumber)
        if (existingRoom != null) {
            roomInformationRepository.deleteById(existingRoom.id)
            return true
        }
        return false
    }
}

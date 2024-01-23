package allegro_hotel_room_reservations.information.domain.service

import allegro_hotel_room_reservations.information.domain.model.Room
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoomInformationService @Autowired constructor(private val roomInformationRepository: RoomInformationRepository) {

    fun getAllRooms(): List<Room> = roomInformationRepository.findAll()

    fun getRoomById(id: String): Room? = roomInformationRepository.findById(ObjectId(id)).orElse(null)

    fun updateRoom(id: String, updatedRoom: Room): Room? {
        if (roomInformationRepository.existsById(ObjectId(id))) {
            updatedRoom.id = ObjectId(id)
            return roomInformationRepository.save(updatedRoom)
        }
        return null
    }

    fun addRoom(newRoom: Room): Room = roomInformationRepository.save(newRoom)

    fun deleteRoom(id: String): Boolean {
        if (roomInformationRepository.existsById(ObjectId(id))) {
            roomInformationRepository.deleteById(ObjectId(id))
            return true
        }
        return false
    }
}

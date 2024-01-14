package allegro_hotel_room_reservations.information.domain.service
import allegro_hotel_room_reservations.information.domain.model.Room
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoomInformationService @Autowired constructor(private val roomInformationRepository: RoomInformationRepository) {

    fun getAllRooms(): List<Room> = roomInformationRepository.findAll()

    fun getRoomById(id: Long): Room = roomInformationRepository.findById(id).orElse(null)

    fun updateRoom(id: Long, updatedRoom: Room): Room? {
        if (roomInformationRepository.existsById(id)) {
            updatedRoom.id = id
            return roomInformationRepository.save(updatedRoom)
        }
        return null
    }

    fun addRoom(newRoom: Room): Room = roomInformationRepository.save(newRoom)

    fun deleteRoom(id: Long): Boolean {
        if (roomInformationRepository.existsById(id)) {
            roomInformationRepository.deleteById(id)
            return true
        }
        return false
    }
}

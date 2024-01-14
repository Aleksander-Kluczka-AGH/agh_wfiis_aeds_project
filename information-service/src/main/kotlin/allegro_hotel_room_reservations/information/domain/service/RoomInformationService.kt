package allegro_hotel_room_reservations.information.domain.service
import allegro_hotel_room_reservations.information.domain.model.Room
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoomService @Autowired constructor(private val roomInformationRepository: RoomInformationRepository) {

    fun updateRoomById(id: Long, updatedRoom: Room): Room? {
        if (roomInformationRepository.existsById(id)) {
            updatedRoom.id = id
            return roomInformationRepository.save(updatedRoom)
        }
        return null
    }
}

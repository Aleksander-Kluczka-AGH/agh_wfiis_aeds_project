package allegro_hotel_room_reservations.information.domain.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface RoomInformationRepository : MongoRepository<Room, ObjectId>{
    fun findByRoomNumber(roomNumber: String): Room?
}

package allegro_hotel_room_reservations.information.domain.service

import org.springframework.data.mongodb.repository.MongoRepository
import allegro_hotel_room_reservations.information.domain.model.Room
import org.bson.types.ObjectId

interface RoomInformationRepository : MongoRepository<Room, ObjectId>{
    fun findByRoomNumber(roomNumber: String): Room?
}

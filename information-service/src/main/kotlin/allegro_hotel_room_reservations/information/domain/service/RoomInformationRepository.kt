package allegro_hotel_room_reservations.information.domain.service

import org.springframework.data.mongodb.repository.MongoRepository
import allegro_hotel_room_reservations.information.domain.model.Room

interface RoomInformationRepository : MongoRepository<Room, Long>{
}

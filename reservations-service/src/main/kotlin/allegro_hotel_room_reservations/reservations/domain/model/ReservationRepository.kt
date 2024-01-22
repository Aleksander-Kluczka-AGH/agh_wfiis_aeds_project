package allegro_hotel_room_reservations.reservations.domain.model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : CrudRepository<Reservation, Long>{
    fun findByClientId(clientId: Long): List<Reservation>}
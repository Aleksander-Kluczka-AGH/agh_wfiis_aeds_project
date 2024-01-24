package allegro_hotel_room_reservations.reservations.domain.model

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ReservationRepository : CrudRepository<Reservation, Long>{
    fun findByClientId(clientId: Long): List<Reservation>
    @Query(
        "SELECT COUNT(r) > 0 " +
                "FROM Reservation r " +
                "WHERE r.roomId = :roomId " +
                "AND (:startDate BETWEEN r.startDate AND r.endDate OR :endDate BETWEEN r.startDate AND r.endDate OR r.startDate BETWEEN :startDate AND :endDate) " +
                "AND r.id != :excludeReservationId"
    )
    fun hasConflict(roomId: Long, startDate: LocalDate, endDate: LocalDate, excludeReservationId: Long): Boolean

}
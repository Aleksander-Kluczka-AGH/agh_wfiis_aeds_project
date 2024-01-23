package allegro_hotel_room_reservations.information.domain.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "rooms")
data class Room(
        @Id
        var id: ObjectId?,
        var roomNumber: String?,
        var roomType: String?,
        var vacant: Boolean?
) {
        constructor() : this(null, null, null, null)
}

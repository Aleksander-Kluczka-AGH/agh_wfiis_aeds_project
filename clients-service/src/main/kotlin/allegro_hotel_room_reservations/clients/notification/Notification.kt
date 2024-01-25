package allegro_hotel_room_reservations.clients.notification

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Notification @JsonCreator constructor(
    @JsonProperty var message: String,
    var id: String?,
)
{
    override fun toString(): String {
        return "{'message':'$message', 'id':'$id'}"
    }
}

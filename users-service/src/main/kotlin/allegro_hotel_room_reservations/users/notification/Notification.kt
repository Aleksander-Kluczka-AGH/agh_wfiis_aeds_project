package allegro_hotel_room_reservations.users.notification

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class Notification @JsonCreator constructor(
    @JsonProperty var message: String,
    var id: String?,
    @JsonIgnore var date: LocalDateTime?
)
{
    @JsonProperty("date")
    fun getFormattedDate(): String {
        return date.toString()
    }
}

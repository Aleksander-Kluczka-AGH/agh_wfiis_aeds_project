import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class RequestMaker {

    fun getClientCheckResponse(clientId: Long): String? {
        val clientCheckUrl = "http://clients-service:8080/api/clients/$clientId"
        return try {
            WebClient.create().get()
                .uri(clientCheckUrl)
                .retrieve()
                .bodyToMono(String::class.java)
                .cast(String::class.java)
                .block()
        } catch (e: Exception) {
            null
        }
    }

    fun getRoomCheckResponse(roomId: Long): String? {
        val roomCheckUrl = "http://information-service:8080/api/rooms/$roomId"
        return try {
            WebClient.create().get()
                .uri(roomCheckUrl)
                .retrieve()
                .bodyToMono(String::class.java)
                .cast(String::class.java)
                .block()
        } catch (e: Exception) {
            null
        }
    }
}
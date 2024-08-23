package tomykulak.gardenmoisturizer.model.moistureSensor

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class MoistureData(
    var percentage: Int,
    var isValveOpen: Boolean
): Serializable {

    fun isEmpty(): Boolean {
        return percentage == 0
    }
}
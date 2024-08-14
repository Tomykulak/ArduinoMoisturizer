package tomykulak.gardenmoisturizer.model.moistureSensor

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class MoistureData(
    var int: Int,
    var float: Float
)

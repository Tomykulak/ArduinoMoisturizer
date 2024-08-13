package tomykulak.gardenmoisturizer.model.moistureSensor

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoistureData(
    var moisture: Int
)

package tomykulak.gardenmoisturizer.communication.moistureSensor

import retrofit2.Response
import retrofit2.http.GET
import tomykulak.gardenmoisturizer.model.moistureSensor.MoistureData

interface MoistureSensorAPI {
    @GET("moisture.json")  // Accesses the specific "int" value under "moisture" in Firebase
    suspend fun getMoistureData(): Response<MoistureData>
}
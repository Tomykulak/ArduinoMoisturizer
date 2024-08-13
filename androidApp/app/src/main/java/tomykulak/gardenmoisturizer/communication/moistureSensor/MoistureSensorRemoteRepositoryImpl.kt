package tomykulak.gardenmoisturizer.communication.moistureSensor

import javax.inject.Inject

class MoistureSensorRemoteRepositoryImpl
    @Inject constructor(private val moistureSensorAPI: MoistureSensorAPI): IMoistureSensorRemoteRepository
{
    override suspend fun getMoistureData() {
        TODO("Not yet implemented")
    }
}
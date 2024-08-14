package tomykulak.gardenmoisturizer.communication.moistureSensor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tomykulak.gardenmoisturizer.architecture.CommunicationResult
import tomykulak.gardenmoisturizer.model.moistureSensor.MoistureData
import javax.inject.Inject

class MoistureSensorRemoteRepositoryImpl
    @Inject constructor(private val moistureSensorAPI: MoistureSensorAPI): IMoistureSensorRemoteRepository
{
    override suspend fun getMoistureData(): CommunicationResult<MoistureData> {
        return processResponse(
            withContext(Dispatchers.IO){
                moistureSensorAPI.getMoistureData()
            }
        )
    }

}
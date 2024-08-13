package tomykulak.gardenmoisturizer.communication.moistureSensor;

import tomykulak.gardenmoisturizer.architecture.CommunicationResult
import tomykulak.gardenmoisturizer.architecture.IBaseRemoteRepository;

interface IMoistureSensorRemoteRepository : IBaseRemoteRepository {
    suspend fun getMoistureData(

    )
}

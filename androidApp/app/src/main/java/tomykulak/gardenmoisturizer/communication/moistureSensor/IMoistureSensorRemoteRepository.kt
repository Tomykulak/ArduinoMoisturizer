package tomykulak.gardenmoisturizer.communication.moistureSensor;

import tomykulak.gardenmoisturizer.architecture.CommunicationResult
import tomykulak.gardenmoisturizer.architecture.IBaseRemoteRepository;
import tomykulak.gardenmoisturizer.model.moistureSensor.MoistureData

interface IMoistureSensorRemoteRepository : IBaseRemoteRepository {
    suspend fun getMoistureData(

    ): CommunicationResult<MoistureData>
}

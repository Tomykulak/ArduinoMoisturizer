package tomykulak.gardenmoisturizer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tomykulak.gardenmoisturizer.communication.moistureSensor.MoistureSensorAPI
import tomykulak.gardenmoisturizer.communication.moistureSensor.MoistureSensorRemoteRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {
    @Provides
    @Singleton
    fun provideMoistureSensorRepository(api: MoistureSensorAPI): MoistureSensorRemoteRepositoryImpl
    = MoistureSensorRemoteRepositoryImpl(api)
}
package tomykulak.gardenmoisturizer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import tomykulak.gardenmoisturizer.communication.moistureSensor.MoistureSensorAPI
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {
    @Provides
    @Singleton
    fun provideMoistureSensorAPI(retrofit: Retrofit): MoistureSensorAPI
        = retrofit.create(MoistureSensorAPI::class.java)
}
package com.radlab.nbpapi.di

import com.radlab.nbpapi.data.repository.CurrencyRepository
import com.radlab.nbpapi.data.api.NbpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNbpApi(): NbpApi {
        return Retrofit.Builder()
            .baseUrl("https://api.nbp.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NbpApi::class.java)
    }

    @Provides
    fun provideCurrencyRepository(api: NbpApi): CurrencyRepository {
        return CurrencyRepository(api)
    }
}

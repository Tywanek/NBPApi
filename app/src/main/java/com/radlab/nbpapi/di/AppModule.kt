package com.radlab.nbpapi.di

import android.content.Context
import com.radlab.nbpapi.config.ApiConstants
import com.radlab.nbpapi.data.api.NbpApi
import com.radlab.nbpapi.data.repository.CurrencyRepository
import com.radlab.nbpapi.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNbpApi(): NbpApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NbpApi::class.java)
    }

    @Provides
    fun provideCurrencyRepository(api: NbpApi): CurrencyRepository {
        return CurrencyRepository(api)
    }

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }
}

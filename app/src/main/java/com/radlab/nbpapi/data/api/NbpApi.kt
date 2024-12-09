package com.radlab.nbpapi.data.api

import com.radlab.nbpapi.data.model.CurrencyHistory
import com.radlab.nbpapi.data.model.ExchangeRateTable
import retrofit2.http.GET
import retrofit2.http.Path

interface NbpApi {
    @GET("api/exchangerates/tables/{table}/")
    suspend fun getExchangeRates(@Path("table") table: String): List<ExchangeRateTable>

    @GET("api/exchangerates/rates/{table}/{currencyCode}/last/14/")
    suspend fun getCurrencyHistory(@Path("table") table: String, @Path("currencyCode") code: String): CurrencyHistory
}

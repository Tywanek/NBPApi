package com.radlab.nbpapi.data.repository

import com.radlab.nbpapi.data.api.NbpApi
import com.radlab.nbpapi.data.model.CurrencyRate
import com.radlab.nbpapi.data.model.HistoricalRate

class CurrencyRepository(private val api: NbpApi) {

    suspend fun getExchangeRates(table: String): List<CurrencyRate> {
        val response = api.getExchangeRates(table)
        return response.firstOrNull()?.rates?.map {
            it.copy(table = table)
        } ?: emptyList()
    }

    suspend fun getCurrencyHistory(table: String, code: String): List<HistoricalRate> {
        return api.getCurrencyHistory(table, code).rates.sortedByDescending { it.effectiveDate }
    }
}

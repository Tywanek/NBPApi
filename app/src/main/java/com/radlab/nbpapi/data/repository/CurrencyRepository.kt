package com.radlab.nbpapi.data.repository

import com.radlab.nbpapi.data.api.NbpApi
import com.radlab.nbpapi.data.model.CurrencyRate
import com.radlab.nbpapi.data.model.HistoricalRate

class CurrencyRepository(private val api: NbpApi) {

    suspend fun getExchangeRates(table: String): Result<List<CurrencyRate>> {
        return runCatching {
            api.getExchangeRates(table).firstOrNull()?.rates?.map { it.copy(table = table) }
                ?: emptyList()
        }
    }

    suspend fun getCurrencyHistory(table: String, code: String): Result<List<HistoricalRate>> {
        return runCatching {
            val history =
                api.getCurrencyHistory(table, code).rates.sortedByDescending { it.effectiveDate }
            history
        }
    }
}


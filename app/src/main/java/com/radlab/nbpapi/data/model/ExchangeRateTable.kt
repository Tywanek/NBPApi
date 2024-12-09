package com.radlab.nbpapi.data.model

data class ExchangeRateTable(
    val table: String,
    val no: String,
    val effectiveDate: String,
    val rates: List<CurrencyRate>
)

data class CurrencyRate(
    val currency: String,
    val code: String,
    val mid: Double,
    val table: String
)

data class CurrencyHistory(
    val table: String,
    val currency: String,
    val code: String,
    val rates: List<HistoricalRate>
)

data class HistoricalRate(
    val no: String,
    val effectiveDate: String,
    val mid: Double
)

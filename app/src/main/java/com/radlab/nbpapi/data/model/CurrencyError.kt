package com.radlab.nbpapi.data.model

sealed class CurrencyError {
    data class NetworkError(val message: String) : CurrencyError()
    data class HttpError(val message: String) : CurrencyError()
    data class UnexpectedError(val message: String) : CurrencyError()
}

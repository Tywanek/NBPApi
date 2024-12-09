package com.radlab.nbpapi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radlab.nbpapi.R
import com.radlab.nbpapi.data.model.CurrencyError
import com.radlab.nbpapi.data.model.CurrencyRate
import com.radlab.nbpapi.data.repository.CurrencyRepository
import com.radlab.nbpapi.data.model.HistoricalRate
import com.radlab.nbpapi.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    init {
        fetchAllExchangeRates()
    }

    private val _exchangeRates = MutableLiveData<List<CurrencyRate>>()
    val exchangeRates: LiveData<List<CurrencyRate>> = _exchangeRates

    private val _currencyHistory = MutableLiveData<List<HistoricalRate>>()
    val currencyHistory: LiveData<List<HistoricalRate>> = _currencyHistory

    private val _currentRate = MutableLiveData<Double>()
    val currentRate: LiveData<Double> = _currentRate

    private val _error = MutableLiveData<CurrencyError>()
    val error: LiveData<CurrencyError> = _error

    private fun fetchAllExchangeRates() {
        viewModelScope.launch {
            val ratesA = fetchRates("A")
            val ratesB = fetchRates("B")
            processExchangeRates(ratesA, ratesB)
        }
    }

    private suspend fun fetchRates(table: String): List<CurrencyRate> {
        val result = repository.getExchangeRates(table)
        return processApiResult(result, emptyList())
    }

    private fun processExchangeRates(ratesA: List<CurrencyRate>, ratesB: List<CurrencyRate>) {
        val rates = (ratesA + ratesB).sortedBy { it.currency }
        _exchangeRates.value = rates
        _currentRate.value = rates.firstOrNull()?.mid ?: 0.0
    }

    fun fetchCurrencyHistory(table: String, code: String) {
        viewModelScope.launch {
            val historyResult = repository.getCurrencyHistory(table, code)
            _currencyHistory.value =
                processApiResult(historyResult, emptyList()).sortedByDescending { it.effectiveDate }
        }
    }

    private fun <T> processApiResult(result: Result<T>, defaultValue: T): T {
        return result.getOrElse {
            _error.value = when (it) {
                is IOException -> CurrencyError.NetworkError(
                    resourceProvider.getString(
                        R.string.network_error,
                        it.message ?: resourceProvider.getString(R.string.unknown_network_error)
                    )
                )

                is HttpException -> CurrencyError.HttpError(
                    resourceProvider.getString(
                        R.string.http_error,
                        it.message() ?: resourceProvider.getString(R.string.unknown_http_error)
                    )
                )

                else -> CurrencyError.UnexpectedError(
                    resourceProvider.getString(
                        R.string.unexpected_error,
                        it.message ?: "Unexpected error"
                    )
                )
            }
            defaultValue
        }
    }
}

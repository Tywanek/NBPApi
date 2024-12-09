package com.radlab.nbpapi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radlab.nbpapi.data.model.CurrencyRate
import com.radlab.nbpapi.data.repository.CurrencyRepository
import com.radlab.nbpapi.data.model.HistoricalRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
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

    private fun fetchAllExchangeRates() {
        viewModelScope.launch {
            try {
                val ratesA = repository.getExchangeRates("A")
                val ratesB = repository.getExchangeRates("B")
                val rates = (ratesA + ratesB).sortedBy { it.currency }
                _exchangeRates.value = rates
                _currentRate.value = rates.firstOrNull()?.mid ?: 0.0
            } catch (e: Exception) {
                _exchangeRates.value = emptyList()
            }
        }
    }


    fun fetchCurrencyHistory(table: String, code: String) {
        viewModelScope.launch {
            try {
                val history = repository.getCurrencyHistory(table, code)
                _currencyHistory.value = history.sortedByDescending { it.effectiveDate }
            } catch (e: Exception) {
                _currencyHistory.value = emptyList()
            }
        }
    }
}

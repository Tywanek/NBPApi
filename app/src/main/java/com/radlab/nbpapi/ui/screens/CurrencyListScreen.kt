package com.radlab.nbpapi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.radlab.nbpapi.R
import com.radlab.nbpapi.data.model.CurrencyError
import com.radlab.nbpapi.viewModel.CurrencyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(
    viewModel: CurrencyViewModel,
    onCurrencyClick: (String, String) -> Unit
) {
    val exchangeRates by viewModel.exchangeRates.observeAsState(emptyList())
    val errorState by viewModel.error.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.list_title)) })
        }
    ) { padding ->
        if (exchangeRates.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                errorState?.let { error ->
                    val message = when (error) {
                        is CurrencyError.NetworkError -> error.message
                        is CurrencyError.HttpError -> error.message
                        is CurrencyError.UnexpectedError -> error.message
                    }
                    Text(text = message)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(exchangeRates) { rate ->
                    CurrencyItem(rate = rate, onCurrencyClick = onCurrencyClick)
                }
            }
        }
    }
}
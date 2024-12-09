@file:OptIn(ExperimentalMaterial3Api::class)

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.radlab.nbpapi.R
import com.radlab.nbpapi.ui.screens.DetailsItem
import com.radlab.nbpapi.viewModel.CurrencyViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CurrencyDetailsScreen(
    table: String,
    currencyCode: String,
    viewModel: CurrencyViewModel,
    onBack: () -> Unit
) {
    val exchangeRates by viewModel.exchangeRates.observeAsState(emptyList())
    val currencyHistory by viewModel.currencyHistory.observeAsState(emptyList())
    val currentRate by viewModel.currentRate.observeAsState(0.0)
    val currencyName =
        exchangeRates.firstOrNull { it.code == currencyCode }?.currency ?: stringResource(
            R.string.unknown
        )

    LaunchedEffect(currencyCode) {
        viewModel.fetchCurrencyHistory(table, currencyCode)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(
                            R.string.currency_details,
                            currencyName,
                            currencyCode
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                }
            )
        }
    ) {
        if (currencyHistory.isEmpty()) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                items(currencyHistory) { rate ->
                    DetailsItem(rate = rate, currentRate = currentRate)
                }
            }
        }
    }
}
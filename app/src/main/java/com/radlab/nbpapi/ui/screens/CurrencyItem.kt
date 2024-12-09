package com.radlab.nbpapi.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.radlab.nbpapi.data.model.CurrencyRate
import java.util.Locale


@Composable
fun CurrencyItem(rate: CurrencyRate, onCurrencyClick: (String, String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCurrencyClick(rate.code, rate.table) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rate.currency,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = String.format(Locale.getDefault(), "%.3f", rate.mid)
            )
        }
    }
}

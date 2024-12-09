package com.radlab.nbpapi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.radlab.nbpapi.data.model.HistoricalRate
import com.radlab.nbpapi.extensions.formatToThreeDecimalPlaces
import com.radlab.nbpapi.extensions.isOutOfRange

@Composable
fun DetailsItem(rate: HistoricalRate, currentRate: Double) {
    val isOutOfRange = rate.mid.isOutOfRange(currentRate)
    val textColor = if (isOutOfRange) Color.Red else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = rate.effectiveDate
        )
        Text(
            text = rate.mid.formatToThreeDecimalPlaces(),
            color = textColor
        )
    }
}

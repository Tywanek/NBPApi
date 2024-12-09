package com.radlab.nbpapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.radlab.nbpapi.ui.screens.MainScreen
import com.radlab.nbpapi.ui.theme.NBPApiTheme
import com.radlab.nbpapi.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val currencyViewModel: CurrencyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NBPApiTheme {
                MainScreen(viewModel = currencyViewModel)
            }
        }
    }
}


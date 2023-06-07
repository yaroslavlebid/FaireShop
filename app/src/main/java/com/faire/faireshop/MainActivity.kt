package com.faire.faireshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.faire.faireshop.presentation.products.ProductsScreen
import com.faire.faireshop.presentation.products.ProductsViewModel
import com.faire.faireshop.ui.theme.FaireShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FaireShopTheme {
                val viewModel = hiltViewModel<ProductsViewModel>()
                val state = viewModel.state.collectAsState()
                ProductsScreen(
                    state = state.value,
                    events = viewModel.uiEvents,
                    onRefreshProducts = {
                        viewModel.refreshProducts()
                    },
                    onReloadProducts = {
                        viewModel.getProducts()
                    })
            }
        }
    }
}
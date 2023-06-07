package com.faire.faireshop.presentation.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.faire.faireshop.R
import com.faire.faireshop.domain.Currency
import com.faire.faireshop.domain.Price
import com.faire.faireshop.domain.Product
import com.faire.faireshop.presentation.products.components.EmptyListFullScreenMessage
import com.faire.faireshop.presentation.products.components.FullScreenLoading
import com.faire.faireshop.presentation.products.components.ProductItem
import com.faire.faireshop.ui.theme.FaireShopTheme
import com.faire.faireshop.ui.theme.Typography
import com.faire.faireshop.util.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductsScreen(
    state: ProductsScreenState,
    events: Flow<UiEvent>,
    modifier: Modifier = Modifier,
    onRefreshProducts: () -> Unit = {},
    onReloadProducts: () -> Unit = {}
) {
    val scaffoldState = rememberScaffoldState()
    val pullRefreshState = rememberPullRefreshState(state.isRefreshing, onRefreshProducts)

    LaunchedEffect(key1 = true) {
        events.collect {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        onRefreshProducts()
                    }
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState, modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                state.isLoading -> {
                    FullScreenLoading()
                }
                state.products.isEmpty() -> {
                    EmptyListFullScreenMessage(onReloadProducts = onReloadProducts)
                }
                else -> {
                    Box(Modifier.pullRefresh(pullRefreshState)) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.products) { product ->
                                ProductItem(item = product)
                            }
                        }
                        PullRefreshIndicator(state.isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProductScreenPreview() {
    FaireShopTheme {
        ProductsScreen(
            state = ProductsScreenState(
                products = listOf(
                    Product(
                        "",
                        "Shooting Arrows Notecards",
                        "https://cdn.faire.com/res/hszgttpjt/image/upload/04760a693446356634eb9a41f4612632932d92a945aae3329078e928acb4e71c/1519715814.jpg",
                        details = "Keep in touch with these fun cards!Keep in touch with these fun cards!Keep in touch with these fun cards!Keep in touch with these fun cards!Keep in touch with these fun cards!",
                        wholeSalePrice = Price(9.25f, Currency.USD)
                    ),
                    Product(
                        "",
                        "Shooting Arrows NotecardsShooting Arrows NotecardsShooting Arrows Notecards",
                        "https://cdn.faire.com/res/hszgttpjt/image/upload/04760a693446356634eb9a41f4612632932d92a945aae3329078e928acb4e71c/1519715814.jpg",
                        details = "Keep in touch with these fun cards!",
                        wholeSalePrice = Price(9.25f, Currency.USD)
                    ),
                    Product(
                        "",
                        "Shooting Arrows Notecards",
                        "https://cdn.faire.com/res/hszgttpjt/image/upload/04760a693446356634eb9a41f4612632932d92a945aae3329078e928acb4e71c/1519715814.jpg",
                        details = "Keep in touch with these fun cards!",
                        wholeSalePrice = Price(9.25f, Currency.USD)
                    )
                ),
                isLoading = false
            ),
            events = flow { }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProductScreenEmptyPreview() {
    FaireShopTheme {
        ProductsScreen(
            state = ProductsScreenState(
                products = emptyList(),
                isLoading = false
            ),
            events = flow { }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProductScreenLoadingPreview() {
    FaireShopTheme {
        ProductsScreen(
            state = ProductsScreenState(
                products = emptyList(),
                isLoading = true
            ),
            events = flow { }
        )
    }
}


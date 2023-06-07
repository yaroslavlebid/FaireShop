package com.faire.faireshop.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faire.faireshop.R
import com.faire.faireshop.data.repository.ProductRepository
import com.faire.faireshop.util.Result
import com.faire.faireshop.util.ResourceProvider
import com.faire.faireshop.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsScreenState())
    val state: StateFlow<ProductsScreenState> = _state

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            _state.update { _state.value.copy(isLoading = true) }
            loadProducts()
            _state.update { _state.value.copy(isLoading = false) }
        }
    }

    fun refreshProducts() {
        viewModelScope.launch {
            _state.update { _state.value.copy(isRefreshing = true) }
            loadProducts()
            _state.update { _state.value.copy(isRefreshing = false) }
        }
    }

    private suspend fun loadProducts() {
        val result = productRepository.getProducts()
        if (result.data != null) {
            _state.update { _state.value.copy(products = result.data) }
        }
        if (result is Result.Error && result.message != null) {
            _uiEvents.send(
                UiEvent.ShowSnackbar(
                    result.message,
                    resourceProvider.getString(R.string.snackbar_try_again)
                )
            )
        }
    }
}
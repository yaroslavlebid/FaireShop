package com.faire.faireshop.presentation.products

import com.faire.faireshop.domain.Product

data class ProductsScreenState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)

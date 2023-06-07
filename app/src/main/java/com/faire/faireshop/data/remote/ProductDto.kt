package com.faire.faireshop.data.remote

data class ProductDto(
    val brandToken: String,
    val detailsText: String,
    val productImage: String,
    val productName: String,
    val productToken: String,
    val quantitySelectorModel: QuantitySelectorModel,
    val wholesalePrice: Price,
    val retailPrice: Price
)

data class QuantitySelectorModel(
    val currentQuantity: Long,
    val max: Long,
    val min: Long,
    val stepSize: Long
)

data class Price(
    val price: Float,
    val currency: String
)

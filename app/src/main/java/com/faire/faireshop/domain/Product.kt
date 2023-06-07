package com.faire.faireshop.domain

data class Product(
    val id: String,
    val name: String,
    val imageUrl: String,
    val details: String,
    val wholeSalePrice: Price
)
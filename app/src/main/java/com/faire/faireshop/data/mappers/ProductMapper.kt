package com.faire.faireshop.data.mappers

import com.faire.faireshop.data.local.ProductEntity
import com.faire.faireshop.data.remote.ProductDto
import com.faire.faireshop.domain.Currency
import com.faire.faireshop.domain.Price
import com.faire.faireshop.domain.Product

fun List<ProductDto>.toProductEntityList(): List<ProductEntity> {
    return this.map {
        it.toProductEntity()
    }
}

fun List<ProductEntity>.toProductList(): List<Product> {
    return this.map {
        it.toProduct()
    }
}

fun ProductDto.toProductEntity() = ProductEntity(
    id = productToken,
    name = productName,
    imageUrl = productImage,
    details = detailsText,
    wholesalePrice = this.wholesalePrice.price,
    wholesaleCurrency = this.wholesalePrice.currency
)

fun ProductEntity.toProduct() = Product(
    id = id,
    name = name,
    imageUrl = imageUrl,
    details = details,
    wholeSalePrice = Price(wholesalePrice, Currency.fromString(wholesaleCurrency))
)
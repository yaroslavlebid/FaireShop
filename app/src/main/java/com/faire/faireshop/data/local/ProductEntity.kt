package com.faire.faireshop.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val imageUrl: String,
    val details: String,
    val wholesalePrice: Float,
    val wholesaleCurrency: String
)

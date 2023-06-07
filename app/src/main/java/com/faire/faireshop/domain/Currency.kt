package com.faire.faireshop.domain

enum class Currency(val currency: String, val sign: String) {
    USD("USD", "$");

    companion object {
        fun fromString(currency: String): Currency =
            values().firstOrNull { it.currency == currency } ?: USD
    }
}
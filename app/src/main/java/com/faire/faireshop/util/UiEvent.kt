package com.faire.faireshop.util

interface UiEvent {
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent
}
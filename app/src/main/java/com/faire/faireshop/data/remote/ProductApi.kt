package com.faire.faireshop.data.remote

import retrofit2.http.GET

interface ProductApi {

    @GET("products-response.json")
    suspend fun getProducts(): List<ProductDto>

    companion object {
        const val BASE_URL = "https://cdn.faire.com/static/mobile-take-home/"
    }
}
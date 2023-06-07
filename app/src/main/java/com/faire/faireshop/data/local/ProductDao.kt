package com.faire.faireshop.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductDao {

    @Query("SELECT * FROM productentity")
    suspend fun getAll(): List<ProductEntity>

    @Upsert
    suspend fun upsertAll(products: List<ProductEntity>)

    @Query("DELETE FROM productentity")
    suspend fun clearAll()
}
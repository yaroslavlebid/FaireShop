package com.faire.faireshop.data.repository

import android.util.Log
import com.faire.faireshop.R
import com.faire.faireshop.data.local.ProductDao
import com.faire.faireshop.data.mappers.toProductEntityList
import com.faire.faireshop.data.mappers.toProductList
import com.faire.faireshop.data.remote.ProductApi
import com.faire.faireshop.domain.Product
import com.faire.faireshop.util.DispatcherProvider
import com.faire.faireshop.util.Result
import com.faire.faireshop.util.ResourceProvider
import javax.inject.Inject
import kotlinx.coroutines.withContext

private const val TAG = "ProductRepository"

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
}

class ProductRepositoryImpl @Inject constructor(
    private val localDataSource: ProductDao,
    private val remoteDataSource: ProductApi,
    private val dispatcherProvider: DispatcherProvider,
    private val resourceProvider: ResourceProvider
): ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> {
        return withContext(dispatcherProvider.io()) {
            return@withContext try {
                val products = remoteDataSource.getProducts()
                val entities = products.toProductEntityList()
                localDataSource.upsertAll(entities)
                Result.Success(entities.toProductList())
            } catch (e: Throwable) {
                Log.e(TAG, "Cant get remote products because of: $e")
                val message = resourceProvider.getString(R.string.no_internet_message)
                Result.Error(message = message, localDataSource.getAll().toProductList())
            }
        }
    }
}
package com.faire.faireshop.di

import android.content.Context
import androidx.room.Room
import com.faire.faireshop.data.local.ProductDao
import com.faire.faireshop.data.local.ProductDatabase
import com.faire.faireshop.data.remote.ProductApi
import com.faire.faireshop.data.repository.ProductRepository
import com.faire.faireshop.data.repository.ProductRepositoryImpl
import com.faire.faireshop.util.DispatcherProvider
import com.faire.faireshop.util.DispatcherProviderImpl
import com.faire.faireshop.util.ResourceProvider
import com.faire.faireshop.util.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            ProductDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(productDatabase: ProductDatabase): ProductDao {
        return productDatabase.dao;
    }

    @Provides
    @Singleton
    fun provideProductApi(client: OkHttpClient): ProductApi {
        return Retrofit.Builder()
            .baseUrl(ProductApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productDao: ProductDao,
        productApi: ProductApi,
        dispatcherProvider: DispatcherProvider,
        resourceProvider: ResourceProvider
    ): ProductRepository {
        return ProductRepositoryImpl(productDao, productApi, dispatcherProvider, resourceProvider)
    }

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProviderImpl(context)
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}
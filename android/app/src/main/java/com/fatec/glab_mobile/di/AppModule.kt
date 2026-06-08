package com.fatec.glab_mobile.di

import com.fatec.glab_mobile.BuildConfig
import com.fatec.glab_mobile.data.remote.GlabApiService
import com.fatec.glab_mobile.data.repository.BookingRepositoryImpl
import com.fatec.glab_mobile.data.repository.ProfessorRepositoryImpl
import com.fatec.glab_mobile.domain.repository.BookingRepository
import com.fatec.glab_mobile.domain.repository.ProfessorRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_URL + "/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideGlabApiService(retrofit: Retrofit): GlabApiService {
        return retrofit.create(GlabApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBookingRepository(
        apiService: GlabApiService
    ): BookingRepository {
        return BookingRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideProfessorRepository(
        apiService: GlabApiService
    ): ProfessorRepository {
        return ProfessorRepositoryImpl(apiService)
    }
}

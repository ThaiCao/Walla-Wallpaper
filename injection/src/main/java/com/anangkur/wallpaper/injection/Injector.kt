package com.anangkur.wallpaper.injection

import android.content.Context
import com.anangkur.wallpaper.data.Repository
import com.anangkur.wallpaper.local.LocalRepository
import com.anangkur.wallpaper.remote.RemoteRepository
import com.anangkur.wallpaper.remote.services.UnsplashService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injector {

    fun provideViewModelFactory(context: Context, baseUrl: String) = ViewModelFactory.getInstance(
        provideRepository(context, baseUrl)
    )

    private fun provideRepository(context: Context, baseUrl: String) = Repository.getInstance(
        localRepository = provideLocalRepository(context),
        remoteRepository = provideRemoteRepository(provideUnsplashService(baseUrl))
    )

    private fun provideLocalRepository(context: Context) = LocalRepository.getInstance(context)

    private fun provideRemoteRepository(unsplashService: UnsplashService) = RemoteRepository.getInstance(unsplashService)

    private fun provideUnsplashService(baseUrl: String) = provideRetrofitBuilder(baseUrl)
        .create(UnsplashService::class.java)

    private fun provideRetrofitBuilder(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpBuilder())
        .build()

    private fun provideOkHttpBuilder() = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor { chain ->
            val request =
                chain.request()
                    .newBuilder()
                    .build()
            chain.proceed(request)
        }
        .build()
}
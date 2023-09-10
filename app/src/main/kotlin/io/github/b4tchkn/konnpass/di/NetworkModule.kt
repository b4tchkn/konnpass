package io.github.b4tchkn.konnpass.di

import dagger.Module
import java.util.concurrent.TimeUnit
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.b4tchkn.konnpass.data.ConnpassDataSource
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providerConnpassDataSource(
        okHttpClient: OkHttpClient,
    ): ConnpassDataSource = ConnpassDataSource.create(okHttpClient)
}
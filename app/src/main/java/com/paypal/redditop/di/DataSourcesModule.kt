package com.paypal.redditop.di

import com.paypal.data.datasources.IPostLocalDataSource
import com.paypal.data.datasources.IPostRemoteDataSource
import com.paypal.redditop.datasources.PostLocalDataSource
import com.paypal.redditop.datasources.PostRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun providePostLocalDataSource(): IPostLocalDataSource {
        return PostLocalDataSource()
    }

    @Provides
    fun providePostRemoteDataSource(): IPostRemoteDataSource {
        return PostRemoteDataSource()
    }
}
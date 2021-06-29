package com.paypal.redditop.di

import com.paypal.redditop.data.datasources.IPostLocalDataSource
import com.paypal.redditop.data.datasources.IPostRemoteDataSource
import com.paypal.redditop.data.datasources.PostLocalDataSource
import com.paypal.redditop.data.datasources.PostRemoteDataSource
import com.paypal.redditop.data.network.RedditApi
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
    fun providePostRemoteDataSource(
        redditApi: RedditApi
    ): IPostRemoteDataSource {
        return PostRemoteDataSource(
            redditApi = redditApi
        )
    }
}
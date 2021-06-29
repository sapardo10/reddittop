package com.paypal.redditop.di

import com.paypal.redditop.data.datasources.IPostLocalDataSource
import com.paypal.redditop.data.datasources.IPostRemoteDataSource
import com.paypal.redditop.data.repositories.IPostRepository
import com.paypal.redditop.data.repositories.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun providePostRepositoryModule(
        postLocalDataSource: IPostLocalDataSource,
        postRemoteDataSource: IPostRemoteDataSource
    ): IPostRepository {
        return PostRepository(
            localDataSource = postLocalDataSource,
            remoteDataSource = postRemoteDataSource
        )
    }
}
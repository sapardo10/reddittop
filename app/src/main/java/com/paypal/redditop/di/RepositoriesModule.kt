package com.paypal.redditop.di

import androidx.paging.ExperimentalPagingApi
import com.paypal.redditop.data.database.AppDatabase
import com.paypal.redditop.data.database.PostDao
import com.paypal.redditop.data.database.PostsKeysDao
import com.paypal.redditop.data.datasources.IPostLocalDataSource
import com.paypal.redditop.data.network.RedditApi
import com.paypal.redditop.data.repositories.IPostRepository
import com.paypal.redditop.data.repositories.PostRepository
import com.paypal.redditop.data.repositories.PostsRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun providePostRepositoryModule(
        postLocalDataSource: IPostLocalDataSource,
        postsRemoteMediator: PostsRemoteMediator
    ): IPostRepository {
        return PostRepository(
            localDataSource = postLocalDataSource,
            remoteMediator = postsRemoteMediator
        )
    }

    @Provides
    fun providePostsRemoteMediator(
        redditApi: RedditApi,
        postsDao: PostDao,
        postsKeysDao: PostsKeysDao,
        appDatabase: AppDatabase
    ): PostsRemoteMediator {
        return PostsRemoteMediator(
            redditApi = redditApi,
            postsDao = postsDao,
            postsKeysDao = postsKeysDao,
            appDatabase = appDatabase
        )
    }
}
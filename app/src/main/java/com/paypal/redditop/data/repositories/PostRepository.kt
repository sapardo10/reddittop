package com.paypal.redditop.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.paypal.redditop.data.datasources.IPostLocalDataSource
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class PostRepository @Inject constructor(
    private val remoteMediator: PostsRemoteMediator,
    private val localDataSource: IPostLocalDataSource
) : IPostRepository {
    override fun getAllPosts(): Flow<PagingData<SimplePost>> {
        return Pager(
            PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { localDataSource.getAll() }
        ).flow
    }

}
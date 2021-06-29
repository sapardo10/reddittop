package com.paypal.redditop.data.repositories

import androidx.paging.PagingData
import com.paypal.redditop.data.datasources.IPostLocalDataSource
import com.paypal.redditop.data.datasources.IPostRemoteDataSource
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val localDataSource: IPostLocalDataSource,
    private val remoteDataSource: IPostRemoteDataSource
) : IPostRepository {
    override fun getAllPosts(): Flow<PagingData<SimplePost>> {
        return remoteDataSource.getAll()
    }

}
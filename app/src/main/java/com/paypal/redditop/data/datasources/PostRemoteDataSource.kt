package com.paypal.redditop.data.datasources

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.paypal.redditop.data.network.RedditApi
import com.paypal.redditop.data.network.RemotePagingSource
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
    private val redditApi: RedditApi
) : IPostRemoteDataSource {

    override fun getAll(): Flow<PagingData<SimplePost>> {
        return Pager(
            PagingConfig(pageSize = 40, enablePlaceholders = false)
        ) {
            RemotePagingSource(redditApi)
        }.flow
    }
}
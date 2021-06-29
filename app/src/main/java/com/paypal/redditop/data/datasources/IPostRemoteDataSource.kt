package com.paypal.redditop.data.datasources

import androidx.paging.PagingData
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow

interface IPostRemoteDataSource {

    /**
     * Returns a list of reddit posts
     */
    fun getAll(): Flow<PagingData<SimplePost>>
}
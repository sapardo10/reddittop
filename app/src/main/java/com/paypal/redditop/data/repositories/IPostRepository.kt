package com.paypal.redditop.data.repositories

import androidx.paging.PagingData
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow

interface IPostRepository {
    fun getAllPosts(): Flow<PagingData<SimplePost>>
    suspend fun getPost(id: String): SimplePost?
}
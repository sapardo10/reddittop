package com.paypal.redditop.data.datasources

import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow

interface IPostLocalDataSource {
    fun getAll(): Flow<List<SimplePost>>
}
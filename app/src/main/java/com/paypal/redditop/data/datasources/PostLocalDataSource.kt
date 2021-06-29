package com.paypal.redditop.data.datasources

import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostLocalDataSource @Inject constructor(

) : IPostLocalDataSource {
    override fun getAll(): Flow<List<SimplePost>> {
        return flow {

        }
    }
}
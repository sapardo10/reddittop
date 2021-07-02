package com.paypal.redditop.data.datasources

import com.paypal.redditop.data.mappers.toSimplePost
import com.paypal.redditop.data.network.RedditApi
import com.paypal.redditop.models.BaseResponse
import com.paypal.redditop.models.Result
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [IPostRemoteDataSource]
 */
class PostRemoteDataSource @Inject constructor(
    private val redditApi: RedditApi
) : IPostRemoteDataSource {

    /**
     * -------------------------------------- PUBLIC METHODS ---------------------------------------
     */

    override suspend fun getTopPosts(
        after: String?,
        before: String?,
        limit: Int
    ): Result<BaseResponse<List<SimplePost>>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = redditApi.getTopPosts(
                    after, before, limit
                )

                val list = response.data.posts.map { it.toSimplePost() }
                val data = BaseResponse<List<SimplePost>>(
                    after = response.data.after,
                    before = response.data.before,
                    list
                )
                return@withContext Result.Success(data = data)
            } catch (e: Exception) {
                return@withContext Result.Failure(
                    e
                )
            }
        }
    }
}
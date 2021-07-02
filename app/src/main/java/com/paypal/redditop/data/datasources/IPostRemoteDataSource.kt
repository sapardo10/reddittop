package com.paypal.redditop.data.datasources

import com.paypal.redditop.models.BaseResponse
import com.paypal.redditop.models.Result
import com.paypal.redditop.models.SimplePost

interface IPostRemoteDataSource {

    /**
     * Returns a list of [SimplePost] from the remote service based on the pagination parameters
     * passed to the function
     * @param after [String] key for the next page to be requested. Null if last page
     * @param before [String] key for the page before to be requested. Null if first page
     * @param limit [Int] amount of registers to be fetched
     * @return [Result] with [List] of [SimplePost] fetched. If failed, with an [Exception] inside
     */
    suspend fun getTopPosts(
        after: String? = null,
        before: String? = null,
        limit: Int = 25
    ): Result<BaseResponse<List<SimplePost>>>
}
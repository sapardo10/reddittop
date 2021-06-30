package com.paypal.redditop.data.network

import androidx.paging.PagingSource
import com.paypal.redditop.data.mappers.toSimplePost
import com.paypal.redditop.models.SimplePost
import retrofit2.HttpException
import java.io.IOException

class RemotePagingSource(
    private val redditApi: RedditApi
) : PagingSource<String, SimplePost>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, SimplePost> {
        return try {
            val response = redditApi.getTopPosts(limit = params.loadSize, after = params.key)

            val redditPosts = response.data.posts.map { it.toSimplePost() }

            LoadResult.Page(
                redditPosts,
                response.data.before,
                response.data.after
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override val keyReuseSupported: Boolean = true

}
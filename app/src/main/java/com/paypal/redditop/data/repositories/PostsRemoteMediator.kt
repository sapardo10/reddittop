package com.paypal.redditop.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.paypal.redditop.data.datasources.IPostLocalDataSource
import com.paypal.redditop.data.datasources.IPostRemoteDataSource
import com.paypal.redditop.models.Result
import com.paypal.redditop.models.SimplePost
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PostsRemoteMediator(
    private val postLocalDataSource: IPostLocalDataSource,
    private val postRemoteDataSource: IPostRemoteDataSource,
) : RemoteMediator<Int, SimplePost>() {

    /**
     * -------------------------------------- PUBLIC METHODS ---------------------------------------
     */

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SimplePost>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    postLocalDataSource.clearAll()
                    null
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    postLocalDataSource.getLastKeys()
                }
            }
            val response = postRemoteDataSource.getTopPosts(
                after = loadKey?.after,
                before = loadKey?.before,
                limit = state.config.pageSize
            )

            when (response) {
                is Result.Success -> {
                    val posts = response.data.data
                    postLocalDataSource.insertPosts(
                        afterKey = response.data.after,
                        beforeKey = response.data.before,
                        cleanBeforeSaving = posts.isNotEmpty() && loadKey?.after == null,
                        posts = posts
                    )
                    MediatorResult.Success(endOfPaginationReached = response.data.after == null)
                }
                is Result.Failure -> {
                    return MediatorResult.Error(response.error)
                }
            }
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
}
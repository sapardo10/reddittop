package com.paypal.redditop.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.paypal.redditop.data.database.AppDatabase
import com.paypal.redditop.data.database.PostDao
import com.paypal.redditop.data.database.PostsKeysDao
import com.paypal.redditop.data.mappers.toSimplePost
import com.paypal.redditop.data.network.RedditApi
import com.paypal.redditop.models.PostsKeys
import com.paypal.redditop.models.SimplePost
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PostsRemoteMediator(
    private val redditApi: RedditApi,
    private val postsDao: PostDao,
    private val postsKeysDao: PostsKeysDao,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, SimplePost>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SimplePost>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    getPostsKeys()
                }
            }
            val response = redditApi.getTopPosts(
                after = loadKey?.after,
                before = loadKey?.before,
                limit = state.config.pageSize
            )

            val posts = response.data.posts.map { it.toSimplePost() }

            appDatabase.withTransaction {
                postsKeysDao.savePostsKeys(PostsKeys(0, response.data.after, response.data.before))
                postsDao.insertAll(posts)
            }

            MediatorResult.Success(endOfPaginationReached = response.data.after == null)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getPostsKeys(): PostsKeys? {
        return postsKeysDao.getPostsKeys().firstOrNull()
    }
}
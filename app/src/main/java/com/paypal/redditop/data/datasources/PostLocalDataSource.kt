package com.paypal.redditop.data.datasources

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.paypal.redditop.data.database.AppDatabase
import com.paypal.redditop.models.PostsKeys
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [IPostLocalDataSource]
 */
class PostLocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase
) : IPostLocalDataSource {

    /**
     * -------------------------------------- PUBLIC METHODS ---------------------------------------
     */

    override suspend fun clearAll() {
        appDatabase.postDao().clearAll()
    }

    override fun getAll(): PagingSource<Int, SimplePost> {
        return appDatabase.postDao().getAllPaged()
    }

    override suspend fun getLastKeys(): PostsKeys? {
        return appDatabase.postKeysDao().getPostsKeys().firstOrNull()
    }

    override suspend fun getPost(id: String): SimplePost? {
        return withContext(Dispatchers.IO) {
            return@withContext appDatabase.postDao().getPost(id)
        }
    }

    override suspend fun insertPosts(
        afterKey: String?,
        beforeKey: String?,
        cleanBeforeSaving: Boolean,
        posts: List<SimplePost>
    ) {
        appDatabase.withTransaction {
            if (cleanBeforeSaving) {
                appDatabase.postDao().clearAll()
            }
            appDatabase.postKeysDao().savePostsKeys(
                PostsKeys(
                    0,
                    afterKey,
                    beforeKey
                )
            )
            appDatabase.postDao().insertAll(posts)
        }
    }
}
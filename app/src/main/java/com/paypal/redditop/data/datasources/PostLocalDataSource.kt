package com.paypal.redditop.data.datasources

import androidx.paging.PagingSource
import com.paypal.redditop.data.database.PostDao
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostLocalDataSource @Inject constructor(
    private val postsDao: PostDao
) : IPostLocalDataSource {
    override fun getAll(): PagingSource<Int, SimplePost> {
        return postsDao.getAllPaged()
    }

    override suspend fun getPost(id: String): SimplePost? {
        return withContext(Dispatchers.IO) {
            return@withContext postsDao.getPost(id)
        }
    }
}
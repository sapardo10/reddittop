package com.paypal.redditop.data.datasources

import androidx.paging.PagingSource
import com.paypal.redditop.data.database.PostDao
import com.paypal.redditop.models.SimplePost
import javax.inject.Inject

class PostLocalDataSource @Inject constructor(
    private val postsDao: PostDao
) : IPostLocalDataSource {
    override fun getAll(): PagingSource<Int, SimplePost> {
        return postsDao.getAllPaged()
    }
}
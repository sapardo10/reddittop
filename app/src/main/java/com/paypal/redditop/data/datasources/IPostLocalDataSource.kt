package com.paypal.redditop.data.datasources

import androidx.paging.PagingSource
import com.paypal.redditop.models.SimplePost

interface IPostLocalDataSource {
    fun getAll(): PagingSource<Int, SimplePost>
}
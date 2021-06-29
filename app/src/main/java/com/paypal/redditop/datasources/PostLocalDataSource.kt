package com.paypal.redditop.datasources

import com.paypal.data.datasources.IPostLocalDataSource
import javax.inject.Inject

class PostLocalDataSource @Inject constructor(

) : IPostLocalDataSource {
    override fun getAll() {
        print("local data source")
    }
}
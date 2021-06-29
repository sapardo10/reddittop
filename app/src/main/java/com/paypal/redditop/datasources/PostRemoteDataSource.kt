package com.paypal.redditop.datasources

import com.paypal.data.datasources.IPostRemoteDataSource
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(

) : IPostRemoteDataSource {
    override fun getAll() {
        print("remote data source")
    }
}
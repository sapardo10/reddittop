package com.paypal.data.repositories

import com.paypal.data.datasources.IPostLocalDataSource
import com.paypal.data.datasources.IPostRemoteDataSource
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val localDataSource: IPostLocalDataSource,
    private val remoteDataSource: IPostRemoteDataSource
) : IPostRepository {
    override fun getAllPosts() {
        print("repository")
        localDataSource.getAll()
        remoteDataSource.getAll()
    }

}
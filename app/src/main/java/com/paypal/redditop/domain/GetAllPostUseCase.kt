package com.paypal.redditop.domain

import androidx.paging.PagingData
import com.paypal.redditop.data.repositories.IPostRepository
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IGetAllPostUseCase {
    operator fun invoke(): Flow<PagingData<SimplePost>>
}

class GetAllPostUseCase @Inject constructor(
    private val postRepository: IPostRepository
) : IGetAllPostUseCase {
    override operator fun invoke(): Flow<PagingData<SimplePost>> {
        return postRepository.getAllPosts()
    }
}
package com.paypal.redditop.domain

import com.paypal.redditop.data.repositories.IPostRepository
import com.paypal.redditop.models.SimplePost
import javax.inject.Inject

interface IGetPostUseCase {
    suspend operator fun invoke(id: String): SimplePost?
}

class GetPostUseCase @Inject constructor(
    private val postRepository: IPostRepository
) : IGetPostUseCase {
    override suspend operator fun invoke(id: String): SimplePost? {
        return postRepository.getPost(id)
    }
}
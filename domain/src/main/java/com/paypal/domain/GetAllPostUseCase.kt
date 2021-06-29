package com.paypal.domain

import com.paypal.data.repositories.IPostRepository
import javax.inject.Inject

interface IGetAllPostUseCase {
    operator fun invoke()
}

class GetAllPostUseCase @Inject constructor(
    private val postRepository: IPostRepository
) : IGetAllPostUseCase {
    override fun invoke() {
        print("use case")
        postRepository.getAllPosts()
    }
}
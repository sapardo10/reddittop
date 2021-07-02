package com.paypal.redditop.domain

import androidx.paging.PagingData
import com.paypal.redditop.data.repositories.IPostRepository
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interface that holds the contract for the use case that returns a flow to watch all the
 * [SimplePost] to be shown to the user
 */
interface IGetAllPostUseCase {

    /**
     * Returns a flow that will update the collector with every update on the posts
     * @return [Flow] of [PagingData] with [SimplePost]
     */
    operator fun invoke(): Flow<PagingData<SimplePost>>
}

class GetAllPostUseCase @Inject constructor(
    private val postRepository: IPostRepository
) : IGetAllPostUseCase {

    /**
     * -------------------------------------- PUBLIC METHODS ---------------------------------------
     */

    override operator fun invoke(): Flow<PagingData<SimplePost>> {
        return postRepository.getAllPosts()
    }
}
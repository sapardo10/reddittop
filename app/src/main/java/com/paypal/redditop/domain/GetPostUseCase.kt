package com.paypal.redditop.domain

import com.paypal.redditop.data.repositories.IPostRepository
import com.paypal.redditop.models.SimplePost
import javax.inject.Inject

/**
 * Interface that holds the contract for the use case that gets the detail of a post given the id
 * of it
 */
interface IGetPostUseCase {

    /**
     * Method that returns a [SimplePost] that has the id passed as parameter
     * @param id [String] of the post to be retrieved
     * @return [SimplePost] with the given if, null if not found
     */
    suspend operator fun invoke(id: String): SimplePost?
}

/**
 * Implementation of [IGetPostUseCase]
 */
class GetPostUseCase @Inject constructor(
    private val postRepository: IPostRepository
) : IGetPostUseCase {

    /**
     * -------------------------------------- PUBLIC METHODS ---------------------------------------
     */

    override suspend operator fun invoke(id: String): SimplePost? {
        return postRepository.getPost(id)
    }
}
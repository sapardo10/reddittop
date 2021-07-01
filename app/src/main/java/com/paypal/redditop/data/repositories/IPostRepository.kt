package com.paypal.redditop.data.repositories

import androidx.paging.PagingData
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.flow.Flow

/**
 * Interface that holds the contract for the class that implements all data transactions related
 * to [SimplePost]
 */
interface IPostRepository {

    /**
     * Method that returns a [Flow] with a [PagingData] that holds [SimplePost] in a paginated
     * fashion
     * @return [Flow] with paginated data that has [SimplePost]
     */
    fun getAllPosts(): Flow<PagingData<SimplePost>>

    /**
     * Method that returns a [SimplePost] with the id given as parameter
     * @param id [String] of the post to be retrieved
     * @return [SimplePost] with the given id
     */
    suspend fun getPost(id: String): SimplePost?
}
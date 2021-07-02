package com.paypal.redditop.data.datasources

import androidx.paging.PagingSource
import com.paypal.redditop.models.PostsKeys
import com.paypal.redditop.models.SimplePost

/**
 * Interface that holds the contract for the data source that is in charge of all the data
 * transactions performed locally on the device related to posts
 */
interface IPostLocalDataSource {

    /**
     * Method that clears all the posts saved locally
     */
    suspend fun clearAll()

    /**
     * Method that returns a list of [SimplePost] paginated
     * @return [PagingSource] of [SimplePost]
     */
    fun getAll(): PagingSource<Int, SimplePost>

    /**
     * Method that returns the las [PostsKeys] saved if any. If none, it returns null
     * @return Last [PostsKeys] saved. Null if none.
     */
    suspend fun getLastKeys(): PostsKeys?

    /**
     * Method that returns the [SimplePost] that has the id passed as parameter
     * @param id [String] of the [SimplePost]
     * @return [SimplePost] with the given id
     */
    suspend fun getPost(id: String): SimplePost?

    /**
     * Method that saves locally the [List] of [SimplePost] passed as parameter.
     * @param afterKey [String] key that refers to the next page to be fetched from the remote
     * source
     * @param beforeKey [String] key that refers to the before page to be fetched from the remote
     * source
     * @param cleanBeforeSaving [Boolean] if true, it will delete all the local posts before
     * performing the insertion
     * @param posts [List] of [SimplePost] to be saved
     */
    suspend fun insertPosts(
        afterKey: String?,
        beforeKey: String?,
        cleanBeforeSaving: Boolean,
        posts: List<SimplePost>
    )
}
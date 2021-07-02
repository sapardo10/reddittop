package com.paypal.redditop.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.paypal.redditop.domain.IGetAllPostUseCase
import com.paypal.redditop.models.SimplePost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllPostUseCase: IGetAllPostUseCase
) : ViewModel() {

    /**
     * ------------------------------------- PUBLIC METHODS ----------------------------------------
     */

    /**
     * Method that returns a flow of paginated data to update the view with the new posts that keep
     * arriving to the application. It also caches the result into the viewModelScope so it can
     * survive better the orientation changes
     * @return [Flow] of [PagingData] with [SimplePost]
     */
    fun getPosts(): Flow<PagingData<SimplePost>> {
        return getAllPostUseCase().cachedIn(viewModelScope)
    }
}
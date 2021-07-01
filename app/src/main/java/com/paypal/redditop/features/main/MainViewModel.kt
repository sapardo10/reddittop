package com.paypal.redditop.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.paypal.redditop.domain.IGetAllPostUseCase
import com.paypal.redditop.models.SimplePost
import com.paypal.redditop.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllPostUseCase: IGetAllPostUseCase
) : ViewModel() {

    val actions = SingleLiveEvent<MainViewModelActions>()


    fun getPosts(): Flow<PagingData<SimplePost>> {
        return getAllPostUseCase().cachedIn(viewModelScope)
    }

    fun onItemClicked(simplePost: SimplePost) {
        actions.postValue(MainViewModelActions.NavigateToPostDetails(simplePost))
    }
}

sealed class MainViewModelActions {
    class NavigateToPostDetails(
        simplePost: SimplePost
    ) : MainViewModelActions()
}
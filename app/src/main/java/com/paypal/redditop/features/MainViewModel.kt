package com.paypal.redditop.features

import androidx.lifecycle.AndroidViewModel
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

    fun getPosts(): Flow<PagingData<SimplePost>> {
        return getAllPostUseCase().cachedIn(viewModelScope)
    }
}
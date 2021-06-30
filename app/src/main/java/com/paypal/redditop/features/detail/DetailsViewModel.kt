package com.paypal.redditop.features.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paypal.redditop.domain.IGetPostUseCase
import com.paypal.redditop.models.SimplePost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPostUseCase: IGetPostUseCase
) : ViewModel() {

    val model = MutableLiveData<SimplePost?>()

    fun getSimplePostDetails(id: String) {
        viewModelScope.launch {
            val post = getPostUseCase(id)
            model.postValue(post)
        }
    }
}
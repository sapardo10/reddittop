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

    /**
     * ------------------------------------- PUBLIC METHODS ----------------------------------------
     */

    /**
     * Method that retrieves the [SimplePost] with the id given as parameter and updates the [model]
     * so the view can react accordingly
     * @param id [String] of the [SimplePost] to be fetched
     */
    fun getSimplePostDetails(id: String) {
        viewModelScope.launch {
            val post = getPostUseCase(id)
            model.postValue(post)
        }
    }
}
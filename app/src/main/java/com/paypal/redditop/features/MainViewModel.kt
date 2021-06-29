package com.paypal.redditop.features

import androidx.lifecycle.ViewModel
import com.paypal.domain.IGetAllPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllPostUseCase: IGetAllPostUseCase
) : ViewModel() {

    fun onClick() {
        print("view model")
        getAllPostUseCase()
    }
}
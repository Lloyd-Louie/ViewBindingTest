package com.example.multilanguage.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.multilanguage.model.MemeImages
import com.example.multilanguage.repositories.UserRepository
import com.example.multilanguage.utils.GLOBAL_TAG
import com.example.multilanguage.utils.SingleLiveEvent

import com.example.multilanguage.utils.decodeNetworkError
import com.example.multilanguage.utils.decodeUnknownError
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemeViewModel(private val userRepository: UserRepository) :BaseViewModel() {
    val memeResponse = SingleLiveEvent<MemeImages>()

    fun getMemeImages(){
        jobList.add(launch {
        val result = withContext(Dispatchers.IO) { userRepository.getMemeImages() }
        when (result) {
            is NetworkResponse.Success -> {
                Log.i("MemeViewModel","Success")
                memeResponse.value = result.body
            }
            is NetworkResponse.ServerError -> {
                Log.i("MemeViewModel","ServerError")
               // showServerError.value = decodeErrorResponse( result.body,result.code)
            }
            is NetworkResponse.NetworkError -> {

                Log.i("MemeViewModel","NetworkError ${result.error}")
                cancelJobs()
                showNetworkError.value = decodeNetworkError(result.error)
            }
            is NetworkResponse.UnknownError -> {
                Log.i("MemeViewModel","UnknownError")
                showUnknownError.value = decodeUnknownError(result.error)
            }
        }
    })
    }

}
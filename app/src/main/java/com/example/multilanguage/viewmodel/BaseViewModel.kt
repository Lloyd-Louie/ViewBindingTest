package com.example.multilanguage.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.multilanguage.model.ServerError
import com.example.multilanguage.utils.GLOBAL_TAG
import com.example.multilanguage.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel(), CoroutineScope, KoinComponent {
    private val className = javaClass.simpleName
    val mTag = "$GLOBAL_TAG ${javaClass.simpleName}"
    /**
     * Setup dispatchers
     */
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    val jobList = mutableListOf<Job>()
    val showServerError = SingleLiveEvent<ServerError>()
    val showNetworkError = SingleLiveEvent<String>()
    val showUnknownError = SingleLiveEvent<String>()


    /**
     * Lifecycle logs
     */
    override fun onCleared() {
        Log.e(GLOBAL_TAG, "$className onCleared")
        super.onCleared()
        cancelJobs()
    }

    /**
     * Utilities
     */
    fun cancelJobs() {
        if (!jobList.isNullOrEmpty()) {
            for (i in jobList) {
                i.cancel()
            }
            Log.e(GLOBAL_TAG, "Cleared ${jobList.size} job(s)")
            jobList.clear()
        }
    }
}
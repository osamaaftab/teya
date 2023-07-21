package com.osamaaftab.teya.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by BaseViewModel.
     * Since we pass viewModelJob, we can cancel all coroutines
     * launched by scope by calling viewModelJob.cancel()
     */
    protected val scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Cancel the job when the view model is destroyed
    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
package com.osamaaftab.teya.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.osamaaftab.teya.domain.model.EntryModel
import com.osamaaftab.teya.domain.model.ErrorModel
import com.osamaaftab.teya.domain.model.FeedModel
import com.osamaaftab.teya.domain.usecase.GetAlbumUseCase
import com.osamaaftab.teya.domain.usecase.base.UseCaseResponse
import com.osamaaftab.teya.presentation.base.BaseViewModel

class AlbumViewModel(private val getAlbumUseCase: GetAlbumUseCase) : BaseViewModel() {

    private val onProgressShowLiveData = MutableLiveData<Boolean>()

    private val onErrorShowLiveData = MutableLiveData<Boolean>()

    private val albumListLiveData = MutableLiveData<List<EntryModel>>()

    fun getAlbumUseCaseResponse() = object : UseCaseResponse<FeedModel> {
        override fun onSuccess(result: FeedModel) {
            Log.i(ContentValues.TAG, "result: $result")
            onProgressShowLiveData.postValue(false)
            onErrorShowLiveData.postValue(false)
            albumListLiveData.postValue(result.entry)
        }

        override fun onError(errorModel: ErrorModel?) {
            Log.i(ContentValues.TAG, "error status: ${errorModel?.errorStatus}")
            Log.i(ContentValues.TAG, "error message: ${errorModel?.message}")
            onProgressShowLiveData.postValue(false)
            onErrorShowLiveData.postValue(true)
        }
    }

    fun loadAlbumList() {
        onProgressShowLiveData.postValue(true)
        getAlbumUseCase.invoke(scope, NUMBER_OF_ITEMS, getAlbumUseCaseResponse())
    }

    fun refreshAlbumList() {
        getAlbumUseCase.invoke(scope, NUMBER_OF_ITEMS, getAlbumUseCaseResponse())
    }

    fun getShowProgressLiveData(): LiveData<Boolean> {
        return onProgressShowLiveData
    }

    fun getShowErrorLiveData(): LiveData<Boolean> {
        return onErrorShowLiveData
    }

    fun getAlbumListLiveData(): LiveData<List<EntryModel>> {
        return albumListLiveData
    }

    companion object {
        private const val NUMBER_OF_ITEMS = 100
    }
}
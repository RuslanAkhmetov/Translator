package ru.geekbrain.android.translator.view.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.geekbrain.android.translator.model.data.AppState
import ru.geekbrain.android.translator.utils.parseSearchResults

class MainViewModel (private val interactor: MainInteractor)
 : BaseViewModel<AppState>(){

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData


    fun subscribe(): LiveData<AppState> =
        liveDataForViewToObserve

    override fun getWord(searchText: String, isOnline: Boolean){
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(searchText, isOnline) }

    }

    private suspend fun startInteractor(searchText: String, online: Boolean) =
        withContext(Dispatchers.IO){
            _mutableLiveData.postValue(parseSearchResults(interactor.getWord(searchText, online)))
        }

    override fun handleError(throwable: Throwable) {
        _mutableLiveData.postValue(AppState.Error(throwable))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }


}
package ru.geekbrain.android.historyscreen.view.history

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.repository.parseLocalSearchResults
import ru.geekbrain.android.core.viewmodel.BaseViewModel
import ru.geekbrain.android.repository.parseSearchResults

class HistoryViewModel(private val interactor: HistoryInteractor): BaseViewModel<AppState>() {

    private val liveDataToForViewObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState>{
        return liveDataToForViewObserve
    }

    override fun handleError(throwable: Throwable) {
        _mutableLiveData.postValue(AppState.Error(throwable))
    }

    override fun getWord(searchText: String, isOnline: Boolean) {
       _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(searchText, isOnline) }
    }

    fun getAll() {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor() }
    }

    private suspend fun startInteractor(){
        _mutableLiveData.postValue(parseLocalSearchResults(interactor.getAll()))
    }

    private suspend fun startInteractor(searchText: String, online: Boolean) {
        _mutableLiveData.postValue(parseSearchResults(interactor.getWord(searchText, online)))

    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }

}

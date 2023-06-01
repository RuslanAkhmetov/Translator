package ru.geekbrain.android.translator.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.geekbrain.android.translator.model.data.AppState

abstract class BaseViewModel<T : AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData(),
) : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    abstract fun handleError(throwable: Throwable)

    protected fun cancelJob(){
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun getWord(searchText: String, isOnline: Boolean)

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }


}
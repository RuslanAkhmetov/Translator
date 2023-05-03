package ru.geekbrain.android.translator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.rx.SchedulerProviderImpl

abstract class BaseViewModel<T : AppState>(
    protected val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val scheduleProvider: ScheduleProvider = SchedulerProviderImpl()
    ) : ViewModel(){

    open fun getWord(searchText: String, isOnline: Boolean): LiveData<T> =
            liveDataForViewToObserve

    override fun onCleared() {
        compositeDisposable.clear()
    }


}
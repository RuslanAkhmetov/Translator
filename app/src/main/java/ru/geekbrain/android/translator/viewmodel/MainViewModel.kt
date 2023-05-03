package ru.geekbrain.android.translator.viewmodel

import androidx.lifecycle.LiveData
import io.reactivex.observers.DisposableObserver
import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.domain.DataSourceLocal
import ru.geekbrain.android.translator.domain.DataSourceRemote
import ru.geekbrain.android.translator.domain.RepositoryImpl
import ru.geekbrain.android.translator.model.BaseViewModel
import ru.geekbrain.android.translator.interactor.MainInteractor

class MainViewModel(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    )
) : BaseViewModel<AppState>(){

    private var appState:AppState? = null

    override fun getWord(searchText: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getWord(searchText, isOnline)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe{liveDataForViewToObserve.value = AppState.Loading(null)}
                .subscribeWith(getObserver())
        )
        return super.getWord(searchText, isOnline)
    }

    private fun getObserver(): DisposableObserver<in AppState> =
        object : DisposableObserver<AppState>(){
            override fun onNext(state: AppState) {
                appState = state
                liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }

        }

}
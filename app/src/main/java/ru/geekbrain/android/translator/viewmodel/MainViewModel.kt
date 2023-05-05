package ru.geekbrain.android.translator.viewmodel

import androidx.lifecycle.LiveData
import io.reactivex.observers.DisposableObserver
import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.model.BaseViewModel
import ru.geekbrain.android.translator.interactor.MainInteractor
import ru.geekbrain.android.translator.utils.parseSearchResults
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor)
 : BaseViewModel<AppState>(){

    private var appState:AppState? = null

    fun subscribe(): LiveData<AppState> =
         liveDataForViewToObserve


    override fun getWord(searchText: String, isOnline: Boolean){
        compositeDisposable.add(
            interactor.getWord(searchText, isOnline)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .doOnSubscribe{liveDataForViewToObserve.value = AppState.Loading(null)}
                .subscribeWith(getObserver())
        )

    }

    private fun getObserver(): DisposableObserver<in AppState> =
        object : DisposableObserver<AppState>(){
            override fun onNext(state: AppState) {
                appState = parseSearchResults(state)
                liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }

        }



}
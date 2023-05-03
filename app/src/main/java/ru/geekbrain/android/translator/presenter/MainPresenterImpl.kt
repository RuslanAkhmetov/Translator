package ru.geekbrain.android.translator.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.domain.*
import ru.geekbrain.android.translator.model.ScheduleProvider
import ru.geekbrain.android.translator.rx.SchedulerProviderImpl
import ru.geekbrain.android.translator.ui.TranslatorContract

class MainPresenterImpl<T: AppState, V: TranslatorContract.View>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: ScheduleProvider = SchedulerProviderImpl()
) : TranslatorContract.Presenter<T,V>{

    private var currentView: V? = null

    override fun attachView(view: V) {
        if(currentView != view)
            currentView = view
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if(currentView != view)
            currentView = null
    }

    override fun getWord(searchText: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getWord(searchText, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe{currentView?.renderData(AppState.Loading(null))}
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> =
        object : DisposableObserver<AppState>() {
            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }

        }




}
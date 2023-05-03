package ru.geekbrain.android.translator.model

import io.reactivex.Observable
import ru.geekbrain.android.translator.data.AppState

class TranslatorContract {

    interface View{
        fun renderData(appState: AppState)
    }

    interface Presenter<T:AppState, V: View>{
        fun attachView(view:V)
        fun detachView(view: V)
        fun getWord(searchText: String, isOnline: Boolean)
    }

    interface Interactor<T> {
        fun getWord(searchText: String, fromRemoteSource: Boolean) : Observable<T>
    }

    interface Repository<T>{
        fun getWord(searchText: String): Observable<T>
    }

    interface DataSource<T>{
        fun getWord(searchText: String):Observable<T>
    }



}
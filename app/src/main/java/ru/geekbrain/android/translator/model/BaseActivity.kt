package ru.geekbrain.android.translator.model

import androidx.appcompat.app.AppCompatActivity
import ru.geekbrain.android.translator.data.AppState

abstract class BaseActivity<T:AppState> : AppCompatActivity(){

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: AppState)

}
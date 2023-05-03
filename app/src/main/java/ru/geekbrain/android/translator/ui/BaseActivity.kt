package ru.geekbrain.android.translator.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrain.android.translator.data.AppState

abstract class BaseActivity<T:AppState> : AppCompatActivity(), TranslatorContract.View{

    protected lateinit var presenter: TranslatorContract.Presenter<T, TranslatorContract.View>

    protected abstract fun createPresenter(): TranslatorContract.Presenter<T, TranslatorContract.View>

    abstract override fun renderData(appState: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }


}
package ru.geekbrain.android.translator.view.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrain.android.translator.R
import ru.geekbrain.android.translator.model.data.AppState
import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.databinding.LoadingLayoutBinding
import ru.geekbrain.android.translator.view.main.BaseViewModel
import ru.geekbrain.android.translator.model.TranslatorContract
import ru.geekbrain.android.translator.utils.AlertDialogFragment
import ru.geekbrain.android.translator.utils.isOnline

private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

abstract class BaseActivity<T : AppState, I : TranslatorContract.Interactor<T>> :
    AppCompatActivity() {

    private lateinit var binding: LoadingLayoutBinding

    abstract val model: BaseViewModel<T>

    protected var isNetworkAvailable = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        binding = LoadingLayoutBinding.inflate(layoutInflater)
        isNetworkAvailable = isOnline(applicationContext)
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.words?.let {
                    if (it.isEmpty()) {
                        AlertDialogFragment.newInstance(
                            getString(R.string.dialog_title_sorry),
                            getString(R.string.empty_server_response)
                        )
                    } else {
                        setDataToAdapter(it)
                    }

                }
            }
            is AppState.Loading -> {
                showViewWorking()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.progressBarHorizontal.progress = appState.progress
                    binding.progressBarRound.visibility = View.GONE
                } else {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                AlertDialogFragment.newInstance(
                    getString(R.string.error_textview_stub),
                    appState.error.message
                )
            }
        }
    }

    abstract fun setDataToAdapter(word: List<Word>)

    private fun showNoInternetConnectionDialog() {
        AlertDialogFragment.newInstance(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    private fun isDialogNull(): Boolean =
        supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null

    private fun showViewWorking() {
        binding.loadingFrameLayout.visibility = View.GONE
    }

}
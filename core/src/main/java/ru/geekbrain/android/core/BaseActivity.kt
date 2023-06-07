package ru.geekbrain.android.core

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrain.android.core.databinding.LoadingLayoutBinding
import ru.geekbrain.android.core.viewmodel.BaseViewModel
import ru.geekbrain.android.core.viewmodel.Interactor
import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.userdata.Word
import ru.geekbrain.android.utils.AlertDialogFragment
import ru.geekbrain.android.utils.online.OnlineLiveData

private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

abstract class BaseActivity<T : AppState, I : Interactor<T>> :
    AppCompatActivity() {

    private lateinit var binding: LoadingLayoutBinding

    abstract val model: BaseViewModel<T>

    private var isNetworkAvailable:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToNetworkChange()
    }

    private fun subscribeToNetworkChange(){
        OnlineLiveData(this).observe(
            this
        ) {
            isNetworkAvailable = it
            if (!isNetworkAvailable) {
                showNoInternetConnectionDialog()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding = LoadingLayoutBinding.inflate(layoutInflater)
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
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
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
                    binding.progressBarHorizontal.progress = appState.progress!!
                    binding.progressBarRound.visibility = View.GONE
                } else {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                AlertDialogFragment.newInstance(
                    getString(R.string.error_stub),
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
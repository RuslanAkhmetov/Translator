package ru.geekbrain.android.translator.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ru.geekbrain.android.translator.R
import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.databinding.ActivityMainBinding
import ru.geekbrain.android.translator.domain.ImageLoaderImpl
import ru.geekbrain.android.translator.presenter.MainPresenterImpl
import ru.geekbrain.android.translator.model.TranslatorContract

class MainActivity : BaseActivity<AppState>() {

    companion object{
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

    }

    private lateinit var binding: ActivityMainBinding

    private var adapter: MainAdapter? = null

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(word: Word) {
                Toast.makeText(this@MainActivity, word.text, Toast.LENGTH_SHORT).show()
            }

        }

    override fun createPresenter(): TranslatorContract.Presenter<AppState, TranslatorContract.View> =
        MainPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(
                object : SearchDialogFragment.OnSearchClickListener {
                    override fun onClick(searchWord: String) {
                        presenter.getWord(searchWord, true)
                    }

                }
            )
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }



    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val word = appState.words
                if (word == null || word.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if(adapter == null){
                        binding.mainActivityRecyclerview.layoutManager =
                            LinearLayoutManager(applicationContext)
                        binding.mainActivityRecyclerview.adapter =
                            MainAdapter(onListItemClickListener, word, ImageLoaderImpl())
                    } else{
                        adapter!!.setData(word)
                    }

                }

            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null){
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.progressBarRound.visibility = View.GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else{
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> showErrorScreen(appState.error.message)
        }
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = View.GONE
        binding.loadingFrameLayout.visibility =  View.VISIBLE
        binding.errorLinearLayout.visibility = View.GONE
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = View.VISIBLE
        binding.loadingFrameLayout.visibility =  View.GONE
        binding.errorLinearLayout.visibility = View.GONE
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener{
            presenter.getWord("hi", true)
        }

    }



    private fun showViewError() {
        binding.successLinearLayout.visibility = View.GONE
        binding.loadingFrameLayout.visibility =  View.GONE
        binding.errorLinearLayout.visibility = View.VISIBLE
    }

}
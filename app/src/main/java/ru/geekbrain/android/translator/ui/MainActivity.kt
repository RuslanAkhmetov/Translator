package ru.geekbrain.android.translator.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import ru.geekbrain.android.translator.R
import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.databinding.ActivityMainBinding
import ru.geekbrain.android.translator.di.ViewModelFactory
import ru.geekbrain.android.translator.model.BaseActivity
import ru.geekbrain.android.translator.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : BaseActivity<AppState>() {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
        private const val ALLERT_DIALOG_TAG = "alertDialog"
        private const val SEARCH_WORD = "searchWord"
        private const val TAG = "MainActivity"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override lateinit var model: MainViewModel

    private val observer = Observer<AppState> { renderData(it) }

    private lateinit var binding: ActivityMainBinding

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private  var lastSearchWord : String = ""

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(word: Word) {
                Toast.makeText(this@MainActivity, word.text, Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = viewModelFactory.create(MainViewModel::class.java)

        model.subscribe().observe(this@MainActivity, observer)

        savedInstanceState?.apply {
            this.getString(SEARCH_WORD)?.let { textWord ->
                model.getWord(textWord, true)
            }
        }

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()

            searchDialogFragment.setOnSearchClickListener(
                object : SearchDialogFragment.OnSearchClickListener {
                    override fun onClick(searchWord: String) {
                        model.getWord(searchWord, true)
                        lastSearchWord = searchWord

                    }

                }
            )
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

        binding.mainActivityRecyclerview.layoutManager =
            LinearLayoutManager(applicationContext)
        binding.mainActivityRecyclerview.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_WORD, lastSearchWord)
    }


    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val word = appState.words
                if (word.isNullOrEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    adapter.setData(word)
                }

            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.progressBarRound.visibility = View.GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                AlertDialogFragment
                    .newInstance(getString(R.string.error_textview_stub), appState.error.message)
                    .show(supportFragmentManager, ALLERT_DIALOG_TAG)
                showViewSuccess()
            }
        }
    }

    private fun showViewWorking() {
        binding.loadingFrameLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = View.GONE
        binding.loadingFrameLayout.visibility = View.VISIBLE
        binding.errorLinearLayout.visibility = View.GONE
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = View.VISIBLE
        binding.loadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.GONE
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            model.getWord("hi", true)
        }

    }


    private fun showViewError() {
        binding.successLinearLayout.visibility = View.GONE
        binding.loadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.VISIBLE
    }


}
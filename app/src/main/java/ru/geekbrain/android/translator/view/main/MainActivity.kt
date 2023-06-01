package ru.geekbrain.android.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ru.geekbrain.android.translator.R
import ru.geekbrain.android.translator.model.data.AppState
import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.databinding.ActivityMainBinding
import ru.geekbrain.android.translator.view.history.HistoryActivity
import ru.geekbrain.android.translator.view.descriptionscreen.DescriptionActivity
import ru.geekbrain.android.translator.view.main.adapter.MainAdapter
import ru.geekbrain.android.translator.utils.convertMeaningsToString
import ru.geekbrain.android.translator.view.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
        private const val ALLERT_DIALOG_TAG = "alertDialog"
        private const val SEARCH_WORD = "searchWord"
        private const val TAG = "MainActivity"
    }

    override lateinit var model: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private  var lastSearchWord : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initViews()


    }

    private fun initViewModel(){
        if(binding.mainActivityRecyclerview.adapter != null){
            throw IllegalStateException("ViewModel should be initialized")
        }
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this, Observer<AppState> { renderData(it) })
    }

    private fun initViews(){
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

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(word: Word) {
                startActivity(
                    word.meanings?.let { convertMeaningsToString(it)}?.let {
                        DescriptionActivity.getIntent(
                            this@MainActivity,
                            word.text,
                            it,
                            word.meanings?.get(0)?.imageUrl
                        )
                    }
                )
            }
        }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun setDataToAdapter(word: List<Word>) {
        adapter.setData(word)
    }




}
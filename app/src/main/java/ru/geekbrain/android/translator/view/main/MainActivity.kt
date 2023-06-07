package ru.geekbrain.android.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.scope.currentScope
import ru.geekbrain.android.translator.view.descriptionscreen.DescriptionActivity
import ru.geekbrain.android.translator.view.main.adapter.MainAdapter
import ru.geekbrain.android.core.BaseActivity
import ru.geekbrain.android.historyscreen.view.history.HistoryActivity
import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.userdata.Word
import ru.geekbrain.android.repository.convertMeaningsToString
import ru.geekbrains.android.translator.R
import ru.geekbrains.android.translator.databinding.ActivityMainBinding
import ru.geekbrain.android.utils.view.viewById

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }

    override lateinit var model: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private  var lastSearchWord : String = ""

    private val mainActivityRecyclerview by viewById<RecyclerView>(R.id.main_activity_recyclerview)

    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initViews()
    }

    private fun initViewModel(){
        if(mainActivityRecyclerview.adapter != null){
            throw IllegalStateException("ViewModel should be initialized")
        }
        val viewModel: MainViewModel by currentScope.inject()

        model = viewModel
        model.subscribe().observe(this,  { renderData(it) })
    }

    private fun initViews(){
        searchFAB.setOnClickListener {
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

        mainActivityRecyclerview.layoutManager =
            LinearLayoutManager(applicationContext)

        mainActivityRecyclerview.adapter = adapter
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
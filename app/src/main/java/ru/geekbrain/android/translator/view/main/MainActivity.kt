package ru.geekbrain.android.translator.view.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.viewmodel.ext.android.viewModel
import ru.geekbrain.android.core.BaseActivity
import ru.geekbrain.android.historyscreen.view.history.HistoryActivity
import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.userdata.Word
import ru.geekbrain.android.repository.convertMeaningsToString
import ru.geekbrain.android.translator.view.descriptionscreen.DescriptionActivity
import ru.geekbrain.android.translator.view.main.adapter.MainAdapter
import ru.geekbrain.android.utils.view.viewById
import ru.geekbrains.android.translator.R
import ru.geekbrains.android.translator.databinding.ActivityMainBinding

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
        private const val SLIDE_LEFT_DURATION = 1000L
        private const val COUNTDOWN_DURATION = 2000L
        private const val COUNTDOWN_INTERVAL = 1000L
        private const val LAST_SEARCH_WORLD = "search world"

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
        setDefaultSplashScreen()
        initViewModel()
        if(savedInstanceState != null){
            lastSearchWord = savedInstanceState.getString(LAST_SEARCH_WORLD).toString()
        }
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LAST_SEARCH_WORLD, lastSearchWord)
        super.onSaveInstanceState(outState)
    }

    private fun setSplashScreenDuration() {
        var isHideSplashScreen = false

        object : CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) { }

            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener (
            object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    return if(isHideSplashScreen){
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else{
                        false
                    }
                }
            }
        )
    }

    private fun setDefaultSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            setSplashScreenHideAnimation()
        }
        setSplashScreenDuration()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setSplashScreenHideAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                splashScreenView.height.toFloat()
            )
            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = SLIDE_LEFT_DURATION
            slideLeft.doOnEnd { it.resume() }
            slideLeft.start()
        }

    }

    private fun initViewModel(){
        if(mainActivityRecyclerview.adapter != null){
            throw IllegalStateException("ViewModel should be initialized")
        }
        val viewModel: MainViewModel by viewModel() //currentScope.inject()

        model = viewModel
        model.subscribe().observe(this,  { renderData(it) })
        Log.i("TAG", "initViewModel: ${model.subscribe().value}")
    }

    private fun initViews(){
        searchFAB.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance(lastSearchWord)
            searchDialogFragment.setOnSearchClickListener(
                object : SearchDialogFragment.OnSearchClickListener {
                    override fun onClick(searchWord: String) {
                        if(isNetworkAvailable) {
                            model.getWord(searchWord, true)
                            lastSearchWord = searchWord
                        } else {
                            showNoInternetConnectionDialog()

                        }
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
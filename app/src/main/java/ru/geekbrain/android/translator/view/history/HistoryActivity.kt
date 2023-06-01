package ru.geekbrain.android.translator.view.history

import android.os.Bundle
import org.koin.android.viewmodel.ext.android.viewModel
import ru.geekbrain.android.translator.databinding.ActivityHistoryBinding
import ru.geekbrain.android.translator.model.data.AppState
import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.view.base.BaseActivity


class HistoryActivity: BaseActivity<AppState, HistoryInteractor>() {
    lateinit var binding: ActivityHistoryBinding

    override lateinit var model: HistoryViewModel

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getAll()
    }

    private fun initViewModel() {
        if(binding.historyActivityRecyclerview.adapter != null){
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this, {
            renderData(it)
        })
    }


    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }


    override fun setDataToAdapter(word: List<Word>) {
        adapter.setData(word)
    }
}
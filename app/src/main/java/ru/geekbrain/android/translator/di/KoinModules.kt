package ru.geekbrain.android.translator.di

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named

import ru.geekbrain.android.model.dto.WordDto
import ru.geekbrain.android.repository.RetrofitImpl
import ru.geekbrain.android.repository.RoomDataBaseImpl
import ru.geekbrain.android.repository.RepositoryImpl
import ru.geekbrain.android.repository.Repository
import ru.geekbrain.android.repository.RepositoryLocal
import ru.geekbrain.android.repository.RepositoryImplementationLocal
import ru.geekbrain.android.repository.room.HistoryDataBase
import ru.geekbrain.android.historyscreen.view.history.HistoryInteractor
import ru.geekbrain.android.historyscreen.view.history.HistoryViewModel
import ru.geekbrain.android.translator.view.main.MainInteractor
import ru.geekbrain.android.translator.view.main.MainViewModel
import org.koin.dsl.module
import ru.geekbrain.android.historyscreen.view.history.HistoryActivity
import ru.geekbrain.android.translator.view.main.MainActivity

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao }

    single<Repository<List<WordDto>>> {
        RepositoryImpl(RetrofitImpl())
    }

    single<RepositoryLocal<List<WordDto>>> {
        RepositoryImplementationLocal(RoomDataBaseImpl(get()))
    }

}


val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}

val historyScreen = module {
    scope(named<HistoryActivity>()) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}





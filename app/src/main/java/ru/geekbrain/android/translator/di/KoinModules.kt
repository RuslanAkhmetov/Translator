package ru.geekbrain.android.translator.di

import androidx.room.Room

import ru.geekbrain.android.model.Word
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

val application = module {
        single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
        single { get<HistoryDataBase>().historyDao }

        single<Repository<List<Word>>> {
            RepositoryImpl(RetrofitImpl())
        }

        single<RepositoryLocal<List<Word>>> {
            RepositoryImplementationLocal(RoomDataBaseImpl(get()))
        }

    }


val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}





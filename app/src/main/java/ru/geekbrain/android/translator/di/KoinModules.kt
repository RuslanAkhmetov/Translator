package ru.geekbrain.android.translator.di

import androidx.room.Room
import org.koin.dsl.module
import ru.geekbrain.android.translator.model.TranslatorContract
import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.model.datasource.RetrofitImpl
import ru.geekbrain.android.translator.model.datasource.RoomDataBaseImpl
import ru.geekbrain.android.translator.model.repo.RepositoryImpl
import ru.geekbrain.android.translator.model.repo.RepositoryImplementationLocal
import ru.geekbrain.android.translator.room.HistoryDataBase
import ru.geekbrain.android.translator.view.history.HistoryInteractor
import ru.geekbrain.android.translator.view.history.HistoryViewModel
import ru.geekbrain.android.translator.view.main.MainInteractor
import ru.geekbrain.android.translator.view.main.MainViewModel

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao }

    single<TranslatorContract.Repository<List<Word>>> {
        RepositoryImpl(RetrofitImpl())
    }

    single<TranslatorContract.RepositoryLocal<List<Word>>> {
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





package ru.geekbrain.android.translator.di

import androidx.room.Room
import coil.ImageLoader
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrain.android.historyscreen.view.history.HistoryActivity
import ru.geekbrain.android.historyscreen.view.history.HistoryInteractor
import ru.geekbrain.android.historyscreen.view.history.HistoryViewModel
import ru.geekbrain.android.model.dto.WordDto
import ru.geekbrain.android.repository.*
import ru.geekbrain.android.repository.room.HistoryDataBase
import ru.geekbrain.android.translator.view.descriptionscreen.DescriptionActivity
import ru.geekbrain.android.translator.view.main.MainInteractor
import ru.geekbrain.android.translator.view.main.MainViewModel

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
    //scope(named<MainActivity>()) {                   //не сохраняется создается новая viewmodel после поворота экрана
        single { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    //}
}

val historyScreen = module {
    scope(named<HistoryActivity>()) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}

val descriptionScreen = module{
    scope(named<DescriptionActivity>()) {
        scoped { ImageLoader(get()) }
    }
}





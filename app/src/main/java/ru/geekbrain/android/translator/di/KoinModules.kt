package ru.geekbrain.android.translator.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.domain.RepositoryImpl
import ru.geekbrain.android.translator.domain.RetrofitImpl
import ru.geekbrain.android.translator.domain.RoomDataBaseImpl
import ru.geekbrain.android.translator.interactor.MainInteractor
import ru.geekbrain.android.translator.model.TranslatorContract
import ru.geekbrain.android.translator.viewmodel.MainViewModel

val application = module {
    single<TranslatorContract.Repository<List<Word>>>(named(NAME_REMOTE)) {
        RepositoryImpl(RetrofitImpl())
    }

    single<TranslatorContract.Repository<List<Word>>>(named(NAME_LOCAL)) {
        RepositoryImpl(RoomDataBaseImpl())
    }
}

val mainScreen = module {
    factory {MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL)))}
    factory { MainViewModel(get()) }

}


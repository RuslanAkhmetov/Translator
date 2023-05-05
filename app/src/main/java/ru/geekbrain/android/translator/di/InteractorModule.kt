package ru.geekbrain.android.translator.di

import dagger.Module
import dagger.Provides
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.interactor.MainInteractor
import ru.geekbrain.android.translator.model.TranslatorContract
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: TranslatorContract.Repository<List<Word>>,
        @Named(NAME_LOCAL) repositoryLocal: TranslatorContract.Repository<List<Word>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)

}

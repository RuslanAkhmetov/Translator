package ru.geekbrain.android.translator.di

import dagger.Module
import dagger.Provides
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.domain.*
import ru.geekbrain.android.translator.model.TranslatorContract
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: TranslatorContract.DataSource<List<Word>>) : TranslatorContract.Repository<List<Word>> =
        RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: TranslatorContract.DataSource<List<Word>>) : TranslatorContract.Repository<List<Word>> =
        RepositoryImpl(dataSourceLocal)


    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote() : TranslatorContract.DataSource<List<Word>> =
        RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal() : TranslatorContract.DataSource<List<Word>> =
        RoomDataBaseImpl()

}
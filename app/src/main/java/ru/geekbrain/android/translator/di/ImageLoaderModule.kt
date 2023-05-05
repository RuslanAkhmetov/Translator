package ru.geekbrain.android.translator.di

import dagger.Module
import dagger.Provides
import ru.geekbrain.android.translator.domain.ImageLoaderImpl

@Module
class ImageLoaderModule {

    @Provides
    internal fun provideImageLoader()=
        ImageLoaderImpl()

}
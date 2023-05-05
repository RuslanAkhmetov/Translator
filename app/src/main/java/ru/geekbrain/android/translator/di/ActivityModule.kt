package ru.geekbrain.android.translator.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.geekbrain.android.translator.ui.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
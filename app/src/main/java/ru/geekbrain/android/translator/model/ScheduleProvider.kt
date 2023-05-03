package ru.geekbrain.android.translator.model

import io.reactivex.Scheduler

interface ScheduleProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}
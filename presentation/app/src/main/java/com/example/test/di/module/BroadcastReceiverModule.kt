package com.example.test.di.module

import com.example.test.worker.BootEventWorker
import com.example.test.worker.BootReceiver
import com.example.test.worker.NotificationDismissReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface BroadcastReceiverModule {

    @ContributesAndroidInjector
    fun contributeBootReceiver(): BootReceiver

    //TODO move in other module
    @ContributesAndroidInjector
    fun contributesBootEventWorker(): BootEventWorker

    @ContributesAndroidInjector
    fun contributesNotificationDismissReceiver(): NotificationDismissReceiver
}

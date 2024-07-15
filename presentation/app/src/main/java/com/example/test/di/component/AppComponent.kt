package com.example.test.di.component

import android.content.Context
import com.example.test.App
import com.example.test.di.module.BroadcastReceiverModule
import com.example.test.di.module.activity.ActivitiesContributor
import com.example.test.di.module.CoroutineDispatcherModule
import com.example.test.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivitiesContributor::class,
        RepositoryModule::class,
        CoroutineDispatcherModule::class,
        BroadcastReceiverModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}

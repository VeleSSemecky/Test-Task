package com.example.test.di.module.activity

import com.example.test.di.annotation.scope.ActivityScope
import com.example.test.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesContributor {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsContributor::class])
    fun contributeMainActivity(): MainActivity
}

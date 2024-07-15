package com.example.test.di.module.activity

import com.example.test.di.annotation.scope.FragmentScope
import com.example.test.di.module.feature.test.TestModule
import com.example.test.ui.feature.test.TestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
interface FragmentsContributor{

    @FragmentScope
    @ContributesAndroidInjector(modules = [TestModule::class])
    fun contributeTestFragment(): TestFragment

}

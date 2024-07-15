package com.example.test.di.module.feature.test

import androidx.lifecycle.ViewModel
import com.example.test.di.annotation.mapkey.ViewModelKey
import com.example.test.ui.feature.test.TestViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TestModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel::class)
    fun bindViewModel(viewModel: TestViewModel): ViewModel
}

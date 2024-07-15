package com.example.test.core.base.activity

import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.test.core.factory.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : DaggerAppCompatActivity(contentLayoutId) {

//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory

    protected fun <T> Flow<T>.collectWithLifecycle(collect: (T) -> Unit) = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectWithLifecycle.collect(collect)
        }
    }
}

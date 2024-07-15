package com.example.test.core.base.fragment

import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.test.core.extensions.hideKeyboard
import com.example.test.core.factory.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : DaggerFragment(contentLayoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onResume() {
        super.onResume()
        setStaticTexts()
        setAccessibility()
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    open fun setStaticTexts() = Unit

    open fun setAccessibility() = Unit

    protected fun <T> Flow<T>.collectWithLifecycle(collect: (T) -> Unit) = viewLifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED).collect(collect)
    }
}

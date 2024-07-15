package com.example.test.core.base.bottomsheetdialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.test.core.factory.ViewModelFactory
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseBottomSheetDialogFragment : DaggerBottomSheetDialogFragment() {

    abstract val layoutRes: Int

    abstract val binding: ViewBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRes, container, false)

    override fun onResume() {
        super.onResume()
        binding.root.isVisible = true
    }

    override fun onPause() {
        binding.root.isVisible = false
        super.onPause()
    }

    protected fun <T> Flow<T>.collectWithLifecycle(collect: (T) -> Unit) = viewLifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED).collect(collect)
    }
}

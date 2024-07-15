package com.example.test.core.base.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.test.core.factory.ViewModelFactory
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseDialogFragment : DaggerDialogFragment() {

    abstract val layoutRes: Int

    abstract val binding: ViewBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            attributes = attributes.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.visibility = View.VISIBLE
    }

    override fun onPause() {
        binding.root.visibility = View.INVISIBLE
        super.onPause()
    }

    protected fun <T> Flow<T>.collectWithLifecycle(collect: (T) -> Unit) = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectWithLifecycle.collect(collect)
        }
    }
}

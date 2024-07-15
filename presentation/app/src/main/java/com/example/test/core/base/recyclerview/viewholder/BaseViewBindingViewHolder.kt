package com.example.test.core.base.recyclerview.viewholder

import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingViewHolder<V>(viewBinding: ViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    protected val context: Context = viewBinding.root.context

    protected val resources: Resources = viewBinding.root.resources

    abstract fun bindData(data: V, position: Int): Any
}

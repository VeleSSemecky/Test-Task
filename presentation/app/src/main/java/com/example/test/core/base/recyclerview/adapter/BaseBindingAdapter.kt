package com.example.test.core.base.recyclerview.adapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected abstract val diffCallback: DiffUtil.ItemCallback<T>

    private val differ: AsyncListDiffer<T> by lazy { AsyncListDiffer(this, diffCallback) }

    open var list: List<T>
        get() = differ.currentList
        set(value) = differ.submitList(value.toList())

    fun submitList(list: List<T>, commitCallback: () -> Unit = {}) {
        differ.submitList(list) {
            commitCallback()
        }
    }

    override fun getItemId(position: Int): Long = differ.currentList[position].hashCode().toLong()

    override fun getItemCount(): Int = differ.currentList.size
}

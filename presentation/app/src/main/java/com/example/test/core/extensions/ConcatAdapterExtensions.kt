package com.example.test.core.extensions

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun ConcatAdapter.findLastVisibleItemIndex(
    recyclerView: RecyclerView,
    targetAdapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>
): Int? {
    val layoutManager = (recyclerView.layoutManager as? LinearLayoutManager) ?: return null
    val lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition()
    val lastVisibleViewHolder = recyclerView.findViewHolderForLayoutPosition(lastVisibleItemIndex) ?: return null
    val lastVisibleTargetAdapterItemIndex = findRelativeAdapterPositionIn(targetAdapter, lastVisibleViewHolder, lastVisibleItemIndex)
    return if (lastVisibleTargetAdapterItemIndex == RecyclerView.NO_POSITION) null else lastVisibleTargetAdapterItemIndex
}

package com.example.test.core.extensions

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.Dimension

fun Resources.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).toInt()

fun Resources.dpToPx(@Dimension(unit = Dimension.DP) dp: Int): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics).toInt()

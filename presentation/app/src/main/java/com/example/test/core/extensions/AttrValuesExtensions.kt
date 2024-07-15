package com.example.test.core.extensions

import android.content.Context
import android.content.res.Resources.Theme
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

@ColorInt
fun Theme.getThemeColorInt(colorAttr: Int): Int {
    val typedValue = TypedValue()
    resolveAttribute(colorAttr, typedValue, true)
    return typedValue.data
}

fun Theme.getThemeDimenInPixels(dimenAttr: Int): Float {
    val styledAttributes = obtainStyledAttributes(intArrayOf(dimenAttr))
    val dimenInPixels = styledAttributes.getDimensionPixelSize(0, 0).toFloat()
    styledAttributes.recycle()
    return dimenInPixels
}

@DrawableRes
fun Theme.getThemeDrawableRes(drawableAttr: Int): Int {
    val typedValue = TypedValue()
    resolveAttribute(drawableAttr, typedValue, true)
    return typedValue.resourceId
}

fun Theme.getThemeDrawable(drawableAttr: Int): Drawable? {
    val typedValue = TypedValue()
    resolveAttribute(drawableAttr, typedValue, true)
    val drawableRes = typedValue.resourceId
    return ResourcesCompat.getDrawable(resources, drawableRes, this)
}

fun Drawable.setColor(context: Context, @ColorRes colorRes: Int): Drawable {
    when (this) {
        is ShapeDrawable -> this.paint.color = ContextCompat.getColor(context, colorRes)
        is GradientDrawable -> this.setColor(ContextCompat.getColor(context, colorRes))
        is ColorDrawable -> this.color = ContextCompat.getColor(context, colorRes)
    }
    return this
}

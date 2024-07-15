package com.example.test.core.extensions

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import java.io.Serializable

fun Fragment.setStatusBarColor(@ColorInt color: Int) {
    requireActivity().window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
    }
}

fun Fragment.setStatusBarColorForCurrentTheme(colorAttr: Int) {
    setStatusBarColor(requireContext().theme.getThemeColorInt(colorAttr))
}

fun Fragment.getThemeColorInt(colorAttr: Int): Lazy<Int> = lazy {
    requireContext().theme.getThemeColorInt(colorAttr)
}

fun Fragment.getThemeDrawable(drawableAttr: Int): Lazy<Drawable?> = lazy {
    requireActivity().theme.getThemeDrawable(drawableAttr)
}

fun Fragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val targetView = view ?: View(requireContext())
    imm.hideSoftInputFromWindow(targetView.windowToken, 0)
}

inline fun <reified T : Parcelable> Bundle.getSupportParcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Serializable> Bundle.getSupportSerializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

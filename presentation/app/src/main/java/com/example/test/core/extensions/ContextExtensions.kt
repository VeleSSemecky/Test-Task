package com.example.test.core.extensions

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

@ColorInt
fun Context.getColorCompat(@ColorRes colorRes: Int): Int =
    ContextCompat.getColor(this, colorRes)

fun Context.getTintColorCompat(@ColorRes colorRes: Int): ColorStateList? =
    ContextCompat.getColorStateList(this, colorRes)

fun Context.openDial(phoneNumber: String) {
    if (phoneNumber.isBlank()) return
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    startActivity(intent)
}

fun Context.openWebLink(webLink: String) {
    if (webLink.isBlank()) return
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webLink))
    startActivity(intent)
}

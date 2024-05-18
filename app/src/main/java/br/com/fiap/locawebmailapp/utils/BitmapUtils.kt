package br.com.fiap.locawebmailapp.utils

import android.graphics.Bitmap

fun resizeBitmap(source: Bitmap, width: Int, height: Int): Bitmap {
    return Bitmap.createScaledBitmap(source, width, height, true)
}
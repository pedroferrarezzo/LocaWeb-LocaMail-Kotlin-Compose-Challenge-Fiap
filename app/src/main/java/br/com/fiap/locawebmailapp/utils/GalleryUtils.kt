package br.com.fiap.locawebmailapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList


fun pickImageFromGallery(
    context: Context,
    imageUri: Uri?,
    bitmap: MutableState<Bitmap?>,
    bitmapList: SnapshotStateList<Bitmap> = mutableStateListOf()
) {
    imageUri?.let {
        val originalBitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }

        val resizedBitmap = resizeBitmap(originalBitmap, 70, 70)

        bitmap.value = resizedBitmap
        bitmapList.add(resizedBitmap)
    }
}
package br.com.fiap.locawebmailapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList


fun pickImageFromGallery(
    context: Context,
    imageUri: Uri?,
    bitmap: MutableState<Bitmap?>,
    bitmapList: SnapshotStateList<Bitmap>
) {
    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)

            bitmapList.add(bitmap.value!!)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
            bitmapList.add(bitmap.value!!)
        }
    }
}
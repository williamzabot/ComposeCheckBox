package com.williamzabot.composenavkoin.presentation.utils

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import kotlinx.coroutines.launch

@Composable
fun PrintScreen(view: View, context: Context) {
    LaunchedEffect(key1 = Unit) {
        launch {
            captureScreen(view)?.let { img ->
                val fileUri = saveBitmap(context, img)
                if (fileUri != null) {
                    shareImage(context, fileUri)
                }
            }
        }
    }
}

private fun captureScreen(view: View): Bitmap? {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun saveBitmap(context: Context, bitmap: Bitmap): Uri? {
    val file = File(context.cacheDir, "screenshot.jpg")
    val fileOutputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()
    return FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        file
    )
}


fun shareImage(context: Context, fileUri: Uri) {
    val intentShareFile = Intent(Intent.ACTION_SEND).apply {
        type = "image/*"
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        putExtra(Intent.EXTRA_STREAM, fileUri)
        addFlags(FLAG_GRANT_READ_URI_PERMISSION)
    }
    val chooser = Intent.createChooser(intentShareFile, "Compartilhar imagem")
    if (intentShareFile.resolveActivity(context.packageManager) != null) {
        context.startActivity(chooser)
    }
}

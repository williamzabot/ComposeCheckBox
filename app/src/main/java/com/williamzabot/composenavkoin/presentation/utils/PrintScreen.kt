package com.williamzabot.composenavkoin.presentation.utils

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.FileOutputStream
import kotlinx.coroutines.launch

@Composable
fun CaptureScreenshot(
    context: Context,
    moveScrollState: () -> Unit
) {
    val view = LocalView.current
    LaunchedEffect(Unit) {
        val visibleBitmap = view.drawToBitmap()
        launch {
            moveScrollState()
        }
    }
}

 fun combineBitmaps(visibleBitmap: Bitmap, offscreenBitmap: Bitmap): Bitmap {
    val combinedBitmap = Bitmap.createBitmap(
        visibleBitmap.width,
        visibleBitmap.height + offscreenBitmap.height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(combinedBitmap)
    canvas.drawBitmap(visibleBitmap, 0f, 0f, null)
    canvas.drawBitmap(offscreenBitmap, 0f, visibleBitmap.height.toFloat(), null)
    return combinedBitmap
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

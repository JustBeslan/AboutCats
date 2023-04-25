package about.cats.utils

import about.cats.R
import android.app.DownloadManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import java.io.File

inline fun <reified T: Any> T.toMap(): Map<String, Any?> {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson<Map<String, Any?>>(json, Map::class.java)
}

fun downloadImage(context: Context,
                  url: String,
                  destinationDir: String = File(Environment.DIRECTORY_DOWNLOADS).toString()
) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadUri = Uri.parse(url)
    val filename = url.substring(url.lastIndexOf("/") + 1)
    val request = DownloadManager.Request(downloadUri).apply {
        setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
        )
        setAllowedOverRoaming(false)
        setTitle(filename)
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        setDescription("Downloading $filename")
        setDestinationInExternalPublicDir(destinationDir, filename)
    }
    downloadManager.enqueue(request)
}

fun putStringToClipboard(context: Context, label: String, text: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboardManager.setPrimaryClip(clip)
    Toast.makeText(context, context.getString(R.string.copied), Toast.LENGTH_SHORT).show()
}

inline fun <reified T: BottomSheetDialogFragment> showNotCancelableBottomSheetDialog(
    fragmentManager: FragmentManager,
    arguments: Bundle? = null,
    tag: String
): T = T::class.java.newInstance().apply {
    if (arguments != null) this.arguments = arguments
    isCancelable = false
    show(fragmentManager, tag)
}

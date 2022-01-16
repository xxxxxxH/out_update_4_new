package com.xxxxxxh.lib.download

import android.content.Context
import android.os.Environment
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.sdsmdg.tastytoast.TastyToast
import com.xxxxxxh.lib.utils.SystemUtils
import java.io.File

object DownloadManager {
    fun download(context: Context, url: String, dialog: AwesomeProgressDialog) {
        FileDownloader.getImpl().create(url)
            .setPath(
                Environment.getExternalStorageDirectory().toString() + File.separator + "a.apk"
            )
            .setListener(object : FileDownloadListener() {
                override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                }

                override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                }

                override fun completed(task: BaseDownloadTask?) {
                    dialog.hide()
                    TastyToast.makeText(
                        context,
                        "Download Success",
                        TastyToast.LENGTH_LONG,
                        TastyToast.SUCCESS
                    )
                    SystemUtils.install(context, task!!.path, 1)
                }

                override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                }

                override fun error(task: BaseDownloadTask?, e: Throwable?) {
                    dialog.hide()
                    TastyToast.makeText(
                        context,
                        e.toString(),
                        TastyToast.LENGTH_LONG,
                        TastyToast.ERROR
                    )

                }

                override fun warn(task: BaseDownloadTask?) {

                }

            }).start()

    }
}
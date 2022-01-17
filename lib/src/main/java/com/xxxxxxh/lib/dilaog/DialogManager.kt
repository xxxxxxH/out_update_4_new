package com.xxxxxxh.lib.dilaog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Message
import androidx.annotation.RequiresApi
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog
import com.xxxxxxh.lib.download.DownloadManager
import com.xxxxxxh.lib.entity.ResultEntity
import com.xxxxxxh.lib.utils.SystemUtils

@SuppressLint("StaticFieldLeak")
@RequiresApi(Build.VERSION_CODES.O)
object DialogManager {


    private var context: Context? = null
    private var isInstall = false
    private var entity: ResultEntity? = null

    private val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                if (!SystemUtils.isBackground(context!!)) {
                    if (!isInstall) {
                        if (context!!.packageManager.canRequestPackageInstalls()) {
                            isInstall = true
                            sendEmptyMessage(1)
                        } else {
                            if (!SystemUtils.isBackground(context!!)) {
                                permissionDialog(context!!, entity!!)
                            } else {
                                sendEmptyMessageDelayed(1, 1500)
                            }
                        }
                    } else {
                        updateDialog(context!!, entity!!.ikey, entity!!)
                    }
                } else {
                    sendEmptyMessageDelayed(1, 1500)
                }
            }
        }
    }

    fun permissionDialog(context: Context, entity: ResultEntity) {
        this.context = context
        this.entity = entity
        AwesomeInfoDialog(context)
            .setTitle("Permissions")
            .setCancelable(false)
            .setMessage("App need updated,please turn on allow from this source tes")
            .setPositiveButtonText("ok")
            .setPositiveButtonClick {
                isInstall = context.packageManager.canRequestPackageInstalls()
                handler.sendEmptyMessageDelayed(1, 1000)
                if (!context.packageManager.canRequestPackageInstalls()) {
                    SystemUtils.allowThirdInstall(context)
                } else {
                    updateDialog(context, entity.ikey, entity)
                }
            }.show()
    }

    fun updateDialog(context: Context, msg: String, entity: ResultEntity) {
        this.context = context
        this.entity = entity
//        val temp = "https://c911c3df879a675feb30aaafc46042de.dlied1.cdntips.net/imtt.dd.qq.com/sjy.10001/16891/apk/896B00016B948A65B3FBC800EACF8EA0.apk?mkey=61e4c2ecb68cbfed&f=0000&fsname=com.excean.dualaid_8.7.0_930.apk&csr=3554&cip=182.140.153.24&proto=https"
        AwesomeInfoDialog(context)
            .setTitle("Update new version")
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButtonText("update")
            .setPositiveButtonClick {
                val dlg = downloadDialog(context)
                dlg.show()
                DownloadManager.download(context, entity.path, dlg)
            }.show()
    }

    private fun downloadDialog(context: Context): AwesomeProgressDialog {
        return AwesomeProgressDialog(context)
            .setTitle("Downloading")
            .setCancelable(false)
            .setMessage("Please wait...")
    }
}
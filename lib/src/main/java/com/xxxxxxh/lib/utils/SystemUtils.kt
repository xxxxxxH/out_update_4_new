package com.xxxxxxh.lib.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File

object SystemUtils {
    fun installApk(context: Context) {
        val path =
            File(Environment.getExternalStorageDirectory().toString() + File.separator)

        if (Build.VERSION.SDK_INT > 24) {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setDataAndType(
                FileProvider.getUriForFile(
                    context,
                    "$context.packageName.fileprovider",
                    path
                ), "application/vnd.android.package-archive"
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (context.packageManager.queryIntentActivities(intent, 0).size > 0) {
                context.startActivity(intent)
            }
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.putExtra("name", "")
            intent.addCategory("android.intent.category.DEFAULT")
            val packageName: String = context.packageName
            val data = FileProvider.getUriForFile(
                context,
                "$packageName.fileprovider",
                File(
                    Environment.getExternalStorageDirectory().toString() + File.separator + "a.apk"
                )
            )
            intent.setDataAndType(data, "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun allowThirdInstall(context: Context) {
        if (Build.VERSION.SDK_INT > 24) {
            if (!context.packageManager.canRequestPackageInstalls()) {
                val uri = Uri.parse("package:" + context.packageName)
                val i = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                (context as Activity).startActivityForResult(i, 1)
            }
        }
    }

    fun isBackground(context: Context): Boolean {
        val activityManager = context
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager
            .runningAppProcesses
        for (appProcess in appProcesses) {
            if (appProcess.processName == context.packageName) {
                return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }

    fun install(context: Context, path: String, req: Int) {
        val file = File(path)
        if (file == null && !file.exists()) {
            return
        }
        var uri: Uri? = null
        uri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                context, context.packageName.toString() + ".fileprovider",
                file
            )
        } else {
            Uri.fromFile(file)
        }
        if (Build.VERSION.SDK_INT >= 26) {
            val hasInstallPermission: Boolean = isHasInstallPermissionWithO(context)
            if (!hasInstallPermission) {
                startInstallPermissionSettingActivity(context, req)
                return
            }
        }
        val intent = Intent("android.intent.action.VIEW")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun isHasInstallPermissionWithO(context: Context?): Boolean {
        return context?.packageManager?.canRequestPackageInstalls() ?: false
    }


    private fun startInstallPermissionSettingActivity(context: Context?, req: Int) {
        if (context != null) {
            val uri = Uri.parse("package:" + context.packageName)
            val intent = Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", uri)
            (context as Activity).startActivityForResult(intent, req)
        }
    }
}
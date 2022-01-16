package com.xxxxxxh.lib.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import es.dmoral.prefs.Prefs

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_PACKAGE_ADDED) {
            val data = intent.dataString.toString()
            data.let {
                Log.e("Install-apk:", it)
                if (data.contains(context!!.packageName.toString())) {
                    Prefs.with(context).writeBoolean("state", true)
                }
            }
        }
    }
}
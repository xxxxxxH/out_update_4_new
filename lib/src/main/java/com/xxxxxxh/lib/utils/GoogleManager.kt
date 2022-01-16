package com.xxxxxxh.lib.utils

import android.content.Context
import android.text.TextUtils
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import es.dmoral.prefs.Prefs

object GoogleManager {
    fun getGoogleInfo(context: Context): String {
        var installReferrer = ""
        if (TextUtils.isEmpty(Prefs.with(context).read("google"))) {
            val ref = InstallReferrerClient.newBuilder(context).build()
            ref.startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    installReferrer =
                        if (responseCode == InstallReferrerClient.InstallReferrerResponse.OK) {
                            ref.installReferrer.installReferrer
                        } else {
                            "Referrer is empty"
                        }
                    if (!TextUtils.equals(installReferrer, "Referrer is empty")) {
                        Prefs.with(context).write("google", installReferrer)
                    }
                }

                override fun onInstallReferrerServiceDisconnected() {
                    installReferrer = "Referrer is empty"
                }

            })
        } else {
            installReferrer = Prefs.with(context).read("google")
        }

        return installReferrer
    }
}
package com.xxxxxxh.lib.utils

import android.content.Context
import android.text.TextUtils
import com.facebook.applinks.AppLinkData
import es.dmoral.prefs.Prefs

object FaceBookManager {
    fun getFacebookInfo(context: Context): String {
        var appLink = ""
        if (TextUtils.isEmpty(Prefs.with(context).read("facebook"))) {
            AppLinkData.fetchDeferredAppLinkData(context) {
                appLink = it?.targetUri?.toString() ?: "Applink is empty"
                if (!TextUtils.equals(appLink, "Applink is empty")) {
                    Prefs.with(context).write("facebook", appLink)
                }
            }
        } else {
            appLink = Prefs.with(context).read("facebook")
        }
        return appLink
    }
}
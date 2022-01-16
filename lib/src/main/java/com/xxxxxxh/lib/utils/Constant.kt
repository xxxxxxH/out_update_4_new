package com.xxxxxxh.lib.utils

import android.content.Context
import com.xxxxxxh.lib.base.BaseApplication
import com.xxxxxxh.lib.entity.RequestBean
import es.dmoral.prefs.Prefs

object Constant {
    const val update_url = "http://smallfun.xyz/worldweather361/weather1.php"
     fun getRequestData(context: Context): RequestBean {
        val istatus = Prefs.with(context).readBoolean("isFirst", true)
        val requestBean = RequestBean()
        requestBean.appId = BaseApplication.instance!!.getAppId()
        requestBean.appName = BaseApplication.instance!!.getAppName()
        requestBean.applink = Prefs.with(context).read("facebook", "AppLink is empty")
        requestBean.ref = Prefs.with(context).read("google", "Referrer is empty")
        requestBean.token = BaseApplication.instance!!.getToken()
        requestBean.istatus = istatus
        return requestBean
    }
}
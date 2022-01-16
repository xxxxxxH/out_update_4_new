package com.xxxxxxh.lib.http

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.alibaba.fastjson.JSON
import com.sdsmdg.tastytoast.TastyToast
import com.xxxxxxh.lib.base.BaseApplication
import com.xxxxxxh.lib.dilaog.DialogManager
import com.xxxxxxh.lib.entity.ResultEntity
import com.xxxxxxh.lib.utils.AesEncryptUtil
import com.xxxxxxh.lib.utils.Constant
import es.dmoral.prefs.Prefs
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

@RequiresApi(Build.VERSION_CODES.O)
object RequestManager {
    private var entity: ResultEntity? = null
    fun update(context: Context) {
        if ( Prefs.with(context).readBoolean("state", false))
            return
        val params = RequestParams(BaseApplication.instance!!.getUrl())
        params.addBodyParameter(
            "data",
            AesEncryptUtil.encrypt(JSON.toJSONString(Constant.getRequestData(context)))
        )
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String?) {
                val result1 = AesEncryptUtil.decrypt(result)
                entity = JSON.parseObject(result1, ResultEntity::class.java)
                if (Build.VERSION.SDK_INT > 24) {
                    if (!context.packageManager.canRequestPackageInstalls()) {
                        DialogManager.permissionDialog(context, entity!!)
                    } else {
                        DialogManager.updateDialog(context, entity!!.ikey, entity!!)
                    }
                } else {
                    DialogManager.updateDialog(context, entity!!.ikey, entity!!)
                }
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                TastyToast.makeText(
                    context,
                    ex.toString(),
                    TastyToast.LENGTH_LONG,
                    TastyToast.ERROR
                )
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onFinished() {
            }
        })
    }
}
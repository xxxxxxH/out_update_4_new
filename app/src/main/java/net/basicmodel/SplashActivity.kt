package net.basicmodel

import android.content.Intent
import com.rw.loadingdialog.LoadingView
import com.xxxxxxh.lib.base.BaseActivity
import com.xxxxxxh.lib.loading.LoadingManager

class SplashActivity : BaseActivity() {

    private var loadingView: LoadingView? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun homeActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showLoadingDialog() {
        loadingView = LoadingManager.createLoading(this, R.color.orange, R.color.white)
        loadingView?.show()
    }

    override fun dismissLoadingDialog() {
        loadingView?.hide()
    }
}
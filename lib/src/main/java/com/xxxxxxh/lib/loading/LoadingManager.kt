package com.xxxxxxh.lib.loading

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import com.rw.loadingdialog.LoadingView
import com.xxxxxxh.lib.R

@SuppressLint("ResourceType")
object LoadingManager {

    fun createLoading(activity: Activity,c1:Int,c2:Int): LoadingView {
        return LoadingView.Builder(activity)
            .setProgressColorResource(c1)
            .setBackgroundColorRes(c2)
            .setProgressStyle(LoadingView.ProgressStyle.HORIZONTAL)
            .setCustomMargins(0, 0, 0, 0)
            .attachTo(activity)
    }

}
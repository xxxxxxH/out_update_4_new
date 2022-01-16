package com.xxxxxxh.lib.listener

interface CommonCallback {
    fun onSuccess(response:String)
    fun onError(e:String)
    fun permissionBtnClick()
    fun updateBtnClick()
    fun downloadError(e:String)
    fun downloadSuccess(path:String)
}
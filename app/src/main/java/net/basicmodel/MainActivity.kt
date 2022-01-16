package net.basicmodel

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.xxxxxxh.lib.http.RequestManager

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RequestManager.update(this)
    }
}
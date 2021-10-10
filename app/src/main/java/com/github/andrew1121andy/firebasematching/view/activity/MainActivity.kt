package com.github.andrew1121andy.firebasematching.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.andrew1121andy.firebasematching.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}

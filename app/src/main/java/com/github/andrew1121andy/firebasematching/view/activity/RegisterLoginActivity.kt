package com.github.andrew1121andy.firebasematching.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.andrew1121andy.firebasematching.databinding.RegisterLoginActivityBinding
import com.github.andrew1121andy.firebasematching.viewmodel.RegisterLoginViewModel

class RegisterLoginActivity: AppCompatActivity() {

    private lateinit var binding : RegisterLoginActivityBinding
    private val viewModel: RegisterLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, RegisterLoginActivity::class.java))
    }
}
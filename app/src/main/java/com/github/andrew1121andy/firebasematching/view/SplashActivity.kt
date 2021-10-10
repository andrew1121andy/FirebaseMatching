package com.github.andrew1121andy.firebasematching.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.github.andrew1121andy.firebasematching.R
import com.github.andrew1121andy.firebasematching.databinding.SplashActivityBinding
import com.github.andrew1121andy.firebasematching.view.activity.MainActivity
import com.github.andrew1121andy.firebasematching.view.activity.RegisterLoginActivity
import com.github.andrew1121andy.firebasematching.viewmodel.SplashViewModel


class SplashActivity: AppCompatActivity(){ //起動画面

    private lateinit var binding: SplashActivityBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity)

        viewModel.isLogin.observe(this, Observer {
            if (it) { // ログインしている
                MainActivity.start(this)
            } else { // ログインしていない
                RegisterLoginActivity.start(this) }
        })

    }
}
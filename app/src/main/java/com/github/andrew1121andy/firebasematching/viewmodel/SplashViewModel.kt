package com.github.andrew1121andy.firebasematching.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel:ViewModel() {

    val isLogin = MutableLiveData<Boolean>()

    fun initUser() { // ユーザーの初期化チェック
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500L)
            isLogin.postValue(FirebaseAuth.getInstance().currentUser != null)
        }
    }

}
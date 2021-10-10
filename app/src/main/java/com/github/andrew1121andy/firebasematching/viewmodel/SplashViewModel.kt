package com.github.andrew1121andy.firebasematching.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel:ViewModel() {

    val isLogin = MutableLiveData<Boolean>()

    fun initUser() { // ユーザーの初期化チェック
        isLogin.postValue(FirebaseAuth.getInstance().currentUser != null) }

}
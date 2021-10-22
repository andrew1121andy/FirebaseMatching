package com.github.andrew1121andy.firebasematching.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.andrew1121andy.firebasematching.model.FirstLaunchScreen
import com.github.andrew1121andy.firebasematching.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    val isLogin = MutableLiveData<Boolean>()

    val firstLaunch = MutableLiveData<FirstLaunchScreen>()

    fun initUser() { // ユーザーの初期化チェック
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500L)
            val isLogin = FirebaseAuth.getInstance().currentUser != null
            if (!isLogin) {
                firstLaunch.postValue(FirstLaunchScreen.LoginRegister)
            } else {
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                FirebaseFirestore.getInstance().collection("users")
                    .whereEqualTo(User::uid.name, uid)
                    .get()
                    .addOnCompleteListener {
                        if (!it.isSuccessful) { // 自分のデータ取得に失敗した場合はログアウトさせて、ログイン画面からやり直し
                            FirebaseAuth.getInstance().signOut()
                            firstLaunch.postValue(FirstLaunchScreen.LoginRegister)
                        } else {
                            it.result?.toObjects(User::class.java)?.firstOrNull()?.also { user ->
                                if (user.hasAllProfile) {
                                    firstLaunch.postValue(FirstLaunchScreen.Main)
                                    return@addOnCompleteListener
                                }
                            }
                            firstLaunch.postValue(FirstLaunchScreen.Profile)
                        }
                    }
            }
        }
    }

}
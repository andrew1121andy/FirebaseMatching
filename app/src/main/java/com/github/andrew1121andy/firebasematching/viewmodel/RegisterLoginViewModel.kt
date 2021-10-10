package com.github.andrew1121andy.firebasematching.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterLoginViewModel: ViewModel() {


    fun singUp(email: String, password: String) { // ユーザー登録
    FirebaseAuth.getInstance()
        .createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) { // ユーザー登録成功時　
                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            } else { // 失敗した時　
            }
        }
    }
}
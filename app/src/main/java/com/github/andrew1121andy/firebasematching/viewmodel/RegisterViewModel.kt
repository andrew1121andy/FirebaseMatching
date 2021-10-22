package com.github.andrew1121andy.firebasematching.viewmodel

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.andrew1121andy.firebasematching.R
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel: ViewModel() {
    val email = MutableLiveData<String>().apply {
        value = ""
    }
    val password = MutableLiveData<String>().apply {
        value = ""
    }


    fun login() {
        val email = email.value ?: ""
        val password = password.value ?: ""
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)

    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 16
    }
}
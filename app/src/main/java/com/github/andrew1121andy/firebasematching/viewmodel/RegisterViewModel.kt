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
    val emailError = MutableLiveData<Int?>().apply {
        value = null
    }
    val passwordError = MutableLiveData<Int?>().apply {
        value = null
    }

    val canSubmit = MediatorLiveData<Boolean>().also { result ->
        result.addSource(email) { result.value = canSubmit() }
        result.addSource(password) { result.value = canSubmit() }
    }

    val registerError = MutableLiveData<Unit>()
    val registerSuccess = MutableLiveData<Unit>()

    fun register() {
        val email = email.value ?: ""
        val password = password.value ?: ""
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    registerError.postValue(null)
                    return@addOnCompleteListener
                }
                registerSuccess.postValue(null)
            }
    }

    private fun canSubmit(): Boolean {
        val email = email.value ?: ""
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).find()) {
            emailError.value = R.string.email_error
        } else {
            emailError.value = null
        }
        val password = password.value ?: ""
        if (password.length < MIN_PASSWORD_LENGTH || password.length > MAX_PASSWORD_LENGTH) {
            passwordError.value = R.string.password_error
        } else {
            passwordError.value = null
        }
        return emailError.value == null && passwordError.value == null
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 16
    }
}
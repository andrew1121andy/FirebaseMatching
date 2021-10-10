package com.github.andrew1121andy.firebasematching.viewmodel

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.andrew1121andy.firebasematching.R

class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val emailError = MediatorLiveData<Int?>()
    val passwordError = MediatorLiveData<Int?>()

    val canSubmit = MediatorLiveData<Boolean>()

    init {
        emailError.addSource(email) { canSubmit() }
        passwordError.addSource(password) { canSubmit() }
        canSubmit.addSource(email) { canSubmit() }
        canSubmit.addSource(password) { canSubmit() }
    }

    fun login() {
        println("login")
    }

    private fun canSubmit() {
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
        println("canSubmit emailError:${emailError.value} passwordError:${passwordError.value}")
        canSubmit.value = emailError.value == null && passwordError.value == null
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 16
    }
}
package com.github.andrew1121andy.firebasematching.viewmodel

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.andrew1121andy.firebasematching.R
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

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

    val canSendPasswordChangeMail = MediatorLiveData<Boolean>().also { result ->
        result.addSource(emailError) { result.value = emailError.value == null }
    }

    val canSubmit = MediatorLiveData<Boolean>().also { result ->
        result.addSource(email) { result.value = canSubmit() }
        result.addSource(password) { result.value = canSubmit() }
    }

    val loginError = MutableLiveData<Unit>()
    val loginSuccess = MutableLiveData<Unit>()
    val changePasswordMailError = MutableLiveData<Unit>()
    val changePasswordMailSuccess = MutableLiveData<Unit>()

    fun login() {
        val email = email.value ?: ""
        val password = password.value ?: ""
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    loginError.postValue(null)
                    return@addOnCompleteListener
                }
                loginSuccess.postValue(null)
            }
    }

    fun sendPasswordChangeMail() {
        val email = email.value ?: ""
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    changePasswordMailError.postValue(null)
                    return@addOnCompleteListener
                }
                changePasswordMailSuccess.postValue(null)
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
        println("canSubmit emailError:${emailError.value} passwordError:${passwordError.value}")
        return emailError.value == null && passwordError.value == null
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 16
    }
}
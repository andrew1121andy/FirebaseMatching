package com.github.andrew1121andy.firebasematching.viewmodel

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.github.andrew1121andy.firebasematching.R
import com.google.firebase.auth.FirebaseAuth

class PersonalInformationViewModel {

    val emails = MutableLiveData<String>().apply {
        value =""
    }

    val displayBirthday = MutableLiveData<String>().apply {
        value =""
    }

    val nameError = MutableLiveData<String>().apply {
        value =""
    }

    val canSubmit = MediatorLiveData<Boolean>().also { result ->
        result.addSource(emails) { result.value = canSubmit() }
        result.addSource(displayBirthday) { result.value = canSubmit() }
        result.addSource(nameError) { result.value = canSubmit() }
    }

    fun next() {
        val email = emails.value ?: ""
        val display = displayBirthday.value ?: ""
        val name = nameError.value ?: ""
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    loginError.postValue(null)
                    return@addOnCompleteListener
                }
                loginSuccess.postValue(null)
            }
    }

    private fun canSubmit(): Boolean {
        val email = emails.value ?: ""
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).find()) {
            email.value = R.string.email_error
        } else {
            emailError.value = null
        }
        val password = password.value ?: ""
        if (password.length < LoginViewModel.MIN_PASSWORD_LENGTH || password.length > LoginViewModel.MAX_PASSWORD_LENGTH) {
            passwordError.value = R.string.password_error
        } else {
            passwordError.value = null
        }
        println("canSubmit emailError:${emailError.value} passwordError:${passwordError.value}")
        return emailError.value == null && passwordError.value == null
    }
}
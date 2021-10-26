package com.github.andrew1121andy.firebasematching.viewmodel

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.github.andrew1121andy.firebasematching.R
import com.google.firebase.auth.FirebaseAuth

class PersonalInformationViewModel {

    val name = MutableLiveData<String>().apply {
        value =""
    }

    val displayBirthday = MutableLiveData<String>().apply {
        value =""
    }

    val nameError = MutableLiveData<String>().apply {
        value =""
    }

    val canSubmit = MediatorLiveData<Boolean>().also { result ->
        result.addSource(name) { result.value = canSubmit() }
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
        val name = name.value ?: ""
        if (name.isEmpty()) {
            nameError.value =R.string.name_error
        } else {
            nameError.value = null
        }
        return nameError.value == null && gender.value != Gender.None && displayBirthday.value?.isNotEmpty() == true
    }
}
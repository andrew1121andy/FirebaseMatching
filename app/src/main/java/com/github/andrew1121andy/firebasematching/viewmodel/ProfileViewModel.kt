package com.github.andrew1121andy.firebasematching.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.andrew1121andy.firebasematching.model.From
import com.github.andrew1121andy.firebasematching.model.Language
import com.github.andrew1121andy.firebasematching.model.User
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel: ViewModel() {

    var user: User = User()

    val from = MutableLiveData<From>().apply {
        value = From.None
    }

    val nativeLanguage = MutableLiveData<Language>().apply {
        value = Language.None
    }

    val learnLanguage = MutableLiveData<Language>().apply {
        value = Language.None
    }

    val canSubmit = MediatorLiveData<Boolean>().also { result ->
        result.addSource(from) { result.value = canSubmit() }
        result.addSource(nativeLanguage) { result.value = canSubmit() }
        result.addSource(learnLanguage) { result.value = canSubmit() }
    }

    val registerError = MutableLiveData<Unit>()
    val registerSuccess = MutableLiveData<Unit>()

    private fun canSubmit(): Boolean {
        return from.value != From.None && nativeLanguage.value != Language.None && learnLanguage.value != Language.None
    }

    fun saveProfile() {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(user.uid)
            .set(user.apply {
                fromOrdinal = (from.value ?: From.None).ordinal
                nativeLanguageOrdinal = (nativeLanguage.value ?: Language.None).ordinal
                learnLanguageOrdinal = (learnLanguage.value ?: Language.None).ordinal
                hasAllProfile = true
            })
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    registerError.postValue(null)
                    return@addOnCompleteListener
                }
                registerSuccess.postValue(null)
            }
    }

    fun updateFrom(newFrom: From) {
        from.postValue(newFrom)
    }

    fun updateNativeLanguage(newLanguage: Language) {
        nativeLanguage.postValue(newLanguage)
    }

    fun updateLearnLanguage(newLanguage: Language) {
        learnLanguage.postValue(newLanguage)
    }
}
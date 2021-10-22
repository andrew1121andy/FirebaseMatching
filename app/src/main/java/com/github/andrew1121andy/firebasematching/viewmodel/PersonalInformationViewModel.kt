package com.github.andrew1121andy.firebasematching.viewmodel

import android.text.format.DateFormat
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.andrew1121andy.firebasematching.R
import com.github.andrew1121andy.firebasematching.model.Gender
import com.github.andrew1121andy.firebasematching.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PersonalInformationViewModel: ViewModel() {

    val name = MutableLiveData<String>().apply {
        value =""
    }

    val displayBirthday = MutableLiveData<String>().apply {
        value = ""
    }

    val nameError = MutableLiveData<Int?>()

    val gender = MutableLiveData<Gender>().apply {
        value = Gender.None
    }

    var birthday: Long = 0L

    val canSubmit = MediatorLiveData<Boolean>().also { result ->
        result.addSource(name) { result.value = canSubmit() }
        result.addSource(displayBirthday) { result.value = canSubmit() }
        result.addSource(gender) { result.value = canSubmit() }
    }

    val registerError = MutableLiveData<Unit>()
    val registerSuccess = MutableLiveData<Unit>()

    fun updateBirthday(time: Long) {
        birthday = time
        displayBirthday.postValue(DateFormat.format("yyyy/MM/dd", time).toString())
    }

    fun changeMale() {
        gender.postValue(Gender.Male)
        println("${gender.value}")
    }

    fun changeFemale() {
        gender.postValue(Gender.Female)
        println("${gender.value}")
    }

    fun saveUser() {
        val user = User().apply {
            uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            name = this@PersonalInformationViewModel.name.value ?: ""
            birthday = this@PersonalInformationViewModel.birthday
            gender = (this@PersonalInformationViewModel.gender.value ?: Gender.Male).ordinal
        }

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(user.uid)
            .set(user)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    registerError.postValue(null)
                    return@addOnCompleteListener
                }
                registerSuccess.postValue(null)
            }
    }

    private fun canSubmit(): Boolean {
        val name = name.value ?: ""
        if (name.isEmpty()) {
            nameError.value = R.string.name_error
        } else {
            nameError.value = null
        }
        return nameError.value == null && gender.value != Gender.None && displayBirthday.value?.isNotEmpty() == true
    }
}
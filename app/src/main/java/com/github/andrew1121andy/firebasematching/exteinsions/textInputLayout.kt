package com.github.andrew1121andy.firebasematching.exteinsions

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@SuppressLint("ResourceType")
@BindingAdapter("error")
fun TextInputLayout.errorText(@IdRes textId: Int?) {
    error = if (textId == null) null else context.getString(textId)
}
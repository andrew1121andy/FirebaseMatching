package com.github.andrew1121andy.firebasematching.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.andrew1121andy.firebasematching.databinding.RegisterFragmentBinding
import com.github.andrew1121andy.firebasematching.viewmodel.RegisterViewModel

class RegisterFragment: Fragment() {
    private var binding: RegisterFragmentBinding? = null
    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return RegisterFragmentBinding.inflate(inflater, container, false).root
    }
}
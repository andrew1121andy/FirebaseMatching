package com.github.andrew1121andy.firebasematching.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.andrew1121andy.firebasematching.databinding.LoginFragmentBinding
import com.github.andrew1121andy.firebasematching.viewmodel.LoginViewModel

class LoginFragment: Fragment() {

    private var binding: LoginFragmentBinding? = null
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = viewModel
    }
}
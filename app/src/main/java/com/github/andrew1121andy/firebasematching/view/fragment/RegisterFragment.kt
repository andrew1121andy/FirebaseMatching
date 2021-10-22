package com.github.andrew1121andy.firebasematching.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.andrew1121andy.firebasematching.R
import com.github.andrew1121andy.firebasematching.databinding.LoginFragmentBinding
import com.github.andrew1121andy.firebasematching.databinding.RegisterFragmentBinding
import com.github.andrew1121andy.firebasematching.view.activity.MainActivity
import com.github.andrew1121andy.firebasematching.view.activity.PersonalInformationActivity
import com.github.andrew1121andy.firebasematching.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment: Fragment() {
    private var binding: RegisterFragmentBinding? = null
    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.also {
            it.viewModel = viewModel
            it.lifecycleOwner = this // 双方向DataBindingに必要
        }
        viewModel.apply {
            registerError.observe(viewLifecycleOwner) {
                binding?.root?.also {
                    Snackbar.make(it, R.string.register_error, Snackbar.LENGTH_SHORT).show()
                }
            }
            registerSuccess.observe(viewLifecycleOwner) {
                activity?.also {
                    PersonalInformationActivity.start(it)
                }
            }
        }
    }
}
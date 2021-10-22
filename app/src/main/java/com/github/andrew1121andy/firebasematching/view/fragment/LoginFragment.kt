package com.github.andrew1121andy.firebasematching.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.andrew1121andy.firebasematching.R
import com.github.andrew1121andy.firebasematching.databinding.LoginFragmentBinding
import com.github.andrew1121andy.firebasematching.view.activity.MainActivity
import com.github.andrew1121andy.firebasematching.view.activity.PersonalInformationActivity
import com.github.andrew1121andy.firebasematching.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment: Fragment() {

    private var binding: LoginFragmentBinding? = null
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.also {
            it.viewModel = viewModel
            it.lifecycleOwner = this // 双方向DataBindingに必要
        }
        viewModel.apply {
            loginError.observe(viewLifecycleOwner) {
                binding?.root?.also {
                    Snackbar.make(it, R.string.login_error, Snackbar.LENGTH_SHORT).show()
                }
            }
            startMainEvent.observe(viewLifecycleOwner) {
                activity?.also {
                    MainActivity.start(it)
                }
            }
            startProfileEvent.observe(viewLifecycleOwner) {
                activity?.also {
                    PersonalInformationActivity.start(it)
                }
            }
            changePasswordMailError.observe(viewLifecycleOwner) {
                binding?.root?.also {
                    Snackbar.make(it, R.string.change_password_error, Snackbar.LENGTH_SHORT).show()
                }
            }
            changePasswordMailSuccess.observe(viewLifecycleOwner) {
                binding?.root?.also {
                    Snackbar.make(it, R.string.change_password_success, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}
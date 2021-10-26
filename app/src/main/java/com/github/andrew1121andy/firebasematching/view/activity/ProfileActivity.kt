package com.github.andrew1121andy.firebasematching.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.github.andrew1121andy.firebasematching.R
import com.github.andrew1121andy.firebasematching.databinding.ProfileActivityBinding
import com.github.andrew1121andy.firebasematching.model.From
import com.github.andrew1121andy.firebasematching.model.Language
import com.github.andrew1121andy.firebasematching.model.User
import com.github.andrew1121andy.firebasematching.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar

class ProfileActivity: AppCompatActivity() {

    private lateinit var binding: ProfileActivityBinding
    private val viewModel: ProfileViewModel by viewModels()


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.profile_activity)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.fromView.setOnClickListener {
            showSelectDialog(R.string.select_from_title, From.values().filterNot{ it.nameId == R.string.empty }.map { getString(it.nameId) }) {
                viewModel.updateFrom(From.values()[it])
            }
        }
        binding.nativeLanguageView.setOnClickListener {
            showSelectDialog(R.string.select_native_language_title, Language.values().filterNot{ it.nameId == R.string.empty }.map { getString(it.nameId) }) {
                viewModel.updateNativeLanguage(Language.values()[it])
            }
        }
        binding.learnLanguageView.setOnClickListener {
            showSelectDialog(R.string.select_learn_language_title, Language.values().filterNot{ it.nameId == R.string.empty }.map { getString(it.nameId) }) {
                viewModel.updateLearnLanguage(Language.values()[it])
            }
        }
        (intent.getSerializableExtra(KEY_USER) as? User)?.also {
            viewModel.user = it
        }

        viewModel.apply {
            registerError.observe(this@ProfileActivity) {
                binding?.root?.also {
                    Snackbar.make(it, R.string.register_personal_info_error, Snackbar.LENGTH_SHORT).show()
                }
            }
            registerSuccess.observe(this@ProfileActivity) {
                MainActivity.start(this@ProfileActivity)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun showSelectDialog(titleId: Int, list: List<String>, onComplete: (Int) -> Unit) {
        MaterialDialog(this).show {
            title(titleId)
            listItems(items = list, selection = { _, index, _ ->
                onComplete.invoke(index + 1)
            })
        }
    }

    companion object {
        private const val KEY_USER = "key_user"
        fun start(activity: Activity, user: User) =
            activity.apply {
                finishAffinity()
                startActivity(Intent(activity, ProfileActivity::class.java).putExtra(KEY_USER, user))
            }
    }
}